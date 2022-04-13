package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class MapGenerator {
    private static final TETile WALL = Tileset.WALL;
    private static final TETile FLOOR = Tileset.FLOOR;
    private TETile[][] world;
    private int WIDTH = 30;
    private int HEIGHT = 30;

    /** Checks if shape is in bounds of world */
    private boolean validShape(int width, int height, int x_pos, int y_pos) {
        if ((y_pos + height) > HEIGHT || (x_pos + width) > WIDTH) {
            return false;
        }

        return true;
    }

    /** Initializes world as a 30x30 world. */
    MapGenerator() {
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x != WIDTH; x += 1) {
            for (int y = 0; y != HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
    /** Initializes world with given width and height. */
    MapGenerator(int width, int height) {
        world = new TETile[width][height];
        WIDTH = width;
        HEIGHT = height;
        for (int x = 0; x != width; x += 1) {
            for (int y = 0; y != height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /** Returns world. */
    public TETile[][] getWorld() {
        return world;
    }

    /** Draws a row of a given tile. */
    private void drawRow(int x_pos, int y_pos, int len,
                               TETile texture) {
        for (int i = x_pos; i != x_pos + len; ++i) {
            world[i][y_pos] = texture;
        }
    }

    /** Draws a rectangular room. */
    public void addRectangularRoom(int width, int height,
                                    int x_pos, int y_pos) {
        if(!validShape(width, height, x_pos, y_pos)) {
            return;
        }

        drawRow(x_pos, y_pos, width + 1, WALL);
        for (int y = y_pos + 1; y != y_pos + height + 1; ++y) {
            world[x_pos][y] = WALL;
            drawRow(x_pos + 1, y, width - 1, FLOOR);
            world[x_pos + width][y] = WALL;
        }
        drawRow(x_pos, y_pos + height, width, WALL);
    }

    /** Draws a horizontal hallway. */
    public void addHorizontalHallway(int length,
                                            int x_pos, int y_pos) {

        if(!validShape(length, 3, x_pos, y_pos)) {
            return;
        }

    }

    /** Draws a vertical hallway. */
    public void addVerticalHallway(int length,
                                            int x_pos, int y_pos) {

        if(!validShape(3, length, x_pos, y_pos)) {
            return;
        }

    }


}

/** Possible implementation of seed is multiply the seed with numbers.
 * Then check if that number has a property (even, odd, etc.). Go row by row
 * generating pieces? */