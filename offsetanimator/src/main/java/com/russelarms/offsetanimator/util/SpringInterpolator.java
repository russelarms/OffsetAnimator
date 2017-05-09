package com.russelarms.offsetanimator.util;

import android.view.animation.Interpolator;

public class SpringInterpolator implements Interpolator {

    private final float factor;

    public SpringInterpolator(float factor) {
        this.factor = factor;
    }

    private float calculateSpring(float factor, float x) {
        return (float) (Math.pow(2, -10 * x) * Math.sin((x - factor / 4) * (2 * Math.PI) / factor) + 1);
    }

    @Override
    public float getInterpolation(float x) {
        return calculateSpring(factor, x);
    }
}