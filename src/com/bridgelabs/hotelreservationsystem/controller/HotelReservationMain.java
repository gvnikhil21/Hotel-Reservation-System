package com.bridgelabs.hotelreservationsystem.controller;

import java.util.Scanner;
import java.util.logging.Logger;

import com.bridgelabs.hotelreservationsystem.model.HotelReservation;

public class HotelReservationMain {
	public static final Logger LOG = Logger.getLogger(HotelReservationMain.class.getName());
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		int choice;
		// welcome message
		LOG.info("Welcome to hotel reservation system!");

		HotelReservationController hotelReservationController = new HotelReservationController();
		HotelReservation hotelReservation = new HotelReservation();
		do {
			LOG.info(
					"Enter the choice no. : \n1. Add Hotel\n2. View All Hotel Details\n3. Find Cheapest Best Rated Hotel\n4. Find Best Rated Hotel\n5. Exit");
			choice = sc.nextInt();
			sc.nextLine();
			switch (choice) {
			case 1:
				hotelReservationController.addHotel(hotelReservation);
				break;
			case 2:
				hotelReservationController.displayHotel(hotelReservation);
				break;
			case 3:
				hotelReservationController.findCheapestBestRatedHotel(hotelReservation);
				break;
			case 4: 
				hotelReservationController.findBestRatedHotel(hotelReservation);
				break;
			case 5:
				LOG.info("You have quit the program! Thank you for your time!");
				break;
			}
		} while (choice != 5);
	}

}
