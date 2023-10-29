package Java_development_project;

import java.util.Scanner;
import java.util.HashMap;

class ReservationSystem {
    private static HashMap<String, Reservation> reservations = new HashMap<>();
    private static int reservationCounter = 1;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
			    System.out.println("Online Reservation System");
			    System.out.println("1. Login");
			    System.out.println("2. Exit");
			    System.out.print("Select an option: ");
			    int choice = scanner.nextInt();

			    if (choice == 1) {
			        login();
			    } else if (choice == 2) {
			        System.out.println("Goodbye!");
			        break;
			    } else {
			        System.out.println("Invalid choice. Please try again.");
			    }
			}
		}
    }

    private static void login() {
        try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter your username: ");
			String username = scanner.nextLine();
			System.out.print("Enter your password: ");
			String password = scanner.nextLine();

			// Simulated login, you can add actual authentication logic here
			if (authenticateUser(username, password)) {
			    System.out.println("Login successful.");
			    reservationMenu();
			} else {
			    System.out.println("Login failed. Please try again.");
			}
		}
    }

    private static boolean authenticateUser(String username, String password) {
        // In a real system, you would validate credentials against a database
        return true; // Simulated successful authentication
    }

    private static void reservationMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Reservation Menu");
            System.out.println("1. Make a Reservation");
            System.out.println("2. Cancel a Reservation");
            System.out.println("3. Logout");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                makeReservation();
            } else if (choice == 2) {
                cancelReservation();
            } else if (choice == 3) {
                System.out.println("Logging out.");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void makeReservation() {
        try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter Reservation Details:");
			System.out.print("Name: ");
			String name = scanner.nextLine();
			System.out.print("Train Number: ");
			String trainNumber = scanner.nextLine();
			System.out.print("Class Type: ");
			String classType = scanner.nextLine();
			System.out.print("Date of Journey: ");
			String dateOfJourney = scanner.nextLine();
			System.out.print("From (Place): ");
			String fromPlace = scanner.nextLine();
			System.out.print("To (Destination): ");
			String toDestination = scanner.nextLine();

			// Generate a unique PNR for this reservation
			String pnr = "PNR" + reservationCounter;
			reservationCounter++;

			Reservation reservation = new Reservation(name, trainNumber, classType, dateOfJourney, fromPlace, toDestination);
			reservations.put(pnr, reservation);

			System.out.println("Reservation Successful. Your PNR is: " + pnr);
		}
    }

    private static void cancelReservation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your PNR to cancel the reservation: ");
        String pnr = scanner.nextLine();

        Reservation reservation = reservations.get(pnr);
        if (reservation != null) {
            System.out.println("Reservation Details:");
            System.out.println("PNR: " + pnr);
            System.out.println("Name: " + reservation.getName());
            System.out.println("Train Number: " + reservation.getTrainNumber());
            System.out.println("Class Type: " + reservation.getClassType());
            System.out.println("Date of Journey: " + reservation.getDateOfJourney());
            System.out.println("From (Place): " + reservation.getFromPlace());
            System.out.println("To (Destination): " + reservation.getToDestination());

            System.out.print("Confirm cancellation (OK/Cancel): ");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("OK")) {
                reservations.remove(pnr);
                System.out.println("Reservation with PNR " + pnr + " has been canceled.");
            } else {
                System.out.println("Cancellation canceled.");
            }
        } else {
            System.out.println("Reservation with PNR " + pnr + " not found.");
        }
    }
}

class Reservation {
    private String name;
    private String trainNumber;
    private String classType;
    private String dateOfJourney;
    private String fromPlace;
    private String toDestination;

    public Reservation(String name, String trainNumber, String classType, String dateOfJourney, String fromPlace, String toDestination) {
        this.name = name;
        this.trainNumber = trainNumber;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.fromPlace = fromPlace;
        this.toDestination = toDestination;
    }

    public String getName() {
        return name;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getClassType() {
        return classType;
    }

    public String getDateOfJourney() {
        return dateOfJourney;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public String getToDestination() {
        return toDestination;
    }
}
