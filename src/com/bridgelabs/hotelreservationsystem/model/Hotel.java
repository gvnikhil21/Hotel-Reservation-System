package com.bridgelabs.hotelreservationsystem.model;

public class Hotel {
	private String hotelName;
	private int regularWeekdayPrice;
	private int regularWeekendPrice;
	private int rating;
	private int totalPrice;

	// constructor
	public Hotel() {
	}

	public Hotel(String hotelName, int rating, int regularWeekdayPrice, int regularWeekendPrice) {
		this.hotelName = hotelName;
		this.rating = rating;
		this.regularWeekdayPrice = regularWeekdayPrice;
		this.regularWeekendPrice = regularWeekendPrice;
	}

	// getters and setters
	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public int getRegularWeekdayPrice() {
		return regularWeekdayPrice;
	}

	public void setRegularWeekdayPrice(int regularWeekdayPrice) {
		this.regularWeekdayPrice = regularWeekdayPrice;
	}

	public int getRegularWeekendPrice() {
		return regularWeekendPrice;
	}

	public void setRegularWeekendPrice(int regularWeekendPrice) {
		this.regularWeekendPrice = regularWeekendPrice;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Hotel Name: " + hotelName + "\nRating: " + rating + "\nPrice for regular customer week-day: $"
				+ regularWeekdayPrice + "\nPrice for regular customer week-end: $" + regularWeekendPrice + "\n";
	}

}
