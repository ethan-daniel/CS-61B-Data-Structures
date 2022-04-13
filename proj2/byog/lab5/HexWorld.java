/** Lab is not to be submitted, and therefore not graded. */
package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    /** Returns a random number between 0 and 1.0 */
    private static double randomNumberGen() {
        Random rand = new Random();
        return rand.nextDouble();
    }

    /** Draws an empty world */
    public static void initializeWorld(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /** Returns the hexagon height */
    private static int hexHeight(int side_len) {
        return side_len * 2;
    }

    /** Check if hexagon is in bounds of world */
    private static boolean validHex(int side_len, int x_pos, int y_pos) {
        if (y_pos + hexHeight(side_len) > HEIGHT || x_pos + side_len > WIDTH
        || x_pos - side_len < 0) {
            return false;
        }
        return true;
    }

    /** Draws a row based on a random number*/
    private static void addRow(TETile[][] world, int x_pos, int y_pos, int size_len, double rng) {
        if (rng > 0.75) {
            for (int i = x_pos; i != x_pos + size_len; ++i) {
                world[i][y_pos] = Tileset.FLOWER;
            }
        }
        else if (rng > 0.50 && rng <= 0.75) {
            for (int i = x_pos; i != x_pos + size_len; ++i) {
                world[i][y_pos] = Tileset.MOUNTAIN;
            }
        }
        else if (rng > 0.25 && rng <= 0.50) {
            for (int i = x_pos; i != x_pos + size_len; ++i) {
                world[i][y_pos] = Tileset.TREE;
            }
        }
        else {
            for (int i = x_pos; i != x_pos + size_len; ++i) {
                world[i][y_pos] = Tileset.SAND;
            }
        }
    }

    /** Draws a hexagon of a random tile given a side at starting x and y coordinates */
    public static void addHexagon(TETile[][] world, int side_len, int x_pos, int y_pos) {
        if (!validHex(side_len, x_pos, y_pos)) {
            return;
        }
        double rng = randomNumberGen();

        int current_len = side_len;
        int current_x_pos = x_pos;
        int y = y_pos;
        for (; y != y_pos + side_len; ++y, --current_x_pos, current_len += 2) {
            addRow(world, current_x_pos, y, current_len, rng);
        }
        current_len -= 2;
        ++current_x_pos;
        for (; y != y_pos + hexHeight(side_len); ++y, ++current_x_pos, current_len -= 2) {
            addRow(world, current_x_pos, y, current_len, rng);
        }
    }
    /** Draws a column of hexagons */
    private static void addHexagonColumn(TETile[][] world, int side_len, int x_pos, int y_pos, int num_hexagons) {
        int hexagon_height = hexHeight(side_len);
        for (int i = 0; i != num_hexagons; ++i, y_pos += hexagon_height){
            addHexagon(world, side_len, x_pos, y_pos);
        }
    }

    /** Draws a tesselation of hexagons */
    public static void tesselate(TETile[][] world, int side_len){
        int left_x_pt = (WIDTH / 2) - (side_len / 2);
        int right_x_pt = (WIDTH / 2) - (side_len / 2);
        int y_pt = 0;
        int num_columns = WIDTH / (side_len + 2);
        int num_hexagons = HEIGHT / hexHeight(side_len);

        for (int i = 0; i != (num_columns) / 2; ++i, y_pt += side_len,
                left_x_pt -= hexHeight(side_len) - 1,
                right_x_pt += hexHeight(side_len) - 1,
                --num_hexagons) {
            addHexagonColumn(world, side_len, left_x_pt, y_pt, num_hexagons);
            addHexagonColumn(world, side_len, right_x_pt, y_pt, num_hexagons);
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);
        tesselate(world, 3);
        ter.renderFrame(world);
    }
}
