package com.misis.brs.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misis.brs.Database.News;
import com.misis.brs.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 *  Класс предназначен для отображения списка Recycle View
 *  Используется для связи графических компонентов и данных
 *  Реализует механизм переиспользования компонентов для оптимизации
 */
public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.NewsViewHolder> {
    private List<News> newsList = new ArrayList<>();

    /**
     * Метод для добавления оценок в список для отображения в Recycle View
     * @param News массив оценок для добавления их в спиоск
     */
    public void setItems(News[] News){
        newsList.addAll(Arrays.asList(News));
        notifyDataSetChanged();
    }

    public void setItems(News News){
        newsList.add(News);
        notifyDataSetChanged();
    }

    /**
     * Метод для удаления оценок из списока для отображения в Recycle View
     */
    public void deleteItems(){
        newsList.clear();
        notifyDataSetChanged();
    }

    public void deleteItems(News News){
        newsList.remove(News);
        notifyDataSetChanged();
    }

    /**
     * Вызывается при создании ViewHolder
     * Создаёт на основе разметки графическое представление эдемента
     * @return NewsViewHolder
     */
    @NonNull
    @Override
    public NewsViewAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_news_simple_item, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new NewsViewHolder(view);
    }

    /**
     * Связывает данные с их графмческим представлением
     */
    @Override
    public void onBindViewHolder(@NonNull NewsViewAdapter.NewsViewHolder viewHolder, int i) {
        viewHolder.bind(newsList.get(i));
    }

    /**
     * Нужен для подсчёта количества элементов
     * @return количество элементов в списке
     */
    @Override
    public int getItemCount() {
        return newsList.size();
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
    public static class NewsViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView date;
        private TextView text;

        /**
         * конструктор в котором мы ищем отображение для данных
         * @param itemView элемент отображения с которым мы связываемся
         */
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            text = (TextView) itemView.findViewById(R.id.textNews);
        }

        /**
         * Связывает данные с их графмческим представлением
         */
        public void bind(News news){
            this.text.setText(news.getDescription());
            this.title.setText(news.getHeader());
            this.date.setText(getTime(news.getDateNews()));
        }

        /**
         *  функция для перевода времени
         * @param millis unix стандарт времени
         * @return строковое представление времени в формате дд.мм.гг
         */
        private String getTime(long millis){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);

            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH)+1;
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            return mDay+"."+mMonth+"."+mYear;
        }
    }
}
