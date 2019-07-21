package com.misis.brs.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.misis.brs.Adapters.MarkViewAdapter;
import com.misis.brs.Database.Mark;
import com.misis.brs.Database.MarkType;
import com.misis.brs.MainActivity;
import com.misis.brs.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Vector;

public class MarksFragment extends Fragment {

    private NumberPicker markPicker;
    private NumberPicker maxMarkPicker;
    private EditText description;
    private Button addPoints;
    private MarkViewAdapter markViewAdapter;
    private Spinner markTypeSpinner;
    private int flag = 1; // флаг для определения отсутствия переключения между пунктами списка

    @Override
    public void onResume() {
        super.onResume();

        //для корректного отображения выбранного пункта меню
        ((MainActivity) getActivity()).bottomMenuStick(2);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_points,container,false);
        markTypeSpinner = (Spinner)getActivity().findViewById(R.id.semester_picker);

        markPicker = (NumberPicker) view.findViewById(R.id.picker);
        maxMarkPicker = (NumberPicker) view.findViewById(R.id.max_mark_picker);
        description = (EditText) view.findViewById(R.id.description_edit_text);
        addPoints = (Button) view.findViewById(R.id.addPoints);

        //изменение toolbar
        ((TextView)getActivity().findViewById(R.id.toolbarText)).setText("");
        markTypeSpinner.setVisibility(View.VISIBLE);

        //настраиваем пикеры
        //TODO подумать почему вылетает за граници массива
        String[] numsForMaxMarkPicker = new String[20];
        String[] numsForMarkPicker = new String[21];

        for(int i=0;i<numsForMaxMarkPicker.length;i++){
            numsForMaxMarkPicker[i] = Integer.toString(i + 1);
        }
        for(int i=0;i<numsForMarkPicker.length;i++){
            numsForMarkPicker[i] = Integer.toString(i);
        }

        //базовая настройка
        markPicker.setMinValue(1);
        markPicker.setMaxValue(numsForMarkPicker.length);
        markPicker.setDisplayedValues(numsForMarkPicker);

        maxMarkPicker.setMinValue(1);
        maxMarkPicker.setMaxValue(numsForMaxMarkPicker.length);
        maxMarkPicker.setDisplayedValues(numsForMaxMarkPicker);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
        int semester = pref.getInt("semester", 0);

        //зависимость от выбранного семестра
        int markTypes = R.array.types_of_marks_list_odd;
        if (semester % 2 != 0) {
            markTypes = R.array.types_of_marks_list_even;
        }

        //изменение spinner
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(getActivity(), markTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.view_mark_type_simple_dropdown_item);

