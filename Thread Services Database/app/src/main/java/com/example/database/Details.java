package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
/* This class file is for displaying DB in a list view */
public class Details extends AppCompatActivity {

    Intent intent;
    ListView lvUserList;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        DbHandler db = new DbHandler(this);
        ArrayList<HashMap<String, String>> userList = db.GetUsers();
        lvUserList = findViewById(R.id.user_list);
        ListAdapter adapter = new SimpleAdapter(Details.this, userList,
                                R.layout.list_row,
                                new String[]{"id", "name", "address"},
                                new int[] {R.id.tvId, R.id.tvName, R.id.tvAddress});
        lvUserList.setAdapter(adapter);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Details.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}