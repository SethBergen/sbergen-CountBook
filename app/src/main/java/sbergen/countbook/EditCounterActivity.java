/*
 * Class Name: EditCounterActivity
 *
 * Version: 1.0
 *
 * Date: September 30, 2017
 *
 * Copyright (c) Seth Bergen, CMPUT301 University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package sbergen.countbook;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Edit Counter Functionality. Provides an interface for the user to edit the name, initial value,
 * current value, and comment of an existing counter. The user can also delete the counter from
 * here, and see the date of the Counter's most recent edit.
 *
 * @author Seth Bergen
 * @version 1.0
 * @since 1.0
 */
public class EditCounterActivity extends AppCompatActivity {

    /** Called when the activity is first created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);

        Intent intent = getIntent();

        final int index = intent.getIntExtra("index", 0);

        final EditText textboxName = (EditText)findViewById(R.id.textbox_edit_name);
        final EditText textboxInitialValue = (EditText)findViewById(R.id.textbox_edit_initialValue);
        final EditText textboxCurrentValue = (EditText)findViewById(R.id.textbox_edit_currentValue);
        final EditText textboxComment = (EditText)findViewById(R.id.textbox_edit_comment);
        EditText textboxDisplayDate = (EditText)findViewById(R.id.textbox_display_date);

        textboxDisplayDate.setEnabled(false);

        final Button buttonEditName = (Button) findViewById(R.id.button_edit_name);
        final Button buttonEditInitialValue = (Button) findViewById(R.id.button_edit_initialValue);
        final Button buttonEditCurrentValue = (Button) findViewById(R.id.button_edit_currentValue);
        final Button buttonEditComment = (Button) findViewById(R.id.button_edit_comment);
        final Button buttonReturn = (Button) findViewById(R.id.button_return);
        final Button buttonDelete = (Button) findViewById(R.id.button_deleteCounter);

        buttonDelete.setBackgroundColor(Color.RED);

        textboxName.setText(MainActivity.countList.get(index).getName());
        textboxInitialValue.setText(
                Integer.toString(MainActivity.countList.get(index).getInitialValue()));
        textboxCurrentValue.setText(
                Integer.toString(MainActivity.countList.get(index).getCurrentValue()));
        textboxComment.setText(MainActivity.countList.get(index).getComment());
        textboxDisplayDate.setText(MainActivity.countList.get(index).getDateString());

        buttonEditName.setOnClickListener(new View.OnClickListener(){
            /**
             * Checks validity of entry. Due to the type of textbox, the user is unable to enter any
             * invalid characters. Thus, the only invalid entry would be to enter nothing at all.
             * If the entry is good, disable the textbox and sets the new value.
             * */
            @Override
            public void onClick(View v){
                setResult(RESULT_OK);
                if(textboxName.getText().length() > 0){
                    MainActivity.countList.get(index).setName(textboxName.getText().toString());
                    textboxName.setEnabled(false);
                }
            }
        });

        buttonEditInitialValue.setOnClickListener(new View.OnClickListener(){
            /**
             * Checks validity of entry. Due to the type of textbox, the user is unable to enter any
             * invalid characters. Thus, the only invalid entry would be to enter nothing at all.
             * If the entry is good, disable the textbox and sets the new value.
             * */
            @Override
            public void onClick(View v){
                setResult(RESULT_OK);
                if (textboxInitialValue.getText().length() > 0) {
                    MainActivity.countList.get(index).setInitialValue(
                            Integer.parseInt(textboxInitialValue.getText().toString()));
                    textboxInitialValue.setEnabled(false);
                }
            }
        });

        buttonEditCurrentValue.setOnClickListener(new View.OnClickListener(){
            /**
             * Checks validity of entry. Due to the type of textbox, the user is unable to enter any
             * invalid characters. Thus, the only invalid entry would be to enter nothing at all.
             * If the entry is good, disable the textbox and sets the new value.
             * */
            @Override
            public void onClick(View v){
                setResult(RESULT_OK);
                if (textboxCurrentValue.getText().length() > 0) {
                    MainActivity.countList.get(index).setCurrentValue(
                            Integer.parseInt(textboxCurrentValue.getText().toString()));
                    textboxCurrentValue.setEnabled(false);
                }
            }
        });

        buttonEditComment.setOnClickListener(new View.OnClickListener(){
            /**
             * Disable the textbox and set the new comment.
             * */
            @Override
            public void onClick(View v){
                setResult(RESULT_OK);

                MainActivity.countList.get(index).setComment(textboxComment.getText().toString());
                textboxComment.setEnabled(false);
            }
        });

        buttonReturn.setOnClickListener(new View.OnClickListener(){
            /** Return to the MainActivity */
            @Override
            public void onClick(View v){
                setResult(RESULT_OK);

                Intent intent = new Intent(EditCounterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            /** Delete the Counter and return to the MainActivity */
            public void onClick(View v){
                setResult(RESULT_OK);

                MainActivity.countList.remove(index);
                Intent intent = new Intent(EditCounterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }
}
