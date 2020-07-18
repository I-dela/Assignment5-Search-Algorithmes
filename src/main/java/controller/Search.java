package controller;

import graphalgorithms.*;
import model.Station;
import model.TransportGraph;

import java.util.List;

public class Search {
    private static AbstractPathSearch pathSearch;

    public static void sAllStations(TransportGraph transportGraph, SearchMethod method) throws Exception {
        List<Station> allStations = transportGraph.getStationList();

        allStations.forEach(stationFrom-> allStations.forEach(stationTo ->{
            if (stationFrom.equals(stationTo)) return;

            try {
                GraphSearch(transportGraph, stationFrom.getStationName(), stationTo.getStationName(), method);
                printResults();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }));
    }


    private static void GraphSearch(TransportGraph transportGraph, String start, String end, SearchMethod method) throws Exception {
        switch (method) {
            case BREADTH:
                pathSearch = new BreadthFirstPath(transportGraph, start, end);
                break;
            case DEPTH:
                pathSearch = new DepthFirstPath(transportGraph, start, end);
                break;
            case DIJKSTRA:
                pathSearch = new DijkstraShortestPath(transportGraph, start, end);
                break;
            case A_STAR:
                pathSearch = new A_starPath(transportGraph, start, end);
                break;
            default:
                throw new Exception("Invalid method: " + method + "\n"+  "Use only these values: (DIJKSTRA, A_STAR, DEPTH, BREADTH)") ;
        }
    }

    private static void printResults() {
        pathSearch.search();
        System.out.println(pathSearch);
        pathSearch.printNodesInVisitedOrder();
        System.out.println();
        System.out.println("Total weight is: "+ pathSearch.getTotalWeight());
    }

    public static void StartPointEndpoint(TransportGraph transportGraph, String start, String end, SearchMethod method) throws Exception {
        GraphSearch(transportGraph, start, end, method);
        printResults();

    }
}
