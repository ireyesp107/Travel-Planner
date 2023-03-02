package sol;

import src.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TravelController implements ITravelController<City, Transport> {

    // TODO: instantiate a new Graph object in the graph field

    private TravelGraph graph;

    public TravelController() {
    }

    /**
     * load reads the inputted file to create the graph's edges and vertices
     * @param citiesFile    the filename of the cities csv
     * @param transportFile the filename of the transportations csv
     * @return a string of the path (either BFS or Dijsktra)
     */

    @Override
    public String load(String citiesFile, String transportFile) {
        this.graph = new TravelGraph();
        // TODO: create an instance of the TravelCSVParser

        TravelCSVParser parser = new TravelCSVParser();

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null; // need explicit return null to account for Void type
        };

        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }



        Function<Map<String, String>, Void> addEdge = map -> {
            City origin = this.graph.getCity(map.get("origin"));
            City destination = this.graph.getCity(map.get("destination"));
            TransportType type = TransportType.fromString(map.get("type"));
            double price = Double.parseDouble(map.get("price"));
            double duration = Double.parseDouble(map.get("duration"));
            Transport transports = new Transport(origin, destination, type, price, duration);
            this.graph.addEdge(origin, transports);
            return null;
        };



        try{
            parser.parseTransportation(transportFile, addEdge);
        } catch (IOException e) {
            return "Error parsing file: " + transportFile;
        }
        return "Succesfully loaded cities and transportation file.";
    }


    /**
     * fastestRoute returns the fastest route depending on the speed
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return a list of transports indicating the fastest route
     */
    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        // TODO: implement this method!
        Function<Transport, Double> speed = edge ->{

            return edge.getMinutes();
        };
        Dijkstra<City, Transport> fastest = new Dijkstra<City, Transport>();

        return fastest.getShortestPath(this.graph, this.graph.getCity(source),this.graph.getCity(destination),speed );
    }

    /**
     * cheapestRoute returns the fastest route depending on the cost
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return a list of transports indicating the cheapest route
     */

    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        // TODO: implement this method!
        Function<Transport, Double> cheap = edge ->{
            return edge.getPrice();
        };
        Dijkstra<City, Transport> fastest = new Dijkstra<City, Transport>();

        return fastest.getShortestPath(this.graph, this.graph.getCity(source),this.graph.getCity(destination),cheap );
    }

    /**
     * mostDirectRoute returns the fastest route depending on the cost
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return a list of transports indicating the most direct route
     */
    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        // TODO: implement this method!
        BFS<City, Transport> bfs = new BFS<City, Transport>();
        return bfs.getPath(this.graph,this.graph.getCity(source),this.graph.getCity(destination));
    }

    /**
     *
     * @return the graph for testing purposes
     */
    public TravelGraph getGraph(){
        return this.graph;
    }

    /**
     * getTotalEdgeWeight is used to calculate to make sure we went the correct route in Testing
     * @param p
     * @param w
     * @return
     */
    public static double getTotalEdgeWeight(List<Transport> p, Function<Transport, Double> w) {
        double total = 0;
        for (Transport t : p) {
            total += w.apply(t);
        }
        return total;
    }
}
