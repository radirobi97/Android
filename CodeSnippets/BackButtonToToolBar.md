# Adding back button on Toolbar

In the manifest the parent activity should be set to the activity where the back button takes place.
`supportActionBar?.setDisplayHomeAsUpEnabled(true)`

If no parant activity was defined, following function should be called instead of previous one:
`supportActionBar?.setDisplayShowHomeEnabled(true)`
