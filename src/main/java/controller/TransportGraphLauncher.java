package controller;

import graphalgorithms.BreadthFirstPath;
import graphalgorithms.DepthFirstPath;
import graphalgorithms.DijkstraShortestPath;
import model.TransportGraph;

import java.util.Arrays;

public class TransportGraphLauncher {
    public static final String[] RED_LINE = {"red", "metro", "Haven", "Marken", "Steigerplein", "Centrum", "Meridiaan", "Dukdalf", "Oostvaarders"};
    public static final String[] BLUE_LINE = {"blue", "metro", "Trojelaan", "Coltrane Cirkel", "Meridiaan", "Robijnpark", "Violetplantsoen"};
    public static final String[] PURPLE_LINE = {"purple", "metro", "Grote Sluis", "Grootzeil", "Coltrane Cirkel", "Centrum", "Swingstraat"};
    public static final String[] GREEN_LINE = {"green", "metro", "Ymeerdijk", "Trojelaan", "Steigerplein", "Swingstraat", "Bachgracht", "Nobelplein"};
    public static final String[] YELLOW_LINE = {"yellow", "bus", "Grote Sluis", "Ymeerdijk", "Haven", "Nobelplein", "Violetplantsoen", "Oostvaarders", "Grote Sluis"};
    public static final double[] RED_LINE_WEIGHTS = new double[]{4.5, 4.7, 6.1, 3.5, 5.4, 5.6};
    public static final double[] BLUE_LINE_WEIGHTS = new double[]{6.0, 5.3, 5.1, 3.3};
    public static final double[] PURPLE_LINE_WEIGHTS = new double[]{6.2, 5.2, 3.8, 3.6};
    public static final double[] GREEN_LINE_WEIGHTS = new double[]{5.0, 3.7, 6.9, 3.9, 3.4};
    public static final double[] YELLOW_LINE_WEIGHTS = new double[]{26.0, 19.0, 37.0, 25.0, 22.0, 28.0};
    public static final int[][] RED_LINE_COORDINATES = new int[][]{{14, 1}, {12, 3}, {10, 5}, {8, 8}, {6, 9}, {3, 10}, {0, 11}};
    public static final int[][] BLUE_LINE_COORDINATES = new int[][]{{9, 3}, {7, 6}, {6, 9}, {6, 12}, {5, 14}};
    public static final int[][] PURPLE_LINE_COORDINATES = new int[][]{{2, 3}, {4, 6}, {7, 6}, {8, 8}, {10, 9}};
    public static final int[][] GREEN_LINE_COORDINATES = new int[][]{{9, 0}, {9, 3}, {10, 5}, {10, 9}, {11, 11}, {12, 13}};
    public static final int[][] YELLOW_LINE_COORDINATES = new int[][]{{2, 3}, {9, 0}, {14, 1}, {12, 13}, {5, 14}, {0, 11}, {2, 3}};

    public static void main(String[] args) throws Exception {
        assignmentA();
        assignmentB();
        assignmentC();
    }

    private static void assignmentA() {
        String[] redLine = {"red", "metro", "A", "B", "C", "D"};
        String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
        String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
        String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

//        Uncomment to test the builder:
        TransportGraph.Builder builder = new TransportGraph.Builder();
        builder.addLine(redLine);
        builder.addLine(blueLine);
        builder.addLine(greenLine);
        builder.addLine(yellowLine);

        builder.buildStationSet();

        builder.addLinesToStations();

        builder.buildConnections();

        TransportGraph transportGraph = builder.build();


        System.out.println(transportGraph);

//        Uncommented to test the DepthFirstPath algorithm
        DepthFirstPath dfpTest = new DepthFirstPath(transportGraph, "E", "J");
        dfpTest.search();
        System.out.println(dfpTest);
        dfpTest.printNodesInVisitedOrder();
        System.out.println();

//        Uncommented to test the BreadthFirstPath algorithm
        BreadthFirstPath bfsTest = new BreadthFirstPath(transportGraph, "E", "J");
        bfsTest.search();
        System.out.println(bfsTest);
        bfsTest.printNodesInVisitedOrder();


    }

    private static void assignmentB() {


        TransportGraph transportGraph = buildGraph(new String[][]{RED_LINE, BLUE_LINE, PURPLE_LINE, GREEN_LINE, YELLOW_LINE},
                new double[][]{RED_LINE_WEIGHTS, BLUE_LINE_WEIGHTS, PURPLE_LINE_WEIGHTS, GREEN_LINE_WEIGHTS, YELLOW_LINE_WEIGHTS});


        System.out.println(transportGraph);

//        Uncommented to test the DepthFirstPath algorithm
        DepthFirstPath dfpTest = new DepthFirstPath(transportGraph, "Haven", "Nobelplein");
        dfpTest.search();
        System.out.println(dfpTest);
        dfpTest.printNodesInVisitedOrder();
        System.out.println();

//        Uncommented to test the BreadthFirstPath algorithm
        BreadthFirstPath bfsTest = new BreadthFirstPath(transportGraph, "Marken", "Oostvaarders");
        bfsTest.search();
        System.out.println(bfsTest);
        bfsTest.printNodesInVisitedOrder();

        System.out.println("---------------------------------");
        // DijkstraShortestPath algorithm
        DijkstraShortestPath dspTest = new DijkstraShortestPath(transportGraph, "Haven", "Oostvaarders");
        DijkstraShortestPath dspTest2 = new DijkstraShortestPath(transportGraph, "Oostvaarders", "Haven");

        dspTest.search();
        dspTest2.search();
        System.out.println(dspTest);
        System.out.println("----------------");
        System.out.println(dspTest2);
        dspTest.printNodesInVisitedOrder();

    }

