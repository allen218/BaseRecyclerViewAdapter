package com.example.activity.recyclerviewloadmore;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity.baserecyclerviewadapter.BaseRecyclerViewAdapter;
import com.example.activity.baserecyclerviewadapter.OnLoadMoreListener;
import com.example.activity.recyclerviewloadmore.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseRecyclerViewAdapter.OnItemClickListener {

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private TestAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private List<Student> studentList;


    protected Handler handler;
    private int start = 20;
    private int end = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        studentList = new ArrayList<Student>();
        handler = new Handler();

        loadData();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        // create an Object for Adapter
        mAdapter = new TestAdapter(studentList, mRecyclerView);

        mAdapter.setOnItemClickListener(this);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);
        //  mAdapter.notifyDataSetChanged();


        if (studentList.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        studentList = new ArrayList<Student>();
                        for (int x = start; x < end; x++) {
                            studentList.add(new Student("Student " + x, "androidstudent" + x + "@gmail.com"));
                        }
                        start += 20;
                        end = start + 20;
                        mAdapter.addData(studentList);

                    }
                }, 2000);


            }
        });

    }


    // load initial data
    private void loadData() {

        for (int i = 0; i <= 19; i++) {
            studentList.add(new Student("Student " + i, "androidstudent" + i + "@gmail.com"));

        }


    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCurrentDistance(int dx, int dy) {

    }

    @Override
    public void currentItem(int totalItem, int lastVisItem, int firstVisItem) {

    }
}

