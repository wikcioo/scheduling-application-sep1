package view;

import javafx.scene.control.ListView;
import model.rooms.Room;
import model.rooms.RoomList;

import java.util.ArrayList;

public class RoomView {
    private ListView list;

    public RoomView(RoomList roomsList) {
        roomsList.readRoomsListFromBinFile();
        list = new ListView();
        ArrayList<Room> rooms = roomsList.getRooms();
        for (Room room : rooms) {
            list.getItems().add(room.toString());
        }
    }

    public ListView getList() {
        return list;
    }
}
