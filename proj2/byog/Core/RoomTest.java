package byog.Core;

public class RoomTest {
    /** Light test of Room's constructor, as well as some get() and print methods. */
    public static void TestConstructor() {
        Room room = new Room(4, 4, 0, 0);
        System.out.println("Width: " + room.getWidth());
        System.out.println("Height: " + room.getHeight());
        System.out.println("Origin X Coordinate: " + room.getOriginXCoordinate());
        System.out.println("Origin Y Coordinate: " + room.getOriginYCoordinate());
        System.out.println("Hallway: " + room.isHallway());
        room.printRoomTiles();
        room.printRoomTileSize();

        Room room2 = new Room(2, 2, 2, 2);
        System.out.println("Width: " + room2.getWidth());
        System.out.println("Height: " + room2.getHeight());
        System.out.println("Origin X Coordinate: " + room2.getOriginXCoordinate());
        System.out.println("Origin Y Coordinate: " + room2.getOriginYCoordinate());
        System.out.println("Hallway: " + room2.isHallway());

        room2.printRoomTiles();
        room2.printRoomTileSize();


    }

    public static void main(String[] args) {
        TestConstructor();
    }
}
