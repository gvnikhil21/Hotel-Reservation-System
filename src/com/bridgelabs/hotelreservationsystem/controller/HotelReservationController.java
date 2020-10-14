package com.bridgelabs.hotelreservationsystem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
			hotelList.stream().forEach(hotel -> {
				HotelReservationMain.LOG.info(hotel.toString());
			});
	}

	// find the cheapest hotel with best rating for a given date range
	public void findCheapestBestRatedHotel(HotelReservation hotelReservation) {
		getDateRange();
		String customerType = getCustomerType();
		List<Hotel> hotelList = getCheapestHotelList(hotelReservation, customerType);
		hotelList.stream().forEach(hotel -> {
			HotelReservationMain.LOG.info("Cheapest Best Rated Hotel for given date range:\nHotel Name: "
					+ hotel.getHotelName() + "\nRating: " + hotel.getRating() + "\nTotal Price for given duration: $"
					+ hotel.getTotalPrice() + "\n");
		});
	}

	// finds the best rated hotel
	public void findBestRatedHotel(HotelReservation hotelReservation) {
		getDateRange();
		List<Hotel> hotelList = hotelReservation.getHotelList();
		String customerType = getCustomerType();
		int maximumRating = hotelList.stream().mapToInt(Hotel::getRating).max().orElseGet(null);
		List<Hotel> bestRateHotelList = hotelList.stream().filter(hotel -> hotel.getRating() == maximumRating)
				.collect(Collectors.toList());
		bestRateHotelList.stream().forEach(hotel -> {
			calculateAndSetTotalPrice(hotel, customerType);
			HotelReservationMain.LOG
					.info("Best Rated Hotel for given date range:\nHotel Name: " + hotel.getHotelName() + "\nRating: "
							+ hotel.getRating() + "\nTotal Price for given duration: $" + hotel.getTotalPrice() + "\n");
		});
	}

	// get date range from user
	private void getDateRange() {
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
		List<Hotel> hotelList = hotelReservation.getHotelList();
		hotelList.stream().forEach(hotel -> {
			calculateAndSetTotalPrice(hotel, customerType);
		});
		int minimumTotalPrice = hotelList.stream().mapToInt(Hotel::getTotalPrice).min().orElseGet(null);
		List<Hotel> cheapHotelList = hotelList.stream().filter(h -> h.getTotalPrice() == minimumTotalPrice)
				.collect(Collectors.toList());
		int maximumRating = cheapHotelList.stream().mapToInt(Hotel::getRating).max().orElseGet(null);
		cheapHotelList = cheapHotelList.stream().filter(hotel -> hotel.getRating() == maximumRating)
				.collect(Collectors.toList());
		return cheapHotelList;
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
