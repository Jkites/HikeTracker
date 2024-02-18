package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HikeListTest {
    HikeList hl1;
    Hike h1;
    Hike h2;
    Hike h3;

    @BeforeEach
    void runBefore() {
        hl1 = new HikeList();
        h1 = new Hike("Rattlesnake",3, 3);
        h2 = new Hike("Battle",2,4);
        h3 = new Hike("Certain", 1, 2);
    }

    @Test
    void testConstructor() {
        assertTrue(hl1.isEmpty());
    }

    @Test
    void testAddHike() {
        assertNull(hl1.selectHike("Rattlesnake"));
        assertNull(hl1.selectHike("Battle"));
        assertNull(hl1.selectHike("Certain"));
        hl1.addHike(h1);
        assertEquals(h1, hl1.selectHike("Rattlesnake"));
        assertNull(hl1.selectHike("Battle"));
        assertNull(hl1.selectHike("Certain"));
        hl1.addHike(h2);
        assertEquals(h2, hl1.selectHike("Battle"));
        assertEquals(h1, hl1.selectHike("Rattlesnake"));
        assertNull(hl1.selectHike("Certain"));
    }

    @Test
    void testRemoveHike() {
        hl1.addHike(h1);
        hl1.addHike(h2);
        hl1.addHike(h3);
        assertEquals(h1, hl1.selectHike("Rattlesnake"));
        assertEquals(h2, hl1.selectHike("Battle"));
        assertEquals(h3, hl1.selectHike("Certain"));
        hl1.removeHike(1);
        assertEquals(h1, hl1.selectHike("Rattlesnake"));
        assertNull(hl1.selectHike("Battle"));
        assertEquals(h3, hl1.selectHike("Certain"));
        hl1.removeHike(0);
        assertNull(hl1.selectHike("Rattlesnake"));
        assertNull(hl1.selectHike("Battle"));
        assertEquals(h3, hl1.selectHike("Certain"));
        hl1.removeHike(0);
        assertNull(hl1.selectHike("Rattlesnake"));
        assertNull(hl1.selectHike("Battle"));
        assertNull(hl1.selectHike("Certain"));
    }

    @Test
    void testSortByLength() {
        hl1.addHike(h1);
        hl1.addHike(h3);
        hl1.addHike(h2);
        assertEquals(h1, hl1.selectHike(0));
        assertEquals(h2, hl1.selectHike(2));
        assertEquals(h3, hl1.selectHike(1));
        hl1.sortByLength();
        assertEquals(h1, hl1.selectHike(0));
        assertEquals(h2, hl1.selectHike(1));
        assertEquals(h3, hl1.selectHike(2));
    }

    @Test
    void testSortByName() {
        hl1.addHike(h1);
        hl1.addHike(h2);
        hl1.addHike(h3);
        assertEquals(h1, hl1.selectHike(0));
        assertEquals(h2, hl1.selectHike(1));
        assertEquals(h3, hl1.selectHike(2));
        hl1.sortByName();
        assertEquals(h1, hl1.selectHike(2));
        assertEquals(h2, hl1.selectHike(0));
        assertEquals(h3, hl1.selectHike(1));
    }

    @Test
    void testSortByRating() {
        hl1.addHike(h1);
        hl1.addHike(h2);
        hl1.addHike(h3);
        assertEquals(h1, hl1.selectHike(0));
        assertEquals(h2, hl1.selectHike(1));
        assertEquals(h3, hl1.selectHike(2));
        hl1.sortByRating();
        assertEquals(h1, hl1.selectHike(1));
        assertEquals(h2, hl1.selectHike(0));
        assertEquals(h3, hl1.selectHike(2));
    }

    @Test
    void testToString() {
        hl1.addHike(h1);
        hl1.addHike(h2);
        hl1.addHike(h3);
        assertEquals(h1.toString() + "\n" + h2.toString() + "\n" + h3.toString() + "\n", hl1.toString());
    }
}
