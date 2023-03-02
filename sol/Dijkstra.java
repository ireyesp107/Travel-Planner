package sol;

import src.IDijkstra;
import src.IGraph;

import java.util.*;
import java.util.function.Function;

public class Dijkstra<V, E> implements IDijkstra<V, E> {

    /**
     * get Shortest path get the shortest path (for the chosen edgea weight) to destination
     * @param graph       the graph including the vertices
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param edgeWeight
     * @return a list of edges indicating the shortest path
     */
    // TODO: implement the getShortestPath method!
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {

        HashMap<V, E> cameFrom = new HashMap<>();
        HashMap<V, Double> routeDist = new HashMap<>();
        Set<V> visited = new HashSet<>();
        Comparator<V> compEdgeWeight = (W1, W2) -> {
            return Double.compare(routeDist.get(W1), routeDist.get(W2));
        };
        PriorityQueue<V> toCheckQueue = new PriorityQueue<>(compEdgeWeight);

        for (V vertex : graph.getVertices()) {
            routeDist.put(vertex, Double.MAX_VALUE); // if it is not the source, set all distances to infinity
            toCheckQueue.add(vertex);
        }
        routeDist.put(source, 0.0);
        toCheckQueue.remove(source);
        toCheckQueue.add(source);


        while (!toCheckQueue.isEmpty()) { // while there are still cities in the priority queue
            V checkingV = toCheckQueue.poll(); // remove the city with the minimum distance from the queue
            if (!visited.contains(checkingV)) { // if the list of visited cities does not contain the city with the
                // minimum distance for the queue
                for (E outgoingEdge : graph.getOutgoingEdges(checkingV)) { // for each edge of the city with min distance
                    V neighbor = graph.getEdgeTarget(outgoingEdge); // get the vertex to which the edge is connected,
                    // which is the neighboring city
                    double rD = routeDist.get(checkingV) + edgeWeight.apply(outgoingEdge); // sum of the distance of
                    // the city with minimum distance and the weight (price or speed)  of the outgoing edge
                    double x = routeDist.get(neighbor);
                    if (rD < x) { // if the new route is shorter than old route
                        routeDist.put(neighbor, rD); // add the neighbor and the weight to the hashmap
                        cameFrom.put(neighbor, outgoingEdge); // add the neighbor to the edge to backtrack
                        toCheckQueue.remove(neighbor);         // when you get to using a PriorityQueue, remember to remove and re-add a vertex to the
                        // PriorityQueue when its priority changes!
                        toCheckQueue.add(neighbor);
                    }
                }
                visited.add(checkingV); // add the city with min distance to the visited cities
            }
        }
        List<E> shortPath = new ArrayList<>();
        while (!destination.toString().equals(source.toString())) { // while the destination is not equal to the source
            E outgoingEdge = cameFrom.get(destination);
            if (outgoingEdge == null) { // if the array list is empty, create a new array list
                return new ArrayList<>();
            }
            shortPath.add(outgoingEdge); // if not, add the outgoing edges to initial array list
            destination = graph.getEdgeSource(outgoingEdge); // get the source of this edge
        }

        return shortPath;
    }


}


