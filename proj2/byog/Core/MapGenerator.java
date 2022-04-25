package byog.Core;

import java.util.ArrayList;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;
import java.lang.Math;

public class MapGenerator {
    public static final TETile FLOOR = Tileset.FLOOR;
    private final TETile[][] world;
    private int WORLD_WIDTH;
    private int WORLD_HEIGHT;
    private int total_rooms;
    private int total_hallways;
    private int total_horizontal_hallways;
    private int total_vertical_hallways;
    private long seed;
    private Random generator;
    private final ArrayList<Coordinates> placeableFloorCoordinates = new ArrayList<>();
    private final ArrayList<Coordinates> placeableFloorCoordinatesRooms = new ArrayList<>();
    private final ArrayList<Room> roomList = new ArrayList<>();
    private final ArrayList<Room> hallwayList = new ArrayList<>();
    private final ArrayList<Room> horizontalHallwayList = new ArrayList<>();
    private final ArrayList<Room> verticalHallwayList = new ArrayList<>();

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

    /** Returns the number of horizontal hallways that are in the map. */
    public int getNumHorizontalHallways() {
        return horizontalHallwayList.size();
    }

    /** Returns the number of vertical hallways that are in the map. */
    public int getNumVerticalHallways() {
        return verticalHallwayList.size();
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
        if (room.getOriginYCoordinate() < 0 ||
                room.getOriginXCoordinate() < 0) {
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

    /** Attempts to draw a rectangular room. */
    public void drawRectangularRoom(Room room) {
        if (!isPlaceableRoom(room) || overlapsRoom(room)){
            return;
        }
        drawRoom(room);
        roomList.add(room);
    }

    /** Attempts to draw a hallway.
     * Will draw one on either end of the current hallway generation. */
    public void drawHallway(Room room) {
        if (!isPlaceableRoom(room)){
            return;
        }
        drawRoom(room);
        int x = room.getOriginXCoordinate();
        int y = room.getOriginYCoordinate();

        if (placeableFloorCoordinates.isEmpty()) {
            for (Coordinates coor : room.getHallwayEnds()) {
                placeableFloorCoordinates.add(coor);
            }
            return;
        }
        for (Coordinates coor : room.getHallwayEnds()) {
            placeableFloorCoordinates.remove(new Coordinates(x + 1, y + 1));
            if (!coor.equals(new Coordinates(x + 1, y + 1))) {
                placeableFloorCoordinates.add(coor);
            }
            placeableFloorCoordinatesRooms.add(coor);
        }
        hallwayList.add(room);
        if (room.getWidth() == 3) {
            verticalHallwayList.add(room);
        }
        else {
            horizontalHallwayList.add(room);
        }
    }

    /** Used to create and draw a rectangular room. */
    public void generateRectangularRoom(int width, int height, Coordinates coor) {
        Room room = new Room(width, height, coor.getX(), coor.getY());
        drawRectangularRoom(room);
    }

    /** Used to create and draw a horizontal hallway. */
    public void generateHorizontalHallway(int length, Coordinates coor) {
        Room room = new Room(length, 3, coor.getX(), coor.getY());
        drawHallway(room);
    }

    /** Used to create and draw a vertical hallway. */
    public void generateVerticalHallway(int length, Coordinates coor) {
        Room room = new Room(3, length, coor.getX(), coor.getY());
        drawHallway(room);
    }

    /** Uses the seed to determine the number of rooms and hallways to be placed
     * in the map.
     * Note: There will be at least one room and two hallways. */
    public void determineNumRoomsAndHallways() {
        int area = WORLD_WIDTH * WORLD_HEIGHT;
        int max_rooms = (int) (Math.sqrt(area) / 3);
        int max_horizontal_hallways = (int) (max_rooms * (1 + generator.nextDouble()));
        int max_vertical_hallways = (int) (max_rooms * (1 + generator.nextDouble()));
        this.total_rooms = generator.nextInt(max_rooms - 2 + 1) + 2;
        this.total_horizontal_hallways = generator.nextInt(max_horizontal_hallways - 2 + 1) + 2;
        this.total_vertical_hallways = generator.nextInt(max_vertical_hallways - 2 + 1) + 2;
        this.total_hallways = this.total_horizontal_hallways + this.total_vertical_hallways;
    }

    /** Uses the given seed to determine the starting coordinates of the
     * map generation (Biased to start in the bottom left corner.). */
    public Coordinates determineStartingCoordinates() {
        int x = generator.nextInt((int) (WORLD_WIDTH / 4) - 1) + 1;
        int y = generator.nextInt((int) (WORLD_HEIGHT / 4) - 1) + 1;

        Coordinates coor = new Coordinates(x - 1, y - 1);
        return coor;
    }

    /** Used to determine side lengths of rooms and hallways.
     * It will decide randomly using the map's height or width. */
    public int determineALength() {
        int fun = generator.nextInt();
        if (fun % 2 == 0) {
            return generator.nextInt((int) (WORLD_HEIGHT / 4) - 4 + 1) + 4;
        }
        return generator.nextInt((int) (WORLD_WIDTH / 4) - 4 + 1) + 4;
    }

    /** Used to determine coordinates for hallways. */
    public Coordinates determineNextCoordinates() {
        Coordinates temp = placeableFloorCoordinates.get(generator.nextInt(placeableFloorCoordinates.size()));

        return new Coordinates(temp.getX() - 1, temp.getY() - 1);
    }

    /** Used to determine coordinates for rooms. */
    public Coordinates determineNextCoordinatesRoom() {
        Coordinates temp = placeableFloorCoordinatesRooms.get(generator.nextInt(placeableFloorCoordinatesRooms.size()));

        return new Coordinates(temp.getX() - 1, temp.getY() - 1);
    }

    /** This will generate all hallways.
     * If a generated hallway does not fit, it will try a different
     * size and coordinates until that hallway fits. If that does not work, then it will
     * only try to generate a hallway a certain number of times. */
    public void generateAllHallways() {
        int i = (int)(getWidth() + getHeight() / 1.5);
        while (getNumHallways() != total_hallways && i != 0) {
            if (getNumHorizontalHallways() != total_horizontal_hallways) {
                generateVerticalHallway(determineALength(), determineNextCoordinates());
            }
            if (getNumVerticalHallways() != total_vertical_hallways){
                generateHorizontalHallway(determineALength(), determineNextCoordinates());
            }
            --i;
        }
    }

    /** This will generate all rectangular rooms.
     * If a generated rectangular room does not fit, it will try a different
     * size and coordinates until that room fits. If that does not work, then it will
     * only try to generate a rectangular room a certain number of times. */
    public void generateAllRooms() {
        int i = (int)(getWidth() + getHeight() / 1.5);
        while (getNumRooms() != total_rooms && i != 0) {
            generateRectangularRoom(determineALength(), determineALength(), determineNextCoordinatesRoom());
            --i;
        }
    }

    /** Used to print the coordinates in a list. */
    public void printArrayListTiles(ArrayList<Coordinates> c_list) {
        for (Coordinates coor : c_list) {
            System.out.print("(" + coor.getX() + ", " + coor.getY() + "), ");
        }
        System.out.println();
    }

    /** Helper function to generate a map.*/
    public void initMap(long input) {
        this.seed = input;
        generator = new Random(this.seed);
        placeableFloorCoordinatesRooms.clear();
        placeableFloorCoordinatesRooms.clear();
        roomList.clear();
        hallwayList.clear();
        horizontalHallwayList.clear();
        verticalHallwayList.clear();
    }

    /** Used to create a map of a given seed. */
    public void generateMap(long seed) {
        initMap(seed);
        determineNumRoomsAndHallways();
        generateHorizontalHallway(determineALength(), determineStartingCoordinates());
        generateAllHallways();
        generateAllRooms();
    }

}