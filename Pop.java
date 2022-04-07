package com.yorku.noobgrammers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class Pop extends Activity implements View.OnClickListener {

    private static final String TAG = "Pop";

    final double WIDTH_PERCENTAGE = 0.8;
    final double HEIGHT_PERCENTAGE = 0.65;

    final int DAYS_IN_A_WEEK = 7;

    NumberPicker numPickerHr;
    NumberPicker numPickerMin;
    final String[] MINUTES = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
    NumberPicker numPickerPeriod;
    final String[] PERIOD = {"AM", "PM"};
    String[] alarm;
    int hour = 12;
    int minutes = 0;
    int period = 0;
    int position = -1;

    String title;
    EditText alarmTitle;

    boolean[] buttonActive = {false, false, false, false, false, false, false};
    Button[] buttons = new Button[DAYS_IN_A_WEEK];

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        // stuff for pop up
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_menu);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * WIDTH_PERCENTAGE), (int) (height * HEIGHT_PERCENTAGE));

        // setup intial values
        alarm = getIntent().getStringArrayExtra("alarm");
        position = getIntent().getIntExtra("position", -1);
        hour = Integer.parseInt(alarm[0].substring(0, 2)) % 12;
        minutes = Integer.parseInt(alarm[0].substring(3));
        period = Integer.parseInt(alarm[0].substring(0, 2)) / 12;
        title = alarm[1];
        for (int i = 0; i < DAYS_IN_A_WEEK; i++)
        {
            buttonActive[i] = alarm[2].charAt(i) != '0';
        }

        // setting up numpickers
        // hours
        numPickerHr = findViewById(R.id.numPickerHr);
        numPickerHr.setMinValue(1);
        numPickerHr.setMaxValue(12);
        numPickerHr.setValue(hour);

        numPickerHr.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                hour = i1;
            }
        });

        // minutes
        numPickerMin = findViewById(R.id.numPickerMin);
        numPickerMin.setMinValue(0);
        numPickerMin.setMaxValue(11);
        numPickerMin.setDisplayedValues(MINUTES);
        numPickerMin.setValue(minutes / 5);

        numPickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                minutes = Integer.parseInt(MINUTES[i1]);
            }
        });

        // period
        numPickerPeriod = findViewById(R.id.numPickerPeriod);
        numPickerPeriod.setMinValue(0);
        numPickerPeriod.setMaxValue(1);
        numPickerPeriod.setDisplayedValues(PERIOD);
        numPickerPeriod.setValue(period);

        numPickerPeriod.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                period = i1;
            }
        });

        // edit text
        alarmTitle = findViewById(R.id.alarmTitle);
        alarmTitle.setText(title);

        // implement buttons
        buttons[0] = findViewById(R.id.sun);
        buttons[0].setOnClickListener(this);
        buttons[1] = findViewById(R.id.mon);
        buttons[1].setOnClickListener(this);
        buttons[2] = findViewById(R.id.tue);
        buttons[2].setOnClickListener(this);
        buttons[3] = findViewById(R.id.wed);
        buttons[3].setOnClickListener(this);
        buttons[4] = findViewById(R.id.thu);
        buttons[4].setOnClickListener(this);
        buttons[5] = findViewById(R.id.fri);
        buttons[5].setOnClickListener(this);
        buttons[6] = findViewById(R.id.sat);
        buttons[6].setOnClickListener(this);

        // update the colors
        for (int i = 0; i < DAYS_IN_A_WEEK; i++)
        {
            if (buttonActive[i])
            {
                buttons[i].setTextColor(Color.parseColor("#0089FF"));
            }
            else
            {
                buttons[i].setTextColor(Color.parseColor("#666666"));
            }
        }
    }

    @Override
    public void onClick (View view)
    {
        switch (view.getId())
        {
            // TODO: try to make this more modular
            case R.id.sun:
                if (!buttonActive[0]) {
                    buttons[0].setTextColor(Color.parseColor("#0089FF"));
                }
                else
                {
                    buttons[0].setTextColor(Color.parseColor("#666666"));
                }
                buttonActive[0] = !buttonActive[0];
                break;
            case R.id.mon:
                if (!buttonActive[1]) {
                    buttons[1].setTextColor(Color.parseColor("#0089FF"));
                }
                else
                {
                    buttons[1].setTextColor(Color.parseColor("#666666"));
                }
                buttonActive[1] = !buttonActive[1];
                break;
            case R.id.tue:
                if (!buttonActive[2]) {
                    buttons[2].setTextColor(Color.parseColor("#0089FF"));
                }
                else
                {
                    buttons[2].setTextColor(Color.parseColor("#666666"));
                }
                buttonActive[2] = !buttonActive[2];
                break;
            case R.id.wed:
                if (!buttonActive[3]) {
                    buttons[3].setTextColor(Color.parseColor("#0089FF"));
                }
                else
                {
                    buttons[3].setTextColor(Color.parseColor("#666666"));
                }
                buttonActive[3] = !buttonActive[3];
                break;
            case R.id.thu:
                if (!buttonActive[4]) {
                    buttons[4].setTextColor(Color.parseColor("#0089FF"));
                }
                else
                {
                    buttons[4].setTextColor(Color.parseColor("#666666"));
                }
                buttonActive[4] = !buttonActive[4];
                break;
            case R.id.fri:
                if (!buttonActive[5]) {
                    buttons[5].setTextColor(Color.parseColor("#0089FF"));
                }
                else
                {
                    buttons[5].setTextColor(Color.parseColor("#666666"));
                }
                buttonActive[5] = !buttonActive[5];
                break;
            case R.id.sat:
                if (!buttonActive[6]) {
                    buttons[6].setTextColor(Color.parseColor("#0089FF"));
                }
                else
                {
                    buttons[6].setTextColor(Color.parseColor("#666666"));
                }
                buttonActive[6] = !buttonActive[6];
                break;
        }
    }

    // create a method to create an alarm from all the forms
    public void createAlarm (View view)
    {
        String time = String.format("%02d:%02d", hour + period * 12, minutes);
        String name = alarmTitle.getText().toString();
        String days = "";
        for (boolean i: buttonActive)
        {
            if (i)
            {
                days += "1";
            }
            else
            {
                days += "0";
            }
        }
        String[] alarm = {time, name, days};
        Intent intent = new Intent();
        intent.putExtra("alarm", alarm);
        /*
         * RESULT VALUE
         * RESULT = -1, means that it is creating a new alarm
         * RESULT = position number, means that it is editing a pre-existing alarm
         */
        if (position == -1)
        {
            setResult(RESULT_OK, intent);
        }
        else
        {
            setResult(position, intent);
        }
        finish();
    }

    // method to delete alarm
    public void deleteAlarm (View view)
    {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        setResult(-2, intent);
        finish();
    }

}
