public class NBody {
    public static double readRadius (String filename) {
        In in = new In(filename);

        int firstItemInFile = in.readInt();
        double secondItemInFile = in.readDouble();

        return secondItemInFile;

    }

    public static void main(String[] args) {

    }


}