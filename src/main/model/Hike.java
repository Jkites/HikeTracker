package model;

// Represents a hike having a name and length (in kilometers)
public class Hike {
    private String name;       // name of hike
    private double length;     // length of hike
    private int rating;        // rating of hike out of 5

    public Hike(String name, double length, int rating) {
        this.name = name;
        this.length = length;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }

    public int getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
