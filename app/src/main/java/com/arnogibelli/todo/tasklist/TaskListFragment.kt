package com.arnogibelli.todo.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.arnogibelli.todo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import form.FormActivity
import java.util.*

class TaskListFragment:Fragment() {
    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )
    val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task?: return@registerForActivityResult
        taskList = taskList + task
        refreshAdapter()
    }
    val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task? ?: return@registerForActivityResult
        taskList = taskList.map { if (it.id == task.id) task else it }
        refreshAdapter()
    }

    private val adapter = TaskListAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter.currentList = taskList
        return inflater.inflate(R.layout.fragment_task_list, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.task_list)
        recyclerView.adapter = adapter


        val addButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        addButton.setOnClickListener {

            val intent = Intent(context, FormActivity::class.java)
            createTask.launch(intent)

           /* val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            taskList = taskList + newTask
            refreshAdapter()*/

        }

        adapter.onClickDelete = { task ->
            taskList = taskList - task
            refreshAdapter()
        }
        adapter.onClickEdit = { task ->
            val intent = Intent(context, FormActivity::class.java)
            intent.putExtra("task", task)
            editTask.launch(intent)
        }
    }
private fun refreshAdapter() {
    adapter.currentList = taskList
    adapter.notifyDataSetChanged()

}
}
