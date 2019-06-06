package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misis.brs.Database.News;
import com.misis.brs.R;

import java.util.Calendar;
import java.util.Vector;

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.ViewHolder> {

    private Vector<News> vectorNews = new Vector<>();

    @NonNull
    @Override
    public NewsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_news_simple_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(vectorNews.get(i));
    }

    @Override
    public int getItemCount() {
        return vectorNews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView date;
        private TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            text = itemView.findViewById(R.id.textNews);
        }

        public void bind(News news){
            this.text.setText(news.getDescription());
            this.title.setText(news.getHeader());
            this.date.setText(getTime(news.getDateNews()));
        }

        private String getTime(long millis){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);

            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            return mDay+"."+mMonth+"."+mYear;
        }
    }
}
