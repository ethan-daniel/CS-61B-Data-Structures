package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class MapGeneratorTest {

    public static void TestPieces() {
        TERenderer ter = new TERenderer();
        ter.initialize(70, 50);

        MapGenerator map = new MapGenerator(30, 30);
        //map.addRectangularRoom(7, 7, 6, 6);
        //map.addRectangularRoom(9, 8, 10, 10);
        //map.addRectangularRoom(9, 6, 14, 15);
        // will overlap the previous rectangles. possible issue?
//        map.addRectangularRoom(20, 20, 5, 5);

        //map.addHorizontalHallway(5, 0, 0);
        //System.out.println(map.isFloor(4, 1));
        //map.addVerticalHallway(7, 15, 4);

        map.addHorizontalHallway(5, 0, 0);


        map.setSeed(100);

        map.printFloorSet();

        //map.createRandomMap();


        ter.renderFrame(map.getWorld());

    }

    public static void TestCoordinates() {
        TERenderer ter = new TERenderer();
        ter.initialize(70, 50);

        MapGenerator map = new MapGenerator(30, 30);

        //map.addRectangularRoom(5, 5, 0, 0);
        //map.addHorizontalHallway(5, 0, 0);
        //map.addVerticalHallway(5, 0, 0);
        map.printFloorSet();

        ter.renderFrame(map.getWorld());



    }

    public static void main(String[] args) {
        //TestPieces();
        //TestCoordinates();
    }

}
