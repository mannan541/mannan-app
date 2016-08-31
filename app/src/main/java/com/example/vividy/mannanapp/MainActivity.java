package com.example.vividy.mannanapp;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.vividtech.pimpmycall.PimpMyCallUtils;

public class MainActivity extends AppCompatActivity {

    private int extremeVariabke = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(4f,0));
        barEntries.add(new BarEntry(8f,1));
        barEntries.add(new BarEntry(6f,2));
        barEntries.add(new BarEntry(12f,3));
        barEntries.add(new BarEntry(18f,4));
        barEntries.add(new BarEntry(9f,5));

        BarDataSet dataset = new BarDataSet(barEntries, "# of Calls");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        BarChart chart = new BarChart(getApplicationContext());
        setContentView(chart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        chart.setDescription("# of times Alice called Bob");
        chart.animateY(5000);

//        LimitLine line = new LimitLine(10f);
//        data.addLimitLine(line);

//        chart.saveToGallery("mychart.jpg",0); // 85 is the quality of the image

        PimpMyCallUtils.init("lastingmannan","softHive","122",getApplicationContext());

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
/*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                smsActivity(view);
            }
        });
*/

        getApkFiles();
    }

    public void getApkFiles(){
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, 0);
        int z = 0;
        for (Object object : pkgAppsList) {

            ResolveInfo info = (ResolveInfo) object;

            File f1 =new File( info.activityInfo.applicationInfo.publicSourceDir);

            Log.v("file--", " "+f1.getName().toString()+"----"+info.loadLabel(getPackageManager()));
            try{

                String file_name = info.loadLabel(getPackageManager()).toString();
                Log.d("file_name--", " "+file_name);

                // File f2 = new File(Environment.getExternalStorageDirectory().toString()+"/Folder/"+file_name+".apk");
                // f2.createNewFile();

                File f2 = new File(Environment.getExternalStorageDirectory().toString()+"/APKFilesBackup");
                f2.mkdirs();
                f2 = new File(f2.getPath()+"/"+file_name+".apk");
                f2.createNewFile();

                InputStream in = new FileInputStream(f1);

                OutputStream out = new FileOutputStream(f2);

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0){
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                System.out.println("File copied.");
            }
            catch(FileNotFoundException ex){
                System.out.println(ex.getMessage() + " in the specified directory.");
            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }


    @Override
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void smsActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), SMSActivity.class);
        startActivity(intent);
    }

    public void calandarFunction(View view) {
        Calendar cal = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= 14) {
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis())
                    .putExtra(Events.TITLE, "LastingSales Follow-up")
                    .putExtra(Events.DESCRIPTION, "Integration of google calendar with lastingSales android app")
                    .putExtra(Events.EVENT_LOCATION, "Arfa Tower")
                    .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
                    .putExtra(Intent.EXTRA_EMAIL, "mannan541@live.com");
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("beginTime", cal.getTimeInMillis());
            intent.putExtra("allDay", true);
            intent.putExtra("rrule", "FREQ=YEARLY");
            intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
            intent.putExtra("title", "LastingSales Follow-up");
            startActivity(intent);
        }
    }

    public void viewAllCalendarEvents(View view) {
        Intent intent = new Intent(getApplicationContext(), CalendarListActivity.class);
        startActivity(intent);
    }

    private String getCalendarUriBase(Activity act) {
        String calendarUriBase = null;
        Uri calendars = Uri.parse("content://calendar/calendars");
        Cursor managedCursor = null;
        try {
            managedCursor = act.managedQuery(calendars, null, null, null, null);
        } catch (Exception e) {
        }
        if (managedCursor != null) {
            calendarUriBase = "content://calendar/";
        } else {
            calendars = Uri.parse("content://com.android.calendar/calendars");
            try {
                managedCursor = act.managedQuery(calendars, null, null, null, null);
            } catch (Exception e) {
            }
            if (managedCursor != null) {
                calendarUriBase = "content://com.android.calendar/";
            }
        }
        return calendarUriBase;
    }


    public void saveCalandarEvent(View view) {
       long startDate = 11;
       long eventID = 11;
        try {
            String eventUriString = "content://com.android.calendar/events";
            ContentValues eventValues = new ContentValues();
            eventValues.put("calendar_id", 1); // id, We need to choose from
            // our mobile for primary its 1
            eventValues.put("title", "Lasting Title");
            eventValues.put("description", "LastingSales Google Calendar task Description");
            eventValues.put("eventLocation", "Lahore");

            long endDate = startDate + 1000 * 10 * 10; // For next 10min
            eventValues.put("dtstart", startDate);
            eventValues.put("dtend", endDate);

            // values.put("allDay", 1); //If it is bithday alarm or such
            // kind (which should remind me for whole day) 0 for false, 1
            // for true
            eventValues.put("eventStatus", "Lasting event Status"); // This information is
            // sufficient for most
            // entries tentative (0),
            // confirmed (1) or canceled
            // (2):
            eventValues.put("eventTimezone", "UTC/GMT +5:30");
 /*
  * Comment below visibility and transparency column to avoid
  * java.lang.IllegalArgumentException column visibility is invalid
  * error
  */
            // eventValues.put("allDay", 1);
            // eventValues.put("visibility", 0); // visibility to default (0),
            // confidential (1), private
            // (2), or public (3):
            // eventValues.put("transparency", 0); // You can control whether
            // an event consumes time
            // opaque (0) or transparent
            // (1).
            eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

            Uri eventUri = MainActivity.this.getApplicationContext()
                    .getContentResolver()
                    .insert(Uri.parse(eventUriString), eventValues);
            eventID = Long.parseLong(eventUri.getLastPathSegment());
            setReminderForEvent(eventID);
        } catch (Exception ex) {
            Log.d("error","Error in adding event on calendar" + ex.getMessage());
        }
    }

    public void setReminderForEvent(long eventID){
        String reminderUriString = "content://com.android.calendar/reminders";
        ContentValues reminderValues = new ContentValues();
        reminderValues.put("event_id", eventID);
        reminderValues.put("minutes", 5); // Default value
        //set time in min which occur before event start
        reminderValues.put("method", 1); // Alert Methods: Default(0),
        // Alert(1), Email(2),SMS(3)
        Uri reminderUri = MainActivity.this.getApplicationContext()
                .getContentResolver()
                .insert(Uri.parse(reminderUriString), reminderValues);

        sendEmailToUserForEvent(eventID);

    }

    private void sendEmailToUserForEvent(long eventID) {
        String attendeuesesUriString = "content://com.android.calendar/attendees";

        /********
         * To add multiple attendees need to insert ContentValues
         * multiple times
         ***********/
        ContentValues attendeesValues = new ContentValues();
        attendeesValues.put("event_id", eventID);
        attendeesValues.put("attendeeName", "MANNAN"); // Attendees name
        attendeesValues.put("attendeeEmail", "mannan796@gmail.com");// Attendee email
        attendeesValues.put("attendeeRelationship", 0); // Relationship_Attendee(1),
        // Relationship_None(0),
        // Organizer(2),
        // Performer(3),
        // Speaker(4)
        attendeesValues.put("attendeeType", 0); // None(0), Optional(1),
        // Required(2),
        // Resource(3)
        attendeesValues.put("attendeeStatus", 0); // NOne(0),
        // Accepted(1),
        // Decline(2),
        // Invited(3),
        // Tentative(4)

        Uri eventsUri = Uri.parse("content://calendar/events");
        Uri url = MainActivity.this.getApplicationContext()
                .getContentResolver()
                .insert(eventsUri, attendeesValues);

         Uri attendeuesesUri = MainActivity.this.getApplicationContext()
         .getContentResolver()
         .insert(Uri.parse(attendeuesesUriString), attendeesValues);

        Intent intent = new Intent(String.valueOf(attendeuesesUri));
        startActivity(intent);

//        deleteEvent(url, eventID);
    }

    private void deleteEvent(Uri CALENDAR_URI, long id){
        Uri uri = ContentUris.withAppendedId(CALENDAR_URI, Integer.parseInt(String.valueOf(id)));
        getContentResolver().delete(uri, null, null);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    public void setEvent(View view) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String dateString = formatter.format(cal.getTime());

        java.util.Date dateStart = null;
        java.util.Date dateEnd = null;
        java.util.Date dateEndCurrent = null;
        try {
            dateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-08-11 10:10:10");
            dateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-08-11 19:19:19");
            dateEndCurrent = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("" + getDateTimeStamp());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long startTime = dateStart.getTime();
        long endTime = dateEnd.getTime();

        if (android.os.Build.VERSION.SDK_INT >= 6){
            CalendarHelper.requestCalendarReadWritePermission(MainActivity.this);
            CalendarHelper.listCalendarId(MainActivity.this);
            CalendarHelper.MakeNewCalendarEntry(MainActivity.this,"Test Tiitle", "Test Des", "Test Lcocation",
                    startTime, endTime, true, true, 1, 1);
        }else {
            IcaCalendarHelper.initActivityObj(MainActivity.this);
            IcaCalendarHelper.IcsMakeNewCalendarEntry("Test Tiitle", "Test Des", "Test Lcocation",
                    startTime, endTime, 1, 1, 1, 1);
        }
    }

    public static String getDateTimeStamp(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = formatter.format(c.getTime());

        return dateTimeString ;
    }

}
