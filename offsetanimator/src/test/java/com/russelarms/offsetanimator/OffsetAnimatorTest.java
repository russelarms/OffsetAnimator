package com.russelarms.offsetanimator;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OffsetAnimatorTest {

    @Test
    public void getCurrentValue() throws Exception {
        OffsetAnimator offsetAnimator = new OffsetAnimator(0, 100);
        float[] positionOffsets = PositionGenerator.generatePositionOffsets();

        final float[] expectedValues = new float[100];
        for (int i = 1; i < 100; i++) {
            expectedValues[i] = i;
        }

        final float[] actualValues = new float[100];

        for (int i = 0; i < positionOffsets.length; i++) {
            actualValues[i] = offsetAnimator.computeValueByOffset(positionOffsets[i]);
        }

        assertArrayEquals(expectedValues, actualValues, 0.00001f);
    }

    @Test
    public void simpleAnimate() throws Exception {
        final float[] expectedValues = new float[100];
        for (int i = 1; i < 100; i++) {
            expectedValues[i] = i;
        }

        OffsetAnimator offsetAnimator = new OffsetAnimator(0, 100);
        offsetAnimator.setListener(new OffsetAnimator.ValueListener() {

            private int k = 0;

            @Override
            public void onValue(float value) {
                assertEquals(expectedValues[k], value, 0.00001f);
                k++;
            }
        });

        float[] positionOffsets = PositionGenerator.generatePositionOffsets();
        for (float positionOffset : positionOffsets) {
            offsetAnimator.animate(positionOffset);
        }
    }

    @Test
    public void animateWithStartThreshold() throws Exception {
        final AtomicInteger callsCounter = new AtomicInteger();

        OffsetAnimator offsetAnimator = new OffsetAnimator(0, 100);
        offsetAnimator.setStartThreshold(0.5f);
        offsetAnimator.setListener(new OffsetAnimator.ValueListener() {

            @Override
            public void onValue(float value) {
                boolean inBetween = value >= 0 && value <= 100;
                assertTrue(inBetween);
                callsCounter.incrementAndGet();
            }
        });

        float[] positionOffsets = PositionGenerator.generatePositionOffsets();
        for (float positionOffset : positionOffsets) {
            offsetAnimator.animate(positionOffset);
        }

        assertEquals(callsCounter.get(), 50);
    }

    @Test
    public void animateWithDuration() throws Exception {
        final AtomicInteger callsCounter = new AtomicInteger();

        OffsetAnimator offsetAnimator = new OffsetAnimator(0, 100);
        offsetAnimator.setDuration(0.5f);
        offsetAnimator.setListener(new OffsetAnimator.ValueListener() {

            @Override
            public void onValue(float value) {
                boolean inBetween = value >= 0 && value <= 100;
                assertTrue(inBetween);
                callsCounter.incrementAndGet();
            }
        });

        float[] positionOffsets = PositionGenerator.generatePositionOffsets();
        for (float positionOffset : positionOffsets) {
            offsetAnimator.animate(positionOffset);
        }

        assertEquals(51, callsCounter.get());
    }

    @Test
    public void animateWithDurationAndStartThreshold() throws Exception {
        final AtomicInteger callsCounter = new AtomicInteger();

        OffsetAnimator offsetAnimator = new OffsetAnimator(0, 100)
                .setDuration(0.3f)
                .setStartThreshold(0.3f);

        offsetAnimator.setListener(new OffsetAnimator.ValueListener() {

            @Override
            public void onValue(float value) {
                boolean inBetween = value >= 0 && value <= 100;
                assertTrue(inBetween);
                callsCounter.incrementAndGet();
            }
        });

        float[] positionOffsets = PositionGenerator.generatePositionOffsets();
        for (float positionOffset : positionOffsets) {
            offsetAnimator.animate(positionOffset);
        }

        assertEquals(31, callsCounter.get());
    }
}