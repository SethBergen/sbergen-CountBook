/*
 * Class Name: MainActivity
 *
 * Version: 1.0
 *
 * Date: September 30, 2017
 *
 * Copyright (c) Seth Bergen, CMPUT301 University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package sbergen.countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * List display functionality. From here, the user can reset, decrement, and increment each counter
 * in the list, as well as add or edit a counter.
 *
 * @author Seth Bergen
 * @version 1.0
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "counters.sav";
    private CounterListAdapter adapter;

    public static ArrayList<Counter> countList;

    /** Called when the activity is first created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFromFile();

        adapter = new CounterListAdapter(this, R.layout.list_item, countList);
        ListView countListDisplay = (ListView) findViewById(R.id.counter_display);
        Button buttonNewCounter = (Button) findViewById(R.id.button_new_counter);

        countListDisplay.setAdapter(adapter);

        buttonNewCounter.setOnClickListener(new View.OnClickListener() {
            /** Sends user to AddCounterActivity when the new Counter button is tapped */
            @Override
            public void onClick(View v){
                setResult(RESULT_OK);

                Intent intent = new Intent(MainActivity.this, AddCounterActivity.class);
                startActivity(intent);
            }
        });

        countListDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            /** Sends user to EditCounterActivity when a Counter is tapped */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(MainActivity.this, EditCounterActivity.class);

                intent.putExtra("index", position);
                startActivity(intent);
            }
        });
    }

    /** Called whenever the activity is started */
    @Override
    protected void onStart(){
        super.onStart();
        notifyAndSave();
    }

    /** Updates the CounterListAdapter and saves the new data to a .json file. */
    private void notifyAndSave(){
        adapter.notifyDataSetChanged();
        saveInFile();
    }

    /** Loads counters from the previous session */
    private void loadFromFile(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            countList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            countList = new ArrayList<Counter>();
            //saveInFile();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /** Saves this session's counters to a .json file */
    private void saveInFile(){
        try {
            //Open a file for writing. Creates the file if it doesn't already exist.
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();

            gson.toJson(countList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Custom ArrayAdapter for countList. This adds the reset, decrement, and increment buttons to
     * each list element.
     * Adapted from https://looksok.wordpress.com/tag/listview-item-with-button/
     *  2017-09-30
     *
     * @author Seth Bergen
     * @version 1.0
     * @see android.widget.ListAdapter
     * @see Counter
     * @since 1.0
     */
    private class CounterListAdapter extends ArrayAdapter<Counter>{
        private int layout;

        /**
         * Constructs CounterListAdapter objects
         *
         * @param context Target activity
         * @param resource Layout of each list item
         * @param objects List of objects to display
         */
        private CounterListAdapter(Context context, int resource, List<Counter> objects){
            super(context, resource, objects);
            layout = resource;

        }

        /**
         * Builds a view for the list item
         *
         * @param position Index of list item
         * @param convertView View used for each list item
         * @param parent Parent activity
         * @return convertView
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            CounterView mainCounterView = null;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                CounterView counterView = new CounterView();

                counterView.counterDetails =
                        (TextView) convertView.findViewById(R.id.textbox_counterDetails);
                counterView.buttonReset =
                        (Button) convertView.findViewById(R.id.button_reset);
                counterView.buttonDecrement =
                        (Button) convertView.findViewById(R.id.button_decrement);
                counterView.buttonIncrement =
                        (Button) convertView.findViewById(R.id.button_increment);

                counterView.buttonReset.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        countList.get(position).setCurrentValue(
                                countList.get(position).getInitialValue());
                        notifyAndSave();
                    }
                });
                counterView.buttonDecrement.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        countList.get(position).decrement();
                        notifyAndSave();
                    }
                });
                counterView.buttonIncrement.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        countList.get(position).increment();
                        notifyAndSave();
                    }
                });

                convertView.setTag(counterView);
            }
            mainCounterView = (CounterView) convertView.getTag();
            mainCounterView.counterDetails.setText(countList.get(position).toString());

            return convertView;
        }
    }

    /**
     * Holds the TextView and three Buttons for each Counter in countList
     *
     * @author Seth Bergen
     * @version 1.0
     * @since 1.0
     */
    private class CounterView {
        TextView counterDetails;
        Button buttonReset;
        Button buttonDecrement;
        Button buttonIncrement;
    }
}
