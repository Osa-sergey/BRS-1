package com.misis.brs.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.misis.brs.Database.DBHelper;
import com.misis.brs.Database.Hometask;
import com.misis.brs.MainActivity;
import com.misis.brs.R;
import com.misis.brs.TimeHelper;

public class CalendarHometaskFragment extends Fragment {

    private CalendarView calendar;
    private Button add;
    private TextView task;
    private CardView card;
    private TextView text;
    private int curYear;
    private int curMonth;
    private  int curDay;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hometask_calendar,container,false);

        //изменение toolbar
        text = (TextView)getActivity().findViewById(R.id.toolbarText);
        text.setText("");
        ((Spinner)getActivity().findViewById(R.id.semester_picker)).setVisibility(View.INVISIBLE);

        //нижнее меню
        ((MainActivity) getActivity()).emptyBottomMenu();

        calendar = (CalendarView) view.findViewById(R.id.calendar);
        add = (Button) view.findViewById(R.id.addDate);
        task = (TextView) view.findViewById(R.id.task);
        card = (CardView) view.findViewById(R.id.hometask);

        //базовая настройка
        card.setVisibility(View.GONE);
        add.setVisibility(View.GONE);

        calendar.setDate(TimeHelper.currentTime());
        configur(calendar.getDate());

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                configur(TimeHelper.currentTime(year, month, dayOfMonth));
                curYear = year;
                curMonth = month;
                curDay = dayOfMonth;
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();
                long time = TimeHelper.currentTime(curYear,curMonth,curDay);
                if(curYear == 0)
                    time = TimeHelper.currentTime();
                //делаем начало дня в сек
                arg.putLong("date",(time - time%86400000)/1000);
                HometaskAddFragment haf = new HometaskAddFragment();
                haf.setArguments(arg);

                ((MainActivity) getActivity()).replaceFragment(R.id.themaincontainer,haf);
            }
        });
        return view;
    }

    /**
     * Функция для изменения внешнего вида фрагмента в зависимости от наличия дз
     * curData в милиск
     */
    private void configur(long curDate){
        text.setText(TimeHelper.getTime(curDate/1000));
        Hometask hometask = DBHelper.selectHometaskByDate((curDate-curDate%86400000)/1000); //настройка на  начало дня 00:00
        if( hometask == null){
            card.setVisibility(View.GONE);
            add.setVisibility(View.VISIBLE);
        }else{
            add.setVisibility(View.GONE);
            card.setVisibility(View.VISIBLE);
            task.setText(hometask.getDescription());
        }
    }
}
