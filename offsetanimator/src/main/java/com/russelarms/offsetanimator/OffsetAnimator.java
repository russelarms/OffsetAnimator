package com.russelarms.offsetanimator;

import android.view.animation.Interpolator;

/**
 * Class that computes a value for position offset between 0 and 1.
 */
public class OffsetAnimator {

    /**
     * Start point. May represent any parameter: position, scale, rotation, opacity etc.
     */
    protected final float x1;
    /**
     * End point. May represent any parameter: position, scale, rotation, opacity etc.
     */
    protected final float x2;

    protected AnimatorParams params = new AnimatorParams();

    protected OffsetAnimator(float x1, float x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    /**
     * Returns calculation result to {@link ValueListener}.
     *
     * @param positionOffset a float between 0 and 1.
     */
    public void animate(float positionOffset) {
        boolean invokeCallback = shouldInvokeCallback(positionOffset);
        float currentValue = computeValueByOffset(positionOffset);
        if (invokeCallback) {
            params.getValueListener().onValue(currentValue);
        }
    }

    protected float computeValueByOffset(float positionOffset) {
        positionOffset = computeByTimeShift(positionOffset);
        positionOffset = computeByInterpolator(positionOffset);
        return computeExactPosition(x1, x2, positionOffset);
    }

    private float computeByInterpolator(float positionOffset) {
        if (params.hasInterpolator()) {
            return params.getInterpolator().getInterpolation(positionOffset);
        }
        return positionOffset;
    }

    private float computeExactPosition(float x1, float x2, float positionOffset) {
        float distance = x2 - x1;
        return x1 + positionOffset * distance;
    }

    private float computeByTimeShift(float positionOffset) {
        if (!params.hasTimeShift()) {
            return positionOffset;
        }

        if (isInsideTimeBounds(positionOffset)) {
            positionOffset = (positionOffset - params.getStartThreshold()) / params.getDuration();
        } else if (positionOffset < params.getStartThreshold()) {
            positionOffset = 0.0f;
        } else if (positionOffset > params.getStartThreshold() + params.getDuration()) {
            positionOffset = 1f;
        }

        return positionOffset;
    }

    private boolean shouldInvokeCallback(float position) {
        return !params.hasTimeShift() || isInsideTimeBounds(position);
    }

    protected boolean isInsideTimeBounds(float position) {
        return position >= params.getStartThreshold() && position <= params.getStartThreshold() + params.getDuration();
    }

    public void setParams(AnimatorParams params) {
        this.params = params;
        ensureListener();
    }

    protected void ensureListener() {
        if (params.getValueListener() == null) {
            throw new IllegalArgumentException("Value Listener must be set to assign values.");
        }
    }

    public OffsetAnimator setStartThreshold(float startThreshold) {
        params.setStartThreshold(startThreshold);
        return this;
    }

    public OffsetAnimator setDuration(float duration) {
        params.setDuration(duration);
        return this;
    }

    public OffsetAnimator setInterpolator(Interpolator interpolator) {
        params.setInterpolator(interpolator);
        return this;
    }

    public OffsetAnimator setListener(ValueListener valueListener) {
        params.setListener(valueListener);
        return this;
    }

    public interface ValueListener {
        void onValue(float value);
    }
}