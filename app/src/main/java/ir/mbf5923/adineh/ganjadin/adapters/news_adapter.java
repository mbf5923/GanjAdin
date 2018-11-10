package ir.mbf5923.adineh.ganjadin.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.mbf5923.adineh.ganjadin.R;
import ir.mbf5923.adineh.ganjadin.models.news_model;


/**
 * Created by MBF5923 on 20/08/2017.
 */

public abstract class news_adapter extends RecyclerView.Adapter<news_adapter.MyViewHolder> {
    private List<news_model> mainitemModelList;
    private static ClickListener clickListener;





    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView tvtitr,tvtime;
        MyViewHolder(View view) {
            super(view);
            tvtitr=  view.findViewById(R.id.tvtitr);
            tvtime=  view.findViewById(R.id.tvtime);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        news_adapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public news_adapter(List<news_model> moviesList) {
        this.mainitemModelList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.news_item, parent, false);
        return new MyViewHolder(v);
    }
    protected abstract void load();
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if ((position >= getItemCount() - 1))
            load();
        news_model movie = mainitemModelList.get(position);
        holder.tvtitr.setText(movie.getTitr());
        holder.tvtime.setText(movie.getTime());

    }

    @Override
    public int getItemCount() {
        return mainitemModelList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
