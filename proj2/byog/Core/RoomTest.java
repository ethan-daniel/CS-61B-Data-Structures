package byog.Core;

public class RoomTest {
    /** Light test of Room's constructor, as well as some get() and print methods. */
    public static void testConstructor() {
        Room room = new Room(6, 6, 0, 0);
        System.out.println("Width: " + room.getWidth());
        System.out.println("Height: " + room.getHeight());
        System.out.println("Origin X Coordinate: " + room.getOriginXCoordinate());
        System.out.println("Origin Y Coordinate: " + room.getOriginYCoordinate());
        System.out.println("Hallway: " + room.isHallway());
        System.out.println("Size of room: " + room.getRoomTileSize());
        System.out.println("Number of outer floor tiles: " + room.getNumHallwayEnds());
        System.out.println("Printing Room tiles: ");
        System.out.println("\n\n");

        Room room2 = new Room(5, 5, 0, 0);
        System.out.println("Width: " + room2.getWidth());
        System.out.println("Height: " + room2.getHeight());
        System.out.println("Origin X Coordinate: " + room2.getOriginXCoordinate());
        System.out.println("Origin Y Coordinate: " + room2.getOriginYCoordinate());
        System.out.println("Hallway: " + room2.isHallway());
        System.out.println("Size of room: " + room2.getRoomTileSize());
        System.out.println("Number of outer floor tiles: " + room2.getNumHallwayEnds());

        Room room3 = new Room(3, 5, 4, 2);
        System.out.println("Width: " + room3.getWidth());
        System.out.println("Height: " + room3.getHeight());
        System.out.println("Origin X Coordinate: " + room3.getOriginXCoordinate());
        System.out.println("Origin Y Coordinate: " + room3.getOriginYCoordinate());
        System.out.println("Hallway: " + room3.isHallway());
        System.out.println("Size of room: " + room3.getRoomTileSize());
        System.out.println("Number of outer floor tiles: " + room3.getNumHallwayEnds());
        System.out.println("Printing Room tiles: ");
        room3.printHashMapTiles(room3.getRoomTiles());
        System.out.println("Printing Hallway ends: ");
        room3.printArrayListTiles(room3.getHallwayEnds());


    }

    public static void main(String[] args) {
        testConstructor();
    }
}
