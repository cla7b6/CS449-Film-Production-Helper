package com.example.filmproductionhelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//list code learned from: https://www.youtube.com/watch?v=YmRPIGFftp0

public class ListTesting extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //attach items from xml
    private EditText itemET;
    private Button btn;
    private ListView itemsList;
    String mTitle[] = {"Test"};
    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];

        MyAdapter(Context c, String title[]){
            super(c, R.layout.rows, R.id.textview1);
            this.context = c;
            this.rTitle = title;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.rows, parent, false);
            TextView myTitle = row.findViewById(R.id.textview1);
            myTitle.setText(rTitle[position]);

            return row;


        }
    }
    //working w/ lists
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter; //like an updater

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_list);

        itemET= findViewById(R.id.item_edit_text);
        btn= findViewById(R.id.add_b);
        itemsList= findViewById(R.id.items_list);

        items = FileHelper.readData(this);

        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        //itemsList.setAdapter(adapter);

        btn.setOnClickListener(this);
        itemsList.setOnItemClickListener(this);

        MyAdapter adapter= new MyAdapter(this, mTitle);
        itemsList.setAdapter(adapter);
    }


    //Add item in list when ADD button clicked on
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_b:
                String itemEntered= itemET.getText().toString(); //gets text entered
                adapter.add(itemEntered); // sets/adapts it as new item
                itemET.setText("");

                FileHelper.writeData(items, this);
                Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //Delete when item clicked on
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        items.remove(position);
        adapter.notifyDataSetChanged();
        FileHelper.writeData(items, this);
        Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
    }
}