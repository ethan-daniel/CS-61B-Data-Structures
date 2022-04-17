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

    /** Some helper functions:
     * */

    /** Draws a row of a given tile. */
    private void drawRow(Room room, int x_coordinate, int y_coordinate, int len, TETile texture) {
        for (int x = x_coordinate; x != x_coordinate + len; ++x) {
                world[x][y_coordinate] = texture;
                room.roomTiles.put(new Coordinates(x_coordinate, y_coordinate), texture);
        }
    }
    /** Checks if room can be placed.
     * This will NOT allow a room to be placed outside the bounds of the world.
     * This will NOT allow a room to be placed on another room. */

    private boolean isPlaceableRoom(Room room) {
        if ((room.origin_y_coordinate + room.height) > WORLD_HEIGHT ||
                (room.origin_x_coordinate + room.width) > WORLD_WIDTH) {
            return false;
        }
        for (Room r : roomList ){
            for (Coordinates c : room.roomTiles.keySet()) {
                if (r.roomTiles.containsKey(c)){
                    return false;
                }
            }
        }
        return true;
    }
    public class Room {
        protected int width;
        protected int height;
        protected int origin_x_coordinate;
        protected int origin_y_coordinate;
        protected final Map<Coordinates, TETile> roomTiles = new HashMap<>();

        Room(int width, int height, int origin_x_coordinate, int origin_y_coordinate) {
            this.width = width;
            this.height = height;
            this.origin_x_coordinate = origin_x_coordinate;
            this.origin_y_coordinate = origin_y_coordinate;
        }
    }

    public void drawRectangularRoom(Room room) {
        if (!isPlaceableRoom(room)){
            return;
        }

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
