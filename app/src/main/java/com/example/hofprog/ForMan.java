package com.example.hofprog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.hofprog.MainActivity.id;
import static com.example.hofprog.MainActivity.ni;
import static com.example.hofprog.ForProg.f;
import static com.example.hofprog.MainActivity.mypreference;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hofprog.model.manage;
import com.example.hofprog.model.proger;
import com.example.hofprog.model.whoi;
import com.example.hofprog.retrofit.ManageApi;
import com.example.hofprog.retrofit.ProgerApi;
import com.example.hofprog.retrofit.RetrofitServis;
import com.example.hofprog.retrofit.WhoiApi;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForMan extends AppCompatActivity {
    public static String progeri = "";
    public static String nick;
    TextView nm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forman);
        LinearLayout linearLayout1 = findViewById(R.id.LL1);
        LinearLayout linearLayout2 = findViewById(R.id.LL2);
        nm = findViewById(R.id.Name);
        linearLayout1.setVisibility(View.GONE);
        Button exit = findViewById(R.id.Exit);
        Button sot = findViewById(R.id.Sot);
        Button bt = findViewById(R.id.BT);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:89193462701"));
                startActivity(intent);
            }
        });
        Button prognew = findViewById(R.id.prognew);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();

                String storedValue = sharedpreferences.getString(id, "ghjk");  // Получаем значение как целое число
                finishAffinity();
            }
        });
        sot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForMan.this, ex.class);
                RetrofitServis retrofitServis = new RetrofitServis();
                ManageApi manageApi = retrofitServis.getRetrofit().create(ManageApi.class);
                manageApi.getAllmanage().enqueue(new Callback<List<manage>>() {
                    @Override
                    public void onResponse(Call<List<manage>> ca, Response<List<manage>> respose) {
                        List<manage> alm = respose.body();
                        for (manage msg : alm) {
                            if (msg.getId() != null && msg.getNick().equals(nick)) {
                                progeri = msg.getProgr_id();
                                intent.putExtra("st", progeri);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<manage>> call, Throwable t) {

                    }
                });
            }
        });
        prognew.setOnClickListener(new View.OnClickListener() {//в список
            @Override
            public void onClick(View v) {
                linearLayout1.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.GONE);
                TextView vx = findViewById(R.id.VX);
                final boolean[] f = {false};
                vx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        f[0] = true;
                        linearLayout1.setVisibility(View.GONE);
                        linearLayout2.setVisibility(View.VISIBLE);
                    }
                });
                if (f[0]) return;
                EditText et1 = findViewById(R.id.ET1);
                EditText et2 = findViewById(R.id.ET2);
                EditText et3 = findViewById(R.id.ET3);
                EditText et4 = findViewById(R.id.ET4);
                EditText et5 = findViewById(R.id.ET5);
                EditText et6 = findViewById(R.id.ET6);
                Button button = findViewById(R.id.BTT);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (et1.getText().toString().equals("") || et2.getText().toString().equals("") || et3.getText().toString().equals("") || et4.getText().toString().equals("") || et5.getText().toString().equals("") || et6.getText().toString().equals("")) {
                            Toast.makeText(ForMan.this, "Введите данные во все поля", Toast.LENGTH_SHORT).show();
                        } else {
                            RetrofitServis retrofitServis = new RetrofitServis();
                            ManageApi manageApi = retrofitServis.getRetrofit().create(ManageApi.class);
                            manageApi.getAllmanage().enqueue(new Callback<List<manage>>() {
                                @Override
                                public void onResponse(Call<List<manage>> call, Response<List<manage>> response) {
                                    if (response.isSuccessful()) {
                                        List<manage> allManag = response.body();
                                        String idToCheck = et1.getText().toString();
                                        boolean idExists = isIdExist(allManag, idToCheck);

                                        if (idExists) {
                                            et1.setText("");
                                            Toast.makeText(ForMan.this, "Такой пользователь уже существует", Toast.LENGTH_SHORT).show();
                                        } else {
                                            ProgerApi progerApi = retrofitServis.getRetrofit().create(ProgerApi.class);
                                            progerApi.getAllprogger().enqueue(new Callback<List<proger>>() {
                                                @Override
                                                public void onResponse(Call<List<proger>> call, Response<List<proger>> response) {
                                                    List<proger> allm = response.body();
                                                    for (proger msg : allm) {
                                                        if (msg.getNick() != null && msg.getNick().equals(idToCheck)) {
                                                            et1.setText("");
                                                            Toast.makeText(ForMan.this, "Такой пользователь уже существует", Toast.LENGTH_SHORT).show();
                                                            return;
                                                        }
                                                    }
                                                    whoi who = new whoi(0, et1.getText().toString(), 0, 1);
                                                    proger prog = new proger(0, et1.getText().toString(), et4.getText().toString(),
                                                            et6.getText().toString(), et3.getText().toString(), et2.getText().toString(), et5.getText().toString(), "", "");
                                                    RetrofitServis retrofitServis = new RetrofitServis();
                                                    ProgerApi progerApi1 = retrofitServis.getRetrofit().create(ProgerApi.class);
                                                    WhoiApi wApi = retrofitServis.getRetrofit().create(WhoiApi.class);
                                                    wApi.save(who).enqueue(new Callback<whoi>() {
                                                        @Override
                                                        public void onResponse(Call<whoi> call, Response<whoi> response) {
                                                        }

                                                        @Override
                                                        public void onFailure(Call<whoi> call, Throwable t) {
                                                        }
                                                    });
                                                    progerApi1.save(prog).enqueue(new Callback<proger>() {
                                                        @Override
                                                        public void onResponse(Call<proger> cal, Response<proger> response) {
                                                            manageApi.getAllmanage().enqueue(new Callback<List<manage>>() {
                                                                @Override
                                                                public void onResponse(Call<List<manage>> ca, Response<List<manage>> respose) {
                                                                    List<manage> alm = respose.body();
                                                                    for (manage msg : alm) {
                                                                        if (msg.getId() != null && msg.getNick().equals(nick)) {
                                                                            String a = msg.getProgr_id();
                                                                            if (msg.getProgr_id().length() != 0) a += " ";
                                                                            manage m = new manage(msg.getId(), msg.getNick(), msg.getName(), msg.getFam(), msg.getPochta(), msg.getPassword(), msg.getTel(), a + et1.getText().toString(), "", "");
                                                                            manageApi.update(msg.getId(), m).enqueue(new Callback<manage>() {
                                                                                @Override
                                                                                public void onResponse(Call<manage> call, Response<manage> response) {
                                                                                }
                                                                                @Override
                                                                                public void onFailure(Call<manage> call, Throwable t) {
                                                                                }
                                                                            });
                                                                            Toast.makeText(ForMan.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                                                                            et1.setText("");
                                                                            et2.setText("");
                                                                            et3.setText("");
                                                                            et4.setText("");
                                                                            et5.setText("");
                                                                            et6.setText("");
                                                                            linearLayout2.setVisibility(View.VISIBLE);
                                                                            linearLayout1.setVisibility(View.GONE);
                                                                            return;
                                                                        }
                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(Call<List<manage>> call, Throwable t) {

                                                                }
                                                            });
                                                        }

                                                        @Override
                                                        public void onFailure(Call<proger> call, Throwable t) {
                                                            Toast.makeText(ForMan.this, "Упс, что-то пошло не так!", Toast.LENGTH_SHORT).show();
                                                            Logger.getLogger(ForMan.class.getName()).log(Level.SEVERE, "Error", t);
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onFailure(Call<List<proger>> call, Throwable t) {

                                                }

                                                public boolean isIdExist(List<manage> messages, String idToCheck) {
                                                    for (manage msg : messages) {
                                                        if (msg.getNick() != null && msg.getNick().equals(idToCheck)) {
                                                            return true;
                                                        }
                                                    }
                                                    return false; // Совпадение не найдено
                                                }
                                            });
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<manage>> call, Throwable t) {

                                }
                            });
                        }
                    }

                    public boolean isIdExist(List<manage> messages, String idToCheck) {
                        for (manage msg : messages) {
                            if (msg.getNick() != null && msg.getNick().equals(idToCheck)) {
                                return true; // Найдено совпадение
                            }
                        }
                        return false; // Совпадение не найден
                    }
                });
            }
        });
        Button exesize = findViewById(R.id.Exesize);
        exesize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f = 0;
                Intent intent = new Intent(ForMan.this, Exesize.class);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        nick = ni;
        RetrofitServis retrofitServis = new RetrofitServis();
        ManageApi manageApi = retrofitServis.getRetrofit().create(ManageApi.class);
        manageApi.getAllmanage().enqueue(new Callback<List<manage>>() {
            @Override
            public void onResponse(Call<List<manage>> call, Response<List<manage>> response) {
                List<manage> m = response.body();
                for (manage u : m){
                    if (Objects.equals(u.getNick(), nick)){
                        nm.setText(u.getName() + " " + u.getFam());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<manage>> call, Throwable t) {
            }
        });
    }

}