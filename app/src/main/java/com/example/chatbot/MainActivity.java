package com.example.chatbot;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView Question;
    private Button Send;
    private ArrayList<Message> messages;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messages = new ArrayList<>();
        Question = (TextView) findViewById(R.id.Question);

        Send = (Button)findViewById(R.id.Send);
        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        ChatAdapter adapter = new ChatAdapter(messages,getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = Question.getText().toString();
                if(!question.isEmpty()) {
                    messages.add(new Message(question,"user"));
                    adapter.notifyItemInserted(messages.size()-1);
                    recyclerView.scrollToPosition(messages.size()-1);

                    Question.setText("");

                    NetworkThread nt = new NetworkThread(getApplicationContext(), question,response->{
                        messages.add(new Message(response,"bot"));
                        adapter.notifyItemInserted(messages.size()-1);
                        recyclerView.scrollToPosition(messages.size()-1);
                    });
                    nt.execute();
                }
            }
        });
    }
}