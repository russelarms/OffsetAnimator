package com.russelarms.offsetanimator;

import android.view.View;

import com.russelarms.offsetanimator.util.ArcSide;


/**
 * Represents a single animation.
 * Uses lazy initialization to simplify animation script.
 */
public class Step {

    private final AnimatorParams params = new AnimatorParams();
    private OffsetAnimator offsetAnimator;
    private LazyInitializer lazy;

    void animate(float positionOffset) {
        lazyInit();
        offsetAnimator.animate(positionOffset);
    }

    public AnimatorParams createAnimation(final float x1, final float x2) {
        lazy = new LazyInitializer() {
            @Override
            public OffsetAnimator get() {
                return AnimatorFactory.createAnimator(x1, x2);
            }
        };
        return params;
    }

    public AnimatorParams createArcAnimation(final View view, final float startX, final float startY,
                                             final float endX, final float endY,
                                             final float degree, final ArcSide side) {
        lazy = new LazyInitializer() {
            @Override
            public OffsetAnimator get() {
                return AnimatorFactory.createArcAnimator(view, startX, startY, endX, endY, degree, side);
            }
        };
        return params;
    }

    public AnimatorParams createAnimation(LazyInitializer initializer) {
        lazy = initializer;
        return params;
    }

    private void lazyInit() {
        if (lazy == null) return;

        offsetAnimator = lazy.get();
        offsetAnimator.setParams(params);
        lazy = null;
    }

    public interface LazyInitializer {
        OffsetAnimator get();
    }
}
