package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a hike having a name and length (in kilometers)
public class Hike implements Writable {
    private String name;    // name of hike
    private double length;  // length of hike
    private int rating;     // rating of hike from 1 out of 10

    // REQUIRES: name is nonzero length
    // EFFECTS: this name becomes name, if passed in length
    //          is > 0 then this length will become passed in length
    //          otherwise this length will become 0
    //          if passed in rating is within the range of (0,10]
    //          this rating will be set to passed in rating,
    //          otherwise it will be set to 0
    public Hike(String name, double length, int rating) {
        this.name = name;
        if (length > 0) {
            this.length = length;
        } else {
            this.length = 0;
        }
        if (rating > 0 && rating <= 10) {
            this.rating = rating;
        } else {
            this.rating = 0;
        }
    }

    // EFFECTS: returns this name
    public String getName() {
        return name;
    }

    // EFFECTS: returns this length
    public double getLength() {
        return length;
    }

    // EFFECTS: returns this rating
    public int getRating() {
        return rating;
    }

    // REQUIRES: given name is nonzero length
    // MODIFIES: this
    // EFFECTS: sets this name to given name
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: sets this length to given length if given length
    //          is > 0, otherwise this length does not change
    public void setLength(double length) {
        if (length > 0) {
            this.length = length;
        }
    }

    // MODIFIES: this
    // EFFECTS: set this rating to given rating if it is within
    //          (0, 10], otherwise rating does not change
    public void setRating(int rating) {
        if (rating > 0 && rating <= 10) {
            this.rating = rating;
        }
    }

    // EFFECTS: returns string representation of Hike
    public String toString() {
        return "Name: " + name + ", Length: " + length + ", Rating: " + rating;
    }

    // EFFECTS: returns
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("length", length);
        json.put("rating", rating);
        return json;
    }
}
