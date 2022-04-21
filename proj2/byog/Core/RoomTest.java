package byog.Core;

public class RoomTest {
    /** Light test of Room's constructor, as well as some get() and print methods. */
    public static void TestConstructor() {
        Room room = new Room(6, 6, 0, 0);
        System.out.println("Width: " + room.getWidth());
        System.out.println("Height: " + room.getHeight());
        System.out.println("Origin X Coordinate: " + room.getOriginXCoordinate());
        System.out.println("Origin Y Coordinate: " + room.getOriginYCoordinate());
        System.out.println("Hallway: " + room.isHallway());
        System.out.println("Size of room: " + room.getRoomTileSize());
        System.out.println("Number of outer floor tiles: " + room.getNumOuterFloorTiles());
        System.out.println("Printing Room tiles: ");
        room.printRoomTiles();
        System.out.println("Printing outer-floor tiles: ");
        room.printOuterFloorTiles();

        System.out.println("\n\n");

        Room room2 = new Room(3, 3, 2, 2);
        System.out.println("Width: " + room2.getWidth());
        System.out.println("Height: " + room2.getHeight());
        System.out.println("Origin X Coordinate: " + room2.getOriginXCoordinate());
        System.out.println("Origin Y Coordinate: " + room2.getOriginYCoordinate());
        System.out.println("Hallway: " + room2.isHallway());
        System.out.println("Size of room: " + room.getRoomTileSize());
        System.out.println("Number of outer floor tiles: " + room.getNumOuterFloorTiles());
        System.out.println("Printing Room tiles: ");
        room2.printRoomTiles();
        System.out.println("Printing outer-floor tiles: ");
        room2.printOuterFloorTiles();


    }

    public static void main(String[] args) {
        TestConstructor();
    }
}
