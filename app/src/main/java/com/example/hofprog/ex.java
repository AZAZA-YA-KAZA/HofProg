package com.example.hofprog;

import static com.example.hofprog.ForMan.progeri;
import static com.example.hofprog.Opis_task.progerri;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hofprog.model.proger;
import com.example.hofprog.retrofit.ProgerApi;
import com.example.hofprog.retrofit.RetrofitServis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ex extends AppCompatActivity {
    static boolean f = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex);
        String y = getIntent().getStringExtra("opop");
        System.out.println(progerri + "   mezz  "+ progeri);
        System.out.println(f);
        ArrayList<String> st = new ArrayList<>();
        String arr;
        String t = "";
        if (Objects.equals(y, "")||y == null) {
            arr = (progeri+" ");
        }else {
            arr = (progerri+" ");
        }
        System.out.println(arr);
        for (int i = 0;i < arr.length();i++){
            if (arr.charAt(i) != ' ') t+= arr.charAt(i);
            else{
                System.out.println(t+" tt");
                if (!t.equals("")) st.add(t);
                t = "";
            }
        }
        RecyclerView recyclerView = findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(this, st, y);
        recyclerView.setAdapter(adapter);
    }
}

