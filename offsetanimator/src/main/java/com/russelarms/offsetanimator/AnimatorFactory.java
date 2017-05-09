package com.russelarms.offsetanimator;

import android.view.View;

import com.russelarms.offsetanimator.util.ArcMetric;
import com.russelarms.offsetanimator.util.ArcSide;


public class AnimatorFactory {

    public static OffsetAnimator createAnimator(float x1, float x2) {
        return new OffsetAnimator(x1, x2);
    }

    public static OffsetAnimator createArcAnimator(View view, float startX, float startY, float endX, float endY,
                                                   float degree, ArcSide side) {
        ArcMetric arcMetric = ArcMetric.evaluate(startX, startY, endX, endY, degree, side);
        return new OffsetArcAnimator(arcMetric, view);
    }

}
