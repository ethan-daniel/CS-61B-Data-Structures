package byog.Core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.sound.midi.SysexMessage;
import java.util.Random;
import java.lang.Math;

public class MapGenerator {
    public static final TETile WALL = Tileset.WALL;
    public static final TETile FLOOR = Tileset.FLOOR;
    private final TETile[][] world;
    private int WORLD_WIDTH;
    private int WORLD_HEIGHT;
    private int total_rooms;
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

    /** Returns the room list. */
    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    /** Returns the hallway list. */
    public ArrayList<Room> gethallwayList() {
        return hallwayList;
    }

    /** Returns the horizontal hallway list. */
    public ArrayList<Room> getHorizontalHallwayList() {
        return horizontalHallwayList;
    }

    /** Returns the vertical hallway list. */
    public ArrayList<Room> getVerticalHallwayList() {
        return verticalHallwayList;
    }

    /** Returns the list of outer floor coordinates. */
    public ArrayList<Coordinates> getPlaceableFloorCoordinates() {
        return placeableFloorCoordinates;
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

    /** Checks if hallway overlaps. */
    private boolean overlapsHallway(Room room) {
        for (Room existing_hallway : hallwayList) {
            for (Coordinates coordinates_key_e : existing_hallway.getRoomTiles().keySet()) {
                for (Coordinates coordinates_key_g : room.getRoomTiles().keySet()) {
                    if (coordinates_key_e.equals(coordinates_key_g)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void drawRectangularRoom(Room room) {
        if (!isPlaceableRoom(room) || overlapsRoom(room)){
            return;
        }
        drawRoom(room);
    }

    public void drawHallway(Room room) {
        System.out.println("NEW LOOP");
        if (!isPlaceableRoom(room)){
            return;
        }
        drawRoom(room);
        //placeableFloorCoordinates.clear();
        int x = room.getOriginXCoordinate();
        int y = room.getOriginYCoordinate();
        System.out.println("The x: " + x);
        System.out.println("The y: " + y);
        //placeableFloorCoordinates.remove(new Coordinates(x + 1, y + 1));

        if (placeableFloorCoordinates.isEmpty()) {
            for (Coordinates coor : room.getHallwayEnds()) {
                placeableFloorCoordinates.add(coor);
                //placeableFloorCoordinatesRooms.add(coor);
            }
            System.out.println("Placeablefloorcoordinates end: ");
            printArrayListTiles(placeableFloorCoordinates);
            return;
        }
        for (Coordinates coor : room.getHallwayEnds()) {
            System.out.println("Placeablefloorcoordinates: ");
            printArrayListTiles(placeableFloorCoordinates);
            System.out.println("loop coors: ");
            coor.print();
            placeableFloorCoordinates.remove(new Coordinates(x + 1, y + 1));
            if (!coor.equals(new Coordinates(x + 1, y + 1))) {
                placeableFloorCoordinates.add(coor);
            }
            placeableFloorCoordinatesRooms.add(coor);
        }
        System.out.println("Placeablefloorcoordinates end: ");
        printArrayListTiles(placeableFloorCoordinates);
        //placeableFloorCoordinates.remove(new Coordinates(x + 1, y + 1));
    }

    public void generateRectangularRoom(int width, int height, Coordinates coor) {
        Room room = new Room(width, height, coor.getX(), coor.getY());
        drawRectangularRoom(room);
        roomList.add(room);
    }

    public void generateHorizontalHallway(int length, Coordinates coor) {
        Room room = new Room(length, 3, coor.getX(), coor.getY());
        drawHallway(room);
        hallwayList.add(room);
        horizontalHallwayList.add(room);
    }

    public void generateVerticalHallway(int length, Coordinates coor) {
        Room room = new Room(3, length, coor.getX(), coor.getY());
        drawHallway(room);
        hallwayList.add(room);
        verticalHallwayList.add(room);
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

        System.out.println("Max rooms: " + max_rooms);
        System.out.println("Max h hallways: " + max_horizontal_hallways);
        System.out.println("Max v hallways: " + max_vertical_hallways);

        System.out.println("Determined total rooms: " + total_rooms);
        System.out.println("Determined total horizontal hallways: " + total_horizontal_hallways);
        System.out.println("Determined total vertical hallways: " + total_vertical_hallways);
    }

    /** Uses the given seed to determine the starting coordinates of the
     * map generation (Biased to start in the bottom left corner.). */
    public Coordinates determineStartingCoordinates() {
        int x = generator.nextInt((int) (WORLD_WIDTH / 4) - 1) + 1;
        int y = generator.nextInt((int) (WORLD_HEIGHT / 4) - 1) + 1;

        Coordinates coor = new Coordinates(x - 1, y - 1);
        return coor;
    }

    public int determineALength() {
        return generator.nextInt((int) (WORLD_WIDTH / 4) - 4 + 1) + 4;
    }
//
    public Coordinates determineNextCoordinates() {
        Coordinates temp = placeableFloorCoordinates.get(generator.nextInt(placeableFloorCoordinates.size()));
        placeableFloorCoordinatesRooms.remove(temp);
        int x = temp.getX();
        int y = temp.getY();

        return new Coordinates(x - 1, y - 1);
    }

    public Coordinates determineNextCoordinatesRoom() {
        Coordinates temp = placeableFloorCoordinatesRooms.get(generator.nextInt(placeableFloorCoordinatesRooms.size()));
        placeableFloorCoordinatesRooms.remove(temp);
        int x = temp.getX();
        int y = temp.getY();

        return new Coordinates(x - 1, y - 1);
    }

    public void generateAllHallways() {
        while (getNumHorizontalHallways() != total_horizontal_hallways ||
        getNumVerticalHallways() != total_vertical_hallways) {

        }
    }

    public void printArrayListTiles(ArrayList<Coordinates> c_list) {
        for (Coordinates coor : c_list) {
            System.out.print("(" + coor.getX() + ", " + coor.getY() + "), ");
        }
        System.out.println();
    }

    public void generateMap(long seed) {
        this.seed = seed;
        generator = new Random(this.seed);
        determineNumRoomsAndHallways();
        Coordinates start = determineStartingCoordinates();
        start.print();

        generateHorizontalHallway(determineALength(), start);
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());

        //printArrayListTiles(placeableFloorCoordinates);
//
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
//
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
//
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());

        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
//
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());

        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());

        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
//
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());

        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());

        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());
        generateHorizontalHallway(determineALength(), determineNextCoordinates());
        generateVerticalHallway(determineALength(), determineNextCoordinates());

//
        while (getNumRooms() != total_rooms) {
            generateRectangularRoom(determineALength(), determineALength(), determineNextCoordinatesRoom());

        }
//        generateRectangularRoom(determineALength(), determineALength(), determineNextCoordinatesRoom());
//        generateRectangularRoom(determineALength(), determineALength(), determineNextCoordinatesRoom());
//        generateRectangularRoom(determineALength(), determineALength(), determineNextCoordinatesRoom());
//        generateRectangularRoom(determineALength(), determineALength(), determineNextCoordinatesRoom());
//        generateRectangularRoom(determineALength(), determineALength(), determineNextCoordinatesRoom());
//        generateRectangularRoom(determineALength(), determineALength(), determineNextCoordinatesRoom());
//



    }

}