package byog.Core;

import byog.TileEngine.TERenderer;

public class WorldTest {
    public static void testSpawnPlayer() {
        MapGenerator map = new MapGenerator(80, 30);
        map.generateMap(9223372036854775807L);


        System.out.println("Num. Rooms: " + map.getNumRooms());
        System.out.println("Num. Hallways: " + map.getNumHallways());
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);
        ter.renderFrame(map.getWorld());
    }

    public static void testInputStringPlayer() {
        World world = new World(9223372036854775807L, 80, 30);
        world.movePlayer("wdddddddds");

        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);
        ter.renderFrame(world.getWorld());
    }




    public static void main(String[] args) {
        //testSpawnPlayer();
        testInputStringPlayer();
    }
}
