/*
 * Class Name: AddCounterActivity
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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Add Counter Functionality. Provides and interface for entering the name, initial value and
 * optionally comment or a new Counter.
 *
 * @author Seth Bergen
 * @version 1.0
 * @since 1.0
 */
public class AddCounterActivity extends AppCompatActivity {

    private EditText textboxName;
    private EditText textboxInitialValue;
    private EditText textboxComment;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        Intent intent = getIntent();

        textboxName = (EditText)findViewById(R.id.textbox_add_name);
        textboxInitialValue = (EditText)findViewById(R.id.textbox_add_initialValue);
        textboxComment = (EditText)findViewById(R.id.textbox_add_comment);

        final Button buttonAddName = (Button) findViewById(R.id.button_add_name);
        final Button buttonAddInitialValue = (Button) findViewById(R.id.button_add_initialValue);
        final Button buttonAddComment = (Button) findViewById(R.id.button_add_comment);
        final Button buttonSubmit = (Button) findViewById(R.id.button_submit);

        buttonSubmit.setEnabled(false);

        buttonAddName.setOnClickListener(new View.OnClickListener(){
            /**
             * Checks validity of entry and disables textbox if entry is good. Due to the type of
             * textbox, the user is unable to enter any invalid characters. Thus, the only invalid
             * entry would be to enter nothing at all.
             * */
            @Override
            public void onClick(View v){
                setResult(RESULT_OK);
                if (textboxName.getText().length() > 0) {
                    textboxName.setEnabled(false);
                    buttonAddName.setEnabled(false);
                    if (fieldsAreFilled()) {
                        buttonSubmit.setEnabled(true);
                    }
                }
            }
        });

        buttonAddInitialValue.setOnClickListener(new View.OnClickListener(){
            /**
             * Checks validity of entry and disables textbox if entry is good. Due to the type of
             * textbox, the user is unable to enter any invalid characters. Thus, the only invalid
             * entry would be to enter nothing at all.
             * */
            @Override
            public void onClick(View v){
                setResult(RESULT_OK);
                if (textboxInitialValue.getText().length() > 0) {
                    textboxInitialValue.setEnabled(false);
                    buttonAddInitialValue.setEnabled(false);
                    if (fieldsAreFilled()) {
                        buttonSubmit.setEnabled(true);
                    }
                }
            }
        });

        buttonAddComment.setOnClickListener(new View.OnClickListener(){
            /**
             * Checks validity of entry and disables textbox if entry is good. Due to the type of
             * textbox, the user is unable to enter any invalid characters. Thus, the only invalid
             * entry would be to enter nothing at all.
             * */
            @Override
            public void onClick(View v){
                setResult(RESULT_OK);
                if (textboxComment.getText().length() > 0) {
                    textboxComment.setEnabled(false);
                    buttonAddComment.setEnabled(false);
                }
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            /**
             * Sends user to MainActivity, where they will see their new Counter appended to the
             * bottom of the list.
             * */
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);

                MainActivity.countList.add(new Counter(textboxName.getText().toString(),
                        Integer.parseInt(textboxInitialValue.getText().toString()),
                        textboxComment.getText().toString()));

                Intent intent = new Intent(AddCounterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }

    /** Returns true if the counter's name and initial value have both been given valid inputs */
    private boolean fieldsAreFilled(){
        return !textboxName.isEnabled() && !textboxInitialValue.isEnabled();
    }

}
