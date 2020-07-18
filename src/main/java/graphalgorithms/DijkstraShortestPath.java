package graphalgorithms;

import model.*;
import org.w3c.dom.Node;

import java.util.*;
import java.util.stream.Collectors;

public class DijkstraShortestPath extends AbstractPathSearch {

    private double[] distTo;
    private IndexMinPQ<Double> queue;
    private int V; // number of vertices

    public DijkstraShortestPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
        this.V = graph.getNumberOfStations();
        this.distTo = new double[V];
        this.queue = new IndexMinPQ<>(V);

    }

    @Override
    public void search() {
        dijkstraShortestPath();



    }


    /**
     * Function for dijkstra's algorithm
     */
    private void dijkstraShortestPath() {


        for (int i = 0; i < this.V; i++) {
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }

        // Add a source node for the priority queue
        this.queue.insert(startIndex, 0.0);

        //distance to the source node is 0
        this.distTo[startIndex] = 0.0;

        /* Looping till priority queue becomes empty (or all
      distances are not finalized) */
        while (!this.queue.isEmpty()) {
            // The first vertex in pair is the minimum distance vertex, extract it from priority queue.
            int u = this.queue.delMin();
            // adding the node whose distance is processed
            marked[u] = true;
            nodesVisited.add(graph.getStation(u));


            pNeighbours(u);
        }
        super.pathTo(endIndex);



    }


    @Override
    public boolean hasPathTo(int vertex) {
        return distTo[vertex] < Double.POSITIVE_INFINITY;
    }


    /**
     * Function to process all the neighbours of the passed node
     *
     * @param u passed node index
     */
    private void pNeighbours(int u) {


        // All the neighbors of u

            List<Integer> unmarkedVertices = graph.getAdjacentVertices(u).stream().filter(neigbour -> !marked[neigbour]).collect(Collectors.toList());


            // for each current node  that hasn't already been processed

            unmarkedVertices.forEach(vertex -> {
                        double edgeDistance = -1;
                        double newDistance = -1;

                        //calculate distance
                        edgeDistance = graph.getConnection(u, vertex).getWeight();

                        newDistance = distTo[u] + edgeDistance;

                        // If new distance is cheaper in cost
                        if (newDistance < distTo[vertex]) {
                            distTo[vertex] = newDistance;
                            edgeTo[vertex] = u;

                            // Insert/update in priority queue
                            if (queue.contains(vertex)) {
                                queue.changeKey(vertex, distTo[vertex]);
                            } else {
                                queue.insert(vertex, distTo[vertex]);
                            }
                        }
//                        marked[vertex] =true;
                    }
            );

    }
}






