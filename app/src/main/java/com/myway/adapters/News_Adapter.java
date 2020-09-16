package com.myway.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.myway.R;
import com.myway.databinding.NewsRowBinding;
import com.myway.databinding.OffersRowBinding;
import com.myway.models.SingleNewsModel;
import com.myway.models.SingleOfferModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class News_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SingleNewsModel> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private int i = -1;

    public News_Adapter(List<SingleNewsModel> orderlist, Context context) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        NewsRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.news_row, parent, false);
        return new EventsHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        EventsHolder msgRightHolder = (EventsHolder) holder;
        msgRightHolder.binding.setModel(orderlist.get(position));
//        Liked_Adapter comments_adapter = new Liked_Adapter(orderlist, context);
//        msgRightHolder.binding.recliked.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
//        msgRightHolder.binding.recliked.setAdapter(comments_adapter);

    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }


    public class EventsHolder extends RecyclerView.ViewHolder {
        public NewsRowBinding binding;

        public EventsHolder(@NonNull NewsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}