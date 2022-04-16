package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class MapGenerator {
    private static final TETile WALL = Tileset.WALL;
    private static final TETile FLOOR = Tileset.FLOOR;
    private final TETile[][] world;
    private int WIDTH = 30;
    private int HEIGHT = 30;
    private Random RANDOM;
    private final Set<Coordinates> floorSet;

    /** Initializes world as a 30x30 world. */
    MapGenerator() {
        floorSet = new HashSet<>();
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x != WIDTH; x += 1) {
            for (int y = 0; y != HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
    /** Initializes world with given width and height. */
    MapGenerator(int width, int height) {
        floorSet = new HashSet<>();
        world = new TETile[width][height];
        WIDTH = width;
        HEIGHT = height;
        for (int x = 0; x != width; x += 1) {
            for (int y = 0; y != height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /** Sets the given seed. */
    public void setSeed(long input) {
        RANDOM = new Random(input);
    }

    /** Generates a PseudoRandomNumber. */
    private long getPseudoRandomNumber() {
        return RANDOM.nextLong();
    }

    /** Returns world. */
    public TETile[][] getWorld() {
        return world;
    }

    /** Checks if a coordinate is a floor. */
    public boolean isFloor(int x, int y) {
        return floorSet.contains(new Coordinates(x, y));
    }

    /** Checks if shape is in bounds of world */
    private boolean validShape(int width, int height, int x_pos, int y_pos) {
        return (y_pos + height) <= HEIGHT && (x_pos + width) <= WIDTH;
    }

    /** Prints the x, y coordinates that are floors. */
    public void printFloorSet() {
        for (Coordinates cor : floorSet){
            System.out.print("(" + cor.getX() + ", " + cor.getY() + ") ");
        }
        System.out.println();
    }

    /** Draws a row of a given tile. */
    private void drawRow(int x_pos, int y_pos, int len, TETile texture) {
        for (int x = x_pos; x != x_pos + len; ++x) {
            if (world[x][y_pos] != FLOOR) {
                world[x][y_pos] = texture;
            }
            if (world[x][y_pos] == FLOOR) {
                floorSet.add(new Coordinates(x, y_pos));
            }
        }
    }

    /** Draws a rectangular room. */
    public void addRectangularRoom(int width, int height, int x_pos, int y_pos) {
        if(!validShape(width, height, x_pos, y_pos)) { return; }

        drawRow(x_pos, y_pos, width, WALL);
        for (int y = y_pos + 1; y != y_pos + height - 1; ++y) {
            if (world[x_pos][y] != FLOOR){
                world[x_pos][y] = WALL;
            }
            drawRow(x_pos + 1, y, width - 2, FLOOR);
            if(world[x_pos + width - 1][y] != FLOOR){
                world[x_pos + width - 1][y] = WALL;
            }
        }
        drawRow(x_pos, y_pos + height - 1, width, WALL);
    }

    /** Draws a horizontal hallway. */
    public void addHorizontalHallway(int length, int x_pos, int y_pos) {
        if(!validShape(length, 3, x_pos, y_pos)) { return; }

        drawRow(x_pos, y_pos, length, WALL);
        if (world[x_pos][y_pos + 1] != FLOOR){
            world[x_pos][y_pos + 1] = WALL;
        }
        drawRow(x_pos + 1, y_pos + 1, length - 2, FLOOR);
        if (world[x_pos + length - 1][y_pos + 1] != FLOOR){
            world[x_pos + length - 1][y_pos + 1] = WALL;
        }
        drawRow(x_pos, y_pos + 2, length, WALL);
    }

    /** Draws a vertical hallway. */
    public void addVerticalHallway(int length, int x_pos, int y_pos) {
        if(!validShape(3, length, x_pos, y_pos)) { return; }

        drawRow(x_pos, y_pos, 3, WALL);
        for (int y = y_pos + 1; y != y_pos + length - 1; ++y) {
            if (world[x_pos][y] != FLOOR){
                world[x_pos][y] = WALL;
            }
            drawRow(x_pos + 1, y, 1, FLOOR);
            if (world[x_pos + 2][y] != FLOOR) {
                world[x_pos + 2][y] = WALL;
            }
        }
        drawRow(x_pos, y_pos + length - 1, 3, WALL);
    }

    public void createRandomMap() {

        long num = getPseudoRandomNumber();
        System.out.println(num);




    }




}

/** Possible implementation of seed is multiply the seed with numbers.
 * Then check if that number has a property (even, odd, etc.). Go row by row
 * generating pieces? */

/** Make a function that will take in a seed and place the rectangles
 *  and hallways all around, connecting to each other.
 *  Then worry about the connecting/ breaking down the walls? */