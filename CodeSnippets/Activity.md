# Activity
[activity tasks & stacks](https://www.youtube.com/watch?v=m8sf0UkJkxo)
#### How to mark an activity as the starting activity?
In the manifest: `<action android:name=" android.intent.action.MAIN" />`

#### States of an activity
- `Resumed`: activity lives in the foreground and has focus
- `Paused`: activity lives in the background, can be seen, but has no focus
- `Stopped`: activity lives in the background, can not be seen, and has no focus

###### Sequence of methods when an activity is created
- `onCreate()`: activity created
- `onStart()`: activity can be seen
- `onResume()`: activity became clickable (has focus)

###### Sequence of methods when an activity is done
- `onPause()`: activity losts focus, but seeable
- `onStop()`: activity can not be Sequence
- `onDestory()`: activity will be destroyed

##### By changing activity:
Change from **A** activity to **B** activity:
1. `A.onPause()`
2. `B.onCreate()`
3. `B.onStart()`
4. `B.onResume()`
5. `A.onStop()`
