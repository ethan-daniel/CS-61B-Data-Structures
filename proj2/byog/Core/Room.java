package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
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
    private ArrayList<Coordinates> outerFloorTiles = new ArrayList<>();

    private ArrayList<Coordinates> north_floors = new ArrayList<>();
    private ArrayList<Coordinates> south_floors = new ArrayList<>();
    private ArrayList<Coordinates> west_floors = new ArrayList<>();
    private ArrayList<Coordinates> east_floors = new ArrayList<>();


    /** Room constructor. Assigns object variables with given arguments, and initializes the room's tiles.
     * Also adds outer floor tiles to a list.
     * Note: A room must have at least height and width 3. */
    Room(int width, int height, int origin_x_coordinate, int origin_y_coordinate) {
        this.width = width;
        this.height = height;
        this.origin_x_coordinate = origin_x_coordinate;
        this.origin_y_coordinate = origin_y_coordinate;

        if (this.width == 3 || this.height == 3) {
            isHallway = true;
        }

        // Adding all room tiles and textures.
        setRoomTiles(origin_x_coordinate, origin_y_coordinate, width, WALL);
        for (int y = origin_y_coordinate + 1; y != origin_y_coordinate + height - 1; ++y) {
            setRoomTiles(origin_x_coordinate, y, 1, WALL);
            setRoomTiles(origin_x_coordinate + 1, y, width - 2, FLOOR);
            setRoomTiles(origin_x_coordinate + width - 1, y, 1, WALL);
        }
        setRoomTiles(origin_x_coordinate, origin_y_coordinate + height - 1, width, WALL);

        // Adding all outer floor tiles excluding corners.
        setOuterFloorTiles(origin_x_coordinate + 2, origin_y_coordinate + 1, width - 3, outerFloorTiles);
        for (int y = origin_y_coordinate + 2; y <= origin_y_coordinate + height - 2; ++y) {
            setOuterFloorTiles(origin_x_coordinate + 1, y, 1, outerFloorTiles);
            setOuterFloorTiles(origin_x_coordinate + width - 2, y, 1, outerFloorTiles);
        }
        setOuterFloorTiles(origin_x_coordinate + 2, origin_y_coordinate + height - 2, width - 3, outerFloorTiles);


        setOuterFloorTiles(origin_x_coordinate + 2, origin_y_coordinate + 1, width - 4, south_floors);
        for (int y = origin_y_coordinate + 2; y <= origin_y_coordinate + height - 3; ++y) {
            setOuterFloorTiles(origin_x_coordinate + 1, y, 1, west_floors);
            setOuterFloorTiles(origin_x_coordinate + width - 2, y, 1, east_floors);
        }
        setOuterFloorTiles(origin_x_coordinate + 2, origin_y_coordinate + height - 2, width - 4, north_floors);



    }

    /** Assigns tile textures with coordinates. */
    private void setRoomTiles(int x_coordinate, int y_coordinate, int len, TETile texture) {
        for (int x = x_coordinate; x != x_coordinate + len; ++x) {
            roomTiles.put(new Coordinates(x, y_coordinate), texture);
        }
    }

    /** Adds coordinates that are the outermost floor tiles. */
    private void setOuterFloorTiles(int x_coordinate, int y_coordinate, int len, ArrayList<Coordinates> al) {
        for (int x = x_coordinate; x != x_coordinate + len; ++x) {
            if (!al.contains(new Coordinates(x, y_coordinate))){
                al.add(new Coordinates(x, y_coordinate));
            }
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

    /** Returns the room tiles data map. */
    public Map<Coordinates, TETile> getRoomTiles() {
        return roomTiles;
    }

    public ArrayList<Coordinates> getOuterFloorTiles() {
        return outerFloorTiles;
    }

    public ArrayList<Coordinates> getNorthFloors() {
        return north_floors;
    }
    public ArrayList<Coordinates> getSouthFloors() {
        return south_floors;
    }
    public ArrayList<Coordinates> getWestFloors() {
        return west_floors;
    }
    public ArrayList<Coordinates> getEastFloors() {
        return east_floors;
    }

    /** Returns the size of the room. */
    public int getRoomTileSize() {
        return roomTiles.size();
    }

    /** Returns the number of outer floor tiles. */
    public int getNumOuterFloorTiles() {
        return outerFloorTiles.size();
    }

    /** Returns the number of north floor tiles. */
    public int getNumNorthFloors() {
        return north_floors.size();
    }

    /** Returns the number of south floor tiles. */
    public int getNumSouthFloors() {
        return south_floors.size();
    }

    /** Returns the number of west floor tiles. */
    public int getNumWestFloors() {
        return west_floors.size();
    }

    /** Returns the number of east floor tiles. */
    public int getNumEastFloors() {
        return east_floors.size();
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

    /** Prints all outer floor tiles. */
    public void printFloorTiles(ArrayList<Coordinates> coor_list) {
        for (Coordinates coor : coor_list) {
            System.out.print("(" + coor.getX() + ", " + coor.getY() + "), ");
        }
        System.out.println();
    }

}
