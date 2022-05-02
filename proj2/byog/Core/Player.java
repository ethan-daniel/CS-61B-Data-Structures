//package byog.Core;
//
//import byog.TileEngine.TETile;
//import byog.TileEngine.Tileset;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//public class Player extends WorldState{
//    public static final TETile PLAYER = Tileset.PLAYER;
//    public static final TETile FLOOR = Tileset.FLOOR;
//    private Coordinates position;
//    private ArrayList<Coordinates> allRoomCoordinates = new ArrayList<>();
//    private Random generator;
//    private MapGenerator map;
//
//
//    public Player(long seed) {
//        for (Room existingRoom : map.getRoomList()) {
//            for (Coordinates coor : existingRoom.getRoomTiles().keySet()) {
//                if (existingRoom.getRoomTiles().get(coor).equals(FLOOR)) {
//                    allRoomCoordinates.add(coor);
//                }
//            }
//        }
//
//        generator = new Random(seed);
//        int index = generator.nextInt(allRoomCoordinates.size());
//        position = allRoomCoordinates.get(index);
//        this.map = map;
//        this.map.getWorld()[position.getX()][position.getY()] = PLAYER;
//    }
//
//    private boolean canUpdatePosition(Coordinates coor) {
//        return map.getWorld()[coor.getX()][coor.getY()].equals(FLOOR);
//    }
//    private void updatePosition(int changeX, int changeY) {
//        Coordinates updatedPosition = new Coordinates(position.getX() + changeX,
//                position.getY() + changeY);
////        if (!canUpdatePosition(updatedPosition)) {
////            return;
////        }
//        map.getWorld()[position.getX()][position.getY()] = FLOOR;
//        position = updatedPosition;
//        map.getWorld()[position.getX()][position.getY()] = PLAYER;
//    }
//
//    public void moveWithInputString(String input) {
//        for (int i = 0; i != input.length(); ++i) {
//            char inputChar = input.charAt(i);
//            String letter = ("" + inputChar).toLowerCase();
//
//            if (letter == "w") {
//                updatePosition(0, 1);
//
//            } else if (letter == "a") {
//                updatePosition(-1, 0);
//
//            } else if (letter == "s") {
//                updatePosition(0, -1);
//
//            } else if (letter == "d") {
//                updatePosition(1, 0);
//            }
//        }
//    }
//
//}
