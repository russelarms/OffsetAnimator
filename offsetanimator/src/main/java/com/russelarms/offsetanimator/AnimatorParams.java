package com.russelarms.offsetanimator;

import android.view.animation.Interpolator;

/**
 * Holds main animator params like time offset, duration, interpolator and value listener.
 */
public final class AnimatorParams {
    float startThreshold;
    float duration;
    Interpolator interpolator;
    OffsetAnimator.ValueListener valueListener;

    public AnimatorParams setStartThreshold(float startThreshold) {
        this.startThreshold = startThreshold;
        if (duration == 0) {
            this.duration = 1 - startThreshold;
        }
        return this;
    }

    public AnimatorParams setDuration(float duration) {
        this.duration = duration;
        return this;
    }

    public AnimatorParams setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public AnimatorParams setListener(OffsetAnimator.ValueListener valueListener) {
        this.valueListener = valueListener;
        return this;
    }

    public float getStartThreshold() {
        return startThreshold;
    }

    public float getDuration() {
        return duration;
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public OffsetAnimator.ValueListener getValueListener() {
        return valueListener;
    }

    public boolean hasInterpolator() {
        return interpolator != null;
    }

    boolean hasTimeShift() {
        return startThreshold > 0 || duration > 0;
    }
}