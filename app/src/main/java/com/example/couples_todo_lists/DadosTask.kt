package com.example.couples_todo_lists

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class DadosTask : AppCompatActivity() {

    private lateinit var nomeView: TextView
    private lateinit var descView: TextView
    private lateinit var cancelarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dados_task)

        nomeView = findViewById(R.id.Nome)
        descView = findViewById(R.id.Desc)

        val message = intent.getStringExtra("com.example.myfirstapp.MESSAGE")
        nomeView.text = "Nome: "+message

        val database = Firebase.database
        val myRef = database.getReference(message.toString())
        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue().toString()
                descView.setText(value)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })

        cancelarButton = findViewById(R.id.Cancelar2)
        cancelarButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}