        markTypeSpinner.setAdapter(adapter);
        markTypeSpinner.setSelection(1);
        markTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] numsForMaxMarkPicker = new String[20];
                String[] numsForMarkPicker = new String[21];

                //в зависимости от выбранного пункта
                switch (position){
                    case 0:
                    case 1:
                        numsForMaxMarkPicker = new String[20];
                        numsForMarkPicker = new String[21];
                        for(int i=0;i<numsForMaxMarkPicker.length;i++){
                            numsForMaxMarkPicker[i] = Integer.toString(i + 1);
                        }
                        for(int i=0;i<numsForMarkPicker.length;i++){
                            numsForMarkPicker[i] = Integer.toString(i);
                        }
                        markPicker.setMinValue(1);
                        markPicker.setMaxValue(numsForMarkPicker.length);
                        markPicker.setDisplayedValues(numsForMarkPicker);
                        markPicker.setValue(6);

                        maxMarkPicker.setMinValue(1);
                        maxMarkPicker.setMaxValue(numsForMaxMarkPicker.length);
                        maxMarkPicker.setDisplayedValues(numsForMaxMarkPicker);
                        maxMarkPicker.setValue(5);
                        break;
                    case 2:
                    case 3:
                        numsForMaxMarkPicker = new String[1];
                        numsForMaxMarkPicker[0] = Integer.toString(10);

                        numsForMarkPicker = new String[11];
                        for(int i=0;i<numsForMarkPicker.length;i++)
                            numsForMarkPicker[i]=Integer.toString(i);
                        markPicker.setMinValue(1);
                        markPicker.setMaxValue(numsForMarkPicker.length);
                        markPicker.setDisplayedValues(numsForMarkPicker);
                        markPicker.setValue(11);

                        maxMarkPicker.setMinValue(1);
                        maxMarkPicker.setMaxValue(numsForMaxMarkPicker.length);
                        maxMarkPicker.setDisplayedValues(numsForMaxMarkPicker);
                        flag++;
                        break;
                    case 4:
                    case 5:
                        numsForMaxMarkPicker = new String[1];
                        numsForMaxMarkPicker[0] = Integer.toString(5);

                        numsForMarkPicker = new String[6];
                        for(int i=0;i<numsForMarkPicker.length;i++)
                            numsForMarkPicker[i]=Integer.toString(i);

                        markPicker.setMinValue(1);
                        markPicker.setMaxValue(numsForMarkPicker.length);
                        markPicker.setDisplayedValues(numsForMarkPicker);
                        markPicker.setValue(6);

                        maxMarkPicker.setMinValue(1);
                        maxMarkPicker.setMaxValue(numsForMaxMarkPicker.length);
                        maxMarkPicker.setDisplayedValues(numsForMaxMarkPicker);
                        flag++;
                        break;
                    case 6:
                    case 7:
                        numsForMaxMarkPicker = new String[1];
                        numsForMaxMarkPicker[0] = Integer.toString(10);

                        numsForMarkPicker = new String[6];
                        for(int i=0;i<numsForMarkPicker.length;i++)
                            numsForMarkPicker[i]=Integer.toString(i*2);
                        markPicker.setMinValue(1);
                        markPicker.setMaxValue(numsForMarkPicker.length);
                        markPicker.setDisplayedValues(numsForMarkPicker);
                        markPicker.setValue(6);

                        maxMarkPicker.setMinValue(1);
                        maxMarkPicker.setMaxValue(numsForMaxMarkPicker.length);
                        maxMarkPicker.setDisplayedValues(numsForMaxMarkPicker);
                        flag++;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int markValue, maxMarkValue = 0;
                //выявляем элемент от его позиции и типа оценки
                markValue = markPicker.getValue()-1;
                switch (markTypeSpinner.getSelectedItemPosition()){
                    case 6:
                    case 7:
                        markValue =((markPicker.getValue()-1)*2);
                        break;
                    default: break;
                }
                switch (markTypeSpinner.getSelectedItemPosition()){
                    case 0:
                    case 1:
                        maxMarkValue = maxMarkPicker.getValue();
                        break;
                    case 2:
                    case 3:
                    case 6:
                    case 7:
                       maxMarkValue = 10;
                        break;
                    case 4:
                    case 5:
                        maxMarkValue = 5;
                        break;
                }
               if (markValue > maxMarkValue)
                {
                    final Snackbar notificationSnackbar = Snackbar.make(
                            v,
                            "Mark can not be higher than the maximum.",
                            Snackbar.LENGTH_SHORT
                    );
                    notificationSnackbar.show();
                    return;
                }
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
                Mark addingMark = new Mark();
                addingMark.setMaxMark(maxMarkValue);
                addingMark.setMark(markValue);
                addingMark.setSemester(pref.getInt("semester", 0));
                MarkType[] types = MarkType.values();
                //тк не всегда корректно отрабатывает определение
                if(flag<2) { //значит мы никуда не заходили
                    addingMark.setMarkType(MarkType.CLASS_PARTICIPATION_PART_1);
                }else {
                    addingMark.setMarkType(types[markTypeSpinner.getSelectedItemPosition()]);
                }
                addingMark.setDescription(description.getText().toString());
                Log.d("MyLOG",addingMark.getDescription() + " " + addingMark.getMaxMark() + " " + addingMark.getMark() + " " +addingMark.getSemester() + " " + addingMark.getMarkType().name());
            }
        });

        //обработка адаптера
        Vector<Mark> marks = new Vector<>();
        Mark mark = new Mark();
        mark.setMark(5);
        mark.setMaxMark(10);
        mark.setMarkType(MarkType.FINAL_TEST);
        mark.setDescription("dvdvfbfbfbfbdbfdbfdbfdbdfbdfb");
        marks.add(mark);
        marks.add(mark);

        markViewAdapter = new MarkViewAdapter(getActivity(),marks);
        ((ListView) view.findViewById(R.id.points_list)).setAdapter(markViewAdapter);

        return view;
    }
}
