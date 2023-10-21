package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etAddress;
    private Button btnSave, btnViewDb, btnShow, btnPlay, btnStop, btnNotAsync, btnAsync, btnAsync2;
    Intent intent;

    //time service
    BoundService timeService;
    boolean isTimeServiceBound = false;

    //music service
    MusicService musicService;
    boolean isMusicServiceBound = false;
    TextView tvMusicPlayer, tvAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        btnSave = findViewById(R.id.btnSave);
        btnViewDb = findViewById(R.id.btnViewDb);
        btnShow = findViewById(R.id.btnShow);
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);
        tvMusicPlayer = findViewById(R.id.tvMusicPlayer);
        btnNotAsync = findViewById(R.id.btnNotAsync);
        btnAsync = findViewById(R.id.btnAsync);
        btnAsync2 = findViewById(R.id.btnAsync2);
        tvAsync = findViewById(R.id.tvAsync);

        //for database
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etName.getText().toString();
                String address = etAddress.getText().toString();
                DbHandler dbHandler = new DbHandler(MainActivity.this);
                dbHandler.insertUserDetails(username, address);
                intent = new Intent(MainActivity.this, Details.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Success",
                                Toast.LENGTH_LONG).show();
            }
        });

        btnViewDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Details.class);
                startActivity(intent);
            }
        });

        //for displaying date time
        Intent timeServiceIntent = new Intent(this, BoundService.class);
        bindService(timeServiceIntent, myTimeServiceConnection, Context.BIND_AUTO_CREATE);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime(v);
            }
        });

        //for playing music
        Intent musicIntent = new Intent(this, MusicService.class);
        bindService(musicIntent, myMusicServiceConnection, Context.BIND_AUTO_CREATE);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });

        //for Async task
        btnNotAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAsync.setText("Doing something....");
                notAsync();
            }
        });

        btnAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAsync.setText("Doing something....");
                new BackgroundTask().execute();
            }
        });

        btnAsync2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAsync.setText("Doing something....2");
                runthread();
            }
        });

    }

    //display time using BoundService
    private void showTime(View view) {
        String currentTime = timeService.getCurrentTime();
        etName.setText(currentTime);
    }

    private ServiceConnection myTimeServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.MyLocalBinder binder = (BoundService.MyLocalBinder) service;
            timeService = binder.getService();
            isTimeServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isTimeServiceBound = false;
        }
    };


    //play music
    private void playMusic() {
        musicService.PlayMusic();
        tvMusicPlayer.setText("Playing music");
    }

    //stop music
    private void stopMusic() {
        musicService.StopMusic();
        tvMusicPlayer.setText("Music stopped");
    }

    private ServiceConnection myMusicServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MyLocalBinder musicBinder = (MusicService.MyLocalBinder) service;
            musicService = musicBinder.getService();
            isMusicServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isMusicServiceBound = false;
        }
    };

    //for Async task
    private void notAsync() {

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tvAsync.setText("Done!");
    }

    private class BackgroundTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            //perform background task

            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Done!";
        }

        @Override
        protected void onPostExecute(String result){
            tvAsync.setText(result);
        }
    }

    //for Async 2:
    private void runthread() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvAsync.setText("Done_2!");
                    }
                });
            }
        }, 6000);
    }
}