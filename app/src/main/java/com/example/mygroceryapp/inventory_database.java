package com.example.mygroceryapp;

import android.app.ListActivity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

public class inventory_database extends ListActivity {

    private static final String TAG = "TESTING";
    private InventoryInfoSource datasource;
    private ArrayAdapter<InventoryItem> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_database);
        Toolbar toolbar = findViewById(R.id.toolbar);
        // not supported in a ListActivity
        //setSupportActionBar(toolbar);

        datasource = new InventoryInfoSource(this);
        datasource.open();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {

        List<InventoryItem> items;

        switch (view.getId()) {
            case R.id.show:
                items = datasource.getProducts();
                adapter = new ArrayAdapter<InventoryItem>(this, android.R.layout.simple_list_item_1, items);
                setListAdapter(adapter);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    public void onDestroy()
    {
        datasource.close();
        super.onDestroy();
    }

}
