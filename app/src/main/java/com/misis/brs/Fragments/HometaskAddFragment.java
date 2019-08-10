package com.misis.brs.Fragments;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.misis.brs.AlarmNotificationReceiver;
import com.misis.brs.Database.DBHelper;
import com.misis.brs.Database.Hometask;
import com.misis.brs.MainActivity;
import com.misis.brs.R;
import com.misis.brs.TimeHelper;

import java.util.Locale;

public class HometaskAddFragment extends Fragment {

    private long deadline;
    private Button add;
    Hometask addingHometask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deadline = getArguments().getLong("date");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_view_hometask, container, false);

        //изменение toolbar
        ((TextView) getActivity().findViewById(R.id.toolbarText)).setText(TimeHelper.getTime(deadline));
        ((Spinner) getActivity().findViewById(R.id.semester_picker)).setVisibility(View.INVISIBLE);

        //нижнее меню
        ((MainActivity) getActivity()).emptyBottomMenu();

        final EditText text = ((EditText) view.findViewById(R.id.textHometask));
        add = ((Button) view.findViewById(R.id.saveButton));
        final FragmentManager fm = getActivity().getFragmentManager();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //скрываем клавиатуру
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(add.getWindowToken(), 0);

                if (text.getText().toString().equals("")) {
                    final Snackbar notificationSnackbar = Snackbar.make(
                            v,
                            R.string.snackbarEmptyText,
                            Snackbar.LENGTH_LONG
                    );
                    notificationSnackbar.show();
                } else {
                    //создаём запись
                    addingHometask = new Hometask(deadline);
                    addingHometask.setCheckNotify(true);
                    addingHometask.setCheckDone(false);
                    addingHometask.setDescription(text.getText().toString());

                    SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
                    addingHometask.setSemester(pref.getInt("semester", 0));
                    // минус 12 часов ставим в 15:00 с учётом сдвига на +3 ( GMT+03:00)
                    long notifyTime = addingHometask.getDeadline() - 43200;
                    //отключаем нотификацию, если ее время прошло
                    if (notifyTime < TimeHelper.currentTime() / 1000) {
                        addingHometask.setCheckNotify(false);
                    }

                    addingHometask.setTimeNotification(notifyTime);
                    DBHelper.insertHometask(addingHometask);

                    startNotification(false);


                    //выписываем время нотификации
                    //добавляем поддержку языков
                    String lang = Locale.getDefault().getDisplayLanguage();
                    String notify = "";
                    if (addingHometask.getCheckNotify()) {
                        switch (lang) {
                            case "English":
                                notify = "You will get the notification on " + TimeHelper.getNotifTime(addingHometask.getTimeNotification());
                                break;
                            case "русский":
                                notify = "Уведомление будет в " + TimeHelper.getNotifTime(addingHometask.getTimeNotification());
                                break;
                        }
                        final Snackbar notificationSnackbar = Snackbar.make(
                                v,
                                notify,
                                Snackbar.LENGTH_LONG
                        );
                        notificationSnackbar.show();
                    }
                    //откатываемся в начало
                    fm.popBackStack();
                    fm.popBackStack();
                }
            }
        });
        return view;
    }

    private void startNotification(boolean isRepeating) {
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent;
        PendingIntent pendingIntent;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent = new Intent(getContext(), AlarmNotificationReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
            if (!isRepeating)
                manager.set(AlarmManager.RTC_WAKEUP, addingHometask.getTimeNotification()*1000, pendingIntent);
            else
                manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 3000, 86400000, pendingIntent); // 86400000 - Интервал повора 24 часа


        }

    }
}
