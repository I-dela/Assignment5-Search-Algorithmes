package graphalgorithms;

import model.Line;
import model.Station;
import model.TransportGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract class that contains methods and attributes shared by the DepthFirstPath en BreadthFirstPath classes
 */
public abstract class AbstractPathSearch {

    protected boolean[] marked;
    protected int[] edgeTo;
    protected int transfers = 0;
    protected List<Station> nodesVisited;
    protected List<Station> nodesInPath;
    protected LinkedList<Integer> verticesInPath;
    protected TransportGraph graph;
    protected final int startIndex;
    protected final int endIndex;


    public AbstractPathSearch(TransportGraph graph, String start, String end) {
        startIndex = graph.getIndexOfStationByName(start);
        endIndex = graph.getIndexOfStationByName(end);
        this.graph = graph;
        nodesVisited = new ArrayList<>();
        marked = new boolean[graph.getNumberOfStations()];
        edgeTo = new int[graph.getNumberOfStations()];
    }

    public abstract void search();

    /**
     * @param vertex Determines whether a path exists to the station as an index from the start station
     * @return
     */
    public boolean hasPathTo(int vertex) {
        return marked[vertex];
    }


    /**
     * Method to build the path to the vertex, index of a Station.
     * First the LinkedList verticesInPath, containing the indexes of the stations, should be build, used as a stack
     * Then the list nodesInPath containing the actual stations is build.
     * Also the number of transfers is counted.
     * @param vertex The station (vertex) as an index
     */
    public void pathTo(int vertex) {

        // if there is no path to the vertex(station) stop the method and return
        if(!hasPathTo(vertex)) return;


        verticesInPath = new LinkedList<>();

        // Build verticesInPath
        int i = vertex;
        this.verticesInPath.push(i);
        do {
            i = edgeTo[i];
            this.verticesInPath.push(i);
        } while (i != startIndex);

        nodesInPath = new ArrayList<>();

        //for each vertex in the verticesInPath list, use the vertex to find the station and add it to the nodesInPath List
        verticesInPath.forEach(v-> nodesInPath.add(graph.getStation(v)));


        this.countTransfers();


    }

    /**
     * Method that calculates the total weight of the path.
     * @return total weight of the path
     */
    public double getTotalWeight(){
        double totalWeight = 0 ;

        for(int i =0 ; i<verticesInPath.size() -1; i++){
            totalWeight+= graph.getConnection(verticesInPath.get(i), verticesInPath.get(i+1)).getWeight();
        }

        return totalWeight;
    }

    /**
     * Method to count the number of transfers in a path of vertices.
     * Uses the line information of the connections between stations.
     * If to consecutive connections are on different lines there was a transfer.
     */
    public void countTransfers() {
        this.transfers = 0;
        Line previousLine  = null;


        for(int i =0 ; i<this.nodesInPath.size() -1 ; i++) {
            Station currentStation = this.nodesInPath.get(i);

            // nextStation is needed to get the currentLine
            Station nextStation = this.nodesInPath.get(i+1);
            Line currentLine = currentStation.getCommonLine(nextStation);

            // if the previousLine is null and the currentLine is different than the previousLine, increase the transfers by 1
            if(previousLine != null && !currentLine.equals(previousLine) ){
                this.transfers++;
            }

            previousLine = currentLine;


        }


    }


    /**
     * Method to print all the nodes that are visited by the search algorithm implemented in one of the subclasses.
     */
    public void printNodesInVisitedOrder() {
        System.out.print("Nodes in visited order: ");
        for (Station vertex : nodesVisited) {
            System.out.print(vertex.getStationName() + " ");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder(String.format("Path from %s to %s: ", graph.getStation(startIndex), graph.getStation(endIndex)));
        resultString.append(nodesInPath).append(" with " + transfers).append(" transfers");
        return resultString.toString();
    }

}
