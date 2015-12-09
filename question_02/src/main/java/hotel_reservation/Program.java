package hotel_reservation;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        Hotel fawlteyTowers = new Hotel(Program.createExampleData());


    }

    private static Room[] createExampleData() {
        List<Room> rooms = new ArrayList<Room>();

        Room room1 = new Room(1, 2, 20);
        room1.bookRoom(Utils.date("2015-07-10"), Utils.date("2015-07-16"), 2);
        room1.bookRoom(Utils.date("2015-07-21"), Utils.date("2015-07-23"), 1);
        rooms.add(room1);

        Room room2 = new Room(2, 4, 100, "Sea View");
        room2.bookRoom(Utils.date("2015-07-12"), Utils.date("2015-07-26"), 4);

        return rooms.toArray(new Room[0]);
    }
}

