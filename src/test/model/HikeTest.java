package model;

import org.junit.jupiter.api.BeforeEach;

class HikeTest {
    HikeList hl1;
    Hike h1;

    @BeforeEach
    void runBefore() {
        hl1 = new HikeList();
        h1 = new Hike("Rattlesnake",12.4, 3);
    }
}