import java.util.*;

class TicketBookingSystem {
    private int availableSeats;

    public TicketBookingSystem(int totalSeats) {
        this.availableSeats = totalSeats;
    }

    
    public synchronized boolean bookSeat(String user, int seats) {
        if (availableSeats >= seats) {
            System.out.println(user + " successfully booked " + seats + " seat(s).");
            availableSeats -= seats;
            return true;
        } else {
            System.out.println(user + " booking failed. Not enough seats available.");
            return false;
        }
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}


class BookingThread extends Thread {
    private TicketBookingSystem system;
    private String user;
    private int seatsToBook;

    public BookingThread(TicketBookingSystem system, String user, int seats, int priority) {
        this.system = system;
        this.user = user;
        this.seatsToBook = seats;
        setPriority(priority);  // Set thread priority
    }

    @Override
    public void run() {
        system.bookSeat(user, seatsToBook);
    }
}


public class TicketBookingApp {
    public static void main(String[] args) {
        TicketBookingSystem system = new TicketBookingSystem(5);  // 5 seats available

        
        BookingThread vip1 = new BookingThread(system, "VIP User 1", 2, Thread.MAX_PRIORITY);
        BookingThread vip2 = new BookingThread(system, "VIP User 2", 2, Thread.MAX_PRIORITY);
        BookingThread regular1 = new BookingThread(system, "Regular User 1", 1, Thread.NORM_PRIORITY);
        BookingThread regular2 = new BookingThread(system, "Regular User 2", 2, Thread.NORM_PRIORITY);
        BookingThread regular3 = new BookingThread(system, "Regular User 3", 1, Thread.MIN_PRIORITY);

        
        vip1.start();
        vip2.start();
        regular1.start();
        regular2.start();
        regular3.start();
    }
}
