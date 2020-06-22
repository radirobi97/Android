# Animate layout changes
Default animation can be triggered when the layout changes in runtime. The following attribute should be set:
`android:animateLayoutChanges="true"`

If we want to apply animiton to views are alreay visible the following should be set in code:
https://proandroiddev.com/the-little-secret-of-android-animatelayoutchanges-e4caab2fddec

```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
    layoutID.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
}
```
