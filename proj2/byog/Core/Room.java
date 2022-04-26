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
    private int originXCoordinate;
    private int originYCoordinate;
    private boolean isHallway = false;
    private Map<Coordinates, TETile> roomTiles = new HashMap<>();
    private ArrayList<Coordinates> hallwayEnds = new ArrayList<>();

    /** Room constructor. Assigns object variables with given arguments,
     * and initializes the room's tiles.
     * Also adds outer floor tiles to a list.
     * Note: A room must have at least height and width 3. */
    Room(int width, int height, int originXCoordinate, int originYCoordinate) {
        this.width = width;
        this.height = height;
        this.originXCoordinate = originXCoordinate;
        this.originYCoordinate = originYCoordinate;

        if (this.width == 3 || this.height == 3) {
            setHallwayProperties();
        }

        // Adding all room tiles and textures.
        setRoomTiles(originXCoordinate, originYCoordinate, width, WALL);
        for (int y = originYCoordinate + 1; y != originYCoordinate + height - 1; ++y) {
            setRoomTiles(originXCoordinate, y, 1, WALL);
            setRoomTiles(originXCoordinate + 1, y, width - 2, FLOOR);
            setRoomTiles(originXCoordinate + width - 1, y, 1, WALL);
        }
        setRoomTiles(originXCoordinate, originYCoordinate + height - 1, width, WALL);
    }

    private void setHallwayProperties() {
        isHallway = true;
        if (this.height == 3) {
            hallwayEnds.add(new Coordinates(originXCoordinate + 1,
                    originYCoordinate + 1));
            hallwayEnds.add(new Coordinates(originXCoordinate + width - 2,
                    originYCoordinate + 1));
        } else if (this.width == 3) {
            hallwayEnds.add(new Coordinates(originXCoordinate + 1,
                    originYCoordinate + 1));
            hallwayEnds.add(new Coordinates(originXCoordinate + 1,
                    originYCoordinate + height - 2));
        }
    }

    /** Assigns tile textures with coordinates. */
    private void setRoomTiles(int xCoordinate, int yCoordinate, int len, TETile texture) {
        for (int x = xCoordinate; x != xCoordinate + len; ++x) {
            roomTiles.put(new Coordinates(x, yCoordinate), texture);
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
        return originXCoordinate;
    }

    /** Returns the room's originating y coordinate. */
    public int getOriginYCoordinate() {
        return originYCoordinate;
    }

    /** Returns true if the room is a hallway. */
    public boolean isHallway() {
        return isHallway;
    }

    /** Returns the room tiles data map. */
    public Map<Coordinates, TETile> getRoomTiles() {
        return roomTiles;
    }

    public ArrayList<Coordinates> getHallwayEnds() {
        return hallwayEnds;
    }

    /** Returns the size of the room. */
    public int getRoomTileSize() {
        return roomTiles.size();
    }

    /** Returns the number of outer floor tiles. */
    public int getNumHallwayEnds() {
        return hallwayEnds.size();
    }

    /** Prints all coordinates and their texture. */
    public void printHashMapTiles(Map<Coordinates, TETile> cMap) {
        for (Coordinates coor : cMap.keySet()) {
            String tileStr = "";
            if (cMap.get(coor) == FLOOR) {
                tileStr = "FLOOR";
            } else if (cMap.get(coor) == WALL) {
                tileStr = "WALL";
            }
            System.out.print("[(" + coor.getX() + ", " + coor.getY() + ") = "
                    + tileStr + "], ");
        }
        System.out.println();
    }

    /** Prints all outer floor tiles. */
    public void printArrayListTiles(ArrayList<Coordinates> cList) {
        for (Coordinates coor : cList) {
            System.out.print("(" + coor.getX() + ", " + coor.getY() + "), ");
        }
        System.out.println();
    }

}
