import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TicketBookingSystem {
    private final int totalSeats;
    private final Set<Integer> bookedSeats = new HashSet<>();

    public TicketBookingSystem(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public synchronized boolean bookSeat(int seatNumber) {
        if (seatNumber < 1 || seatNumber > totalSeats || bookedSeats.contains(seatNumber)) {
            return false;
        }
        bookedSeats.add(seatNumber);
        return true;
    }
}

class BookingThread extends Thread {
    private final TicketBookingSystem system;
    private final int seatNumber;

    public BookingThread(TicketBookingSystem system, int seatNumber, int priority) {
        this.system = system;
        this.seatNumber = seatNumber;
        setPriority(priority);
    }

    @Override
    public void run() {
        if (system.bookSeat(seatNumber)) {
            System.out.println(Thread.currentThread().getName() + " successfully booked seat " + seatNumber);
        } else {
            System.out.println(Thread.currentThread().getName() + " failed to book seat " + seatNumber
                    + " (already taken or invalid)");
        }
    }
}

public class Project4_3 {
    public static void main(String[] args) {
        TicketBookingSystem system = new TicketBookingSystem(10);
        List<BookingThread> threads = new ArrayList<>();

        // Creating VIP and regular booking threads
        threads.add(new BookingThread(system, 3, Thread.MAX_PRIORITY));
        threads.add(new BookingThread(system, 5, Thread.NORM_PRIORITY));
        threads.add(new BookingThread(system, 3, Thread.NORM_PRIORITY)); // Attempt to double book
        threads.add(new BookingThread(system, 7, Thread.MIN_PRIORITY));
        threads.add(new BookingThread(system, 1, Thread.MAX_PRIORITY));

        // Start all threads
        for (BookingThread thread : threads) {
            thread.start();
        }

        // Wait for all threads to complete
        for (BookingThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
