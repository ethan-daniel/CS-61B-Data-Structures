import javax.management.MBeanRegistration;
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

    private String[][] render_grid;
    private double raster_ul_lon;
    private double raster_ul_lat;
    private double raster_lr_lon;
    private double raster_lr_lat;
    private int depth;
    private boolean query_success;
    private double[] lonDPP_depth;
    Map<String, Double> params;
    //private double width;

    public Rasterer() {
        params = new HashMap<>();
        lonDPP_depth = new double[MAX_DEPTH + 1];
        generateLonDPPValues();

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
        Map<String, Object> results = new HashMap<>();
//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                           + "your browser.");
        this.params = params;

        return results;
    }

    private Map<String, Object> getMapRasterHelper(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();


        return results;
    }

    /** Longitudinal distance per pixel calculation. */
    private double calculateLonDPP(double lrlon, double ullon, double width) {
        return (lrlon - ullon) / width;
    }

    /** Calculates the LonDPP values at each depth.  */
    private void generateLonDPPValues() {
        for (int i = 0; i != MAX_DEPTH + 1; ++i) {
            lonDPP_depth[i] = calculateLonDPP(ROOT_LRLON, ROOT_ULLON, TILE_SIZE) /
                    Math.pow(2, i);
        }
    }

    /** Returns true if a number is between two other numbers. */
    private boolean checkFits(double beginning, double end, double point) {
//        System.out.println("Beginning: " + beginning + " End: " +
//                end + " Point: " + point);

        return point >= beginning && point <= end;
    }



    /** Returns the upper-left corner tile for the query box.
     * ULLON -> LRLON (+ direction)
     * ULLAT -> LRLAT (- direction) */
    private String calculateULTile() {
        String tile = "";

        // Get depth
        double expectedLonDPP = calculateLonDPP(params.get("lrlon"),
                params.get("ullon"), TILE_SIZE);

        int depth = 0;
        while (expectedLonDPP < lonDPP_depth[depth]) {
            ++depth;
        }

        // Get x
        // Get y
        int numImages = (int) Math.pow(4, depth);
        int numImagesPerSide = (int) Math.sqrt(numImages);
        double image_width = Math.abs((ROOT_ULLON - ROOT_LRLON) / numImagesPerSide);
        double image_height = Math.abs((ROOT_ULLAT - ROOT_LRLAT) / numImagesPerSide);
        double image_left_longitude = ROOT_ULLON;
        double image_upper_latitude = ROOT_ULLAT;
        int x = 0;
        int y = 0;

        for (; x != numImagesPerSide; ++x) {
            if (image_left_longitude > params.get("ullon")) {
                --x;
                break;
            }
            image_left_longitude += image_width;
        }

        for (; y != numImagesPerSide; ++y) {
            if (image_upper_latitude < params.get("ullat")) {
                --y;
                break;
            }
                image_upper_latitude -= image_height;
        }

        tile = "d" + depth + "_x" + x + "_y" + y + ".png";
        //System.out.println(tile);

        return tile;
    }

    /** For testing ONLY*/
    public void tempSolve() {
        calculateULTile();

    }


    public static void main(String[] args) {
        Rasterer rast = new Rasterer();
        rast.generateLonDPPValues();

        Map<String, Double> query = new HashMap<>();
        query.put("lrlon", -122.24053369025242);
        query.put("ullon", -122.24163047377972);
        query.put("w",892.0);
        query.put("h", 875.0);
        query.put("ullat", 37.87655856892288);
        query.put("lrlat", 37.87548268822065);

        rast.getMapRaster(query);

        rast.tempSolve();

    }

}
