package com.example.goldenatoz.holiday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goldenatoz.R;

import java.util.List;

public class AdapterHolidayCalendar extends RecyclerView.Adapter<AdapterHolidayCalendar.MyHolidayCalendar> {

    Context context;
    List<ModelHolidayCalendar> modelHolidayCalendarList;

    public AdapterHolidayCalendar(Context context, List<ModelHolidayCalendar> modelHolidayCalendarList) {
        this.context = context;
        this.modelHolidayCalendarList = modelHolidayCalendarList;
    }

    @NonNull
    @Override
    public MyHolidayCalendar onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_holiday_calender, parent, false);

        return new MyHolidayCalendar(view);
    }

    @Override
    public void onBindViewHolder(MyHolidayCalendar holder, int position) {

        String date = modelHolidayCalendarList.get(position).getDate();
        String event = modelHolidayCalendarList.get(position).getEvent();

        holder.mDate.setText(date);
        holder.mEvent.setText(event);

        //holder.mDate.setText(date);
    }

    @Override
    public int getItemCount() {

        return modelHolidayCalendarList.size();
    }

    public class MyHolidayCalendar extends RecyclerView.ViewHolder {

        TextView mDate;
        TextView mEvent;

        public MyHolidayCalendar(@NonNull View itemView) {

            super(itemView);

            mDate = itemView.findViewById(R.id.date);
            mEvent = itemView.findViewById(R.id.event);
        }
    }
}
