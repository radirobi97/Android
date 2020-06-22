# SplashScreen

## Components
- **SplashActivity**
- **SlashViewModel**
- **Coroutines**
  - **liveData builder**
- **Dagger**

### Todos in Manifest
SplashActivity should be the launcher one.
```xml
<activity
    android:name=".ui.activities.splash.SplashActivity"
    android:theme="@style/AppTheme.NoActionBar">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />

        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

### Todos in styles.xml
- attributes name CANNOT be android:windowActionBar, etc..

```xml
<style name="AppTheme" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>
</style>


<style name="AppTheme.NoActionBar">
    <item name="windowActionBar">false</item>
    <item name="windowNoTitle">true</item>
</style>
```

### Todos in layout files
`activity_splash.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data></data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".ui.activities.splash.SplashActivity">

        <ImageView
            android:id="@+id/iv_logo"
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:src="@drawable/ic_logo"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <androidx.core.widget.ContentLoadingProgressBar
            android:id="@+id/clpb_splash"
            style="@style/Widget.AppCompat.ProgressBar.Horizontal"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:indeterminate="true"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

### Todos in kotlin code
`SplashActivity.kt`
- injecting custom `viewModelFactory`
- getting `SplashViewModel` provided by viewModelFactory
- observing `liveData`
- starting new activity and finishing current


```java
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        val splashViewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)

        splashViewModel.launchActivityEvent.observe(this, Observer { name ->
            startActivity(Intent(this, NextActivity::class.java))
            finish()
        })
    }
}
```

`SplashViewModel.kt`
- initializing splash time
- emitting liveData


```java
class SplashViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val SPLASH_TIME = 5_000L
        const val START_ACTIVITY = "start_activity"
    }

    val launchActivityEvent = liveData<String> {
        delay(SPLASH_TIME)
        emit(START_ACTIVITY)
    }
}
```
