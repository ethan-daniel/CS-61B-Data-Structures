package byog.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    /** Draws a row of a given tile.
     * Add coordinates and texture to room map. */
    private void drawRow(Room room, int x_coordinate, int y_coordinate, int len, TETile texture) {
        for (int x = x_coordinate; x != x_coordinate + len; ++x) {
                world[x][y_coordinate] = texture;
        }
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
        return true;

//        for (Room r : roomList ){
//            for (Coordinates c_r : r.roomTiles.keySet()){
//                for (Coordinates c : room.roomTiles.keySet()) {
//                    if (c_r.getX() == c.getX() && c_r.getY() == c.getY()){
//                        return false;
//                    }
//                }
//            for (Coordinates c : room.roomTiles.keySet()) {
//                if (r.roomTiles.containsKey(c)){
//                    return false;
//                }
//            }
//            for (Coordinates c : room.roomTiles.keySet()) {
//                if (r.roomTiles.){
//                    return false;
//                }
//            }
//        }
//        return true;
    }


    public void drawRectangularRoom(Room room) {
        if (!isPlaceableRoom(room)){
            return;
        }
        drawRoom(room);
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
