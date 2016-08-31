package com.example.vividy.mannanapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by MANNAN on 1/19/2016.
 */
public class DialogActivity extends AppCompatActivity {

    TextView dateView, timeView;

    private int day_of_week;
    private int daay;
    private int minute;
    String selectedYear = String.valueOf(getCurrentYear());
    String selectedMonth = String.valueOf(getCurrentMonth()+1);
    String selectedDayofWeek = ""+ getCurrentDayofWeek();
    String selectedDay = ""+String.valueOf(getCurrentDay());
    String selectedHour = String.valueOf(getCurrentTimeHour());
    String selectedMinute = String.valueOf(getCurrentTimeMinute());
    String selectedTimeSet = "";
    String monthName = "";
    String dayName = "";

    private int year, month, day;
    private int hour, min, sec;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.schedule_popup_layout);
        popupWindow();

    }

    // this is code for custom dialog design//
    public void popupWindow() {

        final LayoutInflater inflater = this.getLayoutInflater();
        final View layout = inflater.inflate(R.layout.schedule_popup_layout, null);
        final PopupWindow windows = new PopupWindow(layout , 1500,1500,true);

        dateView = (TextView) layout.findViewById(R.id.dialog_date_view);
        timeView = (TextView) layout.findViewById(R.id.dialog_time_view);

        windows.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        windows.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        windows.setFocusable(true);
        windows.setTouchable(true);
        windows.setOutsideTouchable(true);
        layout.post(new Runnable() {
            public void run() {

                windows.showAtLocation(layout, Gravity.CENTER, 0, 0);

                String aTime = selectedHour + ':' + selectedMinute;
                SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
                Date date = null;//
                try {
                    date = displayFormat.parse(aTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String date_picker_value = String.valueOf(new StringBuilder().append(selectedDay).append("/")
                        .append(selectedMonth).append("/").append(selectedYear));
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                Date dt1 = null;
                try {
                    dt1 = format1.parse("" + date_picker_value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat format2 = new SimpleDateFormat("EEE");
                String dayOfWeek = format2.format(dt1);

                dateView.setText(dayOfWeek + ", " + selectedDay + "-" + getMonthName() + "-" + selectedYear);
//                timeView.setText("" + aTime); // 12 hour defult format
                timeView.setText("" + parseFormat.format(date)); // 12 hour defult format

                dateView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(999);
                    }
                });

                timeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(1111);
                    }
                });

            }// end of run()
        });// end of runnable for popup

    }

    public String getMonthName(){
        if (selectedMonth.contentEquals("1")) {monthName = "Jan";}
        else if (selectedMonth.contentEquals("2")){monthName = "Feb";}
        else if (selectedMonth.contentEquals("3")){monthName = "Mar";}
        else if (selectedMonth.contentEquals("4")){monthName = "April";}
        else if (selectedMonth.contentEquals("5")){monthName = "May";}
        else if (selectedMonth.contentEquals("6")){monthName = "June";}
        else if (selectedMonth.contentEquals("7")){monthName = "July";}
        else if (selectedMonth.contentEquals("8")){monthName = "Aug";}
        else if (selectedMonth.contentEquals("9")){monthName = "Sep";}
        else if (selectedMonth.contentEquals("10")){monthName = "Oct";}
        else if (selectedMonth.contentEquals("11")){monthName = "Nov";}
        else if (selectedMonth.contentEquals("12")){monthName = "Dec";}
        return monthName;
    }

    public String getDayName(){
        if (selectedDayofWeek.contentEquals("1")) {dayName = "Mon";}
        else if (selectedDayofWeek.contentEquals("2")){dayName = "Teu";}
        else if (selectedDayofWeek.contentEquals("3")){dayName = "Wed";}
        else if (selectedDayofWeek.contentEquals("4")){dayName = "Thur";}
        else if (selectedDayofWeek.contentEquals("5")){dayName = "Fri";}
        else if (selectedDayofWeek.contentEquals("6")){dayName = "Sat";}
        else if (selectedDayofWeek.contentEquals("7")){dayName = "Sun";}

        return dayName;
    }

    private int getCurrentTimeHour(){
        final Calendar c = Calendar.getInstance();
        // Current Hour
        hour = c.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    private int getCurrentYear(){
        final Calendar c = Calendar.getInstance();
        // Current year
        year = c.get(Calendar.YEAR);
        return year;
    }

    private int getCurrentMonth(){
        final Calendar c = Calendar.getInstance();
        // Current Month
        month = c.get(Calendar.MONTH);
        return month;
    }

    private int getCurrentDayofWeek(){
        final Calendar c = Calendar.getInstance();
        // Current Hour
        day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        return day_of_week;
    }

    private int getCurrentDay(){
        final Calendar c = Calendar.getInstance();
        // Current Hour
        daay = c.get(Calendar.DAY_OF_MONTH);
        return daay;
    }

    private int getCurrentTimeMinute(){
        final Calendar c = Calendar.getInstance();
        // Current Minute
        minute = c.get(Calendar.MINUTE);
        // set current time into output textview
        return minute;
    }

    public String currentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String dateString = formatter.format(new Date(calendar.getTimeInMillis()));
        return dateString;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub

        if (id == 1111){
            return new TimePickerDialog(this,R.style.DialogTheme, timePickerListener, hour, minute, false);
        }
        if (id == 999) {
            return new DatePickerDialog(this,  R.style.DialogTheme,
                    myDateListener, year, month, daay){
                @Override
                public void onDateChanged(DatePicker view, int year1, int monthOfYear, int dayOfMonth)
                {
                    if (year1 < year)
                        view.updateDate(year, month, daay);
                    if (monthOfYear < month && year1 == year)
                        view.updateDate(year, month, daay);
                    if (dayOfMonth < daay && year1 == year
                            && monthOfYear == month)
                        view.updateDate(year, month, daay);
                }
            };
//            return new DatePickerDialog(this, R.style.DialogTheme, myDateListener, year, month, day);
        }
        return null;
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker dialog, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        if (id == 999) {
            ((DatePickerDialog) dialog).updateDate(getCurrentYear() , getCurrentMonth() , getCurrentDay());
        }
    }

    private void showDate(int year, int month, int day) {
//        dateView.setText(new StringBuilder().append(day).append("/")
//                .append(month).append("/").append(year));
        String date_picker_value = String.valueOf(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));

        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
        Date dt1 = null;
        try {
            dt1 = format1.parse(""+date_picker_value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2=new SimpleDateFormat("EEE");
        String dayOfWeek=format2.format(dt1);

        selectedDayofWeek = ""+dayOfWeek;
        selectedDay = ""+day;
        selectedMonth = ""+month;
        selectedYear = ""+year;

        dateView.setText(selectedDayofWeek + ", " + selectedDay + "-" + getMonthName() + "-" + selectedYear);
//        dateView.setText(""+date_picker_value);

        Toast.makeText(getApplicationContext(), ""+selectedDay+":"+selectedMonth+":"+selectedYear,
                Toast.LENGTH_SHORT).show();

    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour   = hourOfDay;
            minute = minutes;

            updateTime(hour, minute);

        }

    };

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        Date date = null;
        try {
            date = parseFormat.parse(""+aTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        selectedHour = displayFormat.format(date).substring(0,2);
        selectedMinute = displayFormat.format(date).substring(3,5);
        selectedTimeSet = ""+ new StringBuilder().append(timeSet);

        timeView.setText(""+aTime); // 12 hour defult format

        Toast.makeText(getApplicationContext(), "Hours: "+selectedHour+" Minutes: "+selectedMinute, Toast.LENGTH_SHORT).show();

    }

}
