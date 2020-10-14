package com.bridgelabs.hotelreservationsystem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bridgelabs.hotelreservationsystem.model.Hotel;
import com.bridgelabs.hotelreservationsystem.model.HotelReservation;

public class HotelReservationController {
	private static int weekDays = 0;
	private static int weekEnds = 0;

	// adds hotel to hotel list
	public void addHotel(HotelReservation hotelReservation) {
		HotelReservationMain.LOG.info("Enter Hotel Name: ");
		String name = HotelReservationMain.sc.nextLine();
		HotelReservationMain.LOG.info("Enter Rating (out of 5): ");
		int rating = HotelReservationMain.sc.nextInt();
		HotelReservationMain.sc.nextLine();
		HotelReservationMain.LOG.info("Enter regularWeekdayPrice: ");
		int regularWeekDayPrice = HotelReservationMain.sc.nextInt();
		HotelReservationMain.LOG.info("Enter regularWeekendPrice: ");
		int regularWeekEndPrice = HotelReservationMain.sc.nextInt();
		HotelReservationMain.LOG.info("Enter rewardsWeekdayPrice: ");
		int rewardsWeekDayPrice = HotelReservationMain.sc.nextInt();
		HotelReservationMain.LOG.info("Enter rewardsWeekendPrice: ");
		int rewardsWeekEndPrice = HotelReservationMain.sc.nextInt();
		HotelReservationMain.sc.nextLine();

		Hotel hotel = new Hotel(name, rating, regularWeekDayPrice, regularWeekEndPrice, rewardsWeekDayPrice,
				rewardsWeekEndPrice);
		hotelReservation.addHotel(hotel);
	}

	// displays hotel details present in the list
	public void displayHotel(HotelReservation hotelReservation) {
		List<Hotel> hotelList = hotelReservation.getHotelList();
		if (hotelList == null || hotelList.size() == 0)
			HotelReservationMain.LOG.warning("No Hotel in the list to display");
		else
			for (Hotel hotel : hotelList) {
				HotelReservationMain.LOG.info(hotel.toString());
			}
	}

	// find the cheapest hotel with best rating for a given date range
	public void findCheapestBestRatedHotel(HotelReservation hotelReservation) {
		HotelReservationMain.LOG.info("Enter start date (format: ddMMMyyyy): ");
		String dateStart = HotelReservationMain.sc.nextLine();
		HotelReservationMain.LOG.info("Enter end date (format: ddMMMyyyy): ");
		String dateEnd = HotelReservationMain.sc.nextLine();
		determineWeekDaysWeekEnds(dateStart, dateEnd);
		String customerType = getCustomerType();
		List<Hotel> cheapestHotelList = getCheapestHotelList(hotelReservation, customerType);
		for (Hotel hotel : cheapestHotelList)
			HotelReservationMain.LOG.info("Cheapest Best Rated Hotel for given date range:\nHotel Name: "
					+ hotel.getHotelName() + "\nRating: " + hotel.getRating() + "\nTotal Price for given duration: $"
					+ hotel.getTotalPrice() + "\n");
	}

