package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class GameTest {
    public static void testPlayWithInputString() {
        Game game = new Game();
        //game.playWithInputString("N12345S");

        TETile[][] worldFrame = game.playWithInputString("N922337203685477587");

        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);
        ter.renderFrame(worldFrame);
    }

    public static void testLoadPlayWithInputString() {
        Game game = new Game();
        //game.playWithInputString("N12345S");

        TETile[][] worldFrame = game.playWithInputString("N922337203685477587");
        //worldFrame = game.playWithInputString("LA");
        //worldFrame = game.playWithInputString("LA");
        //worldFrame = game.playWithInputString("LA");
        //worldFrame = game.playWithInputString("Ls");

        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);
        ter.renderFrame(worldFrame);
    }

    public static void testWriteUpPlayWithInputString() {
        Game game = new Game();

        // CONTROL
        //TETile[][] worldFrame = game.playWithInputString("N999SDDDWWWDDD");

        // PASS
//        TETile[][] worldFrame = game.playWithInputString("N999SDDD:Q");
//        worldFrame = game.playWithInputString("LWWWDDD");

        // PASS
//        TETile[][] worldFrame = game.playWithInputString("N999SDDD:Q");
//        worldFrame = game.playWithInputString("LWWW:Q");
//        worldFrame = game.playWithInputString("LDDD:Q");

        // PASS
        TETile[][] worldFrame = game.playWithInputString("N999SDDD:Q");
        worldFrame = game.playWithInputString("L:Q");
        worldFrame = game.playWithInputString("L:Q");
        worldFrame = game.playWithInputString("LWWWDDD");

        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);
        ter.renderFrame(worldFrame);
    }

    public static void main(String[] args) {
        //testPlayWithInputString();
        //testLoadPlayWithInputString();
        testWriteUpPlayWithInputString();
    }

}
