package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class World implements Serializable {
    public static final TETile PLAYER = Tileset.PLAYER;
    public static final TETile FLOOR = Tileset.FLOOR;
    private Random generator;
    private TETile[][] world;
    private MapGenerator map;
    private Player player;

    public class Player implements Serializable{
        private Coordinates position;
        private ArrayList<Coordinates> allRoomCoordinates = new ArrayList<>();

        public Player(long seed) {
            for (Room existingRoom : map.getRoomList()) {
                for (Coordinates coor : existingRoom.getRoomTiles().keySet()) {
                    if (existingRoom.getRoomTiles().get(coor).equals(FLOOR)) {
                        allRoomCoordinates.add(coor);
                    }
                }
            }

            generator = new Random(seed);
            int index = generator.nextInt(allRoomCoordinates.size());
            position = allRoomCoordinates.get(index);
            world[position.getX()][position.getY()] = PLAYER;
        }

        private boolean canUpdatePosition(Coordinates coor) {
            return map.getWorld()[coor.getX()][coor.getY()].equals(FLOOR);
        }
        private void updatePosition(int changeX, int changeY) {
            Coordinates updatedPosition = new Coordinates(position.getX() + changeX,
                    position.getY() + changeY);
        if (!canUpdatePosition(updatedPosition)) {
            return;
        }
            world[position.getX()][position.getY()] = FLOOR;
            position = updatedPosition;
            world[position.getX()][position.getY()] = PLAYER;
        }

        public void moveWithInputString(String input) {
            for (int i = 0; i != input.length(); ++i) {
                char inputChar = input.charAt(i);
                String letter = ("" + inputChar).toLowerCase();
                if (letter.equals("w")) {
                    updatePosition(0, 1);

                } else if (letter.equals("a")) {
                    updatePosition(-1, 0);

                } else if (letter.equals("s")) {
                    updatePosition(0, -1);

                } else if (letter.equals("d")) {
                    updatePosition(1, 0);
                }
            }
        }

    }

    World(long seed, int width, int height) {
        this.map = new MapGenerator(width, height);
        map.generateMap(seed);
        world = map.getWorld();
        player = new Player(seed);
    }

    public void movePlayer(String input) {
        player.moveWithInputString(input);
    }

    public TETile[][] getWorld() {
        return world;
    }

}
