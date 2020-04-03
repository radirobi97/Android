package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.example.todo.adapter.SimpleItemRecyclerViewAdapter
import com.example.todo.adapter.TodoCreateFragment
import com.example.todo.model.Todo
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlinx.android.synthetic.main.todo_list.*


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [TodoDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class TodoListActivity : AppCompatActivity(), SimpleItemRecyclerViewAdapter.TodoItemClickListener, TodoCreateFragment.TodoCreatedListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private lateinit var simpleItemRecyclerViewAdapter: SimpleItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (todo_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val demoData = mutableListOf(
            Todo("title1", Todo.Priority.LOW, "2011. 09. 26.", "description1"),
            Todo("title2", Todo.Priority.MEDIUM, "2011. 09. 27.", "description2"),
            Todo("title3", Todo.Priority.HIGH, "2011. 09. 28.", "description3")
        )
        simpleItemRecyclerViewAdapter = SimpleItemRecyclerViewAdapter()
        simpleItemRecyclerViewAdapter.itemClickListener = this
        simpleItemRecyclerViewAdapter.addAll(demoData)
        todo_list.adapter = simpleItemRecyclerViewAdapter
    }

    override fun onItemClick(todo: Todo) {
        if (twoPane) {
            val fragment = TodoDetailFragment.newInstance(todo.description)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.todo_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(this, TodoDetailActivity::class.java)
            intent.putExtra(TodoDetailActivity.KEY_DESC, todo.description)
            startActivity(intent)
        }
    }

    override fun onItemLongClick(position: Int, view: View): Boolean {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu_todo)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> simpleItemRecyclerViewAdapter.deleteRow(position)
            }
            false
        }
        popup.show()
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.itemCreateTodo) {
            val todoCreateFragment = TodoCreateFragment()
            todoCreateFragment.show(supportFragmentManager, "TAG")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTodoCreated(todo: Todo) {

        simpleItemRecyclerViewAdapter.addItem(todo)
    }

}
