package com.russelarms.offsetanimator;

import android.util.Log;

/**
 * Binds ViewPager position updates to {@link Scene} instance.
 */
public class ViewPagerAnimatorAdapter {

    private final Scene scene;
    private int prevPageNumber;

    public ViewPagerAnimatorAdapter(Scene scene) {
        this.scene = scene;
    }

    /**
     * @param pageNumber     - takes values from 0 and higher
     * @param positionOffset - takes values from 0.0 to 0.9999+
     */
    public void onPageScrolled(int pageNumber, float positionOffset) {
        try {
            if (!scene.isInitialized()) {
                return;
            }

            if (positionOffset > 0.0f || pageNumber == prevPageNumber) {
                scene.act(pageNumber, positionOffset);
            } else {
                scene.act(prevPageNumber, 1f);
            }

            prevPageNumber = pageNumber;
        } catch (RuntimeException e) {
            String where = "position is " + pageNumber + " previous position is " + prevPageNumber +
                    " position offset is " + positionOffset + "\n cause: " + e;
            Log.e(ViewPagerAnimatorAdapter.class.getCanonicalName(), where);
            throw e;
        }
    }
}
