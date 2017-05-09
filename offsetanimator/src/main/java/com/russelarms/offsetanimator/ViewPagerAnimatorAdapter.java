package com.russelarms.offsetanimator;

import android.util.Log;

/**
 * Binds ViewPager position updates to {@link Scene} instance.
 */
public class ViewPagerAnimatorAdapter {

    private final Scene scene;
    private int prevPosition;

    public ViewPagerAnimatorAdapter(Scene scene) {
        this.scene = scene;
    }

    public void onPageScrolled(int position, float positionOffset) {
        try {
            if (!scene.isInitialized()) {
                return;
            }

            if (positionOffset == 0.0f && prevPosition != position) {
                positionOffset = 1f;
            }

            if (position == prevPosition) {
                scene.act(position, positionOffset);
            }

            prevPosition = position;
        } catch (RuntimeException e) {
            String where = "position is " + position + " previous position is " + prevPosition +
                    " position offset is " + positionOffset + "\n cause: " + e;
            Log.e(ViewPagerAnimatorAdapter.class.getCanonicalName(), where);
            throw e;
        }
    }
}
