package com.misis.brs.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.misis.brs.Adapters.HometasksViewAdapter;
import com.misis.brs.Database.Hometask;
import com.misis.brs.MainActivity;
import com.misis.brs.R;

import java.util.Vector;

public class HometasksFragment extends Fragment {

    private HometasksViewAdapter hometasksViewAdapter;

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

        //обработка адаптера
        Vector<Hometask> hometasks = new Vector<>();
        Hometask ht = new Hometask(1563543062);
        ht.setCheckDone(true);
        ht.setCheckNotify(false);
        ht.setDescription("fgfgfdbgnnnnnnnnnglkmkgtlngtlntlntntlbt");

        hometasks.add(ht);
        hometasks.add(ht);

        hometasksViewAdapter = new HometasksViewAdapter(getActivity(),hometasks);
        ((ListView) view.findViewById(R.id.tasks_list)).setAdapter(hometasksViewAdapter);

        return view;
    }
}
