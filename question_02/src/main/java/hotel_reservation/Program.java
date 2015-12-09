package hotel_reservation;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        Hotel fawlteyTowers = new Hotel(Program.createExampleData());

        try {
            System.out.println("Available rooms:");
            for (Room room : fawlteyTowers.getAvailableRooms(Utils.date("2015-07-17"), Utils.date("2015-07-20"), 1)) {
                System.out.println(room.toString());
            }
        } catch (Exception ex) {
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

            Room room2 = new Room(2, 4, 100, "Sea View");
            room2.bookRoom(Utils.date("2015-07-12"), Utils.date("2015-07-26"), 4);
            rooms.add(room2);

            for (Room room : rooms) {
                System.out.println(room.toString());
            }

            return rooms.toArray(new Room[0]);
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }
}

