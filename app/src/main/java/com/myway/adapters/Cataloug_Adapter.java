package com.myway.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.myway.R;
import com.myway.databinding.CatlougRowBinding;
import com.myway.databinding.OffersRowBinding;
import com.myway.models.FileDownloader;
import com.myway.models.SingleCatalougModel;
import com.myway.tags.Tags;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
        showfile(orderlist.get(msgRightHolder.getLayoutPosition()).getFile());
    }
});
msgRightHolder.binding.btnSend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Log.e("kkkkk",orderlist.get(msgRightHolder.getLayoutPosition()).getFile());
        try {

            String fileName=orderlist.get(msgRightHolder.getLayoutPosition()).getFile().split("/")[3];

//           download pdf file.

            URL url = new URL(Tags.base_url+orderlist.get(msgRightHolder.getLayoutPosition()).getFile());
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            String PATH = Environment.getExternalStorageDirectory() + "/mydownload/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, fileName);
            FileOutputStream fos = new FileOutputStream(outputFile);
            InputStream is = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            System.out.println("--pdf downloaded--ok--"+url.getPath());
        } catch (Exception e) {
     Log.e("kkkkkkk",e.toString());

        }}
});
//        Liked_Adapter comments_adapter = new Liked_Adapter(orderlist, context);
//        msgRightHolder.binding.recliked.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
//        msgRightHolder.binding.recliked.setAdapter(comments_adapter);

    }

    private void showfile(String file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(Tags.base_url+file),"application/pdf");
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
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];
// -> https://letuscsolutions.files.wordpress.com/2015/07/five-point-someone-chetan-bhagat_ebook.pdf
            String fileName = strings[1];
// ->five-point-someone-chetan-bhagat_ebook.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().getAbsoluteFile()+"/pdffff";
            Log.e("lllll",extStorageDirectory);
            File folder = new File(extStorageDirectory);
            folder.mkdir();
            Log.e("lllll",folder.getPath()+folder.getName()+fileName);
            String path = folder.getAbsolutePath() + "/" + fileName;

            File pdfFile = new File(path);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
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