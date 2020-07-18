package graphalgorithms;

import model.TransportGraph;

import java.util.Iterator;
import java.util.LinkedList;

public class BreadthFirstPath extends AbstractPathSearch {

    public BreadthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {
        breadthFirstPath(startIndex);
        super.pathTo(endIndex);
    }


    private void breadthFirstPath(int vertex) {
        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<>();

        // Mark the current node as visited and enqueue it
        marked[vertex] = true;
        queue.push(vertex);
        nodesVisited.add(graph.getStation(vertex));

        while (queue.size() != 0) {
            //Dequeue a vertex from the queue
            vertex = queue.poll();

            // Get all adjacent vertices of the dequeued vertex
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = graph.getAdjacentVertices(vertex).listIterator();
            while (i.hasNext()) {
                int next = i.next();
                if (!marked[next]) {
                    edgeTo[next] = vertex;
                    marked[next] = true;
                    queue.add(next);
                    nodesVisited.add(graph.getStation(next));

                }
            }

        }


    }
}
