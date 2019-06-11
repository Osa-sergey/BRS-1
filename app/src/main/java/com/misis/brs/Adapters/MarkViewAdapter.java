package com.misis.brs.Adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misis.brs.Database.Mark;
import com.misis.brs.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  Класс предназначен для отображения списка Recycle View
 *  Используется для связи графических компонентов и данных
 *  Реализует механизм переиспользования компонентов для оптимизации
 */
public class MarkViewAdapter extends RecyclerView.Adapter<MarkViewAdapter.MarkViewHolder> {
    private List<Mark> marksList = new ArrayList<>();

    /**
     * Метод для добавления оценок в список для отображения в Recycle View
     * @param marks массив оценок для добавления их в спиоск
     */
    public void setItems(Mark[] marks){
        marksList.addAll(Arrays.asList(marks));
        notifyDataSetChanged();
    }

    public void setItems(Mark mark){
        marksList.add(0,mark);
        notifyItemInserted(0);
    }

    /**
     * Метод для удаления оценок из списока для отображения в Recycle View
     */
    public void deleteItems(){
        marksList.clear();
        notifyDataSetChanged();
    }

    public void deleteItems(Mark mark){
        marksList.remove(mark);
        notifyDataSetChanged();
    }

    /**
     * Вызывается при создании ViewHolder
     * Создаёт на основе разметки графическое представление эдемента
     * @return MarkViewHolder
     */
    @NonNull
    @Override
    public MarkViewAdapter.MarkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_mark_simple_item, viewGroup, false);
        return new MarkViewHolder(view);
    }

    /**
     * Связывает данные с их графмческим представлением
     */
    @Override
    public void onBindViewHolder(@NonNull MarkViewAdapter.MarkViewHolder viewHolder, int i) {
       viewHolder.bind(marksList.get(i));
    }

    /**
     * Нужен для подсчёта количества элементов
     * @return количество элементов в списке
     */
    @Override
    public int getItemCount() {
        return marksList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Класс используется для хранения связок между данными и отображением
     * Позволяет переиспользовать отображения
     * Экономит количество вызовов дорогих по времени операцмй
     */
    public static class MarkViewHolder extends RecyclerView.ViewHolder{
        private TextView mark;
        private TextView description;
        private TextView markType;

        /**
         * конструктор в котором мы ищем отображение для данных
         * @param itemView элемент отображения с которым мы связываемся
         */
        public MarkViewHolder(@NonNull View itemView) {
            super(itemView);
            mark = (TextView) itemView.findViewById(R.id.mark);
            description = (TextView) itemView.findViewById(R.id.description);
            markType = (TextView) itemView.findViewById(R.id.markType);
        }

        /**
         * Связывает данные с их графмческим представлением
         */
        public void bind(Mark mark){
            String markText = mark.getMark() + "/" + mark.getMaxMark();
            this.mark.setText(markText);
            this.description.setText(mark.getDescription());
            this.markType.setText(mark.getMarkType().name());
        }
    }
}
