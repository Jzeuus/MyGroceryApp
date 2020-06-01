package com.example.mygroceryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private AlertDialog.Builder message;
    private InventoryInfoSource datasource;
    public static final String TAG_NAME = "name";
    public static final String TAG_NUM = "number";
    public static final String TAG_UNI = "units";
    private static final String TAG = "TESTING";
    private int count =0;
    ArrayList<HashMap<String, String>> locationList;

    EditText editName;

    private static String quantity;   //default values
    private static String units;
    private ArrayList<String> arrayNames;
    private ArrayList<String> arrayQ;
    private ArrayList<String> arrayU;
    Spinner spinner1;
    Spinner spinner2;

    //private ArrayList<>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.edit_name);
        arrayNames = new ArrayList<String>();
        arrayQ = new ArrayList<String>();
        arrayU = new ArrayList<String>();

        quantity = "1";
        units = " ";

        locationList = new ArrayList<HashMap<String, String>>();
        //message = new AlertDialog.Builder(MainActivity.this);
        
        spinner1 = (Spinner)findViewById(R.id.num_spinner);
        spinner1.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.nums_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        spinner2 = (Spinner)findViewById(R.id.units_spinner);
        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

    }


    public void addItem(View v){

            String productName = editName.getText().toString();
            arrayNames.add(productName);
            arrayQ.add(quantity);
            arrayU.add(units);

            // create a HashMap
            HashMap<String, String> itemRow = new HashMap<>();
            // add the HashMap to the ArrayList
            itemRow.put(TAG_NAME, productName);
            itemRow.put(TAG_NUM, quantity);
            itemRow.put(TAG_UNI, units);
            locationList.add(itemRow);
            // create a ListAdapter object that maps the data to the
            // views in the list_item layout
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, locationList,
                    R.layout.list_item,
                    new String[]{TAG_NAME, TAG_NUM, TAG_UNI},
                    new int[]{R.id.item_name, R.id.item_number, R.id.item_units});

            // get access to the list view in the layout file
            ListView myList = (ListView) findViewById(R.id.list);
            myList.setItemsCanFocus(true);
            myList.setClickable(true);
            // set the adapter to be displayed in the list view
            myList.setAdapter(adapter);
            editName.setText("");
    }


    public void showInventory(View v) {
        Intent intent = new Intent(this, inventory_database.class);
        startActivity(intent);
    }

    public void getScan(View v){

    }


    public void deleteItem(View v){

        if(!arrayNames.isEmpty())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            final int length = arrayNames.size();
            final String[] toDisplay = new String[length];
            //boolean array for selected item
            final boolean[] checkedArray = new boolean[arrayNames.size()];

            for (int i = 0; i < length; i++) {
                toDisplay[i] = arrayNames.get(i);
                checkedArray[i] = false;
            }

            //set alert dialog title
            builder.setTitle("Choose selection to delete..");

            builder.setMultiChoiceItems(toDisplay, checkedArray, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    checkedArray[i] = b;
                    Toast.makeText(MainActivity.this, "i=" + i, Toast.LENGTH_LONG).show();
                }
            });

            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    ArrayList<Integer> indexes = new ArrayList<Integer>();
                    for (int j = 0; j < length; j++)
                        if (checkedArray[j])
                            indexes.add(j);

                    int i = indexes.size() - 1;
                    for (; i >= 0; i--) {
                        //Toast.makeText(MainActivity.this, "i=" + i, Toast.LENGTH_LONG).show();
                        int k = indexes.get(i);

                        locationList.remove(k);
                        arrayNames.remove(k);
                        arrayQ.remove(k);
                        arrayU.remove(k);

                        indexes.remove(i);
                    }

                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this, locationList,
                            R.layout.list_item,
                            new String[]{TAG_NAME, TAG_NUM, TAG_UNI},
                            new int[]{R.id.item_name, R.id.item_number, R.id.item_units});

                    // get access to the list view in the layout file
                    ListView myList = (ListView) findViewById(R.id.list);
                    myList.setItemsCanFocus(true);
                    myList.setClickable(true);
                    // set the adapter to be displayed in the list view
                    myList.setAdapter(adapter);
                    editName.setText("");


                }
            });

            //set neutral/cancel button
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do someting here
                }
            });

            AlertDialog dialog = builder.create();
            //show alert dialog
            dialog.show();


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String str = (String) adapterView.getItemAtPosition(i);

        //adapterView.getId()
        if(isNumber(str)) {
            quantity = str;
            //Toast.makeText(getApplicationContext(), "quant=" + quantity, Toast.LENGTH_LONG).show();
        }
        else{
            units = str;
            //Toast.makeText(getApplicationContext(),"units="+units, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private boolean isNumber(String str)
    {
        for(int i=0;i<str.length();i++)
        {
            char c = str.charAt(i);
            if( !(c>='0' && c<='9'))
                return false;
        }
        return true;
    }


    public void onSaveInstanceState(Bundle outState){

        outState.putInt("stateCount", count);
        outState.putString("stateQuant", quantity);
        outState.putString("stateUnits", units);
        outState.putStringArrayList("saveNames",arrayNames);
        outState.putStringArrayList("saveQuants",arrayQ);
        outState.putStringArrayList("saveUnits",arrayU);

        super.onSaveInstanceState(outState);
    }
    /**
     * onRestoreInstanceState --
     * @param: Bundle inState
     * Call back method to be invoked after the onStart(). This method is responsible for restoring our UI to the
     * previous state it was before the rotation occured. It accomplishes this by using a string Key to retrieve
     * the stored data in our bundle object and instantiates it to the app's instance variables.
     */
    public void onRestoreInstanceState(Bundle inState){
        super.onRestoreInstanceState(inState);

        count = inState.getInt("currentCount");
        quantity = inState.getString("stateQuant");
        units = inState.getString("stateUnits");

        arrayNames = inState.getStringArrayList("saveNames");
        arrayQ = inState.getStringArrayList("saveQuants");
        arrayU = inState.getStringArrayList("saveUnits");
        //Toast.makeText(getApplicationContext(), "size=" + size, Toast.LENGTH_LONG).show();
        int size = arrayNames.size();
        for(int i=0; i<size; i++)
        {
            HashMap<String, String> itemRow = new HashMap<>();
            // add the HashMap to the ArrayList
            itemRow.put(TAG_NAME, arrayNames.get(i));
            itemRow.put(TAG_NUM, arrayQ.get(i));
            itemRow.put(TAG_UNI, arrayU.get(i));
            locationList.add(itemRow);
        }

        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this, locationList,
                R.layout.list_item,
                new String[] {TAG_NAME, TAG_NUM, TAG_UNI},
                new int[] {R.id.item_name, R.id.item_number, R.id.item_units});

        // get access to the list view in the layout file
        ListView myList = (ListView)findViewById(R.id.list);
        //myList.setBackgroundColor(-65281); //magenta color
        myList.setItemsCanFocus(true);
        myList.setClickable(true);
        // set the adapter to be displayed in the list view
        myList.setAdapter(adapter);

    }


}
