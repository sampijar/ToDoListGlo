package com.example.couples_todo_lists

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class MainActivity : AppCompatActivity() {

    lateinit var createTask_button: Button
    lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // paleta de cores
        // https://coolors.co/palette/210b2c-55286f-bc96e6-d8b4e2-ae759f

        createTask_button = findViewById(R.id.CreateTaskButton)

        database = Firebase.database


        createTable(this)



        createTask_button.setOnClickListener {
            val intent = Intent(this, CriarTask::class.java)
            startActivity(intent)
        }

    }

    fun createTable(context: Context) {
        val table = findViewById<TableLayout>(R.id.tabela)
        val myRef = database.reference
        var values: HashMap<String, String>
        var v:String
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue()
                try {
                    values = value as HashMap<String, String>
                }catch (error:Exception){
                    return
                }

                Log.d(TAG, "Value is: " + value)
                var count = 0
                for ((name, desc) in values) {
                    if(desc == "vazio"){
                        continue
                    }
                    val row = addRow(name, context, table)
                    table.addView(row, count)
                    count++
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }

    fun addRow(name:String, context: Context, table:TableLayout):TableRow{
        val row = TableRow(context)
        val lp = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
        row.layoutParams = lp
        row.setPadding(0, 20, 0, 0)

        val textView1 = TextView(context)
        textView1.text = " " + name
        textView1.typeface =
            ResourcesCompat.getFont(context, R.font.poetsen_one_regular)
        textView1.textSize = 25f

        val button = Button(context)
        button.setText("Descrição")
        button.setOnClickListener {
            val intent = Intent(this, DadosTask::class.java).apply {
                putExtra("com.example.myfirstapp.MESSAGE", name)
            }
            startActivity(intent)
        }


        val checkbox = CheckBox(context)
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val ref = database.getReference(name)
                for (i in table.childCount - 1 downTo 0) {
                    val roww: TableRow = table.getChildAt(i) as TableRow
                    table.removeView(roww)
                }
                ref.setValue("vazio")
                createTable(context)
            }
        }
        row.addView(textView1)
        row.addView(button)
        row.addView(checkbox)

        return row
    }

}