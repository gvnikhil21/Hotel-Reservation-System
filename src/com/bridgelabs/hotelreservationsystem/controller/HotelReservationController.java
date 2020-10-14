package com.bridgelabs.hotelreservationsystem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bridgelabs.hotelreservationsystem.model.Hotel;
import com.bridgelabs.hotelreservationsystem.model.HotelReservation;

public class HotelReservationController {

	// adds hotel to hotel list
	public void addHotel(HotelReservation hotelReservation) {
		HotelReservationMain.LOG.info("Enter Hotel Name: ");
		String name = HotelReservationMain.sc.nextLine();
		HotelReservationMain.LOG.info("Enter regularWeekdayPrice: ");
		int regularWeekdayPrice = HotelReservationMain.sc.nextInt();
		HotelReservationMain.LOG.info("Enter regularWeekendPrice: ");
		int regularWeekendPrice = HotelReservationMain.sc.nextInt();
		HotelReservationMain.sc.nextLine();

		Hotel hotel = new Hotel(name, regularWeekdayPrice, regularWeekendPrice);
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

	@SuppressWarnings("deprecation")
	public void findCheapestHotel(HotelReservation hotelReservation) {
		SimpleDateFormat format = new SimpleDateFormat("ddMMMyyyy");
		HotelReservationMain.LOG.info("Enter start date (format: ddMMMyyyy): ");
		String dateStart = HotelReservationMain.sc.nextLine();
		HotelReservationMain.LOG.info("Enter end date (format: ddMMMyyyy): ");
		String dateEnd = HotelReservationMain.sc.nextLine();
		Date startDate = null;
		Date endDate = null;
		int weekDays = 0;
		int weekEnds = 0;
		try {
			startDate = format.parse(dateStart);
			endDate = format.parse(dateEnd);
			Date date = startDate;
			while (!date.after(endDate)) {
				if (date.getDay() == 0 || date.getDay() == 6)
					weekEnds++;
				else
					weekDays++;
				date.setTime(date.getTime() + (1000 * 60 * 60 * 24));
			}
			Hotel cheapestHotel = new Hotel();
			cheapestHotel.setTotalPrice(Integer.MAX_VALUE);
			List<Hotel> hotelList = hotelReservation.getHotelList();
			for (Hotel hotel : hotelList) {
				int price = hotel.getRegularWeekdayPrice() * weekDays + hotel.getRegularWeekendPrice() * weekEnds;
				hotel.setTotalPrice(price);
				if (hotel.getTotalPrice() < cheapestHotel.getTotalPrice()) {
					cheapestHotel.setTotalPrice(price);
				}
			}
			HotelReservationMain.LOG.info(
					"Cheapest Hotel for date range " + startDate.getDate() + " to " + endDate.getDate() + " :\n");
			for (Hotel hotel : hotelList) {
				if (cheapestHotel.getTotalPrice() == hotel.getTotalPrice())
					HotelReservationMain.LOG.info("Hotel Name: " + hotel.getHotelName()
							+ "\nTotal Price for given duration: $" + hotel.getTotalPrice() + "\n");
			}
		} catch (ParseException | NullPointerException e) {
			e.printStackTrace();
		}
	}
}
