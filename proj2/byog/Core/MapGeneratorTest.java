package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class MapGeneratorTest {

    public static void TestPieces() {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);

        MapGenerator map = new MapGenerator(30, 30);
        map.addRectangularRoom(5,5, 5, 5);
        map.addRectangularRoom(5, 5, 10, 10);
        // will overlap the previous rectangles. possible issue?
        // map.addRectangularRoom(20, 20, 5, 5);



        ter.renderFrame(map.getWorld());

    }

    public static void main(String[] args) {
        TestPieces();
    }

}
