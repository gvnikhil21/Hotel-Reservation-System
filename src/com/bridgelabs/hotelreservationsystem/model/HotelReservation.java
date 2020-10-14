package com.bridgelabs.hotelreservationsystem.model;

import java.util.ArrayList;
import java.util.List;

public class HotelReservation {
	List<Hotel> hotelList;

	// constructor to initialize hotelList
	public HotelReservation() {
		hotelList = new ArrayList<Hotel>();
	}

	// adds hotel to hotelList
	public void addHotel(Hotel hotel) {
		hotelList.add(hotel);
	}

	// returns hotelList
	public List<Hotel> getHotelList() {
		return hotelList;
	}
}
