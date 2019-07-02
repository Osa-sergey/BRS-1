package com.misis.brs.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.misis.brs.Adapters.MarkViewAdapter;
import com.misis.brs.Database.Mark;
import com.misis.brs.Database.MarkType;
import com.misis.brs.R;

public class MarksFragment extends Fragment {

    private RecyclerView recyclerView;
    private MarkViewAdapter markViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private NumberPicker mark;
    private NumberPicker maxMark;
    private EditText description;
    private Button addPoints;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_points,container,false);

        mark = (NumberPicker) view.findViewById(R.id.mark_picker);
        maxMark = (NumberPicker) view.findViewById(R.id.max_mark_picker);
        description = (EditText) view.findViewById(R.id.description_edit_text);
        addPoints = (Button) view.findViewById(R.id.addPoints);
        recyclerView = (RecyclerView) view.findViewById(R.id.points_list);

        //изменение toolbar
        ((TextView)getActivity().findViewById(R.id.toolbarText)).setText("");
        ((Spinner)getActivity().findViewById(R.id.semester_picker)).setVisibility(View.VISIBLE);

        //изменение spinner
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.types_of_marks_list_even, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.view_mark_type_simple_dropdown_item);

        ((Spinner)getActivity().findViewById(R.id.semester_picker)).setAdapter(adapter);
        ((Spinner)getActivity().findViewById(R.id.semester_picker)).setSelection(0);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        markViewAdapter = new MarkViewAdapter();


        //проба
        Mark mark = new Mark();
        mark.setMark(5);
        mark.setMaxMark(10);
        mark.setMarkType(MarkType.FINAL_TEST);
        mark.setDescription("ddf");
        markViewAdapter.setItems(mark);

        recyclerView.setAdapter(markViewAdapter);

        addPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Mark mark = new Mark();
                mark.setMark(5);
                mark.setMaxMark(10);
                mark.setMarkType(MarkType.FINAL_TEST);
                mark.setDescription("ddf");
                markViewAdapter.setItems(mark);
                markViewAdapter.notifyItemInserted(0);*/
            }
        });
        return view;
    }
}
