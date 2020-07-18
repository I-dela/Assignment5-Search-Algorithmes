package model;

import java.util.*;

public class TransportGraph {

    private int numberOfStations;
    private int numberOfConnections;
    private List<Station> stationList;
    private Map<String, Integer> stationIndices;
    private List<Integer>[] adjacencyLists;
    private Connection[][] connections;

    public TransportGraph(int size) {
        this.numberOfStations = size;
        stationList = new ArrayList<>(size);
        stationIndices = new HashMap<>();
        connections = new Connection[size][size];
        adjacencyLists = (List<Integer>[]) new List[size];
        for (int vertex = 0; vertex < size; vertex++) {
            adjacencyLists[vertex] = new LinkedList<>();
        }
    }

    /**
     * @param vertex Station to be added to the stationList
     *               The method also adds the station with it's index to the map stationIndices
     */
    public void addVertex(Station vertex) {
        // TODO

        // add the vertex(station) to the stationList
        stationList.add(vertex);

        //put the index of the station in the map of stationIndices if its not there already
        stationIndices.putIfAbsent(vertex.getStationName(), stationList.indexOf(vertex));

    }


    /**
     * Method to add an edge to a adjancencyList. The indexes of the vertices are used to define the edge.
     * Method also increments the number of edges, that is number of Connections.
     * The grap is bidirected, so edge(to, from) should also be added.
     *
     * @param from
     * @param to
     */
    private void addEdge(int from, int to) {
        // TODO

        //add the edge(to) to the adjancencyList with index of vertex from
        this.adjacencyLists[from].add(to);
        //add the edge(from) to the adjancencyList with index of vertex to
        this.adjacencyLists[to].add(from);

        //increment the number of connections
        this.numberOfConnections++;
    }


    /**
     * Method to add an edge in the form of a connection between stations.
     * The method also adds the edge as an edge of indices by calling addEdge(int from, int to).
     * The method adds the connection to the connections 2D-array.
     * The method also builds the reverse connection, Connection(To, From) and adds this to the connections 2D-array.
     *
     * @param connection The edge as a connection between stations
     */
    public void addEdge(Connection connection) {
        // TODO

        //
        Station stationFrom = connection.getFrom();

        Station stationTo = connection.getTo();

        int from = this.stationIndices.get(stationFrom.getStationName());
        int to = this.stationIndices.get(stationTo.getStationName());

        this.addEdge(from, to);

        this.connections[from][to] = connection;
        this.connections[to][from] = connection;


    }

    public List<Integer> getAdjacentVertices(int index) {
        return adjacencyLists[index];
    }

    public Connection getConnection(int from, int to) {
        return connections[from][to];
    }

    public int getIndexOfStationByName(String stationName) {
        return stationIndices.get(stationName);
    }

