package com.endpoint.myway.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.endpoint.myway.R;
import com.endpoint.myway.activities_fragments.activity_detilas.DetialsActivity;
import com.endpoint.myway.databinding.NewsRowBinding;
import com.endpoint.myway.databinding.OffersRowBinding;
import com.endpoint.myway.models.SingleNewsModel;

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
        msgRightHolder.binding.tvread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetialsActivity.class);
                intent.putExtra("title",  orderlist.get(msgRightHolder.getLayoutPosition()).getTitle()+"");
                intent.putExtra("content",  orderlist.get(msgRightHolder.getLayoutPosition()).getContent()+"");
                intent.putExtra("image",  orderlist.get(msgRightHolder.getLayoutPosition()).getImage()+"");

                context.startActivity(intent);
            }
        });
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