	// finds the best rated hotel
	public void findBestRatedHotel(HotelReservation hotelReservation) {
		String dateStart = "";
		String dateEnd = "";
		do {
			HotelReservationMain.LOG.info("Enter start date (format: ddMMMyyyy): ");
			dateStart = HotelReservationMain.sc.nextLine();
			HotelReservationMain.LOG.info("Enter end date (format: ddMMMyyyy): ");
			dateEnd = HotelReservationMain.sc.nextLine();
			if (dateStart.matches("[0-9]{1,2}[A-zA-Z]{3}[0-9]{4}") && dateEnd.matches("[0-9]{1,2}[A-zA-Z]{3}[0-9]{4}"))
				break;
			else
				HotelReservationMain.LOG.info("Invalid date formtat! Enter proper date (format: ddMMMyyyy): ");
		} while (true);
		determineWeekDaysWeekEnds(dateStart, dateEnd);
		List<Hotel> bestRatedHotelList = new ArrayList<Hotel>();
		List<Hotel> hotelList = hotelReservation.getHotelList();
		String customerType = getCustomerType();
		int rating = Integer.MIN_VALUE;
		for (Hotel hotel : hotelList) {
			calculateAndSetTotalPrice(hotel, customerType);
			if (hotel.getRating() > rating)
				rating = hotel.getRating();
		}
		for (Hotel hotel : hotelList) {
			if (hotel.getRating() == rating)
				bestRatedHotelList.add(hotel);
		}
		for (Hotel hotel : bestRatedHotelList)
			HotelReservationMain.LOG
					.info("Best Rated Hotel for given date range:\nHotel Name: " + hotel.getHotelName() + "\nRating: "
							+ hotel.getRating() + "\nTotal Price for given duration: $" + hotel.getTotalPrice() + "\n");
	}

	// gets the customer type from the user
	private String getCustomerType() {
		String customerType = "";
		do {
			HotelReservationMain.LOG.info("Enter customer type (Regular or Rewards): ");
			customerType = HotelReservationMain.sc.nextLine();
			if (customerType.matches("(?i)(?:regular|rewards)"))
				break;
			else
				HotelReservationMain.LOG.info("Invalid customer type! Enter proper customer type (Regular or Rewards)");
		} while (true);
		return customerType;
	}

	// calculates total price for a hotel for given duration based on customer-type
	private int calculateAndSetTotalPrice(Hotel hotel, String customerType) {
		int price = 0;
		if (customerType.equalsIgnoreCase("regular"))
			price = hotel.getRegularWeekDayPrice() * weekDays + hotel.getRegularWeekEndPrice() * weekEnds;
		if (customerType.equalsIgnoreCase("rewards"))
			price = hotel.getRewardsWeekDayPrice() * weekDays + hotel.getRewardsWeekEndPrice() * weekEnds;
		hotel.setTotalPrice(price);
		return price;
	}

	// returns the list of best rated cheapest hotels for a given date range
	private List<Hotel> getCheapestHotelList(HotelReservation hotelReservation, String customerType) {
		List<Hotel> cheapestHotelList = new ArrayList<Hotel>();
		Hotel cheapestHotel = new Hotel();
		cheapestHotel.setTotalPrice(Integer.MAX_VALUE);
		cheapestHotel.setRating(Integer.MIN_VALUE);
		List<Hotel> hotelList = hotelReservation.getHotelList();
		for (Hotel hotel : hotelList) {
			calculateAndSetTotalPrice(hotel, customerType);
			if ((hotel.getTotalPrice() <= cheapestHotel.getTotalPrice())
					&& (hotel.getRating() > cheapestHotel.getRating())) {
				cheapestHotel.setRating(hotel.getRating());
				cheapestHotel.setTotalPrice(hotel.getTotalPrice());
			}
		}
		for (Hotel hotel : hotelList) {
			if ((cheapestHotel.getTotalPrice() == hotel.getTotalPrice())
					&& (cheapestHotel.getRating() == hotel.getRating()))
				cheapestHotelList.add(hotel);
		}
		return cheapestHotelList;
	}

	// determines no. of weekEnds and weekDays
	@SuppressWarnings("deprecation")
	private void determineWeekDaysWeekEnds(String dateStart, String dateEnd) {
		SimpleDateFormat format = new SimpleDateFormat("ddMMMyyyy");
		weekDays = 0;
		weekEnds = 0;
		try {
			Date startDate = format.parse(dateStart);
			Date endDate = format.parse(dateEnd);
			Date date = startDate;
			while (!date.after(endDate)) {
				if (date.getDay() == 0 || date.getDay() == 6)
					weekEnds++;
				else
					weekDays++;
				date.setTime(date.getTime() + (1000 * 60 * 60 * 24));
			}
		} catch (ParseException | NullPointerException e) {
			e.printStackTrace();
		}
	}
}