    private static void assignmentC() throws Exception {
        TransportGraph transportGraph = buildGraph(new String[][]{RED_LINE, BLUE_LINE, PURPLE_LINE, GREEN_LINE, YELLOW_LINE},
                new double[][]{RED_LINE_WEIGHTS, BLUE_LINE_WEIGHTS, PURPLE_LINE_WEIGHTS, GREEN_LINE_WEIGHTS, YELLOW_LINE_WEIGHTS},
                new int[][][]{RED_LINE_COORDINATES, BLUE_LINE_COORDINATES, PURPLE_LINE_COORDINATES, GREEN_LINE_COORDINATES, YELLOW_LINE_COORDINATES});


        System.out.println(transportGraph);


        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";

        System.out.println(ANSI_GREEN + "~~~~~~~~~~~~~~~~~~~[SEARCH METHOD: DIJKSTA]~~~~~~~~~~~~~~~~~~~~~ ALL STATIONS BEGIN\n\n" + ANSI_RESET);
        Search.sAllStations(transportGraph, SearchMethod.DIJKSTRA);
        System.out.println(ANSI_GREEN + "~~~~~~~~~~~~~~~~~~~[SEARCH METHOD: DIJKSTA]~~~~~~~~~~~~~~~~~~~~~ ALL STATIONS END\n\n" + ANSI_RESET);

        System.out.println(ANSI_GREEN +"~~~~~~~~~~~~~~~~~~~~~[SEARCH METHOD: A_STAR]~~~~~~~~~~~~~~~~~~~~~ ALL STATIONS BEGIN\n\n" + ANSI_RESET);

        Search.sAllStations(transportGraph, SearchMethod.A_STAR);
        System.out.println(ANSI_GREEN +"~~~~~~~~~~~~~~~~~~~~~[SEARCH METHOD: A_STAR]~~~~~~~~~~~~~~~~~~~~~ ALL STATIONS END\n\n" + ANSI_RESET);


        System.out.println(ANSI_GREEN +"~~~~~[SEARCH METHOD: A_STAR]~~~~~ SINGLE\n\n" + ANSI_RESET);
        Search.StartPointEndpoint(transportGraph, "Marken", "Coltrane Cirkel", SearchMethod.A_STAR);
        // Next line added by Nico Tromp
        Search.StartPointEndpoint(transportGraph, "Coltrane Cirkel", "Marken", SearchMethod.A_STAR);

        System.out.println(ANSI_GREEN + "~~~~~[SEARCH METHOD: DIJKSTA]~~~~~~~ SINGLE\n\n" + ANSI_RESET);
        Search.StartPointEndpoint(transportGraph, "Marken", "Coltrane Cirkel", SearchMethod.DIJKSTRA);
        // Next line added by Nico Tromp
        Search.StartPointEndpoint(transportGraph, "Coltrane Cirkel", "Marken", SearchMethod.DIJKSTRA);


    }


    // I use method overLoading to make a method for each assignemnt because not every assignment requires the same parameters

    private static TransportGraph buildGraph(String[][] lineDefinitions, double[][] weightDefinitions) {
        return buildGraph(lineDefinitions, weightDefinitions, null);
    }

    private static TransportGraph buildGraph(String[][] lineDefinitions,
                                             double[][] weightDefinitions,
                                             int[][][] allCoordinates) {
        TransportGraph.Builder builder = new TransportGraph.Builder();

        // For every line definitions

//        Arrays.stream(lineDefinitions).forEach(line-> {
//            String[] lineDefinition = lineDefinitions[i];
//            if(allCoordinates != null) {
//                int[][] lineCoordinates = allCoordinates[i];
//                builder.addLine(lineDefinition, lineCoordinates);
//                i++;
//            }else{
//                builder.addLine(lineDefinition);
//                i++;
//            }
//        });

        for (int j = 0; j < lineDefinitions.length; j++) {

            if(allCoordinates != null) {
                builder.addLine(lineDefinitions[j], allCoordinates[j]);
            }else{
                builder.addLine(lineDefinitions[j]);
            }
        }


        return builder
                .buildStationSet()
                .addLinesToStations()
                .buildConnectionsWithWeights(weightDefinitions)
                .build();
    }


}



