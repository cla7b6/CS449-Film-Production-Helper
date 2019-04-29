package com.example.filmproductionhelper

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.example.filmproductionhelper.DTO.ToDo
import kotlinx.android.synthetic.main.activity_material_list.*

//code learned from: https://www.youtube.com/watch?v=OqRO-B-zlKU

class MaterialList : AppCompatActivity() {

    lateinit var dbHandler: DBHandler //database handler for SQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Material Lists" //setting up layout
        dbHandler = DBHandler(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_list)
        rv_dashboard.layoutManager = LinearLayoutManager(this)
        setSupportActionBar(dashboard_toolbar)

        fab_dashboard.setOnClickListener {//clicking on the Plus button
            val dialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.lists_popup, null)
            val toDoName = view.findViewById<EditText>(R.id.lists_insert)
            dialog.setTitle("Add List")
            dialog.setView(view)

            dialog.setPositiveButton("Create List") { _: DialogInterface, _: Int -> //Create List Button
                if (toDoName.text.isNotEmpty()) {
                    val toDo = ToDo()
                    toDo.name = toDoName.text.toString()
                    dbHandler.addToDo(toDo)
                    refreshList()
                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int -> } //Cancel Button
            dialog.show()
        }
    } //Create Lists

    fun updateLists(toDo: ToDo){
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Update List Name")
        val view = layoutInflater.inflate(R.layout.lists_popup, null)
        val toDoName = view.findViewById<EditText>(R.id.lists_insert)
        toDoName.setText(toDo.name)
        dialog.setView(view)
        dialog.setPositiveButton("Update") { _: DialogInterface, _: Int ->
            if (toDoName.text.isNotEmpty()) {

                toDo.name = toDoName.text.toString()
                dbHandler.updateToDo(toDo)
                refreshList()
            }
        }
        dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int -> }
        dialog.show()
    } //Update List Name

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    private fun refreshList(){
        rv_dashboard.adapter = DashboardAdapter(this, dbHandler.getToDos())
    }

    class DashboardAdapter(val activity: MaterialList, val list: MutableList<ToDo>) :
            RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_child_dashboard, p0, false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
            holder.toDoName.text = list[p1].name

            holder.toDoName.setOnClickListener{
                val intent = Intent(activity, ItemActivity::class.java)
                intent.putExtra(INTENT_TODO_ID, list[p1].id)
                intent.putExtra(INTENT_TODO_NAME,list[p1].name)
                activity.startActivity(intent)
            }
            holder.menu.setOnClickListener {
                val popup = PopupMenu(activity,holder.menu)
                popup.inflate(R.menu.dashboard_child)
                popup.setOnMenuItemClickListener {

                    when(it.itemId){
                        R.id.menu_edit->{
                            activity.updateLists(list[p1])
                        }
                        R.id.menu_delete->{
                            activity.dbHandler.deleteToDo(list[p1].id)
                            activity.refreshList()
                        }
                        R.id.menu_mark_as_completed->{
                            activity.dbHandler.updateToDoItemCompletedStatus(list[p1].id,true)
                        }
                        R.id.menu_reset->{
                           activity.dbHandler.updateToDoItemCompletedStatus(list[p1].id,false)
                        }
                    }

                    true
                }
                popup.show()
            }
        } //create lists sub-materials

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val toDoName: TextView = v.findViewById(R.id.tv_todo_name)
            val menu : ImageView = v.findViewById(R.id.iv_menu)
        }
    } //Adapter for List/Recycler View
}
