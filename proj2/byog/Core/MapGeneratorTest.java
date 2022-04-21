package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class MapGeneratorTest {

    /** Light test of MapGenerator's constructor, as well as some get() methods. */
    public static void TestConstructor() {
        MapGenerator map = new MapGenerator(50, 50);
        System.out.println("Width: " + map.getWidth());
        System.out.println("Height: " + map.getHeight());
        System.out.println("Num. Rooms: " + map.getNumRooms());
        System.out.println("Num. Hallways: " + map.getNumHallways());

    }

    /** Visual test of drawing rectangular rooms. Room 1 and 3 should appear on the map
     * and room 2 should not since it would overlap room 1. */
    public static void TestRectangularRoomRendering() {
        MapGenerator map = new MapGenerator(50, 50);
        Room room = new Room(10, 10, 4, 4);
        Room room2 = new Room(10, 10, 0, 0);
        Room room3 = new Room(5, 5, 30, 30);

        map.drawRectangularRoom(room);
        map.drawRectangularRoom(room2);
        map.drawRectangularRoom(room3);

        System.out.println("Num. Rooms: " + map.getNumRooms());
        TERenderer ter = new TERenderer();
        ter.initialize(50, 50);
        ter.renderFrame(map.getWorld());

    }

    /** Visual test of drawing hallways near a rectangular room. */
    public static void TestHallwayRendering() {
        MapGenerator map = new MapGenerator(50, 50);
        Room room = new Room(10, 15, 4, 4);

        Room hallway = new Room(10, 3, 7, 6);
        Room hallway2 = new Room(10, 3, 12, 14);

        map.drawRectangularRoom(room);
        map.drawHallway(hallway);
        map.drawHallway(hallway2);

        System.out.println("Num. Rooms: " + map.getNumRooms());
        System.out.println("Num. Hallways: " + map.getNumHallways());
        TERenderer ter = new TERenderer();
        ter.initialize(50, 50);
        ter.renderFrame(map.getWorld());
    }

    public static void main(String[] args) {
//        TestConstructor();
//        TestRectangularRoomRendering();
        TestHallwayRendering();
    }


}
