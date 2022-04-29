package com.arnogibelli.todo.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.arnogibelli.todo.R
import com.arnogibelli.todo.tasklist.Task
import java.util.*

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val buttonVal = findViewById<Button>(R.id.buttonVal)
        val labelTitle = findViewById<EditText>(R.id.labelTitle)
        val labelDes = findViewById<EditText>(R.id.labeldes)
        val buttonEdit = intent.getSerializableExtra("task") as? Task
        labelTitle.setText(buttonEdit?.description)
        labelDes.setText(buttonEdit?.title)


        buttonVal.setOnClickListener {

            val newTask = Task(id = buttonEdit?.id ?:UUID.randomUUID().toString(),
                title = labelTitle.text.toString(),description = labelDes.text.toString())
            intent.putExtra("task", newTask)
            setResult(RESULT_OK, intent)
            finish()



        }
    }
}