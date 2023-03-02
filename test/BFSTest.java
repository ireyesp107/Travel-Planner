package test;

import org.junit.Assert;
import org.junit.Test;
import sol.BFS;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.ITravelController;
import src.Transport;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import javax.xml.crypto.dsig.TransformService;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

/**
 * Your BFS tests should all go in this class!
 * The test we've given you will pass if you've implemented BFS correctly, but we still expect
 * you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class BFSTest {

    private static final double DELTA = 0.001;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex f;
    private SimpleGraph graph;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */
    public void makeSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.e));

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));



    }

    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200.0, DELTA);
        assertEquals(path.size(), 2);
    }

    @Test
    public void testBFS2(){

        /*
        first test is NYC to Providence because two different routes one longer than the other
        second test is Boston to Providence because it has two edges to the same place
         */

        TravelController myControl = new TravelController();
        myControl.load("/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/cities1.csv","/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/transport1.csv");
        Function<Transport, Double> edgeMin = e->e.getMinutes();
        Function<Transport, Double> edgePrice = e->e.getPrice();

        //1 has a longer and a shorter path
        List<Transport> myPath1 =myControl.mostDirectRoute("New York City","Providence");
        Assert.assertEquals(myPath1.size(),1);
        assertEquals(TravelController.getTotalEdgeWeight(myPath1,edgeMin),225.0,DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(myPath1,edgePrice),40.0,DELTA);

        //2)Checks to see if the price and time matches the most direct route
        List<Transport> myPath2 =myControl.mostDirectRoute("Boston","Providence");

        Assert.assertEquals(myPath2.size(),1);

        assertEquals(TravelController.getTotalEdgeWeight(myPath2,edgeMin),150.0,DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(myPath2,edgePrice),7.0,DELTA);

    }
    @Test
    public void testBFS3(){
        /*
        first test is Boston to Providence because two different routes one longer than the other
        second test is Providence to Boston because it has no edges
        The ThirdTest checks path from Boston to Chicago when there are more than one path that goes to chicago

         */
        TravelController myControl = new TravelController();
        myControl.load("/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/cities2.csv","/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/transport2.csv");
        Function<Transport, Double> edgeMin = e->e.getMinutes();
        Function<Transport, Double> edgePrice = e->e.getPrice();
        //1 has a longer and a shorter path
        List<Transport> myPath1 =myControl.mostDirectRoute("Boston","Providence");
        Assert.assertEquals(myPath1.size(),1);

        List<Transport> myPath5 =myControl.mostDirectRoute("Washington","Chicago");
        Assert.assertEquals(myPath5.size(),1);

        assertEquals(TravelController.getTotalEdgeWeight(myPath1,edgeMin),1.0,DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(myPath1,edgePrice),100.0,DELTA);

        System.out.println(myControl.getGraph().getOutgoingEdges(myControl.getGraph().getCity("Boston")));
        System.out.println(myControl.getGraph().getOutgoingEdges(myControl.getGraph().getCity("Durham")));

        //2)Checks to see Providence to Boston and should be zero because no edges exist
        List<Transport> myPath2 =myControl.mostDirectRoute("Providence","Boston");
        Assert.assertEquals(myPath2.size(),0);

        //test 3
        List<Transport> myPath3 =myControl.mostDirectRoute("Boston","Chicago");
        System.out.println(myPath3);
        Assert.assertEquals(myPath3.size(),2);


    }

    @Test
    public void myBFS1(){

        /*
        First test Marseille to Paris one route
        Second test Paris to Marseille we can go back
        Third test Paris to Rome because there are two routes one more direct
        Fourth checks Rome because it has no edges and its size should be zero
         */
        TravelController myControl = new TravelController();
        myControl.load("/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/cities3.csv","/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/transport3.csv");
        Function<Transport, Double> edgeMin = e->e.getMinutes();
        Function<Transport, Double> edgePrice = e->e.getPrice();

        List<Transport> myPath1 =myControl.mostDirectRoute("Marseille","Paris");
       Assert.assertEquals(myPath1.size(),1);


        assertEquals(TravelController.getTotalEdgeWeight(myPath1,edgePrice),10.0,DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(myPath1,edgeMin),60.0,DELTA);

        List<Transport> myPath2 =myControl.mostDirectRoute("Paris","Marseille");
        Assert.assertEquals(myPath2.size(),1);

        assertEquals(TravelController.getTotalEdgeWeight(myPath2,edgeMin),60.0,DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(myPath2,edgePrice),10.0,DELTA);
        //Shows same route for Paris to Marseille and Marseille to Paris
        assertEquals(TravelController.getTotalEdgeWeight(myPath1,edgeMin),TravelController.getTotalEdgeWeight(myPath2,edgeMin),DELTA);

        //Test 3
        List<Transport> myPath3 =myControl.mostDirectRoute("Paris","Rome");
        Assert.assertEquals(myPath3.size(),1);
        assertEquals(TravelController.getTotalEdgeWeight(myPath3,edgeMin),20.0,DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(myPath3,edgePrice),150.0,DELTA);

        //Test 4
        List<Transport> myPath4 =myControl.mostDirectRoute("Rome","Marseille");
        Assert.assertEquals(myPath4.size(),0);

    }
    @Test
    public void myBFS2() {
        /*
        First test is Hackensack to Teaneck because there are 3 different routes each with different path lenghts
        Second Test is Hackensack to Los Angeles because there 3 different ways an on way has two different transport
        Third Test Hackensack to Philadelphia because only one way
        Fourth Test Philadeplhia to Hackensack because an the path is different from Hackensack to Philadelphia
        FIfth Test Los Angeles to Philadeplhia because path is  a size > 1
         */
        TravelController myControl = new TravelController();
        myControl.load("/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/cities4.csv","/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/transport4.crv");
        Function<Transport, Double> edgeMin = e->e.getMinutes();
        Function<Transport, Double> edgePrice = e->e.getPrice();


        //Test1
        List<Transport> myPath1 =myControl.mostDirectRoute("Hackensack","Teaneck");
        Assert.assertEquals(myPath1.size(),1);
        assertEquals(TravelController.getTotalEdgeWeight(myPath1,edgePrice),5.0,DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(myPath1,edgeMin),10.0,DELTA);

        //Test2
        List<Transport> myPath2 =myControl.mostDirectRoute("Hackensack","Los Angeles");
        Assert.assertEquals(myPath2.size(),1);
        assertEquals(TravelController.getTotalEdgeWeight(myPath2,edgePrice),50.0,DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(myPath2,edgeMin),20.0,DELTA);

        //Test3
        List<Transport> myPath3 =myControl.mostDirectRoute("Hackensack","Philadelphia");
        Assert.assertEquals(myPath3.size(),1);
        assertEquals(TravelController.getTotalEdgeWeight(myPath3,edgePrice),50.0,DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(myPath3,edgeMin),10.0,DELTA);

        //Test4
        List<Transport> myPath4 =myControl.mostDirectRoute("Philadelphia","Hackensack");
        Assert.assertEquals(myPath4.size(),2);
        assertEquals(TravelController.getTotalEdgeWeight(myPath4,edgePrice),290.0,DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(myPath4,edgeMin),15.0,DELTA);



        //Test5
        List<Transport> myPath5 =myControl.mostDirectRoute("Los Angeles","Philadelphia");
        Assert.assertEquals(myPath5.size(),2);
        assertEquals(TravelController.getTotalEdgeWeight(myPath5,edgePrice),320.0,DELTA);
        assertEquals(TravelController.getTotalEdgeWeight(myPath5,edgeMin),15.0,DELTA);
    }
    // TODO: write more tests + make sure you test all the cases in your testing plan!
}
