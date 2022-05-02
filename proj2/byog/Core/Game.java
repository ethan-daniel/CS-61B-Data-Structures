package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.io.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    //private static String fileName = "./worldSave.bin";
    public static File f = new File("./worldSave.bin");

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        char firstChar = input.charAt(0);
        String menuOption = ("" + firstChar).toLowerCase();
        TETile[][] finalWorldFrame = null;

        if (menuOption.equals("n")) {
            long seed = Long.parseLong(input.replaceAll("\\D", ""));
            String seedString = "" + seed;
            String keyboardInput = input.substring(menuOption.length() + seedString.length());
            World world = new World(seed, WIDTH, HEIGHT);;
            world.movePlayer(keyboardInput);
            finalWorldFrame = world.getWorld();
            if (input.substring(input.length() - 2).toLowerCase().equals(":q")) {
                saveWorld(world);
            }

        } else if (menuOption.equals("l")) {
            String keyboardInput = input.substring(menuOption.length());
            World world = loadWorld();
            if (world != null) {
                world.movePlayer(keyboardInput);
                finalWorldFrame = world.getWorld();
            }
            if (input.substring(input.length() - 2).toLowerCase().equals(":q")) {
                saveWorld(world);
            }
        }

        return finalWorldFrame;
    }

    public void saveWorld(World world) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
            os.writeObject(world);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public World loadWorld() {
        if (f.exists()){
            try {
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(f));
                World world = (World) is.readObject();
                return world;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return null;
    }



}
