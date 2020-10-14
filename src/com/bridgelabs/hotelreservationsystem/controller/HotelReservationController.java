package com.bridgelabs.hotelreservationsystem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		int regularWeekdayPrice = HotelReservationMain.sc.nextInt();
		HotelReservationMain.LOG.info("Enter regularWeekendPrice: ");
		int regularWeekendPrice = HotelReservationMain.sc.nextInt();
		HotelReservationMain.sc.nextLine();

		Hotel hotel = new Hotel(name, rating, regularWeekdayPrice, regularWeekendPrice);
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
		List<Hotel> cheapestHotelList = getCheapestHotelList(hotelReservation);
		printCheapestHotel(cheapestHotelList);
	}

	// returns the list of cheapest hotels for a given date range
	public List<Hotel> getCheapestHotelList(HotelReservation hotelReservation) {
		List<Hotel> cheapestHotelList = new ArrayList<Hotel>();
		Hotel cheapestHotel = new Hotel();
		cheapestHotel.setTotalPrice(Integer.MAX_VALUE);
		cheapestHotel.setRating(Integer.MIN_VALUE);
		List<Hotel> hotelList = hotelReservation.getHotelList();
		for (Hotel hotel : hotelList) {
			int price = hotel.getRegularWeekdayPrice() * weekDays + hotel.getRegularWeekendPrice() * weekEnds;
			hotel.setTotalPrice(price);
			if ((hotel.getTotalPrice() <= cheapestHotel.getTotalPrice())
					&& (hotel.getRating() > cheapestHotel.getRating())) {
				cheapestHotel.setRating(hotel.getRating());
				cheapestHotel.setTotalPrice(price);
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
	public void determineWeekDaysWeekEnds(String dateStart, String dateEnd) {
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

	// prints cheapest hotel details
	public void printCheapestHotel(List<Hotel> cheapestHotelList) {
		for (Hotel hotel : cheapestHotelList)
			HotelReservationMain.LOG
					.info("Cheapest Hotel for given date range:\nHotel Name: " + hotel.getHotelName() + "\nRating: "
							+ hotel.getRating() + "\nTotal Price for given duration: $" + hotel.getTotalPrice() + "\n");
	}
}
