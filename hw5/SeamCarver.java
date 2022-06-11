import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private final int DNE = -1;
    private Picture pic;
    private double[][] energies;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        pic = picture;
        width = picture.width();
        height = picture.height();
        calculateEnergies();
    }

    // current picture
    public Picture picture() {
        return new Picture(pic);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (!validColRow(x, y)) {
            throw new java.lang.IndexOutOfBoundsException("Out of Bounds");
        }

        return xGradient(x, y) + yGradient(x, y);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] retSeam;
        transpose();
        retSeam = findVerticalSeam();
        transpose();

        return retSeam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int height = energies[0].length;
        int width = energies.length;
        int[] seam = new int[height];
        int[] retSeam = new int[height];
        double totalEnergy = 0;
        double minTotalEnergy = Double.MAX_VALUE;
        int currentX;

        for (int x = 0; x != width - 1; ++x) {    // do this calculation for width # times
            seam[0] = x;
            currentX = x;
            totalEnergy += energies[currentX][0];
            for (int y = 1; y != height; ++y) {   // vertical seam per x
                if (currentX - 1 < 0 && currentX + 1 > width) { // left and right DNE
                    currentX = compareXs(DNE, currentX, DNE, y);
                    totalEnergy += energies[currentX][y];

                } else if (currentX - 1 < 0 && currentX + 1 < width) {    // left DNE
                    currentX = compareXs(DNE, currentX, currentX + 1, y);
                    totalEnergy += energies[currentX][y];
                } else if (currentX + 1 > width && currentX - 1 > 0) {    // right DNE
                    currentX = compareXs(currentX - 1, currentX, DNE, y);
                    totalEnergy += energies[currentX][y];

                } else {    // left and right exists
                    currentX = compareXs(currentX - 1, currentX, currentX + 1, y);
                    totalEnergy += energies[currentX][y];
                }
                seam[y] = currentX;
            }
            if (totalEnergy < minTotalEnergy) {
                minTotalEnergy = totalEnergy;
                System.arraycopy(seam, 0, retSeam, 0, seam.length);
            }
            totalEnergy = 0.0;
        }
        return retSeam;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam.length == 0) {
            return;
        }
        if (isValidSeam(seam)) {
            pic = new Picture(SeamRemover.removeHorizontalSeam(pic, seam));
            --height;
            calculateEnergies();
        } else {
            throw new java.lang.IllegalArgumentException();
        }
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        if (seam.length == 0) {
            return;
        }
        if (isValidSeam(seam)) {
            pic = new Picture(SeamRemover.removeHorizontalSeam(pic, seam));
            --width;
            calculateEnergies();
        } else {
            throw new java.lang.IllegalArgumentException();
        }
    }

    private boolean validColRow(int x, int y) {
        return x >= 0 && x < width()
                && y >= 0 && y < height();
    }

    /**
     * Computes the x-gradient.
     */
    private int xGradient(int x, int y) {
        int leftX;
        int rightX;

        if ((x - 1) < 0) {
            leftX = width() - 1;
        } else {
            leftX = x - 1;
        }

        if ((x + 1) >= width()) {
            rightX = 0;
        } else {
            rightX = x + 1;
        }

        Color leftColor = pic.get(leftX, y);
        Color rightColor = pic.get(rightX, y);

        int rX = Math.abs(leftColor.getRed() - rightColor.getRed());
        int gX = Math.abs(leftColor.getGreen() - rightColor.getGreen());
        int bX = Math.abs(leftColor.getBlue() - rightColor.getBlue());

        return (rX * rX) + (gX * gX) + (bX * bX);
    }

    /**
     * Computes the y-gradient.
     */
    private int yGradient(int x, int y) {
        int lowerY;
        int upperY;

        if ((y - 1) < 0) {
            lowerY = height() - 1;
        } else {
            lowerY = y - 1;
        }

        if ((y + 1) >= height()) {
            upperY = 0;
        } else {
            upperY = y + 1;
        }

        Color lowerColor = pic.get(x, lowerY);
        Color upperColor = pic.get(x, upperY);

        int rY = Math.abs(lowerColor.getRed() - upperColor.getRed());
        int gY = Math.abs(lowerColor.getGreen() - upperColor.getGreen());
        int bY = Math.abs(lowerColor.getBlue() - upperColor.getBlue());

        return (rY * rY) + (gY * gY) + (bY * bY);
    }

    /**
     * Chooses the smallest energy in the row in range of 3 choices.
     */
    private int compareXs(int leftX, int middleX, int rightX, int y) {
        if (leftX == DNE && rightX == DNE) {
            return middleX;
        } else if (leftX == DNE) {
            if (energies[rightX][y] > energies[middleX][y]) {
                return middleX;
            } else {
                return rightX;
            }
        } else if (rightX == DNE) {
            if (energies[middleX][y] > energies[leftX][y]) {
                return leftX;
            } else {
                return middleX;
            }
        } else {
            double min = energies[leftX][y];
            int ret = leftX;
            if (energies[middleX][y] < min) {
                min = energies[middleX][y];
                ret = middleX;
            }
            if (energies[rightX][y] < min) {
                ret = rightX;
            }
            return ret;
        }
    }

    private void transpose() {
        int height = energies[0].length;
        int width = energies.length;

        double[][] transposed = new double[height][width];

        for (int x = 0; x != height; ++x) {
            for (int y = 0; y != width; ++y) {
                transposed[x][y] = energies[y][x];
            }
        }
        energies = transposed;
    }

    private boolean isValidSeam(int[] seam) {
        for (int i = 0; i != seam.length - 1; ++i) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                return false;
            }
        }
        return true;
    }

    private void calculateEnergies() {
        energies = new double[width()][height()];

        for (int x = 0; x != width(); ++x) {
            for (int y = 0; y != height(); ++y) {
                energies[x][y] = energy(x, y);
            }
        }
    }
//    private int minCol() {
//        double minEnergy = energy(0, 0);
//        int min = 0;
//
//        for (int x = 1; x != width(); ++x) {
//            double currEnergy = energy(x, 0);
//            if (currEnergy > minEnergy) {
//                minEnergy = currEnergy;
//                min = x;
//            }
//        }
//
//        return min;
//    }
//
//    private int minRow() {
//        double minEnergy = energy(0, 0);
//        int min = 0;
//
//        for (int y = 1; y != height(); ++y) {
//            double currEnergy = energy(0, y);
//            if (currEnergy > minEnergy) {
//                minEnergy = currEnergy;
//                min = y;
//            }
//        }
//
//        return min;
//    }


}
