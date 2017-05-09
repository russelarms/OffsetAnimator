package com.russelarms.offsetanimator;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;


import com.russelarms.offsetanimator.util.CollectionSizeAdjuster;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds collection of {@link OffsetAnimator} animators. Initialized when onGlobalLayout callback is called.
 */
public class Scene {

    private final List<Page> pages = new ArrayList<>();
    private final CollectionSizeAdjuster<Page> adjuster = new CollectionSizeAdjuster<>(pages, new CollectionSizeAdjuster.UnitFactory<Page>() {
        @Override
        public Page init() {
            return new Page();
        }
    });
    private final InitializerFunc initializer;
    private boolean initialized;

    public static Scene create(View rootView, InitializerFunc func) {
        Scene scene = new Scene(func);
        scene.scheduleInit(rootView);
        return scene;
    }

    private Scene(InitializerFunc initializer) {
        this.initializer = initializer;
    }

    public Page page(int number) {
        adjuster.adjustSizeIfNeed(number);
        return pages.get(number);
    }

    public void act(int pageNumber, float positionOffset) {
        if (pageNumber < 0 || pageNumber > pages.size() - 1) return;

        pages.get(pageNumber).act(positionOffset);
    }

    private void scheduleInit(final View rootView) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //layout is ready
                //this might be called after on resume, so all initialization that depends on views measures should be done here
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                initializer.init();
                initialized = true;
            }
        });
    }

    public boolean isInitialized() {
        return initialized;
    }

    public interface InitializerFunc {
        void init();
    }
}
