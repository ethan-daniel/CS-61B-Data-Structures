package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import javax.sound.midi.SysexMessage;
import java.util.Map;

public class MapGeneratorTest {
//
//    public static void TestRoomPlacement() {
//        TERenderer ter = new TERenderer();
//        ter.initialize(70, 50);
//
//        MapGenerator map = new MapGenerator(50, 50);
//        Room roomOne = new Room(10, 10, 0, 0);
//        Room roomTwo = new Room(10, 10, 11, 11);
//        map.drawRectangularRoom(roomOne);
//        //map.drawRectangularRoom(roomTwo);
//
//        //System.out.println(map.roomList.get(0).roomTiles);
//
//        ter.renderFrame(map.getWorld());
//
//    }
//    public static void TestOne() {
//        TERenderer ter = new TERenderer();
//        ter.initialize(70, 50);
//
//        MapGenerator map = new MapGenerator(30, 30);
//        Room roomOne = new Room(10, 10, 0, 0);
//        roomOne.printRoomTiles();
//        map.drawRectangularRoom(roomOne);
//
//
//
//
//        roomOne.printRoomTiles();
//        roomOne.printRoomTileSize();
//
//
//
//
//
//        ter.renderFrame(map.getWorld());

    /** Light test of MapGenerator's constructor, as well as some get() methods. */
    public static void TestConstructor() {
        MapGenerator map = new MapGenerator(50, 50);
        System.out.println("Width: " + map.getWidth());
        System.out.println("Height: " + map.getHeight());
        System.out.println("Num. Rooms: " + map.getNumRooms());

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

    public static void main(String[] args) {
//        //TestOne();
//        TestRoomPlacement();
//        TestConstructor();
        TestRectangularRoomRendering();
    }


}
