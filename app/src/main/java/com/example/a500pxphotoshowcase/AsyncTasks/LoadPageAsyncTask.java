package com.example.a500pxphotoshowcase.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;
import com.example.a500pxphotoshowcase.API;
import com.example.a500pxphotoshowcase.Models.PageModel;
import java.io.IOException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadPageAsyncTask extends AsyncTask<Void, Void, PageModel> {


    public interface TaskListener {
        void onComplete(PageModel pageModel);
    }


    private String key;
    private String filter;
    private int page;
    private int numberOfResults;
    private TaskListener taskListener;

    public LoadPageAsyncTask(String key, String filter,int numberOfResults, int page, TaskListener taskListener){
        this.key = key;
        this.filter = filter;
        this.page = page;
        this.numberOfResults = numberOfResults;
        this.taskListener = taskListener;
    }

    @Override
    protected PageModel doInBackground(Void... voids) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        PageModel pageModel = new PageModel();

        try{
            pageModel = api.getPage(key, filter, "3,1080", numberOfResults, page).execute().body();
        } catch (IOException e) {
            Log.d("ERROR","LoadPageAsyncTask error" +  e);
        }


        return pageModel;
    }


    @Override
    protected void onPostExecute(PageModel pageModel) {
        super.onPostExecute(pageModel);
        taskListener.onComplete(pageModel);
    }
}
