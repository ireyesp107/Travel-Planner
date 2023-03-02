package sol;

import src.City;
import src.IGraph;
import src.Transport;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.security.KeyException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TravelGraph implements IGraph<City, Transport> {
    private HashMap<String, City> myCities;
    public TravelGraph(){
        this.myCities= new HashMap<String, City>();
    }

    /**
     * addVertex adds the inputted vertex to the graph
     * @param vertex the vertex
     */
    @Override
    public void addVertex(City vertex) {
        // TODO: implement this method!

       try {
           this.myCities.put(vertex.toString(), vertex);
       }
       catch(NullPointerException e){
           throw new NullPointerException();
       }

    }

    /**
     * addEdge adds the inputted edges to the graph
     * @param origin the origin of the edge.
     * @param edge that contains all the fields of a Transport
     */

    @Override
    public void addEdge(City origin, Transport edge) {
        // TODO: implement this method!
        origin.addOut(edge);
    }

    /**
     * getVertices gets all the vertices of the graph
     * @return a set of the vertices of the graph
     */

    @Override
    public Set<City> getVertices() {

        return new HashSet<>(this.myCities.values());
    }

    /**
     * getEdgeSource gets the City from which the inputted edge comes from
     * @param edge the edge
     * @return the City from which the inputted edge comes from
     */
    @Override
    public City getEdgeSource(Transport edge) {
        // TODO: implement this method!
        return edge.getSource();
    }

    /**
     * getEdgeSource gets the City the inputted edge goes to from
     * @param edge the edge
     * @return the City the inputted edge goes to from
     */

    @Override
    public City getEdgeTarget(Transport edge) {
        // TODO: implement this method!
        return edge.getTarget();
    }

    /**
     * getOUtgoingEdges gets all the outgoing edges of the inputted City
     * @param fromVertex the vertex
     * @return a set of all the outgoing edges of the inputted City
     */

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        // TODO: implement this method!

       return fromVertex.getOutgoing();

    }

    /**
     * getCity gets the city object from the city's string
     * @param name
     * @return the city object from the city's string
     */

    // TODO: feel free to add your own methods here!

    public City getCity(String name) {

        if(this.myCities.containsKey(name)){
            return this.myCities.get(name);
        }
        else{
            throw new NullPointerException("no city");
        }


    }

    public Set getCityName(){
        return this.myCities.keySet();
    }
}