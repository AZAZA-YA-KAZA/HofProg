package com.example.hofprog;

import static com.example.hofprog.MainActivity.mypreference;
import static com.example.hofprog.MainActivity.ni;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.lang.UProperty;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hofprog.model.manage;
import com.example.hofprog.model.proger;
import com.example.hofprog.retrofit.ManageApi;
import com.example.hofprog.retrofit.ProgerApi;
import com.example.hofprog.retrofit.RetrofitServis;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForProg extends AppCompatActivity {
    static int f = 0;
    public static String nick;
    TextView rar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forprog);
        Button exit = findViewById(R.id.Exit);
        rar = findViewById(R.id.rar);
        Button bt = findViewById(R.id.BT);
        Button tec = findViewById(R.id.tec);
        tec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f = 1;
                Intent intent = new Intent(ForProg.this, Exesize.class);
                startActivity(intent);
            }
        });
        Button otpr = findViewById(R.id.otpr);
        otpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f = 2;
                Intent intent = new Intent(ForProg.this, Exesize.class);
                startActivity(intent);
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:89193462701"));
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply(); // или editor.commit();
                finishAffinity();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        nick = ni;
        System.out.println(ni+"  ni");
        RetrofitServis retrofitServis = new RetrofitServis();
        ProgerApi progerApi = retrofitServis.getRetrofit().create(ProgerApi.class);
        progerApi.getAllprogger().enqueue(new Callback<List<proger>>() {
            @Override
            public void onResponse(Call<List<proger>> call, Response<List<proger>> response) {
                List<proger> m = response.body();
                for (proger u : m){
                    if (Objects.equals(u.getNick(), nick)){
                        rar.setText(u.getName() + " " + u.getFam());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<proger>> call, Throwable t) {
            }
        });
    }
}
