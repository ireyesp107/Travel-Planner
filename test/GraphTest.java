package test;

import org.junit.Assert;
import org.junit.Test;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.ITravelController;
import src.Transport;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.security.Key;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Your Graph method tests should all go in this class!
 * The test we've given you will pass, but we still expect you to write more tests using the
 * City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class GraphTest {
    private SimpleGraph graph;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;

    private SimpleEdge edgeAB;
    private SimpleEdge edgeBC;
    private SimpleEdge edgeCA;
    private SimpleEdge edgeAC;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("A");
        this.b = new SimpleVertex("B");
        this.c = new SimpleVertex("C");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);

        // create and insert edges
        this.edgeAB = new SimpleEdge(1, this.a, this.b);
        this.edgeBC = new SimpleEdge(1, this.b, this.c);
        this.edgeCA = new SimpleEdge(1, this.c, this.a);
        this.edgeAC = new SimpleEdge(1, this.a, this.c);

        this.graph.addEdge(this.a, this.edgeAB);
        this.graph.addEdge(this.b, this.edgeBC);
        this.graph.addEdge(this.c, this.edgeCA);
        this.graph.addEdge(this.a, this.edgeAC);
    }

    @Test
    public void testGetVertices() {
        this.createSimpleGraph();

        // test getVertices to check this method AND addVertex
        assertEquals(this.graph.getVertices().size(), 3);
        assertTrue(this.graph.getVertices().contains(this.a));
        assertTrue(this.graph.getVertices().contains(this.b));
        assertTrue(this.graph.getVertices().contains(this.c));

        SimpleVertex g = new SimpleVertex("G");
        SimpleVertex h = new SimpleVertex("H");

        this.graph.addVertex(g);
        this.graph.addVertex(h);

        assertTrue(this.graph.getVertices().contains(g));
        assertTrue(this.graph.getVertices().contains(h));

    }

    @Test
    public void testGetTarget(){
        this.createSimpleGraph();
        assertTrue(this.graph.getEdgeTarget(this.edgeCA).equals(this.a));
        assertTrue(this.graph.getEdgeTarget(this.edgeAB).equals(this.b));
        assertTrue(this.graph.getEdgeTarget(this.edgeAC).equals(this.c));
        assertTrue(this.graph.getEdgeTarget(this.edgeBC).equals(this.c));

    }

    @Test
    public void testGraph(){
        TravelController myController = new TravelController();
        myController.load("/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/cities2.csv","/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/transport2.csv");
        TravelGraph myGraph=myController.getGraph();
        Set<String> myCities= myGraph.getCityName();


        //Checks to make sure the correct cities are in our graph
        assertTrue(myCities.contains("Boston"));
        assertTrue(myCities.contains("Providence"));
        assertTrue(myCities.contains("Durham"));
        assertTrue(myCities.contains("Chicago"));
        assertTrue(myCities.contains("Washington"));
        assertFalse(myCities.contains("Hackensack"));

        //Checks the corret number of vertices
        Assert.assertEquals(myCities.size(), 5);
        LinkedList<Transport> edgeofBoston = new LinkedList<Transport>(myGraph.getOutgoingEdges(myGraph.getCity("Boston")));
        LinkedList<Transport> edgeofChicago = new LinkedList<Transport>(myGraph.getOutgoingEdges(myGraph.getCity("Chicago")));

        //Testing getEdgeSource
        Assert.assertEquals(myGraph.getEdgeSource(edgeofBoston.get(0)).toString().equals("Boston"),true);
        Assert.assertEquals(myGraph.getEdgeSource(edgeofBoston.get(1)).toString().equals("Boston"),true);
        Assert.assertEquals(myGraph.getEdgeSource(edgeofBoston.get(2)).toString().equals("Boston"),true);

        Assert.assertEquals(myGraph.getEdgeSource(edgeofChicago.get(0)).toString().equals("Chicago"),true);


        //Test the getEdgeTarget
        assertTrue(myGraph.getEdgeTarget(edgeofChicago.get(0)).toString().equals("Providence"));

    }


    @Test(expected = NullPointerException.class)
    public void wrongCityName(){
        TravelController myController = new TravelController();
        myController.load("/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/cities2.csv","/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/transport2.csv");
        TravelGraph myGraph=myController.getGraph();

        myGraph.getCity("Hackensack");
    }


    @Test(expected = NullPointerException.class)
    public void wrongAddVertex(){
        TravelController myController = new TravelController();
        myController.load("/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/cities2.csv","/Users/isaias/Desktop/cs200/project/travel-planner-amarisg25-ireyesp107/data/transport2.csv");
        TravelGraph myGraph=myController.getGraph();

        City wrongCity = null;
        myGraph.getOutgoingEdges(wrongCity);
    }

}
