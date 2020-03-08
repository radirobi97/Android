# How to create dialog pop up messages?

Do the following in the manifest file:

```xml
<activity
    android:name=".ActivityName" <--! name of the activity
    android:label="@string/title_activity" <--! title
    android:parentActivityName=".ParentActivity" <--! name of parent activity
    android:theme="@style/Theme.AppCompat.Light.Dialog">
    <meta-data
        android:name="android.support.PARENT_ACTIVITY"
        android:value="package.To.MainActivity" />
</activity>
```
