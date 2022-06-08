import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    private final Map<String, Node> nodes = new HashMap<>();

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        String[] arrNodes = nodes.keySet().toArray(new String[nodes.size()]);
        for (int i = 0; i != arrNodes.length; ++i) {
            Node n = nodes.get(arrNodes[i]);
            if (n.numEdges() == 0) {
                removeNode(arrNodes[i]);
            }
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        List<Long> vertices = new ArrayList<>();
        for (String id : nodes.keySet()) {
            long num = Long.parseLong(id);
            vertices.add(num);
        }
        //YOUR CODE HERE, this currently returns only an empty list.
        return vertices;
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        List<Long> vertices = new ArrayList<>();
        String s = String.valueOf(v);
        Node n = nodes.get(s);
        for (String vertex : n.edges) {
            long num = Long.parseLong(vertex);
            vertices.add(num);
        }

        return vertices;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double minDis = Double.MAX_VALUE;
        long minV = Long.parseLong(nodes.keySet().iterator().next());

        for (String id : nodes.keySet()) {
            long v = Long.parseLong(id);
            double vLon = lon(v);
            double vLat = lat(v);
            double distance = distance(vLon, vLat, lon, lat);

            if (distance < minDis) {
                minDis = distance;
                minV = v;
            }
        }

        return minV;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        String s = String.valueOf(v);
        Node n = nodes.get(s);
        double num = Double.parseDouble(n.lon);
        return num;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        String s = String.valueOf(v);
        Node n = nodes.get(s);
        double num = Double.parseDouble(n.lat);
        return num;
    }

    /** Add a node to the database. */
    void addNode(Node n) {
        this.nodes.put(n.id, n);
    }

    /** Add an edge to the database. */
    void addEdge(String from, String to) {
        this.nodes.get(to).edges.add(from);
        this.nodes.get(from).edges.add(to);
    }

    /** Remove a node from the database. */
    void removeNode(String id) {
        this.nodes.remove(id);
    }

    /** Returns a node from the database. */
    public Node getNode(String id) {
        return this.nodes.get(id);
    }

    /** A node. */
    static class Node {
        String id;
        String lon;
        String lat;
        Set<String> edges;
        String locationName;
        long priority;  // Smaller == Higher Priority

        Node prevNode;

        Node(String id, String lon, String lat) {
            this.id = id;
            this.lon = lon;
            this.lat = lat;
            this.edges = new HashSet<>();
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public int numEdges() {
            return edges.size();
        }

        public void setPriority(long p) {
            this.priority = p;
        }

        public void setPrevNode(Node n) {
            this.prevNode = n;
        }

        public long getPriority() {
            return priority;
        }

        public long getID() {
            return Long.parseLong(id);
        }

    }
}
