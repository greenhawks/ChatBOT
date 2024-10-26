package com.example.chatbot;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NetworkThread extends AsyncTask<String,String,String> {

    String question,answer;
    Context context;
    private Network network;
    public NetworkThread(Context context , String  question,Network network)
    {

        this.question = question;
        this.context = context;
        this.network = network;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }
    private String Return="";
    @Override
    protected String doInBackground(String... strings) {

        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash","AIzaSyDTd0E2tM9iSpHbvKB1ueuHgVF-P5_VfJw");
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);
        Content content = new Content.Builder().addText(question).build();
        Executor executor = Executors.newSingleThreadExecutor();
        ListenableFuture<GenerateContentResponse> listenableFuture = model.generateContent(content);
        Futures.addCallback(
                listenableFuture, new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                         Return = result.getText().toString();

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("Error",t.toString());

                    }
                }

        ,executor);

        return Return;
    }
    @Override
    protected void onProgressUpdate(String... values)
    {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String string)
    {
        super.onPostExecute(string);
        
    }
}
