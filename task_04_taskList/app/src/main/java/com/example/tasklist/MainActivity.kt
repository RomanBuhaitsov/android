package com.example.tasklist

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipDescription
import android.content.DialogInterface
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var swipeList: RecyclerView
    private lateinit var dbHelper: SQLiteHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = SQLiteHelper(this, null)
        swipeList = findViewById(R.id.taskList)
        findViewById<Button>(R.id.addTaskButton).setOnClickListener(this::callDialogue)
    }

    @SuppressLint("Range")
    private fun callDialogue(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.add_task_dialog, null)
        builder.setView(dialogView)
        val nameField: EditText = dialogView.findViewById(R.id.name)
        val descriptionField: EditText = dialogView.findViewById(R.id.description)
        val dataField: EditText = dialogView.findViewById(R.id.data)
        val statusField: EditText = dialogView.findViewById(R.id.status)

        builder.setPositiveButton("Add",
            DialogInterface.OnClickListener { dialog, id ->
                dbHelper.addEntry(nameField.text.toString(),
                    descriptionField.text.toString(),
                    dataField.text.toString(),
                    statusField.text.toString())
                println("DATABASE:")
                val cursor = dbHelper.getAllEntries()
                cursor!!.moveToFirst()

                var nameString = cursor.getString(cursor.getColumnIndex(SQLiteHelper.NAME_COl))
                var descString = cursor.getString(cursor.getColumnIndex(SQLiteHelper.DESCRIPTION_COL))
                var dataString = cursor.getString(cursor.getColumnIndex(SQLiteHelper.DATA_COL))
                var statusString = cursor.getString(cursor.getColumnIndex(SQLiteHelper.STATUS_COL))

                while (cursor.moveToNext()) {
                    nameString += cursor.getString(cursor.getColumnIndex(SQLiteHelper.NAME_COl))
                    descString += cursor.getString(cursor.getColumnIndex(SQLiteHelper.DESCRIPTION_COL))
                    dataString += cursor.getString(cursor.getColumnIndex(SQLiteHelper.DATA_COL))
                    statusString += cursor.getString(cursor.getColumnIndex(SQLiteHelper.STATUS_COL))
                }

                println(nameString)
                println(descString)
                println(dataString)
                println(statusString)
            })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(applicationContext,
                        "Decline", Toast.LENGTH_SHORT).show()
                })
        builder.create()
        builder.setTitle("Add New Task")
        builder.show()
    }

    fun addTask(name: String, description: String, data: String, status: String) {
        if (name.isNotEmpty())
            println(name)
        else
            println("empty")
    }
}