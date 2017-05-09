package com.russelarms.offsetanimatorsample;


import com.russelarms.offsetanimator.OffsetAnimator;

/**
 * Inheritance demonstration.
 */
public class AnotherOffsetAnimator extends OffsetAnimator {

    public AnotherOffsetAnimator(float x1, float x2) {
        super(x1, x2);
    }

    @Override
    public void animate(float positionOffset) {
        float duration = params.getDuration();
        super.animate(positionOffset);
    }
}
