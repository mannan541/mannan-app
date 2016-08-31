package com.example.vividy.mannanapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CalendarListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<MyModel> arrayList;
    ArrayList<String> arrayStringList;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.inquiry_swipe_refresh);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        new MyAsyncTask(getApplicationContext()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new MyAsyncTask(getApplicationContext()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

    }


    private void initializeViews(){
        listView = (ListView) findViewById(R.id.inquiry_missed_calls_list_view);
        progress_bar = (ProgressBar) findViewById(R.id.inquiry_progress_bar);

        arrayList = new ArrayList<MyModel>();
        arrayStringList = new ArrayList<String>();
    }

    private  void inflateMyListView(){

        List<String> calendarList = Utility.readCalendarEvent(getApplicationContext());

        List<MyModel> myModelArrayList = new ArrayList<MyModel>();

        for (MyModel myModel : myModelArrayList){
            myModelArrayList.add(myModel);
        }

        arrayList.addAll(0, myModelArrayList);
        arrayStringList.addAll(0, calendarList);

    }

    public void getArrayAdapter(){
//        final MyAdapter adapter = new MyAdapter(getApplicationContext(), arrayList);
        final MyAdapter adapter = new MyAdapter(getApplicationContext(), arrayStringList);
        listView.setAdapter(adapter);

        swipeRefreshLayout.setRefreshing(false);
    }

    private class MyAsyncTask extends AsyncTask<Object,Void,Void> {

        Context context;

        public MyAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            initializeViews();

//            missedCallslv.setVisibility(View.GONE);
            progress_bar.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(true);

        }

        @Override
        protected Void doInBackground(Object[] params) {

            inflateMyListView();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (arrayList.size() != 0){
                getArrayAdapter();
            }
            else {
                swipeRefreshLayout.setRefreshing(false);
            }

//            missedCallslv.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

        }
    }

}
