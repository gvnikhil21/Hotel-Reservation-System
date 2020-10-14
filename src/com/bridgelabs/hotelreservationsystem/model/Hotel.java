package com.bridgelabs.hotelreservationsystem.model;

public class Hotel {
	private String hotelName;
	private int regularWeekDayPrice;
	private int regularWeekEndPrice;
	private int rewardsWeekDayPrice;
	private int rewardsWeekEndPrice;
	private int rating;
	private int totalPrice;

	// constructor
	public Hotel() {
	}

	public Hotel(String hotelName, int rating, int regularWeekDayPrice, int regularWeekEndPrice,
			int rewardsWeekDayPrice, int rewardsWeekEndPrice) {
		this.hotelName = hotelName;
		this.rating = rating;
		this.regularWeekDayPrice = regularWeekDayPrice;
		this.regularWeekEndPrice = regularWeekEndPrice;
		this.rewardsWeekDayPrice = rewardsWeekDayPrice;
		this.rewardsWeekEndPrice = rewardsWeekEndPrice;
	}

	// getters and setters
	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public int getRegularWeekDayPrice() {
		return regularWeekDayPrice;
	}

	public void setRegularWeekDayPrice(int regularWeekDayPrice) {
		this.regularWeekDayPrice = regularWeekDayPrice;
	}

	public int getRegularWeekEndPrice() {
		return regularWeekEndPrice;
	}

	public void setRegularWeekendPrice(int regularWeekEndPrice) {
		this.regularWeekEndPrice = regularWeekEndPrice;
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

	public int getRewardsWeekDayPrice() {
		return rewardsWeekDayPrice;
	}

	public void setRewardsWeekDayPrice(int rewardsWeekDayPrice) {
		this.rewardsWeekDayPrice = rewardsWeekDayPrice;
	}

	public int getRewardsWeekEndPrice() {
		return rewardsWeekEndPrice;
	}

	public void setRewardsWeekEndPrice(int rewardsWeekEndPrice) {
		this.rewardsWeekEndPrice = rewardsWeekEndPrice;
	}

	@Override
	public String toString() {
		return "Hotel Name: " + hotelName + "\nRating: " + rating + "\nPrice for regular customer week-day: $"
				+ regularWeekDayPrice + "\nPrice for regular customer week-end: $" + regularWeekEndPrice
				+ "\nPrice for rewards customer week-day: $" + rewardsWeekDayPrice
				+ "\nPrice for rewards customer week-end: $" + rewardsWeekEndPrice + "\n";
	}

}
