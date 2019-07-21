package com.misis.brs.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.misis.brs.MainActivity;
import com.misis.brs.R;

public class HomeFragment extends Fragment {

    private Spinner semester;
    private SharedPreferences pref;

    @Override
    public void onResume() {
        super.onResume();

        //для корректного отображения выбранного пункта меню
        ((MainActivity) getActivity()).bottomMenuStick(1);
    //TODO удалить после отладки

        int semesterValue = pref.getInt("semester", 1);
        Toast.makeText(getActivity().getApplicationContext(),semesterValue+"",Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        semester = (Spinner)getActivity().findViewById(R.id.semester_picker);

        //изменение toolbar
        ((TextView)getActivity().findViewById(R.id.toolbarText)).setText("");
        semester.setVisibility(View.VISIBLE);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.semesters_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.view_semester_simple_dropdown_item);
        semester.setAdapter(adapter);

        //поиск и инициализация записи о семестре
        pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
        if (!pref.contains("semester"))
        {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("semester", 1);
            editor.apply();
        }

        int semesterValue = pref.getInt("semester", 1);
        semester.setSelection(semesterValue - 1); //так как считается с 0
        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("semester", position + 1); //так как считается с 0
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
}
