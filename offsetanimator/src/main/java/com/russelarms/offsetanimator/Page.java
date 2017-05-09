package com.russelarms.offsetanimator;


import com.russelarms.offsetanimator.util.CollectionSizeAdjuster;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a page with animation.
 */
public class Page {

    private final List<Step> steps = new ArrayList<>();
    private final CollectionSizeAdjuster<Step> adjuster = new CollectionSizeAdjuster<>(steps, new CollectionSizeAdjuster.UnitFactory<Step>() {
        @Override
        public Step init() {
            return new Step();
        }
    });

    void act(float positionOffset) {
        for (Step step : steps) {
            step.animate(positionOffset);
        }
    }

    public Step step(int number) {
        adjuster.adjustSizeIfNeed(number);
        return steps.get(number);
    }
}
