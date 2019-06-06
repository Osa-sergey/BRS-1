package Adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misis.brs.Database.Mark;
import com.misis.brs.R;

import java.util.Vector;


public class MarkViewAdapter extends RecyclerView.Adapter<MarkViewAdapter.ViewHolder> {

    private Vector<Mark> marksVector = new Vector<>();

    public void setItems(Vector<Mark> marks){
        marksVector.addAll(marks);
        notifyDataSetChanged();
    }

    public void setItems(Mark mark){
        marksVector.add(mark);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MarkViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_points_simple_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkViewAdapter.ViewHolder viewHolder, int i) {
       viewHolder.bind(marksVector.get(i));
    }

    @Override
    public int getItemCount() {
        return marksVector.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mark;
        private TextView description;
        private TextView markType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mark = itemView.findViewById(R.id.mark);
            description = itemView.findViewById(R.id.description);
            markType = itemView.findViewById(R.id.markType);
        }

        public void bind(Mark mark){
            this.mark.setText(mark.getMark());
            this.description.setText(mark.getDescription());
            this.markType.setText(mark.getMarkType().name());
        }
    }
}
