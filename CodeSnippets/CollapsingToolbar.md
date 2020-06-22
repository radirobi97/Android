# CollapsingToolbar
A good reference can be found:
https://github.com/PatilShreyas/Foodium/blob/master/app/src/main/res/layout/activity_post_details.xml

collapse mode and scrollFlags description:
- https://medium.com/@tpom6oh/managing-content-in-collapsingtoolbarlayout-e9afbc91be24
- https://medium.com/@tonia.tkachuk/appbarlayout-scroll-behavior-with-layout-scrollflags-2eec41b4366b
- https://medium.com/martinomburajr/android-design-collapsing-toolbar-scrollflags-e1d8a05dcb02

## Todos in build.gradle
Import material design

```xml
"com.google.android.material:material:1.1.0"
```

## Todos in styles.xml

Use **NoActionBar** as style.

`<style name="AppTheme.NoActionBar">`


## Todos in layoutfile
- cordinator layout
- appbar layout


```xml
<?xml version="1.0" encoding="utf-8"?>

<androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true">

    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/appbar"
        android:layout_width="match_parent"
        android:layout_height="@dimen/app_bar_height"
        android:fitsSystemWindows="true">

        <com.google.android.material.appbar.CollapsingToolbarLayout
            android:id="@+id/collapsing_toolbar"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:fitsSystemWindows="true"
            app:contentScrim="?attr/colorPrimary"   <!-- ilyen lesz a toolbar szine miutan osszecsuktuk -->
            app:expandedTitleGravity="top"
            app:expandedTitleTextAppearance="@style/ToolbarTitleTextAppearance"
            app:layout_scrollFlags="scroll|exitUntilCollapsed|snap"
            app:statusBarScrim="@color/colorPrimary"  <!-- ilyen lesz a statusbar szine miutan osszecsuktuk -->
            app:title="@string/app_name">

            <ImageView
                android:id="@+id/imageView"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:contentDescription="@string/text_post_header"
                android:fitsSystemWindows="true"
                android:scaleType="centerCrop"
                android:transitionName="photo"
                app:layout_collapseMode="parallax"
                app:layout_collapseParallaxMultiplier="0.5"
                tools:src="@tools:sample/backgrounds/scenic" />

            <androidx.appcompat.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                app:layout_collapseMode="pin"
                app:popupTheme="@style/AppTheme.PopupOverlay" />

        </com.google.android.material.appbar.CollapsingToolbarLayout>

    </com.google.android.material.appbar.AppBarLayout>


    </NestedScrollView
      ....
      app:layout_behavior="@string/appbar_scrolling_view_behavior"

</androidx.coordinatorlayout.widget.CoordinatorLayout>

```
