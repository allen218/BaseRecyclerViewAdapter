package com.example.activity.recyclerviewloadmore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.activity.baserecyclerviewadapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by allen on 1/14/2016 18:13.
 */
public class TestAdapter<Student> extends BaseRecyclerViewAdapter {
    public TestAdapter(List<Student> students, RecyclerView recyclerView) {
        super(students, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view) {

        return new StudentViewHolder(view);
    }

    @Override
    protected View createView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return view;
    }

    @Override
    protected void showData(RecyclerView.ViewHolder holder, int position, List studentList) {
        StudentViewHolder viewHodler = (StudentViewHolder) holder;
        viewHodler.tvName.setText(((com.example.activity.recyclerviewloadmore.bean.Student) studentList.get(position)).getName());
        viewHodler.tvEmailId.setText(((com.example.activity.recyclerviewloadmore.bean.Student) studentList.get(position)).getEmailId());
    }

    public void addData(List data) {
//        datas.clear();
        datas.addAll(data);
        notifyDataSetChanged();
        setLoaded();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {


        public TextView tvName;

        public TextView tvEmailId;


        public StudentViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvEmailId = (TextView) itemView.findViewById(R.id.tvEmailId);
        }
    }


}
