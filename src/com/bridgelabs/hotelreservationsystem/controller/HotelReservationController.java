package com.bridgelabs.hotelreservationsystem.controller;

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
}
