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
        room.printFloorTiles(room.getOuterFloorTiles());
        System.out.println("Printing north floor tiles: ");
        room.printFloorTiles(room.getNorthFloors());
        System.out.println("Printing south floor tiles: ");
        room.printFloorTiles(room.getSouthFloors());
        System.out.println("Printing west floor tiles: ");
        room.printFloorTiles(room.getWestFloors());
        System.out.println("Printing east floor tiles: ");
        room.printFloorTiles(room.getEastFloors());

        System.out.println("\n\n");

        Room room2 = new Room(5, 5, 0, 0);
        System.out.println("Width: " + room2.getWidth());
        System.out.println("Height: " + room2.getHeight());
        System.out.println("Origin X Coordinate: " + room2.getOriginXCoordinate());
        System.out.println("Origin Y Coordinate: " + room2.getOriginYCoordinate());
        System.out.println("Hallway: " + room2.isHallway());
        System.out.println("Size of room: " + room.getRoomTileSize());
        System.out.println("Number of outer floor tiles: " + room.getNumOuterFloorTiles());
        System.out.println("Printing Room tiles: ");

        System.out.println("Printing outer-floor tiles: ");
        room2.printFloorTiles(room2.getOuterFloorTiles());
        System.out.println("Printing north floor tiles: ");
        room2.printFloorTiles(room2.getNorthFloors());
        System.out.println("Printing south floor tiles: ");
        room2.printFloorTiles(room2.getSouthFloors());
        System.out.println("Printing west floor tiles: ");
        room2.printFloorTiles(room2.getWestFloors());
        System.out.println("Printing east floor tiles: ");
        room2.printFloorTiles(room2.getEastFloors());


    }

    public static void main(String[] args) {
        TestConstructor();
    }
}
