package com.misis.brs.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.misis.brs.MainActivity;
import com.misis.brs.R;

public class HomeFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).bottomMenuStick(1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        //изменение toolbar
        ((TextView)getActivity().findViewById(R.id.toolbarText)).setText("");
        ((Spinner)getActivity().findViewById(R.id.semester_picker)).setVisibility(View.VISIBLE);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.semesters_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.view_semester_simple_dropdown_item);

        ((Spinner)getActivity().findViewById(R.id.semester_picker)).setAdapter(adapter);
        return view;
    }
}
