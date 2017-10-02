/*
 * Class Name: Counter
 *
 * Version: 1.0
 *
 * Date: September 30, 2017
 *
 * Copyright (c) Seth Bergen, CMPUT301 University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package sbergen.countbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents and Counter
 *
 * @author Seth Bergen
 * @version 1.0
 * @since 1.0
 */
public class Counter {
    private String name;
    private Date date;
    private int initialValue;
    private int currentValue;
    private String comment;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /** Constructs Counter objects
     *
     * @param name The name of the Counter
     * @param initialValue The initial value of the Counter
     */
    public Counter(String name, int initialValue){
        this.name = name;
        this.date = new Date();
        this.initialValue = initialValue;
        this.currentValue = initialValue;
        this.comment = "";
    }

    /** Constructs Counter objects
     *
     * @param name The name of the Counter
     * @param initialValue The initial value of the Counter
     * @param comment A comment associated with the counter.
     */
    public Counter(String name, int initialValue, String comment){
        this(name, initialValue);
        this.comment = comment;
    }

    /**
     * Sets the name of the Counter
     *
     * @param name The new name of the Counter
     */
    public void setName(String name){
        this.name = name;
        updateDate();
    }

    /** Sets the date of last edit to the current system time */
    public void updateDate(){
        date = new Date();
    }

    /**
     * Sets the initial value of the Counter
     *
     * @param initialValue the new initial value of the Counter
     */
    public void setInitialValue(int initialValue){
        this.initialValue = initialValue;
        updateDate();
    }

    /**
     * Sets the current value of the Counter
     *
     * @param currentValue The new current value of the Counter
     */
    public void setCurrentValue(int currentValue){
        this.currentValue = currentValue;
        updateDate();
    }

    /** Sets the comment of the Counter
     *
     * @param comment the new comment of the Counter
     */
    public void setComment(String comment) {
        this.comment = comment;
        updateDate();
    }

    /** Increments the Counter by one */
    public void increment(){
        currentValue++;
        updateDate();
    }

    /** Decrements the counter by one */
    public boolean decrement(){
        updateDate();
        if (currentValue > 0) {
            currentValue--;
            return true;
        } else {
            return false;
        }
    }

    /** Gets the name of the Counter */
    public String getName(){
        return name;
    }

    /** Gets the initial value of the Counter */
    public int getInitialValue(){
        return initialValue;
    }

    /** Gets the current value of the Counter */
    public int getCurrentValue(){
        return currentValue;
    }

    /** Gets the comment of the Counter */
    public String getComment(){
        return comment;
    }

    /** Returns a String representation of the Counter in form:
     * name: currentValue
     * Last Changed: date
     * Comment: comment
     */
    @Override
    public String toString(){
        return name + ": " + currentValue + "\nLast Changed: " + dateFormat.format(date)
                + (!comment.equals("") ? "\nComment: " + comment : "");
    }

    /** Returns the date as a String in form yyyy-MM-dd */
    public String getDateString(){
        return "Last Changed: "+dateFormat.format(date);
    }
}
