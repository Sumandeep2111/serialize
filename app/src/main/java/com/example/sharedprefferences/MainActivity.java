package com.example.sharedprefferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.w20.sharedpreference.ObjectSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String SHARED_PREF = "name";
    public static final String KEY_NAME = "key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //there is class named SharedPrefferences
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREF,MODE_PRIVATE);

        //write into shared preferences
        sharedPreferences.edit().putString(KEY_NAME,"Suman").apply();

        //read from shared prefferences
        String name = sharedPreferences.getString(KEY_NAME,"Simran");

        Log.i(TAG,"onCreate: "+name);

        //save an array
        ArrayList<String> names = new ArrayList<>(Arrays.asList("Suman","Simran","Satveer"));
//        sharedPreferences.edit().putStringSet("names",new HashSet<String>(names)).apply();
//
//        //retrieve data
//       Set<String> recvNames =  sharedPreferences.getStringSet("names", new HashSet<String>());
//       Log.i(TAG,"onCreate: " + recvNames.toString());

        try{
            sharedPreferences.edit().putString("names", ObjectSerializer.serialize(names)).apply();
            Log.i(TAG,"onCreate:" + ObjectSerializer.serialize(names));
        }catch (IOException e){
            e.printStackTrace();
        }

        List<String> recvNames = new ArrayList<>();
        try{
            recvNames = (ArrayList) ObjectSerializer.deserialize(sharedPreferences.getString("names",ObjectSerializer.serialize(new ArrayList<>())));
        }catch (IOException e){
            e.printStackTrace();
        }
        Log.i(TAG,"onCreate:" + recvNames.toString());
    }
}
