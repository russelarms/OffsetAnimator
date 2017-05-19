# OffsetAnimator

OffsetAnimator lets animate objects basing on touchevents, so users can be engaged in an animation process.

![Yellow submarine](./preview/sample_video.gif)

## Usage

Add the library to your project:

1)  Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
2)  Add the dependency:

```groovy
dependencies {
    compile 'com.github.russelarms:offsetanimator:1.0.1'
}
```

## Requirements

Min sdk version is 11.
The library doesn't have any transitive dependencies.

## Scene

To create viewpager-based animation you should bind a scene to position updates:

```java
ViewPagerAnimatorAdapter animatorAdapter = new ViewPagerAnimatorAdapter(scene.getScene());
viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        animatorAdapter.onPageScrolled(position, positionOffset);
    }
});
```

Then create a scene instance and pass it a root view and an initializer function:

```java
Scene.create(getRootView(), () -> initSteps());
```

## Scripting

Script desired animation like this:

```java
private void initSteps() {
    scene.page(0).step(0)
            .createAnimation(ocean.getY(), ocean.getY() - convertDIPToPixels(getContext(), 120))
            .setStartThreshold(0.5f)
            .setDuration(0.8f)
            .setInterpolator(new DecelerateInterpolator())
            .setListener(value -> ocean.setY(value));
}
```

A single page is taken as 1.0f. So duration 0.5f is a half of a distance.

## Lazy initialization

It is not always convenient to specify exact view positions after some script done, so we can pass a lazy initializer. In this example animator will be initialized only on page 3 with corresponding coordinates:

```java
scene.page(3).step(3)
        .createAnimation(() -> AnimatorFactory.createAnimator(fishLeftBottom.getY(), fishLeftBottom.getY() + screenDimensions.y / 2))
        .setDuration(0.5f)
        .setListener(value -> fishLeftBottom.setY(value));
```

Here's how to set the rotation:

```java
scene.page(2).step(2)
        .createAnimation(0, 90)
        .setDuration(0.25f)
        .setListener(value -> submarine.setRotation(value));
```




## Interpolators

We can specify an interpolator (from android.view.animation). The library ships its own SpringInterpolator:

```java
scene.page(1).step(0)
        .createAnimation(1926, 1032)
        .setInterpolator(new SpringInterpolator(0.8f))
        .setListener(value -> submarine.setY(value));
```

## Arc animations

Create arc animation:

```java
scene.page(2).step(1)
        .createAnimation(() -> AnimatorFactory.createArcAnimator(submarine,
                Utils.centerX(submarine),
                convertDIPToPixels(getContext(), 48),
                submarine.getX() + submarine.getWidth() / 2,
                Utils.centerY(submarine),
                180f, Side.RIGHT))
        .setStartThreshold(0.5f)
        .setDuration(0.5f);
```
While standard OffsetAnimator requires user to set listeners and update fields by hand, arc animator doesn't need listener: it assigns value implicitly.

## Inheritance

OffsetAnimator is open to be inherited:

```java
public class AnotherOffsetAnimator extends OffsetAnimator {

    public AnotherOffsetAnimator(float x1, float x2) {
        super(x1, x2);
    }

    @Override
    public void animate(float position) {
        super.animate(position);
    }
}
```
and to be used with a scene:

```java
scene.page(3).step(1)
        .createAnimation(() -> new AnotherOffsetAnimator(fishRight.getX(), fishRight.getX() + convertDIPToPixels(getContext(), 160)))
        .setDuration(0.5f)
        .setListener(value -> fishRight.setX(value));
```

## License

`OffsetAnimator` is available under the MIT license. See the LICENSE file for more info.
The library uses some code from https://github.com/asyl/ArcAnimator for arc animations.

The submarine and the fish images picked from freepik.com:
<a href='http://www.freepik.com/free-vector/nice-diving-background-with-a-yellow-submarine_891930.htm'>Designed by Freepik</a>
<a href="http://www.freepik.com/free-photos-vectors/background">Background vector created by Freepik</a>


Copyright 2017 russelarms.

