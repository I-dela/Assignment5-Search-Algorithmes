package graphalgorithms;

import model.IndexMinPQ;
import model.Location;
import model.TransportGraph;

import java.util.List;
import java.util.stream.Collectors;

public class A_starPath extends AbstractPathSearch {
    private double[] distTo;
    private IndexMinPQ<Double> queue;
    private int V; // number of vertices

    public A_starPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
        this.V = graph.getNumberOfStations();
        this.distTo = new double[V];
        this.queue = new IndexMinPQ<>(V);
    }

    @Override
    public void search() {
        A_starSearch();

    }

    public void A_starSearch() {

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
            nodesVisited.add(graph.getStation(u));
            marked[u] = true;
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
                        //calculate distance
                        double weightDistance = graph.getConnection(u, vertex).getWeight();
                        double heuristicDistance = calDistance(vertex, endIndex);
                        double distanceFromMinVertexToNeighbour = weightDistance + heuristicDistance;
                        double totalDistance = distTo[u] + distanceFromMinVertexToNeighbour;

                        // If new distance is cheaper in cost
                        if (totalDistance < distTo[vertex]) {
                            distTo[vertex] = distTo[u] + weightDistance;
                            edgeTo[vertex] = u;

                            // Insert/update in priority queue
                            if (queue.contains(vertex)) {
                                queue.changeKey(vertex, distTo[vertex]);
                            } else {
                                queue.insert(vertex, distTo[vertex]);
                            }
                        }
//                        marked[vertex] = true;
                    }
            );
    }

    private double calDistance(int vertex1, int vertex2) {
        Location location1 = graph.getStation(vertex1).getLocation();
        Location location2 = graph.getStation(vertex2).getLocation();
        return location1.calcTravelTime(location2);
    }
}

