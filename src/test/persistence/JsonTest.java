package persistence;

import model.Hike;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkHike(String name, double length, int rating, Hike hike) {
        assertEquals(name, hike.getName());
        assertEquals(length, hike.getLength());
        assertEquals(rating, hike.getRating());
    }
}
