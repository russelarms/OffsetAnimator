package com.russelarms.offsetanimator.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CollectionSizeAdjusterTest {

    private List<String> list = new ArrayList<>();
    private CollectionSizeAdjuster<String> adjuster = new CollectionSizeAdjuster<>(list, new CollectionSizeAdjuster.UnitFactory<String>() {
        @Override
        public String init() {
            return "loem";
        }
    });

    @Test
    public void adjustSizeIfNeed() throws Exception {
        for (int i = 0; i < 100; i++) {
            useAdjuster(i);
        }
    }

    private void useAdjuster(int number) {
        adjuster.adjustSizeIfNeed(number);
        String s = list.get(number);
        assertNotNull(s);
        assertEquals(list.size() - 1, number);
    }

}