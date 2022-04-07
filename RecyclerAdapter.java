package com.yorku.noobgrammers;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.time.LocalTime;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    private static final String TAG = "RecyclerAdapter";

    // variables to store list of alarms
    private ArrayList<Alarm> alarmList;

    // button interact
    private OnAlarmListener mOnAlarmListener;

    // constructor method
    public RecyclerAdapter (ArrayList<Alarm> alarmList, OnAlarmListener onAlarmListener)
    {
        this.alarmList = alarmList;
        this.mOnAlarmListener = onAlarmListener;
    }

    // this class receives a view and does something dunno, essential for recyclerview
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final TextView name;
        private final TextView time;
        private final TextView period;
        private final TextView[] days = new TextView[7];

        OnAlarmListener onAlarmListener;

        public ViewHolder (final View view, OnAlarmListener onAlarmListener)
        {
            super(view);
            name = view.findViewById(R.id.name);
            time = view.findViewById(R.id.time);
            period = view.findViewById(R.id.period);

            days[0] = view.findViewById(R.id.sunday);
            days[1] = view.findViewById(R.id.monday);
            days[2] = view.findViewById(R.id.tuesday);
            days[3] = view.findViewById(R.id.wednesday);
            days[4] = view.findViewById(R.id.thursday);
            days[5] = view.findViewById(R.id.friday);
            days[6] = view.findViewById(R.id.saturday);

            this.onAlarmListener = onAlarmListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onAlarmListener.onAlarmClick(getAdapterPosition());
        }
    }

    // detect button presses
    public interface OnAlarmListener
    {
        void onAlarmClick (int position);
    }

    // this method creates the recyclerview(?), there is no data in the contents though
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarms, parent, false);
        return new ViewHolder(itemView, mOnAlarmListener);
    }

    // this method puts respective data in the contents of the recyclerview
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position)
    {
        // update the details of the alarm
        Alarm temp = alarmList.get(position);

        // time
        holder.time.setText(String.format("%2d:%02d", temp.getTime()[0] % 12, temp.getTime()[1]));

        // period
        holder.period.setText(temp.getPeriod());

        // name
        holder.name.setText(temp.getName());

        // change color of days of the week
        for (int i = 0; i < holder.days.length; i++)
        {
            // TODO: fix this, current solution works except is not modular
            if (!temp.getRepeat(i))
            {
                holder.days[i].setTextColor(Color.parseColor("#808080"));
            }
            else
            {
                holder.days[i].setTextColor(Color.parseColor("#0089FF"));
            }
        }
    }

    // this method returns the list the recyclerview is handling
    @Override
    public int getItemCount()
    {
        return alarmList.size();
    }

}
