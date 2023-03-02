package sol;

import src.IBFS;
import src.IGraph;

import java.util.*;

/**
 * BFS works by getting the list of outGoingEdges from the starting city
 * We use the edges to get the Target City and from there we check if we have checked that city already
 * if yes we don't proceed further and then check the next target from the edge until we see that the
 * end city equals the city we are checking. Then we use a while loop to trace back and add the edges
 * to the array
 *
 * @param <V> Cities
 * @param <E> Transport
 */
public class BFS<V, E> implements IBFS<V, E> {

    // TODO: implement the getPath method!
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {

        HashMap<V, E> cameFrom = new HashMap<>();
        Set<V> visitedCities = new HashSet<>();
        visitedCities.add(start);
        Queue<E> edgesToVisit = new LinkedList<E>(graph.getOutgoingEdges(start));

        if (start.equals(end)) {

            return new ArrayList<>();
        }
        while (!edgesToVisit.isEmpty()) {
            E edge = edgesToVisit.remove();

            V city = graph.getEdgeTarget(edge);
            //next city equals the destination
            if (!visitedCities.contains(city)) {

                if(city.toString().equals(end.toString())){

                LinkedList<E> pathToCity = new LinkedList<>();
                //If the next city doesn't equal the previous city
                while (!(graph.getEdgeSource(edge).toString().equals(start.toString()))) {
                    pathToCity.addFirst(edge);
                    edge = cameFrom.get(graph.getEdgeSource(edge));
                }
                pathToCity.addFirst(edge);

                return pathToCity;
            }

            edgesToVisit.addAll((graph.getOutgoingEdges(city)));
            cameFrom.putIfAbsent(city, edge);
            visitedCities.add(city);
        }
        }
        return new ArrayList<>();
    }
}
