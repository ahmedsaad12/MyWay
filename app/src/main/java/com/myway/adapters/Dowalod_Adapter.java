package com.myway.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.myway.R;
import com.myway.activities_fragments.activity_detilas.DetialsActivity;
import com.myway.databinding.DownalodRowBinding;
import com.myway.databinding.NewsRowBinding;
import com.myway.models.SingleNewsModel;
import com.myway.tags.Tags;

import java.io.File;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Dowalod_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  File[] files ;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private int i = -1;

    public Dowalod_Adapter( File[] files , Context context) {
        this.files = files;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        DownalodRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.downalod_row, parent, false);
        return new EventsHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        EventsHolder msgRightHolder = (EventsHolder) holder;
        msgRightHolder.binding.setTitle(files[position].getName());
     msgRightHolder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(Intent.ACTION_VIEW);
             intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().toString()+"/foldr/" + files[msgRightHolder.getLayoutPosition()].getName()), "application/pdf");
             intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
             context.startActivity(intent);
         }
     });
//        Liked_Adapter comments_adapter = new Liked_Adapter(orderlist, context);
//        msgRightHolder.binding.recliked.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
//        msgRightHolder.binding.recliked.setAdapter(comments_adapter);

    }

    @Override
    public int getItemCount() {
        return files.length;
    }


    public class EventsHolder extends RecyclerView.ViewHolder {
        public DownalodRowBinding binding;

        public EventsHolder(@NonNull DownalodRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}