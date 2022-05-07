package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class World implements Serializable {
    public static final TETile PLAYER = Tileset.PLAYER;
    public static final TETile FLOOR = Tileset.FLOOR;
    public static final TETile FLOWER = Tileset.FLOWER;
    private final TETile[][] world;
    private final MapGenerator map;
    private final Player player1;
    private final Player player2;
    private static final TETile DOOR = Tileset.LOCKED_DOOR;
    public static boolean existsDoor;
    private static boolean gotDoor;

    public class Player implements Serializable {
        private Coordinates position;
        private int collectedFlowers;
        private String left;
        private String right;
        private String up;
        private String down;

        /** Spawns a player and a door. */
        public Player(long seed, String l, String r, String u, String d) {
            this.left = l;
            this.right = r;
            this.up = u;
            this.down = d;
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

            if (!existsDoor) {
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
                existsDoor = true;
            }

            int placedFlowers = 0;
            Coordinates flowerPosition;
            while (placedFlowers != 2) {
                int flowerIndex = generator.nextInt(allRoomCoordinates.size());
                if (flowerIndex != playerIndex) {
                    flowerIndex = generator.nextInt(allRoomCoordinates.size());
                    flowerPosition = allRoomCoordinates.get(flowerIndex);
                    world[flowerPosition.getX()][flowerPosition.getY()] = FLOWER;
                    placedFlowers += 1;
                }
            }

        }

        private boolean canUpdatePosition(Coordinates coor) {
            return map.getWorld()[coor.getX()][coor.getY()].equals(FLOOR);
        }

        private boolean nextPositionisDoor(Coordinates coor) {
            return map.getWorld()[coor.getX()][coor.getY()].equals(DOOR);
        }

        private boolean nextPositionisFlower(Coordinates coor) {
            return map.getWorld()[coor.getX()][coor.getY()].equals(FLOWER);
        }

        private void updatePosition(int changeX, int changeY) {
            Coordinates updatedPosition = new Coordinates(position.getX() + changeX,
                    position.getY() + changeY);

            if (nextPositionisFlower(updatedPosition)) {
                ++collectedFlowers;
                world[position.getX()][position.getY()] = FLOOR;
                position = updatedPosition;
                world[position.getX()][position.getY()] = PLAYER;
            }

            if (nextPositionisDoor(updatedPosition)) {
                if (collectedFlowers > 0) {
                    gotDoor = true;
                    world[position.getX()][position.getY()] = FLOOR;
                    position = updatedPosition;
                    world[position.getX()][position.getY()] = PLAYER;
                    return;
                }
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
                if (letter.equals(up)) {
                    updatePosition(0, 1);

                } else if (letter.equals(left)) {
                    updatePosition(-1, 0);

                } else if (letter.equals(down)) {
                    updatePosition(0, -1);

                } else if (letter.equals(right)) {
                    updatePosition(1, 0);
                }
            }
        }

    }

    World(long seed, int width, int height) {
        this.map = new MapGenerator(width, height, randomEnvironment(seed));
        map.generateMap(seed);
        world = map.getWorld();
        player1 = new Player(seed, "a", "d", "w", "s");
        player2 = new Player(seed - 1, "j", "l", "i", "k");
    }

    private TETile randomEnvironment(long seed) {
        Random generator = new Random(seed);
        int num = generator.nextInt(4);

        if (num == 0) {
            return Tileset.SAND;
        } else if (num == 1) {
            return Tileset.MOUNTAIN;
        } else if (num == 2) {
            return Tileset.TREE;
        } else if (num == 3) {
            return Tileset.WATER;
        }

        return  Tileset.GRASS;
    }

    public boolean isGotDoor() {
        return gotDoor;
    }

    public void movePlayer1(String input) {
        player1.moveWithInputString(input);
    }

    public void movePlayer2(String input) {
        player2.moveWithInputString(input);
    }

    public TETile[][] getWorld() {
        return world;
    }

}
