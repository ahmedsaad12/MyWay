package com.endpoint.myway.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.endpoint.myway.R;
import com.endpoint.myway.databinding.CatlougRowBinding;
import com.endpoint.myway.databinding.OffersRowBinding;
import com.endpoint.myway.models.FileDownloader;
import com.endpoint.myway.models.SingleCatalougModel;
import com.endpoint.myway.tags.Tags;
import com.kodmap.app.library.PopopDialogBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Cataloug_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SingleCatalougModel> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private int i = -1;

    public Cataloug_Adapter(List<SingleCatalougModel> orderlist, Context context) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        CatlougRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.catloug_row, parent, false);
        return new EventsHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        EventsHolder msgRightHolder = (EventsHolder) holder;
        msgRightHolder.binding.setModel(orderlist.get(position));
        msgRightHolder.binding.tvshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //     showfile(orderlist.get(msgRightHolder.getLayoutPosition()).getFile());
                showfiles(orderlist.get(msgRightHolder.getLayoutPosition()).getImages());
            }
        });
        msgRightHolder.binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("kkkkk", orderlist.get(msgRightHolder.getLayoutPosition()).getFile());
                new DownloadFile().execute(Tags.base_url + orderlist.get(msgRightHolder.getLayoutPosition()).getFile(), orderlist.get(msgRightHolder.getLayoutPosition()).getFile().split("/")[3]);
            }
        });
//        Liked_Adapter comments_adapter = new Liked_Adapter(orderlist, context);
//        msgRightHolder.binding.recliked.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
//        msgRightHolder.binding.recliked.setAdapter(comments_adapter);

    }

    private void showfiles(List<SingleCatalougModel.Images> images) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            list.add(Tags.IMAGE_URL + images.get(i).getImage());
        }
        Dialog dialog = new PopopDialogBuilder(context)
                // Set list like as option1 or option2 or option3
                .setList(list,list.size())
                // or setList with initial position that like .setList(list,position)
                // Set dialog header color
                .setHeaderBackgroundColor(android.R.color.holo_blue_light)
                // Set dialog background color
                .setDialogBackgroundColor(R.color.color_dialog_bg)
                // Set close icon drawable
                .setCloseDrawable(R.drawable.ic_close_white_24dp)
                // Set loading view for pager image and preview image
                .setLoadingView(R.layout.loading_view)
                // Set dialog style
                .setDialogStyle(R.style.DialogStyle)
                // Choose selector type, indicator or thumbnail
                .showThumbSlider(true)
                // Set image scale type for slider image
                .setSliderImageScaleType(ImageView.ScaleType.FIT_CENTER)
                // Set indicator drawable
                // .setSelectorIndicator(R.drawable.sample_indicator_selector)
                // Enable or disable zoomable
                .setIsZoomable(true)

                // Build Km Slider Popup Dialog
                .build();
        dialog.show();
    }

    private void showfile(String file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(Tags.base_url + file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }


    public class EventsHolder extends RecyclerView.ViewHolder {
        public CatlougRowBinding binding;

        public EventsHolder(@NonNull CatlougRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString() + "/foldr";
            File folder = new File(extStorageDirectory);
            folder.mkdirs();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                Log.e("lll", e.toString());
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context, "Download PDf successfully", Toast.LENGTH_SHORT).show();

            Log.e("Download complete", "----------");
        }
    }


}