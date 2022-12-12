package com.jpmc.theater;

import com.jpmc.theater.exception.ReservationException;
import com.jpmc.theater.service.ReservationService;
import com.jpmc.theater.service.TheaterInformationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

import static com.jpmc.theater.service.TheaterInformationService.PrintFormat.*;

@SpringBootApplication
public class MovieTheaterApplication {

  public static void main(String[] args) {

    ConfigurableApplicationContext ctx = SpringApplication.run(MovieTheaterApplication.class, args);

    TheaterInformationService informationService = ctx.getBean(TheaterInformationService.class);
    ReservationService reservationService = ctx.getBean(ReservationService.class);

    Scanner input = new Scanner(System.in);
    int choice, formatChoice;

    do {
      try {
        System.out.print("Press 1 for theater schedule OR 2 for book a reservation:");
        choice = input.nextInt();
        switch (choice) {
          case 1:
            System.out.print("Press 1 for plain text, 2 for JSON, 3 for both:");
            formatChoice = input.nextInt();
            switch (formatChoice) {
              case 1:
                informationService.printTheaterSchedule(1, PLAIN_TEXT);
                break;
              case 2:
                informationService.printTheaterSchedule(1, JSON);
                break;
              case 3:
                informationService.printTheaterSchedule(1, JSON_AND_PLAINTEXT);
                break;
              default:
                System.out.println("Incorrect format choice...");
            }
            input.nextLine();
            break;
          case 2:
            String customerId;
            int sequence, ticketCount;
            System.out.println("Enter customer id:");
            input.nextLine();
            customerId = input.nextLine();
            System.out.println("Enter showing sequence id:");
            sequence = input.nextInt();
            System.out.println("Enter number of tickets:");
            ticketCount = input.nextInt();
            try {
              var reservation = reservationService.reserve(customerId, sequence, ticketCount, 1);
              System.out.println("Reservation Successful, here are the details: ");
              System.out.println(reservation.summary());
            } catch (ReservationException re) {
              System.out.println("Sorry, we found following error: " + re.getMessage());
            }
            input.nextLine();
            break;
          default:
            System.out.println("Incorrect menu option chosen");
        }
      } catch (Exception e) {
        System.out.println("Sorry, we have faced a problem: " + e.getMessage());
        input.nextLine();
      }
      System.out.println("Do you want to see the main options again? (Y/N):");
      if (!"Y".equalsIgnoreCase(input.nextLine())) break;
    } while (true);
  }
}
