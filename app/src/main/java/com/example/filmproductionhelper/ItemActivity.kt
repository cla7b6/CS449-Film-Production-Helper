package com.example.filmproductionhelper

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import com.example.filmproductionhelper.DTO.ToDoItem
import kotlinx.android.synthetic.main.activity_item.*

//code learned from: https://www.youtube.com/watch?v=OqRO-B-zlKU

class ItemActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler //handler
    var todoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        setSupportActionBar(item_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = intent.getStringExtra(INTENT_TODO_NAME)
        todoId = intent.getLongExtra(INTENT_TODO_ID, -1)
        dbHandler = DBHandler(this)

        rv_item.layoutManager = LinearLayoutManager(this) //set up layout

        fab_item.setOnClickListener {//clicking on add button
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Add Item")
            val view = layoutInflater.inflate(R.layout.lists_popup, null)
            val toDoName = view.findViewById<EditText>(R.id.lists_insert)
            dialog.setView(view)
            dialog.setPositiveButton("Create Item") { _: DialogInterface, _: Int -> //Create Item Button
                if (toDoName.text.isNotEmpty()) {
                    val item = ToDoItem()
                    item.itemName = toDoName.text.toString()
                    item.toDoId = todoId
                    item.isCompleted = false
                    dbHandler.addToDoItem(item)
                    refreshList()
                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->} //Cancel Button
            dialog.show()
        }

    } //Create Items

    fun updateItem(item: ToDoItem){
        val dialog = AlertDialog.Builder(this) //set up layout
        dialog.setTitle("Update Item Name")
        val view = layoutInflater.inflate(R.layout.lists_popup, null)
        val toDoName = view.findViewById<EditText>(R.id.lists_insert)
        toDoName.setText(item.itemName)
        dialog.setView(view)

        dialog.setPositiveButton("Update") { _: DialogInterface, _: Int -> //Update Button
            if (toDoName.text.isNotEmpty()) {
                val item = ToDoItem()
                item.itemName = toDoName.text.toString()
                item.toDoId = todoId
                item.isCompleted = false
                dbHandler.updateToDoItem(item)
                refreshList()
            }
        }
        dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->} //Cancel Button
        dialog.show()
    } //Update Items

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    private fun refreshList() {
        rv_item.adapter = ItemAdapter(this,dbHandler.getToDoItems(todoId))
    }

    class ItemAdapter(val activity: ItemActivity, val list: MutableList<ToDoItem>) :
            RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_child_item, p0, false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
            holder.itemName.text = list[p1].itemName
            holder.itemName.isChecked = list[p1].isCompleted
            holder.itemName.setOnClickListener {
                list[p1].isCompleted = !list[p1].isCompleted
                activity.dbHandler.updateToDoItem(list[p1])
            }
            holder.delete.setOnClickListener {
                val dialog = AlertDialog.Builder(activity)
                dialog.setTitle("Are you sure you want to delete?")
                dialog.setMessage("Do you want to delete this item?")
                dialog.setPositiveButton("Continue") { _: DialogInterface, _: Int ->
                    activity.dbHandler.deleteToDoItem(list[p1].id)
                    activity.refreshList()
                }
                dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

                }
                dialog.show()
            }
            holder.edit.setOnClickListener {
                activity.updateItem(list[p1])
            }
        }

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val itemName: CheckBox = v.findViewById(R.id.cb_item)
            val edit : ImageView = v.findViewById(R.id.iv_edit)
            val delete : ImageView = v.findViewById(R.id.iv_delete)
        }
    } //adapter for items

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else
            super.onOptionsItemSelected(item)
    } //ellipses button

}
