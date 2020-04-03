package com.example.todo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.model.Todo
import kotlinx.android.synthetic.main.row_todo.view.*

class SimpleItemRecyclerViewAdapter : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val todoList = mutableListOf<Todo>()

    var itemClickListener: TodoItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_todo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todoList[position]

        holder.todo = todo

        holder.tvTitle.text = todo.title
        holder.tvDueDate.text = todo.dueDate

    }

    fun addItem(todo: Todo) {
        val size = todoList.size
        todoList.add(todo)
        notifyItemInserted(size)
    }

    fun addAll(todos: List<Todo>) {
        val size = todoList.size
        todoList += todos
        notifyItemRangeInserted(size, todos.size)
    }

    fun deleteRow(position: Int) {
        todoList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = todoList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDueDate: TextView = itemView.tvDueDate
        val tvTitle: TextView = itemView.tvTitle
        val ivPriority: ImageView = itemView.ivPriority

        var todo: Todo? = null

        init {
            itemView.setOnClickListener {
                todo?.let { todo -> itemClickListener?.onItemClick(todo) }
            }

            itemView.setOnLongClickListener { view ->
                itemClickListener?.onItemLongClick(adapterPosition, view)
                true
            }
        }
    }

    interface TodoItemClickListener {
        fun onItemClick(todo: Todo)
        fun onItemLongClick(position: Int, view: View): Boolean
    }

}