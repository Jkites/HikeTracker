package persistence;

import model.Hike;
import model.HikeList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads JSON data to HikeList
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads hike list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public HikeList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHikeList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses hike list from JSON object and returns it
    private HikeList parseHikeList(JSONObject jsonObject) {
        HikeList hl = new HikeList();
        addHikes(hl, jsonObject);
        return hl;
    }

    // MODIFIES: hl
    // EFFECTS: parses hikes from JSON object and adds them to hike list
    private void addHikes(HikeList hl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("hikes");
        for (Object json : jsonArray) {
            JSONObject nextHike = (JSONObject) json;
            addHike(hl, nextHike);
        }
    }

    // MODIFIES: hl
    // EFFECTS: parses hike from JSON object and adds it to HikeList
    private void addHike(HikeList hl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double length = jsonObject.getDouble("length");
        int rating = jsonObject.getInt("rating");
        Hike hike = new Hike(name, length, rating);
        hl.addHike(hike);
    }
}
