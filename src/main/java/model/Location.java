package model;

public class Location {
    private final static double TRAVEL_TIME_PER_SQUARE= 1.5;
    private int [] coordinates;

    public Location(int[] coordinates) throws IllegalArgumentException {
        validationCoordinates(coordinates);
        this.coordinates = coordinates;
    }

    public double calcTravelTime(Location other) {

        // Calculate the diffrence of the x and y coordiantes seperately and them to total difference variable
        int diffX = Math.abs(other.coordinates[0] - this.coordinates[0]);
        int diffY = Math.abs(other.coordinates[1] - this.coordinates[1]);
        int totalDiff = diffX + diffY;

        // return total difference multiplied with the travel time per square.
        return TRAVEL_TIME_PER_SQUARE * totalDiff;
    }

    private void validationCoordinates(int[] coordinates) throws IllegalArgumentException {
        if (coordinates.length != 2) throw new IllegalArgumentException("Invalid! : The Coordinates must consist of two numbers");
    }
}
