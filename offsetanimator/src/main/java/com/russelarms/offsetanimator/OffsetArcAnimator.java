package com.russelarms.offsetanimator;

import android.view.View;

import com.russelarms.offsetanimator.util.ArcMetric;
import com.russelarms.offsetanimator.util.ArcUtils;


/**
 * Moves an object along a circular path.
 * Assigns a value to the view by itself, does not require a value listener.
 */
class OffsetArcAnimator extends OffsetAnimator {

    private final View view;
    private final ArcMetric arcMetric;

    protected OffsetArcAnimator(ArcMetric arcMetric, View view) {
        super(arcMetric.getStartDegree(), arcMetric.getEndDegree());
        this.view = view;
        this.arcMetric = arcMetric;
    }

    @Override
    public void animate(float positionOffset) {
        float currentDegree = computeValueByOffset(positionOffset);

        if (!isInsideTimeBounds(positionOffset)) {
            return;
        }

        assignValue(currentDegree);
    }

    @Override
    protected void ensureListener() {
    }

    private void assignValue(float currentDegree) {
        float x = arcMetric.getAxisPoint().x + arcMetric.getRadius()
                * ArcUtils.cos(currentDegree);
        float y = arcMetric.getAxisPoint().y - arcMetric.getRadius()
                * ArcUtils.sin(currentDegree);
        view.setX(x - view.getWidth() / 2);
        view.setY(y - view.getHeight() / 2);
    }
}
