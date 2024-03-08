package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a list of hikes
public class HikeList implements Writable {
    private List<Hike> hikes;

    public HikeList() {
        this.hikes = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds given hike into the list
    public void addHike(Hike hike) {
        hikes.add(hike);
    }

    // REQUIRES: hikes is not empty, index < hikes.size()
    // MODIFIES: this
    // EFFECTS: removes hike at given index
    public void removeHike(int index) {
        hikes.remove(index);
    }

    // EFFECTS: returns true if hikes is empty, false if not
    public boolean isEmpty() {
        return hikes.isEmpty();
    }

    // REQUIRES: !hikes.isEmpty()
    // MODIFIES: this
    // EFFECTS: sorts hikes in descending order of length, if same length then
    //          first hike will remain first
    public void sortByLength() {
        int max;
        for (int i = 0; i < hikes.size() - 1; i++) {
            max = i;
            for (int j = i + 1; j < hikes.size(); j++) {
                if (hikes.get(max).getLength() < hikes.get(j).getLength()) {
                    max = j;
                }
            }
            Hike temp = hikes.get(i);
            hikes.set(i, hikes.get(max));
            hikes.set(max, temp);
        }
    }

    // REQUIRES: !hikes.isEmpty()
    // MODIFIES: this
    // EFFECTS: sorts hikes in ascending order of name, if exactly same,
    //          then first hike will remain first
    public void sortByName() {
        int min;
        for (int i = 0; i < hikes.size() - 1; i++) {
            min = i;
            for (int j = i + 1; j < hikes.size(); j++) {
                if (hikes.get(min).getName().compareTo(hikes.get(j).getName()) > 0) { // min < j = negative
                    min = j;
                }
            }
            Hike temp = hikes.get(i);
            hikes.set(i, hikes.get(min));
            hikes.set(min, temp);
        }
    }

    // REQUIRES: !hikes.isEmpty()
    // MODIFIES: this
    // EFFECTS: sorts hikes in descending order of name, if equal,
    //          then first hike will remain first
    public void sortByRating() {
        int max;
        for (int i = 0; i < hikes.size() - 1; i++) {
            max = i;
            for (int j = i + 1; j < hikes.size(); j++) {
                if (hikes.get(max).getRating() < hikes.get(j).getRating()) {
                    max = j;
                }
            }
            Hike temp = hikes.get(i);
            hikes.set(i, hikes.get(max));
            hikes.set(max, temp);
        }
    }

    // EFFECTS: returns first hike in list with matching name
    //          otherwise returns null
    public Hike selectHike(String name) {
        for (Hike h : hikes) {
            if (h.getName().equals(name)) {
                return h;
            }
        }
        return null;
    }

    // REQUIRES: index < hikes.size()
    // EFFECTS: returns hike in hikes at given index
    public Hike selectHike(int index) {
        return hikes.get(index);
    }

    // EFFECTS: returns string representation of HikeList
    public String toString() {
        String result = "";
        int index = 0;
        for (Hike h: hikes) {
            result = result + index +  ". " + h.toString() + "\n";
            index++;
        }
        return result;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("hikes", hikesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray hikesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Hike h : hikes) {
            jsonArray.put(h.toJson());
        }

        return jsonArray;
    }
}
