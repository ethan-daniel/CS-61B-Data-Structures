import edu.princeton.cs.algs4.Picture;
import java.lang.Math;

import java.awt.Color;

public class SeamCarver {
    private Picture pic;
    public SeamCarver(Picture picture) {
        pic = picture;
    }

    // current picture
    public Picture picture() {
        return pic;
    }

    // width of current picture
    public int width() {
        return pic.width();
    }

    // height of current picture
    public int height() {
        return pic.height();
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


        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        int[] seam = new int[height()];
        int currentX = minCol();
        seam[0] = currentX;

        int leftX;
        int rightX;
        //int middleX;

        int leftEnergy;
        int rightEnergy;


        for (int y = 1; y != height(); ++y) {
            if (currentX - 1 > 0) {

            }

            if (currentX + 1 < width()) {

            }

        }



        return seam;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {

    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {

    }

    private boolean validColRow(int x, int y) {
        return x >= 0 && x < width()
                && y >= 0 && y < height();
    }

    /** Computes the x-gradient. */
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

    /** Computes the y-gradient. */
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
