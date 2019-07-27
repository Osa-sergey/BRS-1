package com.misis.brs.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.misis.brs.Adapters.HometaskViewAdapter;
import com.misis.brs.Database.DBHelper;
import com.misis.brs.Database.Hometask;
import com.misis.brs.MainActivity;
import com.misis.brs.R;

import java.util.Arrays;
import java.util.Vector;

public class HometaskFragment extends Fragment {

    private HometaskViewAdapter hometasksViewAdapter;
    private ListView hometaskList;
    private AlertDialog.Builder builder;
    private FloatingActionButton addHometask;

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).bottomMenuStick(3);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks,container,false);

        //изменение toolbar
        ((TextView)getActivity().findViewById(R.id.toolbarText)).setText("");
        ((Spinner)getActivity().findViewById(R.id.semester_picker)).setVisibility(View.INVISIBLE);

        hometaskList = (ListView) view.findViewById(R.id.tasks_list);
        addHometask = (FloatingActionButton) view.findViewById(R.id.addButton);

        //обработка адаптер. Выводим спиоск дз при создании вида
        Vector<Hometask> hometasks = new Vector<>();
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
        Hometask[] bdHometasks = DBHelper.selectHometaskForSemester(pref.getInt("semester",0));
        if (bdHometasks != null) {
            hometasks.addAll(Arrays.asList(bdHometasks));
        }
        hometasksViewAdapter = new HometaskViewAdapter(getActivity(),hometasks);
        hometaskList.setAdapter(hometasksViewAdapter);
        hometaskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                builder = new AlertDialog.Builder(getActivity());//необходим именно этот метод другой вариант взятия контекста не работает
                builder.setTitle(R.string.hometaskDialogTitle);
                builder.setMessage(R.string.message);
                //удаляем запись
                builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper.deleteHometaskByDeadline(((Hometask)hometasksViewAdapter.getItem(position)).getDeadline());
                        //обновляем список
                        refreshHometaskList();
                    }
                });
                //не удадяем запись
                builder.setNegativeButton(R.string.reject, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ничего не делаем
                    }
                });
                builder.setCancelable(true);
                builder.create();
                builder.show();
                return false;
            }
        });

        //добавление дз по кнопке
        addHometask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void refreshHometaskList() {
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
        //подгружаем записи из БД
        Hometask[] bdHometasks = DBHelper.selectHometaskForSemester(pref.getInt("semester",0));
        Vector<Hometask> hometasks = new Vector<>();
        if (bdHometasks != null) {
            hometasks = new Vector<>(Arrays.asList(bdHometasks));
        }
        HometaskViewAdapter.setHometasks(hometasks);
        //говорим об изменении массива для перерисовки
        hometasksViewAdapter.notifyDataSetChanged();
    }
}
