package hotel_reservation;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class Hotel {
    private Room[] rooms;

    public Hotel(Room[] rooms) {
        this.rooms = Utils.cloneArray(rooms);
    }

    public Room[] getAvailableRooms(Calendar startDate, Calendar endDate, int numberOfPeople) {
        List<Room> availableRooms = new ArrayList<Room>();

        for (Room room : this.rooms) {
            if (room.isAvailable(startDate, endDate) && room.getNumberOfBeds() <= numberOfPeople) {
                availableRooms.add(room);
            }
        }

        return availableRooms.toArray(new Room[0]);
    }
}

