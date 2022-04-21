package byog.Core;

import java.util.ArrayList;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class MapGenerator {
    public static final TETile WALL = Tileset.WALL;
    public static final TETile FLOOR = Tileset.FLOOR;
    private final TETile[][] world;
    private int WORLD_WIDTH;
    private int WORLD_HEIGHT;
    private final ArrayList<Room> roomList = new ArrayList<>();

    /** Initializes world with given width and height. */
    MapGenerator(int width, int height) {
        WORLD_WIDTH = width;
        WORLD_HEIGHT = height;
        world = new TETile[WORLD_WIDTH][WORLD_HEIGHT];
        for (int x = 0; x != WORLD_WIDTH; ++x) {
            for (int y = 0; y != WORLD_HEIGHT; ++y) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /** Returns the width of the map. */
    public int getWidth() {
        return WORLD_WIDTH;
    }

    /** Returns the height of the map. */
    public int getHeight() {
        return WORLD_HEIGHT;
    }

    /** Returns the room list. */
    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    /** Returns world. */
    public TETile[][] getWorld() {
        return world;
    }

    /** Returns the number of rooms (hallways included) that are in the map. */
    public int getNumRooms() {
        return roomList.size();
    }

    private void drawRoom(Room room) {
        for (Coordinates coor : room.getRoomTiles().keySet()) {
            world[coor.getX()][coor.getY()] = room.getRoomTiles().get(coor);
        }
    }

    /** Checks if room can be placed.
     * This will NOT allow a room to be placed outside the bounds of the world.
     * This will NOT allow a room to be placed on another room. */

    private boolean isPlaceableRoom(Room room) {
        if ((room.getOriginYCoordinate() + room.getHeight()) > WORLD_HEIGHT ||
                (room.getOriginXCoordinate() + room.getWidth()) > WORLD_WIDTH) {
            return false;
        }

        for (Room existing_room : roomList) {
            for (Coordinates coordinates_key_e : existing_room.getRoomTiles().keySet()) {
                for (Coordinates coordinates_key_g : room.getRoomTiles().keySet()) {
                    if (coordinates_key_e.equals(coordinates_key_g)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void drawRectangularRoom(Room room) {
        if (!isPlaceableRoom(room)){
            return;
        }
        drawRoom(room);
        roomList.add(room);
    }

    public void drawHorizontalHallway(Room room) {
        if (!isPlaceableRoom(room)){
            return;
        }

    }

    public void drawVerticalHallway(Room room) {
        if (!isPlaceableRoom(room)){
            return;
        }

    }

    public void drawCorner(Room room_a, Room room_b) {

    }

}
