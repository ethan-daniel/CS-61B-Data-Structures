package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class World implements Serializable {
    public static final TETile PLAYER = Tileset.PLAYER;
    public static final TETile FLOOR = Tileset.FLOOR;
    private final TETile[][] world;
    private final MapGenerator map;
    private final Player player;
    private static final TETile DOOR = Tileset.UNLOCKED_DOOR;
    private static boolean gotDoor;

    public class Player implements Serializable {
        private Coordinates position;

        /** Spawns a player and a door. */
        public Player(long seed) {
            ArrayList<Coordinates> allRoomCoordinates = new ArrayList<>();
            for (Room existingRoom : map.getRoomList()) {
                for (Coordinates coor : existingRoom.getRoomTiles().keySet()) {
                    if (existingRoom.getRoomTiles().get(coor).equals(FLOOR)) {
                        allRoomCoordinates.add(coor);
                    }
                }
            }

            Random generator = new Random(seed);
            int playerIndex = generator.nextInt(allRoomCoordinates.size());
            position = allRoomCoordinates.get(playerIndex);
            world[position.getX()][position.getY()] = PLAYER;

            int flag = 0;
            Coordinates doorPosition;
            while (flag == 0) {
                int doorIndex = generator.nextInt(allRoomCoordinates.size());
                if (doorIndex != playerIndex) {
                    doorIndex = generator.nextInt(allRoomCoordinates.size());
                    doorPosition = allRoomCoordinates.get(doorIndex);
                    world[doorPosition.getX()][doorPosition.getY()] = DOOR;
                    flag = 1;
                }
            }
        }

        private boolean canUpdatePosition(Coordinates coor) {
            return map.getWorld()[coor.getX()][coor.getY()].equals(FLOOR);
        }

        private boolean nextPositionisDoor(Coordinates coor) {
            return map.getWorld()[coor.getX()][coor.getY()].equals(DOOR);
        }
        private void updatePosition(int changeX, int changeY) {
            Coordinates updatedPosition = new Coordinates(position.getX() + changeX,
                    position.getY() + changeY);

            if (nextPositionisDoor(updatedPosition)) {
                gotDoor = true;
                world[position.getX()][position.getY()] = FLOOR;
                position = updatedPosition;
                world[position.getX()][position.getY()] = PLAYER;
                return;
            }

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

    public boolean isGotDoor() {
        return gotDoor;
    }

    public void movePlayer(String input) {
        player.moveWithInputString(input);
    }

    public TETile[][] getWorld() {
        return world;
    }

}
