package com.example.couples_todo_lists

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.database.database



class CriarTask: AppCompatActivity() {

    private lateinit var cancelarButton:Button
    private lateinit var criarButton:Button
    private lateinit var nomeTarefaTextInput: TextInputEditText
    private lateinit var descTarefaTextInput: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.criar_task)

        nomeTarefaTextInput = findViewById(R.id.NomeTarefa)
        descTarefaTextInput = findViewById(R.id.DescriçãoTarefa)

        cancelarButton = findViewById(R.id.Cancelar2)
        cancelarButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val database = Firebase.database

        criarButton = findViewById(R.id.CriarTask2)
        criarButton.setOnClickListener {
            val myRef = database.getReference("${nomeTarefaTextInput.text}")

            myRef.setValue(descTarefaTextInput.text.toString())
        }
    }
}