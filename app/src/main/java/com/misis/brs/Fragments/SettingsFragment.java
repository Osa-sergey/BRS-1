package com.misis.brs.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;

import com.misis.brs.MainActivity;
import com.misis.brs.R;

public class SettingsFragment extends Fragment implements View.OnKeyListener {

    private Switch notif;
    private EditText studName;
    private EditText group;
    private EditText teacherName;
    private EditText day1;
    private EditText day2;
    private EditText day3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_settings,container,false);

        //изменение toolbar
        ((TextView)getActivity().findViewById(R.id.toolbarText)).setText(R.string.settings_toolbar);
        ((Spinner)getActivity().findViewById(R.id.semester_picker)).setVisibility(View.GONE);

        notif = (Switch) view.findViewById(R.id.notif_switch);

        studName = (EditText) view.findViewById(R.id.studNameEdit);
        group = (EditText) view.findViewById(R.id.groupEdit);
        teacherName = (EditText) view.findViewById(R.id.teacherNameEdit);
        day1 = (EditText) view.findViewById(R.id.day1Edit);
        day2 = (EditText) view.findViewById(R.id.day2Edit);
        day3 = (EditText) view.findViewById(R.id.day3Edit);

        //применяем обработчик
        studName.setOnKeyListener(this);
        group.setOnKeyListener(this);
        teacherName.setOnKeyListener(this);
        day1.setOnKeyListener(this);
        day2.setOnKeyListener(this);
        day3.setOnKeyListener(this);

        //задаём значения по умолчанию
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
        studName.setText(pref.getString("studName", ""));
        group.setText(pref.getString("group", ""));
        teacherName.setText(pref.getString("teacherName", ""));
        day1.setText(pref.getString("day1", ""));
        day2.setText(pref.getString("day2", ""));
        day3.setText(pref.getString("day3", ""));

        notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO реализовать переключение
            }
        });
        return view;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //для сохранения данных
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
        SharedPreferences.Editor editor = pref.edit();
        //обработка сохранения текста после нажатия enter
        if(event.getAction() == KeyEvent.ACTION_DOWN &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            switch (v.getId()){
                case R.id.studNameEdit:
                    if(studName.getText().toString() != ""){
                        editor.putString("studName",studName.getText().toString());
                    }
                    break;
                case R.id.groupEdit:
                    if(group.getText().toString() != ""){
                        editor.putString("group",group.getText().toString());
                    }
                    break;
                case R.id.teacherNameEdit:
                    if(teacherName.getText().toString() != ""){
                        editor.putString("teacherName",teacherName.getText().toString());
                    }
                    break;
                case R.id.day1Edit:
                    if(day1.getText().toString() != ""){
                        editor.putString("day1",day1.getText().toString());
                    }
                    break;
                case R.id.day2Edit:
                    if(day2.getText().toString() != ""){
                        editor.putString("day2",day2.getText().toString());
                    }
                    break;
                case R.id.day3Edit:
                    if(day3.getText().toString() != ""){
                        editor.putString("day3",day3.getText().toString());
                    }
                    break;
            }
            editor.apply();
            //обновляем header
            ((MainActivity)getActivity()).setHeader();
            return true;
        }
        return false;
    }
}
