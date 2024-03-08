package persistence;

import model.Hike;
import model.HikeList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            HikeList hl = new HikeList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            HikeList hl = new HikeList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHikeList.json");
            writer.open();
            writer.write(hl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyHikeList.json");
            hl = reader.read();
            assertTrue(hl.isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            HikeList hl = new HikeList();
            hl.addHike(new Hike("Rattlesnake",3, 3));
            hl.addHike(new Hike("Battle",2,4));
            hl.addHike(new Hike("Certain", 1, 2));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHikeList.json");
            writer.open();
            writer.write(hl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralHikeList.json");
            hl = reader.read();
            assertFalse(hl.isEmpty());
            checkHike("Rattlesnake", 3, 3, hl.selectHike("Rattlesnake"));
            checkHike("Battle", 2, 4, hl.selectHike("Battle"));
            checkHike("Certain", 1, 2, hl.selectHike("Certain"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
