package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;


public class Game implements Serializable {
    /* Feel free to change the width and height. */
    TERenderer ter = new TERenderer();
    public static final int WIDTH = 100;
    public static final int HEIGHT = 50;
    private static boolean gameOver;
    private TETile HUDTile;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        initGame();
        drawMenu();
        String menuOption = singleKeyboardInput();

        if (menuOption.equals("n")) {
            long seed = getUserSeed();
            startNewGame(seed);

        } else if (menuOption.equals("l")) {
            startLoadedGame();

        } else if (menuOption.equals("q")) {
            drawExitGame("You have exited the game. Thank you for playing! (Program will automatically close)");
        }

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

        boolean existsQ = input.substring(input.length() - 2).toLowerCase().equals(":q");

        if (menuOption.equals("n")) {
            long seed = Long.parseLong(input.replaceAll("\\D", ""));
            String seedString = "" + seed;
            String keyboardInput = input.substring(menuOption.length() + seedString.length());
            World world = new World(seed, WIDTH, HEIGHT);
            world.movePlayer1(keyboardInput);
            world.movePlayer2(keyboardInput);
            finalWorldFrame = world.getWorld();
            if (existsQ) {
                saveWorld(world);
            }

        } else if (menuOption.equals("l")) {
            String keyboardInput = input.substring(menuOption.length());
            World world = loadWorld();
            if (world != null) {
                world.movePlayer1(keyboardInput);
                world.movePlayer2(keyboardInput);
                finalWorldFrame = world.getWorld();
            }
            if (existsQ) {
                saveWorld(world);
            }
        }

        return finalWorldFrame;
    }

    public void saveWorld(World world) {
        File F = new File("./worldSave.txt");
        F.setReadable(true, false);
        F.setExecutable(true, false);
        F.setWritable(true, false);
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(F));
            os.writeObject(world);
        } catch (FileNotFoundException e) {
            System.out.println("SAVE WORLD: FileNotFoundException");
        } catch (IOException e) {
            System.out.println("SAVE WORLD: IOException");
        }
    }
    public World loadWorld() {
        File F = new File("./worldSave.txt");
        F.setReadable(true, false);
        F.setExecutable(true, false);
        F.setWritable(true, false);
        if (F.exists()) {
            try {
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(F));
                return (World) is.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("LOAD WORLD: FileNotFoundException");
                System.exit(1);
            } catch (IOException e) {
                System.out.println("LOAD WORLD: IOException");
                System.exit(1);
            } catch (ClassNotFoundException e) {
                System.out.println("LOAD WORLD: ClassNotFoundException");
                System.exit(1);
            }
        }

        return null;
    }

    public void drawGetPlayerName() {

    }

    public void initGame() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void initDrawFrame() {
        StdDraw.clear();
        StdDraw.clear(Color.BLACK);

        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
    }
    public void drawMenu() {
        initDrawFrame();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 6, "CS61B: The Game");
        StdDraw.text(WIDTH / 2, HEIGHT / 3, "New Game (N)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 3) - 2, "Load Game (L)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 3) - 4, "Quit (Q)");
        StdDraw.show();
    }

    public void drawExitGame(String s) {
        initDrawFrame();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 6, s);
        StdDraw.show();
        StdDraw.pause(5000);
        System.exit(1);
    }

    public void drawSeedFrame(String s) {
        initDrawFrame();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 6, "Enter a seed (Press S to start): ");
        StdDraw.text(WIDTH / 2, HEIGHT / 3, s);
        StdDraw.show();
    }

    /** Writes the name of the tile the mouse is hovering over to the top right of the window. */
    public void drawMouseTile(World world) {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        TETile tile = world.getWorld()[x][y];

        if (!tile.equals(HUDTile)) {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledRectangle(1,HEIGHT - 1,8, 1);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.textLeft(1, HEIGHT - 1, tile.description());
        }
        StdDraw.show();
    }

    /** Waits for a user to click a key, and returns it. */
    public String singleKeyboardInput() {
        StringBuilder singleKeyboardInput = new StringBuilder();
        while (singleKeyboardInput.length() != 1) {
            if (StdDraw.hasNextKeyTyped()) {
                singleKeyboardInput.append(StdDraw.nextKeyTyped());
            }
        }
        return singleKeyboardInput.toString().toLowerCase();
    }

    /** Similar function to singleKeyboardInput, but also draws tiles that are moused over. */
    public String singleKeyboardInputInGame(World world) {
        StringBuilder singleKeyboardInput = new StringBuilder();
        while (singleKeyboardInput.length() != 1) {
            if (StdDraw.hasNextKeyTyped()) {
                singleKeyboardInput.append(StdDraw.nextKeyTyped());
            }
            drawMouseTile(world);
        }
        return singleKeyboardInput.toString().toLowerCase();
    }

    /** Gets user seed. Stops when user inputs s. */
    public long getUserSeed() {
        String seedString = " ";
        String temp;
        drawSeedFrame(seedString);
        while (!seedString.endsWith("S")) {
            temp = "";
            if (StdDraw.hasNextKeyTyped()) {
                temp += StdDraw.nextKeyTyped();
                seedString += temp.toUpperCase();
                drawSeedFrame(seedString);
            }
        }
        return Long.parseLong(seedString.substring(1, seedString.length() - 1));
    }

    public void gameLoop(World world) {
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world.getWorld());
        while (!gameOver) {
            String input = singleKeyboardInputInGame(world);
            if (input.toLowerCase().equals("q")) {
                saveWorld(world);
                gameOver = true;
                drawExitGame("You have exited the game. Thank you for playing! (Program will automatically close)");
                break;
            }

            world.movePlayer1(input);
            world.movePlayer2(input);
            StdDraw.show();
            ter.renderFrame(world.getWorld());
            if (world.isGotDoor()) {
                gameOver = true;
                drawExitGame("You both escaped! Thank you for playing! (Program will automatically close)");
            }
        }
    }

    public void startNewGame(long seed) {
        gameOver = false;
        World world = new World(seed, WIDTH, HEIGHT);
        gameLoop(world);
    }

    public void startLoadedGame() {
        World world = loadWorld();
        gameOver = false;
        if (world != null) {
            gameLoop(world);
        }
    }

}
