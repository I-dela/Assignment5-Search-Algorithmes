package graphalgorithms;

import model.TransportGraph;

import java.util.Iterator;

public class DepthFirstPath extends AbstractPathSearch {

    public DepthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {
        depthFirstSearch(startIndex);
        super.pathTo(endIndex);

    }

    private void depthFirstSearch(int vertex){
        // mark the current node as visited
        marked[vertex] = true;

        // add the visited station to the nodesVisited list
        nodesVisited.add(graph.getStation(vertex));

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> i = graph.getAdjacentVertices(vertex).listIterator();

        while(i.hasNext()){
            int next = i.next();
            if(!marked[next]){
                edgeTo[next] = vertex;
                depthFirstSearch(next);
            }
        }





    }
}
