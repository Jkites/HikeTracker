package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HikeTest {
    Hike h1;
    Hike h2;
    Hike h3;

    @BeforeEach
    void runBefore() {
        h1 = new Hike("Rattlesnake",12.4, 3);
        h2 = new Hike("testHike1", -23, -2);
        h3 = new Hike("testHike2", 2, 100);

    }

    @Test
    void testConstructor() {
        assertEquals("Rattlesnake", h1.getName());
        assertEquals(12.4, h1.getLength());
        assertEquals(3, h1.getRating());
        assertEquals(0, h2.getLength());
        assertEquals(0, h2.getRating());
        assertEquals(2, h3.getLength());
        assertEquals(0, h3.getRating());
    }

    @Test
    void testSetName() {
        h1.setName("newName");
        assertEquals("newName", h1.getName());
    }

    @Test
    void testSetLength() {
        h1.setLength(3);
        assertEquals(3, h1.getLength());
        h1.setLength(-1);
        assertEquals(3, h1.getLength());
        h1.setLength(-12);
        assertEquals(3, h1.getLength());
        h1.setLength(250);
        assertEquals(250, h1.getLength());
    }

    @Test
    void testSetRating() {
        h1.setRating(2);
        assertEquals(2, h1.getRating());
        h1.setRating(-3);
        assertEquals(2,h1.getRating());
        assertEquals(0, h2.getRating());
        h2.setRating(1);
        assertEquals(1, h2.getRating());
        h2.setRating(11);
        assertEquals(1, h2.getRating());
        h3.setRating(2);
        h3.setRating(0);
        assertEquals(2, h3.getRating());
        h3.setRating(-1);
        assertEquals(2, h3.getRating());
    }

    @Test
    void testToString() {
        assertEquals("Name: Rattlesnake, Length: 12.4, Rating: 3", h1.toString());
        assertEquals("Name: testHike1, Length: 0.0, Rating: 0", h2.toString());
        assertEquals("Name: testHike2, Length: 2.0, Rating: 0", h3.toString());
    }
}