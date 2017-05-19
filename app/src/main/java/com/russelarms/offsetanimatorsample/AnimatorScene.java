package com.russelarms.offsetanimatorsample;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.russelarms.offsetanimator.AnimatorFactory;
import com.russelarms.offsetanimator.Scene;
import com.russelarms.offsetanimator.util.ArcSide;
import com.russelarms.offsetanimator.util.ArcUtils;
import com.russelarms.offsetanimator.util.SpringInterpolator;

import butterknife.BindView;
import butterknife.ButterKnife;
import static com.russelarms.offsetanimatorsample.ScreenUtils.convertDIPToPixels;

/**
 * Custom view that containes {@link Scene} and animation script.
 */
public class AnimatorScene extends RelativeLayout {

    private Point screenDimensions;

    @BindView(R.id.ocean)
    View ocean;
    @BindView(R.id.fish_left)
    View fishLeft;
    @BindView(R.id.fish_left_bottom)
    View fishLeftBottom;
    @BindView(R.id.fish_right)
    View fishRight;
    @BindView(R.id.submarine)
    View submarine;

    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    public AnimatorScene(Context context) {
        super(context);
        init();
    }

    public AnimatorScene(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.scene_layout, this);
        ButterKnife.bind(this);

        screenDimensions = ScreenUtils.getScreenDimensions(getContext());

        scene = Scene.create(getRootView(), () -> {
            prepare();
            initSteps();
        });
    }

    public void prepare() {
        submarine.setY(screenDimensions.y + dipToPx(50));
        submarine.setX(screenDimensions.x);
        fishLeftBottom.setY(screenDimensions.y);
    }

    private void initSteps() {
        scene.page(0).step(0)
                .createAnimation(ocean.getY(), ocean.getY() - dipToPx(120))
                .setDuration(0.8f)
                .setListener(value -> ocean.setY(value));

        scene.page(0).step(1)
                .createAnimation(fishLeft.getX(), fishLeft.getX() + dipToPx(160))
                .setDuration(0.3f)
                .setStartThreshold(0.5f)
                .setListener(value -> fishLeft.setX(value));

        scene.page(0).step(2)
                .createAnimation(fishRight.getX(), fishRight.getX() - dipToPx(160))
                .setDuration(0.5f)
                .setStartThreshold(0.3f)
                .setListener(value -> fishRight.setX(value));

        scene.page(0).step(3)
                .createAnimation(fishLeftBottom.getY(), fishLeftBottom.getY() - screenDimensions.y / 2)
                .setListener(value -> fishLeftBottom.setY(value));

        scene.page(1).step(0)
                .createAnimation(1926, 1032)
                .setInterpolator(new SpringInterpolator(0.8f))
                .setListener(value -> submarine.setY(value));

        scene.page(1).step(1)
                .createAnimation(submarine.getX(), screenDimensions.x / 2 - submarine.getWidth() / 2)
                .setListener(value -> submarine.setX(value));


        scene.page(2).step(0)
                .createAnimation(() -> AnimatorFactory.createArcAnimator(submarine,
                        ArcUtils.centerX(submarine),
                        ArcUtils.centerY(submarine),
                        ArcUtils.centerX(submarine),
                        dipToPx(48),
                        180f, ArcSide.LEFT))
                .setDuration(0.5f);

        scene.page(2).step(1)
                .createAnimation(() -> AnimatorFactory.createArcAnimator(submarine,
                        ArcUtils.centerX(submarine),
                        dipToPx(48),
                        submarine.getX() + submarine.getWidth() / 2,
                        ArcUtils.centerY(submarine),
                        180f, ArcSide.RIGHT))
                .setStartThreshold(0.5f)
                .setDuration(0.5f);

        scene.page(2).step(2)
                .createAnimation(0, 90)
                .setDuration(0.25f)
                .setListener(value -> submarine.setRotation(value));

        scene.page(2).step(3)
                .createAnimation(90, 180)
                .setStartThreshold(0.25f)
                .setDuration(0.25f)
                .setListener(value -> submarine.setRotation(value));

        scene.page(2).step(4)
                .createAnimation(180, 270)
                .setStartThreshold(0.5f)
                .setListener(value -> submarine.setRotation(value))
                .setDuration(0.25f);

        scene.page(2).step(5)
                .createAnimation(270, 360)
                .setStartThreshold(0.75f)
                .setDuration(0.25f)
                .setListener(value -> submarine.setRotation(value));


        scene.page(3).step(0)
                .createAnimation(() -> AnimatorFactory.createAnimator(fishLeft.getX(), fishLeft.getX() - dipToPx(160)))
                .setDuration(0.5f)
                .setListener(value -> fishLeft.setX(value));

        scene.page(3).step(1)
                .createAnimation(() -> new AnotherOffsetAnimator(fishRight.getX(), fishRight.getX() + dipToPx(160)))
                .setDuration(0.5f)
                .setListener(value -> fishRight.setX(value));

        scene.page(3).step(2)
                .createAnimation(() -> AnimatorFactory.createAnimator(fishLeftBottom.getY(), fishLeftBottom.getY() + screenDimensions.y / 2))
                .setDuration(0.5f)
                .setListener(value -> fishLeftBottom.setY(value));

        scene.page(3).step(3)
                .createAnimation(() -> AnimatorFactory.createAnimator(fishLeftBottom.getY(), fishLeftBottom.getY() + screenDimensions.y / 2))
                .setDuration(0.5f)
                .setListener(value -> fishLeftBottom.setY(value));

        scene.page(3).step(4)
                .createAnimation(() -> AnimatorFactory.createAnimator(submarine.getScaleX(), submarine.getScaleX() * 2))
                .setListener(value -> {
                    submarine.setScaleX(value);
                    submarine.setScaleY(value);
                });

        scene.page(3).step(5)
                .createAnimation(1, 0.5f)
                .setListener(value -> {
                    fishLeftBottom.setScaleY(value);
                    fishLeftBottom.setScaleX(value);
                    fishLeft.setScaleX(value);
                    fishLeft.setScaleY(value);
                    fishRight.setScaleX(value);
                    fishRight.setScaleY(value);
                });

        scene.page(3).step(6)
                .createAnimation(() -> AnimatorFactory.createAnimator(submarine.getY(), screenDimensions.y / 2 - submarine.getHeight()))
                .setInterpolator(new DecelerateInterpolator())
                .setListener(value -> submarine.setY(value));
    }

    private int dipToPx(int dip) {
        return convertDIPToPixels(getContext(), dip);
    }
}
