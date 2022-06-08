import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    public static final int TILE_SIZE = 256;
    private static final int MAX_DEPTH = 7;

    private String[][] renderGrid;
    private double rasterUlLon;
    private double rasterUlLat;
    private double rasterLrLon;
    private double rasterLrLat;
    private int depth;
    private boolean querySuccess;
    private final double[] lonDPPDepth;
    Map<String, Double> paramaters;

    public Rasterer() {
        paramaters = new HashMap<>();
        lonDPPDepth = new double[MAX_DEPTH + 1];
        generateLonDPPValues();
        querySuccess = false;

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results;
//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                           + "your browser.");
        results = getMapRasterHelper(params);

        return results;
    }

    private Map<String, Object> getMapRasterHelper(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();

        this.paramaters = params;
        depth = calculateDepth();

        querySuccess = checkQuery();
        querySuccess = true;
        results.put("query_success", querySuccess);
        if (!querySuccess) {
            return results;
        }
        generateLonDPPValues();
        calculateRenderGrid();

        results.put("render_grid", renderGrid);
        results.put("raster_ul_lon", rasterUlLon);
        results.put("raster_ul_lat", rasterUlLat);
        results.put("raster_lr_lon", rasterLrLon);
        results.put("raster_lr_lat", rasterLrLat);
        results.put("depth", depth);

        return results;
    }

    /** Checks that the query box is located completely inside
     * the root longitude/ latitudes. */
    private boolean checkQuery() {
        if (paramaters.get("ullon") > paramaters.get("lrlon")
                || paramaters.get("ullat") < paramaters.get("lrlat")) {
            return false;
        }

        return (paramaters.get("lrlon") < ROOT_LRLON)
                && (paramaters.get("ullon") > ROOT_ULLON)
                && (paramaters.get("lrlat") > ROOT_LRLAT)
                && (paramaters.get("ullat") < ROOT_ULLAT);
        //System.out.println(querySuccess);
    }

    /** Longitudinal distance per pixel calculation. */
    private double calculateLonDPP(double lrlon, double ullon, double width) {
        return (lrlon - ullon) / width;
    }

    /** Calculates the LonDPP values at each depth.  */
    private void generateLonDPPValues() {
        for (int i = 0; i != MAX_DEPTH + 1; ++i) {
            lonDPPDepth[i] = calculateLonDPP(ROOT_LRLON, ROOT_ULLON, TILE_SIZE)
                    / Math.pow(2, i);
        }
    }

    /** Calculates the depth for rastering. */
    private int calculateDepth() {
        double expectedLonDPP = calculateLonDPP(paramaters.get("lrlon"),
                paramaters.get("ullon"), paramaters.get("w"));

        int calcDepth = 0;
        while (expectedLonDPP < lonDPPDepth[calcDepth] && calcDepth != MAX_DEPTH) {
            ++calcDepth;
        }
        return calcDepth;
    }

    /** Helper to set a corner with a value. */
    private void setRasterCornerValue(String corner, double value) {
        if (corner.equals("lrlon")) {
            rasterLrLon = value;
        } else if (corner.equals("ullon")) {
            rasterUlLon = value;
        } else if (corner.equals("ullat")) {
            rasterUlLat = value;
        } else if (corner.equals("lrlat")) {
            rasterLrLat = value;
        }
    }

    /** Returns the data of a corner tile for the query box.
     * ULLON -> LRLON (+ direction)
     * ULLAT -> LRLAT (- direction) */
    private int[] calculateCornerTile(String cornerLon, String cornerLat) {
        int[] data = new int[3];

        // Get x
        // Get y
        int numImages = (int) Math.pow(4, depth);
        int numImagesPerSide = (int) Math.sqrt(numImages);
        double imageWidth = Math.abs((ROOT_ULLON - ROOT_LRLON) / numImagesPerSide);
        double imageHeight = Math.abs((ROOT_ULLAT - ROOT_LRLAT) / numImagesPerSide);
        double imageLeftLongitude = ROOT_ULLON;
        double imageUpperLatitude = ROOT_ULLAT;

        int x = 0;
        int y = 0;
        for (; x != numImagesPerSide; ++x) {
            if (imageLeftLongitude > paramaters.get(cornerLon)) {
                break;
            }
            imageLeftLongitude += imageWidth;
        }

        for (; y != numImagesPerSide; ++y) {
            if (imageUpperLatitude < paramaters.get(cornerLat)) {
                break;
            }
            imageUpperLatitude -= imageHeight;
        }

        if (cornerLon.equals("ullon") && x != 0) {
            setRasterCornerValue(cornerLon, imageLeftLongitude - imageWidth);
        } else {
            setRasterCornerValue(cornerLon, imageLeftLongitude);
        }

        if (cornerLat.equals("ullat") && y != 0) {
            setRasterCornerValue(cornerLat, imageUpperLatitude + imageHeight);
        } else {
            setRasterCornerValue(cornerLat, imageUpperLatitude);
        }

        if (x != 0) {
            --x;
        }
        if (y != 0) {
            --y;
        }

        //tile = "d" + depth + "_x" + x + "_y" + y + ".png";
        data[0] = depth;
        data[1] = x;
        data[2] = y;
//        System.out.println(data[0] + ", " + data[1] + ", " + data[2]);
//        System.out.println(rasterUlLon);
//        System.out.println(rasterUlLat);

        return data;
    }

    /** Calculates render grid. */
    private void calculateRenderGrid() {
        int[] upperLeft = calculateCornerTile("ullon", "ullat");
        int[] lowerRight = calculateCornerTile("lrlon", "lrlat");

        int xMin = upperLeft[1];
        int xMax = lowerRight[1];
        int yMin = upperLeft[2];
        int yMax = lowerRight[2];

        System.out.println("xMin: " + xMin + ", xMax: " + xMax + ", yMin: " + yMin
                + ", yMax: " + yMax);

        String[] renderRow;
        renderGrid = new String[yMax - yMin + 1][xMax - xMin + 1];

        int i;
        int j = 0;
        for (int y = upperLeft[2]; y <= lowerRight[2]; ++y) {
            i = 0;
            renderRow = new String[xMax - xMin + 1];
            for (int x = upperLeft[1]; x <= lowerRight[1]; ++x) {
                String tile = "d" + depth + "_x" + x + "_y" + y + ".png";
                renderRow[i] = tile;
                ++i;
            }
            renderGrid[j] = renderRow;
            ++j;
        }
    }

    private void testCalculateCornerTile() {
        calculateCornerTile("ullon", "ullat");  // upper-left corner
        calculateCornerTile("ullon", "lrlat"); // lower-left corner
        calculateCornerTile("lrlon", "ullat"); // upper-right corner
        calculateCornerTile("lrlon", "lrlat"); // lower-right corner

        System.out.println("rasterUlLat: " + rasterUlLat);
        System.out.println("rasterUlLon: " + rasterUlLon);
        System.out.println("rasterLrLat: " + rasterLrLat);
        System.out.println("rasterLrLon: " + rasterLrLon);

    }

    /** For testing ONLY*/
    public void tempSolve() {
        testCalculateCornerTile();
//        calculateRenderGrid();
//        System.out.println(checkQuery());
    }


    private static Map<String, Double> test() {
        Map<String, Double> query = new HashMap<>();
        query.put("lrlon", -122.24053369025242);
        query.put("ullon", -122.24163047377972);
        query.put("w", 892.0);
        query.put("h", 875.0);
        query.put("ullat", 37.87655856892288);
        query.put("lrlat", 37.87548268822065);

        return query;
    }

    private static Map<String, Double> test1234() {
        Map<String, Double> query = new HashMap<>();
        query.put("lrlon", -122.20908713544797);
        query.put("ullon", -122.3027284165759);
        query.put("w", 305.0);
        query.put("h", 300.0);
        query.put("ullat", 37.88708748276975);
        query.put("lrlat", 37.848731523430196);

        return query;
    }

    private static Map<String, Double> testTwelveImages() {
        Map<String, Double> query = new HashMap<>();
        query.put("lrlon", -122.2104604264636);
        query.put("ullon", -122.30410170759153);
        query.put("w", 1091.0);
        query.put("h", 566.0);
        query.put("ullat", 37.870213571328854);
        query.put("lrlat", 37.8318576119893);

        return query;
    }

    public static void main(String[] args) {
        Rasterer rast = new Rasterer();

        //Map<String, Double> query = query = test();
        //Map<String, Double> query = query = test1234();
        Map<String, Double> query = testTwelveImages();

        rast.getMapRaster(query);

        rast.tempSolve();

    }

}
