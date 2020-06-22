# Shared Element Transition

## 1. Define transition name to the proper view in layout, e.g.:
`android:transitionName="example_transition"`

## 2. Add proper flag to the theme
`<item name="android:windowContentTransitions">true</item>`

## 3. Start activity with transition
```java
val intent = Intent(MainActivity::class.java, Activity2::class.java);
val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, imageView.transitionName);
startActivity(intent, options.toBundle());
```

## 4. Exclude some elements from transition

```java
val fade = Fade()
decor = getWindow().getDecorView()

#ActionBar
fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true)

#statusbar
fade.excludeTarget(android.R.id.statusBarBackground, true)

# navigation bar
fade.excludeTarget(android.R.id.navigationBarBackground, true)

## 5. Activit y should be finished with:
`finishAfterTransition()`

It reverses entry and exit transitions and after that calles finish().
