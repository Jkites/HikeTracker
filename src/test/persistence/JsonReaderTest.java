package persistence;

import model.HikeList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            HikeList hl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyHikeList.json");
        try {
            HikeList hl = reader.read();
            assertTrue(hl.isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralHikeList.json");
        try {
            HikeList hl = reader.read();
            assertFalse(hl.isEmpty());
            checkHike("Rattlesnake", 3, 3, hl.selectHike("Rattlesnake"));
            checkHike("Battle", 2, 4, hl.selectHike("Battle"));
            checkHike("Certain", 1, 2, hl.selectHike("Certain"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
