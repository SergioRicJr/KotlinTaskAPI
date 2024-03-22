package com.example.basictaskapplication

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.example.basictaskapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    public val tasks = ArrayList<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->

            showTituloPopup(view)
        }
    }

    fun showTituloPopup(view: View) {
        val popupTitulo = AlertDialog.Builder(this)
        popupTitulo.setTitle("Título")
        val textTitulo = EditText(this)
        popupTitulo.setView(textTitulo)
        popupTitulo.setPositiveButton("OK") { dialog, which ->
            val titulo = textTitulo.text.toString()
            showDescricaoPopup(view, titulo)
        }
        popupTitulo.setNegativeButton("Cancelar") { dialog, which ->
            dialog.cancel()
        }
        popupTitulo.show()
    }

    fun showDescricaoPopup(view: View, titulo: String) {
        val popupDescricao = AlertDialog.Builder(this)
        popupDescricao.setTitle("Descrição")
        val textDescricao = EditText(this)
        popupDescricao.setView(textDescricao)
        popupDescricao.setPositiveButton("OK") { dialog, which ->
            val descricao = textDescricao.text.toString()
            showStatusPopup(view, titulo, descricao)
        }
        popupDescricao.setNegativeButton("Cancelar") { dialog, which ->
            dialog.cancel()
        }
        popupDescricao.show()
    }

    fun showStatusPopup(view: View, titulo: String, descricao: String) {
        val popupStatus = AlertDialog.Builder(this)
        popupStatus.setTitle("Status")

        val spinner = Spinner(this)
        val statusOptions = arrayOf("Pendente", "Concluído")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusOptions)
        spinner.adapter = adapter

        popupStatus.setView(spinner)
        popupStatus.setPositiveButton("OK") { dialog, which ->
            val status = spinner.selectedItem.toString()
            val task = Task(titulo, descricao, status)
            tasks.add(task)
            Snackbar.make(view, task.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        popupStatus.setNegativeButton("Cancelar") { dialog, which ->
            dialog.cancel()
        }
        popupStatus.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}