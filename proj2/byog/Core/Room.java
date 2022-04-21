package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Map;

public class Room {
    private static final TETile WALL = Tileset.WALL;
    private static final TETile FLOOR = Tileset.FLOOR;
    private int width;
    private int height;
    private int origin_x_coordinate;
    private int origin_y_coordinate;
    private boolean isHallway = false;
    private Map<Coordinates, TETile> roomTiles = new HashMap<>();

    /** Room constructor. Assigns object variables with given arguments, and initializes the room's tiles. */
    Room(int width, int height, int origin_x_coordinate, int origin_y_coordinate) {
        this.width = width;
        this.height = height;
        this.origin_x_coordinate = origin_x_coordinate;
        this.origin_y_coordinate = origin_y_coordinate;

        if (this.width == 3 || this.height == 3) {
            isHallway = true;
        }

        setRoomTiles(origin_x_coordinate, origin_y_coordinate, width, WALL);
        for (int y = origin_y_coordinate + 1; y != origin_y_coordinate + height - 1; ++y) {
            setRoomTiles(origin_x_coordinate, y, 1, WALL);
            setRoomTiles(origin_x_coordinate + 1, y, width - 2, FLOOR);
            setRoomTiles(origin_x_coordinate + width - 1, y, 1, WALL);
        }
        setRoomTiles(origin_x_coordinate, origin_y_coordinate + height - 1, width, WALL);
    }

    /** Assigns tile textures with coordinates. */
    private void setRoomTiles(int x_coordinate, int y_coordinate, int len, TETile texture) {
        for (int x = x_coordinate; x != x_coordinate + len; ++x) {
            roomTiles.put(new Coordinates(x, y_coordinate), texture);
        }
    }

    /** Returns the room's width. */
    public int getWidth() {
        return width;
    }

    /** Returns the room's height. */
    public int getHeight() {
        return height;
    }

    /** Returns the room's originating x coordinate. */
    public int getOriginXCoordinate() {
        return origin_x_coordinate;
    }

    /** Returns the room's originating y coordinate. */
    public int getOriginYCoordinate() {
        return origin_y_coordinate;
    }

    /** Returns true if the room is a hallway. */
    public boolean isHallway() {
        return isHallway;
    }

    public Map<Coordinates, TETile> getRoomTiles() {
        return roomTiles;
    }

    /** Prints the size of the room. */
    public void printRoomTileSize() {
        System.out.println(roomTiles.size());
    }

    /** Prints all coordinates and their texture. */
    public void printRoomTiles() {
        for (Coordinates coor : roomTiles.keySet()){
            String tile_str = "";
            if (roomTiles.get(coor) == FLOOR) {
                tile_str = "FLOOR";
            }
            else if (roomTiles.get(coor) == WALL) {
                tile_str = "WALL";
            }
            System.out.print("[(" + coor.getX() + ", " + coor.getY() + ") = "
            + tile_str + "], ");
        }
        System.out.println();
    }

}
