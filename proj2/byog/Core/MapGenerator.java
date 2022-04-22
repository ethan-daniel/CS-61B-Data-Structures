package byog.Core;

import java.util.ArrayList;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;
import java.lang.Math;

public class MapGenerator {
    public static final TETile WALL = Tileset.WALL;
    public static final TETile FLOOR = Tileset.FLOOR;
    private final TETile[][] world;
    private int WORLD_WIDTH;
    private int WORLD_HEIGHT;
    private int num_Rooms;
    private int num_Hallways;
    private long seed;
    private final ArrayList<Coordinates> outerFloorCoordinates = new ArrayList<>();
    private final ArrayList<Room> roomList = new ArrayList<>();
    private final ArrayList<Room> hallwayList = new ArrayList<>();

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

    /** Returns the hallway list. */
    public ArrayList<Room> getHallwayList() {
        return hallwayList;
    }

    /** Returns world. */
    public TETile[][] getWorld() {
        return world;
    }

    /** Returns the number of rooms that are in the map. */
    public int getNumRooms() {
        return roomList.size();
    }

    /** Returns the number of hallways that are in the map. */
    public int getNumHallways() {
        return hallwayList.size();
    }

    /** Draws a room on the world, and forbids Wall textures from overwriting
     * Floor textures. */
    private void drawRoom(Room room) {
        for (Coordinates coor : room.getRoomTiles().keySet()) {
            if (world[coor.getX()][coor.getY()] != FLOOR){
                world[coor.getX()][coor.getY()] = room.getRoomTiles().get(coor);
            }
        }
    }

    /** Checks if room can be placed.
     * This will NOT allow a room to be placed outside the bounds of the world.
     * */
    private boolean isPlaceableRoom(Room room) {
        if ((room.getOriginYCoordinate() + room.getHeight()) > WORLD_HEIGHT ||
                (room.getOriginXCoordinate() + room.getWidth()) > WORLD_WIDTH) {
            return false;
        }

        return true;
    }

    /** Checks if room overlaps.
     * This will NOT allow a room to be placed on another room. */
    private boolean overlapsRoom(Room room) {
        for (Room existing_room : roomList) {
            for (Coordinates coordinates_key_e : existing_room.getRoomTiles().keySet()) {
                for (Coordinates coordinates_key_g : room.getRoomTiles().keySet()) {
                    if (coordinates_key_e.equals(coordinates_key_g)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

//    private boolean overlapsOuterFloor(Room room) {
//        for (Room existing_room : roomList) {
//            for (Coordinates coordinates_key : existing_room.getRoomTiles().keySet()) {
//                for (Coordinates coordinates_room : room.getOuterFloorTiles()) {
//                    if ()
//                }
//            }
//        }
//    }

    public void drawRectangularRoom(Room room) {
        if (!isPlaceableRoom(room) || overlapsRoom(room)){
            return;
        }
        drawRoom(room);
        roomList.add(room);
    }

    public void drawHallway(Room room) {
        if (!isPlaceableRoom(room)){
            return;
        }
        drawRoom(room);
        roomList.add(room);
        hallwayList.add(room);
    }

//    public void drawVerticalHallway(Room room) {
//        if (!isPlaceableRoom(room)){
//            return;
//        }
//
//    }

    public void drawCorner(Room room_a, Room room_b) {

    }


    public void generateRectangularRoom(int width, int height, Coordinates coor) {
        Room room = new Room(width, height, coor.getX(), coor.getY());
        drawRoom(room);
    }

    public void generateHorizontalHallway(int length, Coordinates coor) {
        Room room = new Room(length, 3, coor.getX(), coor.getY());
        drawHallway(room);
    }

    public void generateVerticalHallway(int length, Coordinates coor) {
        Room room = new Room(3, length, coor.getX(), coor.getY());
        drawHallway(room);
    }

    /** Uses the seed to determine the number of rooms and hallways to be placed
     * in the map.
     * Note: There will be at least one room and two hallways. */
    public void determineNumRoomsAndHallways() {
        int area = WORLD_WIDTH * WORLD_HEIGHT;
        int max_rooms = (int) (Math.sqrt(area) / 2);
        int max_hallways = (int) (max_rooms * 1.5);
        Random generator = new Random(seed);
        num_Rooms = generator.nextInt(max_rooms - 1 + 1) + 1;
        num_Hallways = generator.nextInt(max_hallways - 2 + 1) + 2;

        System.out.println(num_Rooms);
        System.out.println(num_Hallways);
    }

    /** Uses the given seed to determine the starting coordinates of the
     * map generation (Biased to start in the bottom left corner.). */
    public Coordinates determineStartingCoordinates() {
        Random generator = new Random(seed);
        int x = generator.nextInt((int) WORLD_WIDTH / 4);
        int y = generator.nextInt((int) WORLD_HEIGHT / 4);

        Coordinates coor = new Coordinates(x, y);
        return coor;
    }

    /** Gets passed a pseudo-randomly different number to determine the next
     * coordinate for placement on the map. */
    public Coordinates determineNextCoordinateForPlacement(long input) {
        Random generator = new Random(input);
        int random_index = generator.nextInt(outerFloorCoordinates.size());
        return outerFloorCoordinates.get(random_index);
    }

    /** Uses a given input to determine a pseudo random width.
     * Note: The width must be at least 3. */
    public int determineAWidth(long input) {
        Random generator = new Random(input);
        return generator.nextInt((int) (WORLD_WIDTH / 6) - 3 + 1) + 3;
    }

    /** Uses a given input to determine a pseudo random height.
     * Note: The height must be at least 3. */
    public int determineAHeight(long input) {
        Random generator = new Random(input);
        return generator.nextInt((int) (WORLD_HEIGHT / 6) - 3 + 1) + 3;
    }

    public void generateMap(long seed) {
        this.seed = seed;
        determineNumRoomsAndHallways();
        Coordinates starting_coordinates = determineStartingCoordinates();
        System.out.println("Starting Coordinates: (" + starting_coordinates.getX() + ", "
                + starting_coordinates.getY() + ") ");

        Random generator = new Random(seed);
        generateRectangularRoom(determineAWidth(seed), 6, starting_coordinates);

//        while (num_rooms != max_Rooms || num_hallways != max_Hallways) {
//
//        }

//        Random generator = new Random(seed);
//
//        for (int i = 0; i != 10; ++i) {
//            double num = generator.nextDouble();
//            System.out.println(num);
//        }
    }

}
