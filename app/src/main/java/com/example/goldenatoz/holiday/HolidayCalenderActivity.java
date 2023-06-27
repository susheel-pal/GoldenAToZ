package com.example.goldenatoz.holiday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldenatoz.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.widget.CalendarView.OnDateChangeListener;


public class HolidayCalenderActivity extends AppCompatActivity {

    //private Context context;
    List<Date> holidayDates;

    String stringMonth = "";

    MaterialCalendarView calendarView;
    //CalendarView calendarView;
    RecyclerView recyclerView;
    AdapterHolidayCalendar adapterHolidayCalendar;
    List<ModelHolidayCalendar> modelHolidayCalendarList;

    boolean isHoliday = false;
    String currentDate = "";
    String event = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_calender);

        calendarView = findViewById(R.id.calendarView);
        modelHolidayCalendarList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the selected date
        calendarView.setSelectedDate(CalendarDay.today());

        // Set the range of selectable dates
        calendarView.state().edit()
                .setMinimumDate(CalendarDay.from(2021, 1, 1))
                .setMaximumDate(CalendarDay.from(2023, 12, 31))
                .commit();

        // Set a listener for date selection
        calendarView.setOnDateChangedListener((widget, date, selected) -> {

            //date.getDay() + " " + date.getMonth()
            //Toast.makeText(this, "" + , Toast.LENGTH_SHORT).show();

            int curDate = date.getDay();
            int curMonth = date.getMonth() + 1;


            String stringMonth = "";

            switch (curMonth) {
                case 1:
                    stringMonth = "Jan";
                    break;
                case 2:
                    stringMonth = "Fab";
                    break;
                case 3:
                    stringMonth = "Mar";
                    break;
                case 4:
                    stringMonth = "Apr";
                    break;
                case 5:
                    stringMonth = "May";
                    break;
                case 6:
                    stringMonth = "Jun";
                    break;
                case 7:
                    stringMonth = "Jul";
                    break;
                case 8:
                    stringMonth = "Aug";
                    break;
                case 9:
                    stringMonth = "Sep";
                    break;
                case 10:
                    stringMonth = "Oct";
                    break;
                case 11:
                    stringMonth = "Nov";
                    break;
                case 12:
                    stringMonth = "Dec";
                    break;
            }

            modelHolidayCalendarList.clear();

            currentDate = curDate + " " + stringMonth;

            //Toast.makeText(this, "" + currentDate, Toast.LENGTH_SHORT).show();

            holiday(curDate, curMonth);

            //Toast.makeText(HolidayCalenderActivity.this, "" + event, Toast.LENGTH_SHORT).show();

            //Toast.makeText(HolidayCalenderActivity.this, "" + currentDate , Toast.LENGTH_SHORT).show();
            //title = currentDate;
            //modelHolidayCalendarList = new ArrayList<>();
            modelHolidayCalendarList.add(new ModelHolidayCalendar(currentDate, event));
            adapterHolidayCalendar = new AdapterHolidayCalendar(HolidayCalenderActivity.this, modelHolidayCalendarList);
            //recyclerView.setAdapter(adapterHolidayCalendar);

            if (event != null) {

                recyclerView.setAdapter(adapterHolidayCalendar);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.GONE);
            }


            calendarView.addDecorator(new EventDecorator(Color.RED, curDate, curMonth, event));
        });

        // Set up holiday event
        //CalendarDay holiday = CalendarDay.from(2023, 12, 25);
        //calendarView.addDecorator(new EventDecorator(Color.RED,curDate,curMonth,event));

        CalendarDay day;

        // Set Sunday color to red
        calendarView.addDecorator(new SundayDecorator());

//        // Set a decorator for a specific date
//        calendarView.addDecorator(new DayViewDecorator() {
//            @Override
//            public boolean shouldDecorate(CalendarDay day) {
//                // Define the conditions for decoration
//                return day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
//            }
//
//            @Override
//            public void decorate(DayViewFacade view) {
//                // Apply the decoration to the date
//                view.addSpan(new ForegroundColorSpan(Color.RED));
//
//            }
//        });

//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month,
//                                            int dayOfMonth) {
//
//                int curDate = dayOfMonth;
//                String curMonth = String.valueOf(month + 1);
//
//
//                String stringMonth = "";
//
//                switch (curMonth) {
//                    case "1":
//                        stringMonth = "Jan";
//                        break;
//                    case "2":
//                        stringMonth = "Fab";
//                        break;
//                    case "3":
//                        stringMonth = "Mar";
//                        break;
//                    case "4":
//                        stringMonth = "Apr";
//                        break;
//                    case "5":
//                        stringMonth = "May";
//                        break;
//                    case "6":
//                        stringMonth = "Jun";
//                        break;
//                    case "7":
//                        stringMonth = "Jul";
//                        break;
//                    case "8":
//                        stringMonth = "Aug";
//                        break;
//                    case "9":
//                        stringMonth = "Sep";
//                        break;
//                    case "10":
//                        stringMonth = "Oct";
//                        break;
//                    case "11":
//                        stringMonth = "Nov";
//                        break;
//                    case "12":
//                        stringMonth = "Dec";
//                        break;
//                }
//                modelHolidayCalendarList.clear();
//
//                currentDate = curDate + " " + stringMonth;
//
//                holiday(dayOfMonth, month);
//
//                //Toast.makeText(HolidayCalenderActivity.this, "" + event, Toast.LENGTH_SHORT).show();
//
//                //Toast.makeText(HolidayCalenderActivity.this, "" + currentDate , Toast.LENGTH_SHORT).show();
//                //title = currentDate;
//                //modelHolidayCalendarList = new ArrayList<>();
//                modelHolidayCalendarList.add(new ModelHolidayCalendar(currentDate, event));
//                adapterHolidayCalendar = new AdapterHolidayCalendar(HolidayCalenderActivity.this, modelHolidayCalendarList);
//                //recyclerView.setAdapter(adapterHolidayCalendar);
//
//                if(event != null){
//
//                    recyclerView.setAdapter(adapterHolidayCalendar);
//                    recyclerView.setVisibility(View.VISIBLE);
//                }
//                else{
//                    recyclerView.setVisibility(View.GONE);
//                }
//
//
//            }
//
//
//
//
//        });

    }

    public void holiday(int curDate, int curMonth) {

        if (curDate == 20 && curMonth == 6) {

            event = "today is holiday";
            //Date day = curDate == 20 && curMonth == 6;

        } else if (curDate == 21 && curMonth == 6) {

            event = "today is also holiday";

        } else {

            event = null;

        }

    }

}

class EventDecorator implements DayViewDecorator {
    private final int color;
    int date1;
    int month1;
    String event;


    public EventDecorator(int color, int date1, int month1, String event) {
        this.color = color;
        this.event = event;
        this.date1 = date1;
        this.month1 = month1;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        //return date != null && day.equals(date);
        return event != null && (day.getDay() == date1 && day.getMonth() == (month1 - 1));
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(8, color)); // Display a dot with the specified color
    }
}

// Decorator to set Sunday color to red
class SundayDecorator implements DayViewDecorator {
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        // Check if it is Sunday (Calendar.SUNDAY = 1)
        return day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.RED)); // Set text color to red
    }
}