    public Station getStation(int index) {
        return stationList.get(index);
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public int getNumberEdges() {
        return numberOfConnections;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append(String.format("Graph with %d vertices and %d edges: \n", numberOfStations, numberOfConnections));
        for (int indexVertex = 0; indexVertex < numberOfStations; indexVertex++) {
            resultString.append(stationList.get(indexVertex) + ": ");
            int loopsize = adjacencyLists[indexVertex].size() - 1;
            for (int indexAdjacent = 0; indexAdjacent < loopsize; indexAdjacent++) {
                resultString.append(stationList.get(adjacencyLists[indexVertex].get(indexAdjacent)).getStationName() + "-");
            }
            resultString.append(stationList.get(adjacencyLists[indexVertex].get(loopsize)).getStationName() + "\n");
        }
        return resultString.toString();
    }


    /**
     * A Builder helper class to build a TransportGraph by adding lines and building sets of stations and connections from these lines.
     * Then build the graph from these sets.
     */
    public static class Builder {

        private Set<Station> stationSet;
        private List<Line> lineList;
        private Set<Connection> connectionSet;

        public Builder() {
            lineList = new ArrayList<>();
            stationSet = new HashSet<>();
            connectionSet = new HashSet<>();
        }


        /**
         * Method to add a line to the list of lines and add stations to the line.
         *
         * @param lineDefinition String array that defines the line. The array should start with the name of the line,
         *                       followed by the type of the line and the stations on the line in order.
         * @return
         */
        public Builder addLine(String[] lineDefinition) {
            //init line
            Line line = new Line(lineDefinition[1], lineDefinition[0]);

            //make a copy of the original array
            String[] stations = Arrays.copyOfRange(lineDefinition, 2, lineDefinition.length);


            // add stations to the line
            for (String station : stations) {


                // add station to the line and add the coordinates of the station to the station
                line.addStation(new Station(station));


            }


            //add line to the linelist
            lineList.add(line);


            return this;
        }


        /**
         * Method to add a line to the list of lines and add stations to the line.
         *
         * @param lineDefinition String array that defines the line. The array should start with the name of the line,
         *                       followed by the type of the line and the stations on the line in order.
         * @param coordinates    The coordinates of the stations on the line
         * @return
         */
        public Builder addLine(String[] lineDefinition, int[][] coordinates) {
            //init line
            Line line = new Line(lineDefinition[1], lineDefinition[0]);

            //make a copy of the original array
            String[] stations = Arrays.copyOfRange(lineDefinition, 2, lineDefinition.length);


            // add stations to the line
            for (String station : stations) {

                int i = 0;

                // add station to the line and add the coordinates of the station to the station
                line.addStation(new Station(station, new Location(coordinates[i])));

                ++i;
            }


            //add line to the linelist
            this.lineList.add(line);


            return this;
        }


        /**
         * Method that reads all the lines and their stations to build a set of stations.
         * Stations that are on more than one line will only appear once in the set.
         *
         * @return
         */
        public Builder buildStationSet() {

            // use a forEach method to get trough the lines to get the stations of each line and add it to the stationset
            this.lineList.forEach(line -> stationSet.addAll(line.getStationsOnLine()));

            return this;
        }

        /**
         * For every station on the set of station add the lines of that station to the lineList in the station
         *
         * @return
         */
        public Builder addLinesToStations() {

            // for each station filter lineList to check whether the line contains the station
            this.stationSet.forEach(station -> this.lineList.stream()
                    .filter(line -> line.getStationsOnLine().contains(station))
                    .forEach(station::addLine)); // then add each of the lines to the station


            return this;
        }

        /**
         * Method that uses the list of Lines to build connections from the consecutive stations in the stationList of a line.
         *
         * @return
         */
        public Builder buildConnectionsWithWeights(double[]... weightDefinitions) {
            // loop trough the lines.  Loop trough the stations and make an object of Connection with the stations as from and to.
            // Finally set the corresponding line as the line for the connection

            for(int j = 0; j < weightDefinitions.length; j++){

                Line line = this.lineList.get(j);

                //loop trough the stations.Condition -1 otherwise indexOutOfBoundsException , because at the end it will iterate above the maximum stations.
                for (int i = 0; i < line.getStationsOnLine().size() - 1; i++) {

                    //Make the connection



                    Connection connection = new Connection(line.getStationsOnLine().get(i), line.getStationsOnLine().get(i + 1),
                             weightDefinitions[j][i] , line);

                    //add the connection  to the connectionSet
                    this.connectionSet.add(connection);

                }


            }


            return this;
        }


        public Builder buildConnections() {
            // loop trough the lines.  Loop trough the stations and make an object of Connection with the stations as from and to. Finally set the corresponding line as the line for the connection

            lineList.forEach(line -> {

                for (int j = 0; j < line.getStationsOnLine().size() - 1; j++) {
                    //Make the connection
                    Connection connection = new Connection(line.getStationsOnLine().get(j),
                            line.getStationsOnLine().get(j + 1));
                    //add the connection  to the connectionSet
                    this.connectionSet.add(connection);
                }
            });
            return this;
        }

        /**
         * Method that builds the graph.
         * All stations of the stationSet are addes as vertices to the graph.
         * All connections of the connectionSet are addes as edges to the graph.
         *
         * @return
         */
        public TransportGraph build() {

            TransportGraph graph = new TransportGraph(stationSet.size());

            // add all the station in the stationSet as vertices to the graph
            this.stationSet.forEach(graph::addVertex);
            //add all the connections in the connectionSet as connections to the graph
            this.connectionSet.forEach(graph::addEdge);


            return graph;
        }

    }
}
