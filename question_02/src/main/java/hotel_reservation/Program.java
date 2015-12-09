package hotel_reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.text.ParseException;

public class Program {
    public static void main(String[] args) {
        Hotel fawlteyTowers = new Hotel(Program.createExampleData());

        try {
            Calendar fromDate = Utils.date("2015-07-17");
            Calendar toDate = Utils.date("2015-07-20");
            int people = 1;

            System.out.println(String.format("Available rooms between %s to %s for %s " + (people == 1 ? "person" : "people"), Utils.formatDate(fromDate), Utils.formatDate(toDate), people));
            for (Room room : fawlteyTowers.getAvailableRooms(fromDate, toDate, people)) {
                System.out.println(room.toString());
                System.out.printf("Cost to book this room: £%s%n%n", Booking.getPrice(fromDate, toDate, room.getPricePerNight()));
            }
        } catch (ParseException ex) {
            System.out.println(ex.toString());
        }
    }

    private static Room[] createExampleData() {
        try {
            List<Room> rooms = new ArrayList<Room>();

            Room room1 = new Room(1, 2, 20);
            room1.bookRoom(Utils.date("2015-07-10"), Utils.date("2015-07-16"), 2);
            room1.bookRoom(Utils.date("2015-07-21"), Utils.date("2015-07-23"), 1);
            rooms.add(room1);

            Room room2 = new Room(2, 4, 85, "Sea View", "Scenic view of Sky (gets a little damp when it rains)");
            room2.bookRoom(Utils.date("2015-07-12"), Utils.date("2015-07-26"), 4);
            rooms.add(room2);

            System.out.println("List of Rooms:");

            for (Room room : rooms) {
                System.out.println(room.toString());
            }

            return rooms.toArray(new Room[0]);
        } catch (RoomUnavailableException ex) {
            throw new Error(ex);
        } catch (IllegalArgumentException ex) {
            throw new Error(ex);
        } catch (ParseException ex) {
            throw new Error(ex);
        }
        // I know I shouldn't really use unchecked exceptions like this,
        // but frankly, this isn't supposed to be a fully-functional Hotel booking system
    }
}

