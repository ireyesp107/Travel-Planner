package test;

import org.junit.Test;
import sol.Dijkstra;
import sol.TravelController;
import src.City;
import src.IDijkstra;
import src.ITravelController;
import src.Transport;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;


import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Dijkstra's tests should all go in this class!
 * The test we've given you will pass if you've implemented Dijkstra's correctly, but we still
 * expect you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */

public class DijkstraTest {

    private static final double DELTA = 0.001;

    private SimpleGraph graph;
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     * <p>
     * TODO: create more setup methods!
     */


    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.b));
        this.graph.addEdge(this.a, new SimpleEdge(3, this.a, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.e));
        this.graph.addEdge(this.c, new SimpleEdge(6, this.c, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(2, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph.addEdge(this.d, new SimpleEdge(5, this.e, this.d));
    }

    @Test
    public void testSimple() {
        this.createSimpleGraph();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        // a -> c -> d -> b
        List<SimpleEdge> path =
                dijkstra.getShortestPath(this.graph, this.a, this.b, edgeWeightCalculation);
        assertEquals(6, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(3, path.size());

        // c -> d -> b
        path = dijkstra.getShortestPath(this.graph, this.c, this.b, edgeWeightCalculation);
        assertEquals(3, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    /**
     * test for file 'cities1'
     */
    @Test
    public void testDijsktraCities1() {
        Function<Transport, Double> edgeWeightCalculation1 = e -> e.getMinutes();
        Function<Transport, Double> edgeWeightCalculation2 = e -> e.getPrice();
        ITravelController<City, Transport> myController = new TravelController();
        myController.load("/Users/amaris/IdeaProjects/travel-planner-amarisg25-ireyesp107/data/cities1.csv",
                "/Users/amaris/IdeaProjects/travel-planner-amarisg25-ireyesp107/data/transport1.csv");



        // test for fastest path from NYC to Providence because it has a more expensive route
        List<Transport> p1 = myController.fastestRoute("New York City", "Providence");
        System.out.println(p1);
        assertEquals(p1.size(), 2);
        assertEquals(TravelController.getTotalEdgeWeight(p1, edgeWeightCalculation1), 130.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p1, edgeWeightCalculation2), 280.0, DELTA);
        System.out.println(p1);

        // test fastest from NYC to Boston because it's more expensive
        List<Transport> p2 = myController.fastestRoute("Boston", "New York City");
        assertEquals(p2.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p2, edgeWeightCalculation1), 50.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p2, edgeWeightCalculation2), 267.0, DELTA);
        System.out.println(p2);

        // test fastest path from Boston to NYC because it's the only way there
        List<Transport> p3 = myController.fastestRoute("Boston", "New York City");
        assertEquals(p3.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p3, edgeWeightCalculation1), 50.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p3, edgeWeightCalculation2), 267.0, DELTA);
        System.out.println(p3);

        // test cheapest path from Boston to NYC because it's the only way there
        List<Transport> p4 = myController.cheapestRoute("Boston", "New York City");
        assertEquals(p4.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p4, edgeWeightCalculation1), 50.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p4, edgeWeightCalculation2), 267.0, DELTA);
        System.out.println(p4);


        // test cheapest path from NYC to Providence because it takes longer
        List<Transport> p5 = myController.cheapestRoute("New York City", "Providence");
        assertEquals(p5.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p5, edgeWeightCalculation1), 225.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p5, edgeWeightCalculation2), 40.0, DELTA);
        System.out.println(p5);

        // test cheapest from NYC to Boston because it's longer
        List<Transport> p6 = myController.cheapestRoute("New York City", "Boston");
        assertEquals(p6.size(), 2);
        assertEquals(TravelController.getTotalEdgeWeight(p6, edgeWeightCalculation1), 375.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p6, edgeWeightCalculation2), 47.0, DELTA);
        System.out.println(p6);
    }

    /**
     * test for file 'cities2'
     */


    @Test
    public void testDijsktraCities2() {
        ITravelController<City, Transport>  myController = new TravelController();
        myController.load("/Users/amaris/IdeaProjects/travel-planner-amarisg25-ireyesp107/data/cities2.csv",
                "/Users/amaris/IdeaProjects/travel-planner-amarisg25-ireyesp107/data/transport2.csv");
        Function<Transport, Double> edgeWeightCalculation1 = e -> e.getMinutes();
        Function<Transport, Double> edgeWeightCalculation2 = e -> e.getPrice();

        // test cheapest route from Providence to Washington because there is no way from Providence to Washington
        List<Transport> p1 = myController.cheapestRoute("Providence", "Washington");
        assertEquals(p1.size(), 0);

        //test fastest route from Durham to Boston because there is no edge from Durham to Boston
        List<Transport> p2 = myController.fastestRoute("Durham", "Boston");
        assertEquals(p2.size(), 0);



        // test cheapest route from Durham to Providence because it's longer
        List<Transport> p3 = myController.cheapestRoute("Durham", "Providence");
        assertEquals(p3.size(), 2);
        assertEquals(TravelController.getTotalEdgeWeight(p3, edgeWeightCalculation1), 2.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p3, edgeWeightCalculation2), 3.0, DELTA);

        // test cheapest route from Boston to Durham because it is also the fastest route
        List<Transport> p4 = myController.cheapestRoute("Boston", "Durham");
        assertEquals(p4.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p4, edgeWeightCalculation1), 1.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p4, edgeWeightCalculation2), 3.0, DELTA);

        // test fastest route from Boston to Durham because it is also the cheapest route
        List<Transport> p5 = myController.fastestRoute("Boston", "Durham");
        assertEquals(p5.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p5, edgeWeightCalculation1), 1.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p5, edgeWeightCalculation2), 3.0, DELTA);

        // test fastest route from Boston to Providence because it is the more expensive route
        List<Transport> p6 = myController.fastestRoute("Boston", "Providence");
        assertEquals(p6.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p6, edgeWeightCalculation1), 1.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p6, edgeWeightCalculation2), 100.0, DELTA);

        // test cheapest route from Boston to Chicago because both routes there are the same duration but one of them
        // is cheaper
        List<Transport> p7 = myController.cheapestRoute("Boston", "Chicago");
        assertEquals(p7.size(), 2);
        assertEquals(TravelController.getTotalEdgeWeight(p7, edgeWeightCalculation1), 2.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p7, edgeWeightCalculation2), 5.0, DELTA);

        // test fastest route from Boston to Chicago because two routes to Chicago are the same duration
        List<Transport> p8 = myController.fastestRoute("Boston", "Chicago");
        assertEquals(p8.size(), 2);
        assertEquals(TravelController.getTotalEdgeWeight(p8, edgeWeightCalculation1), 2.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p8, edgeWeightCalculation2), 5.0, DELTA);

        // TODO: write more tests + make sure you test all the cases in your testing plan!
    }

    /**
     * test for file 'cities 3'
     */

    @Test
    public void testDijkstraCities3() {
        ITravelController<City, Transport> myController = new TravelController();
        myController.load("/Users/amaris/IdeaProjects/travel-planner-amarisg25-ireyesp107/data/cities3.csv",
                "/Users/amaris/IdeaProjects/travel-planner-amarisg25-ireyesp107/data/transport3.csv");
        Function<Transport, Double> edgeWeightCalculation1 = e -> e.getMinutes();
        Function<Transport, Double> edgeWeightCalculation2 = e -> e.getPrice();

        // test cheapest route from Marseille to Cannes because it's a longer route
        List<Transport> p1 = myController.cheapestRoute("Marseille", "Cannes");
        assertEquals(p1.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p1, edgeWeightCalculation1), 100.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p1, edgeWeightCalculation2), 10.0, DELTA);

        // test fastest route from Marseille to Cannes because it's a more expensive route
        List<Transport> p2 = myController.fastestRoute("Marseille", "Cannes");
        assertEquals(p2.size(), 2);
        assertEquals(TravelController.getTotalEdgeWeight(p2, edgeWeightCalculation1), 70.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p2, edgeWeightCalculation2), 260.0, DELTA);

        // test fastest route from Cannes to Marseille because there is no route
        List<Transport> p3 = myController.fastestRoute("Cannes", "Marseille");
        assertEquals(p3.size(), 0);

        // test cheapest route from Cannes to Marseille because there is no route
        List<Transport> p4 = myController.cheapestRoute("Cannes", "Marseille");
        assertEquals(p4.size(), 0);

        // test cheapest route from Cannes to Rome because it is also the fastest route
        List<Transport> p5 = myController.cheapestRoute("Cannes", "Rome");
        assertEquals(p5.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p5, edgeWeightCalculation1), 150.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p5, edgeWeightCalculation2), 80.0, DELTA);


        // test fastest route from Cannes to Rome because it is also the fastest route
        List<Transport> p6 = myController.fastestRoute("Cannes", "Rome");
        assertEquals(p6.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p6, edgeWeightCalculation1), 150.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p6, edgeWeightCalculation2), 80.0, DELTA);
    }

    @Test
    public void testDijkstraCities4() {
        ITravelController<City, Transport> myController = new TravelController();
        myController.load("/Users/amaris/IdeaProjects/travel-planner-amarisg25-ireyesp107/data/cities4.csv",
                "/Users/amaris/IdeaProjects/travel-planner-amarisg25-ireyesp107/data/transport4.csv");
        Function<Transport, Double> edgeWeightCalculation1 = e -> e.getMinutes();
        Function<Transport, Double> edgeWeightCalculation2 = e -> e.getPrice();

        // test fastest route from Hackensack to Los Angeles because there are two routes of the same duration
        List<Transport> p1 = myController.fastestRoute("Hackensack", "Los Angeles");
        assertEquals(p1.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p1, edgeWeightCalculation1), 20.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p1, edgeWeightCalculation2), 50.0, DELTA);

        // test fastest route from Philadelphia to Los Angeles because there is only one route
        List<Transport> p2 = myController.fastestRoute("Philadelphia", "Los Angeles");
        assertEquals(p2.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p2, edgeWeightCalculation1), 10.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p2, edgeWeightCalculation2), 20.0, DELTA);

        // test cheapest route from Philadelphia to Los Angeles because there is only one route
        List<Transport> p3 = myController.cheapestRoute("Philadelphia", "Los Angeles");
        assertEquals(p3.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p3, edgeWeightCalculation1), 10.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p3, edgeWeightCalculation2), 20.0, DELTA);

        // test cheapest route from Hackensack to Los Angeles because it is longer
        List<Transport> p4 = myController.cheapestRoute("Hackensack", "Los Angeles");
        assertEquals(p4.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p4, edgeWeightCalculation1), 30.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p4, edgeWeightCalculation2), 10.0, DELTA);

        // test cheapest from Hackensack to Teaneck because it is also the fastest route
        List<Transport> p5 = myController.cheapestRoute("Hackensack", "Teaneck");
        assertEquals(p5.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p5, edgeWeightCalculation1), 10.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p5, edgeWeightCalculation2), 5.0, DELTA);

        // test fastest from Hackensack to Teaneck because it is also the cheapest route
        List<Transport> p6 = myController.fastestRoute("Hackensack", "Teaneck");
        assertEquals(p6.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p6, edgeWeightCalculation1), 10.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p6, edgeWeightCalculation2), 5.0, DELTA);

        // test fastest from Hackensack to Los Angeles because it is a more expensive route
        List<Transport> p7 = myController.fastestRoute("Hackensack", "Los Angeles");
        assertEquals(p7.size(), 1);
        assertEquals(TravelController.getTotalEdgeWeight(p7, edgeWeightCalculation1), 20.0, DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(p7, edgeWeightCalculation2), 50.0, DELTA);
    }

}


