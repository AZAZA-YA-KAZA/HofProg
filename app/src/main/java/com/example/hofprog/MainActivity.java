package com.example.hofprog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hofprog.model.manage;
import com.example.hofprog.model.proger;
import com.example.hofprog.model.task;
import com.example.hofprog.model.whoi;
import com.example.hofprog.retrofit.ManageApi;
import com.example.hofprog.retrofit.ProgerApi;
import com.example.hofprog.retrofit.RetrofitServis;
import com.example.hofprog.retrofit.TaskApi;
import com.example.hofprog.retrofit.WhoiApi;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static String progeri = "";
    public static final String mypreference = "mypref";
    public static final String id = null;//может другое
    public static String ni= "132";
    public static int i= 132;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = findViewById(R.id.FN);
        LinearLayout linearLayout1 = findViewById(R.id.LL1);
        LinearLayout linearLayout2 = findViewById(R.id.LL2);
        FrameLayout frameLayout = findViewById(R.id.FL);
        frameLayout.setVisibility(View.GONE);
        linearLayout1.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.GONE);
        Button button1 = findViewById(R.id.BT1);
        Button button2 = findViewById(R.id.BT2);
        SharedPreferences sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        String storedValue = sharedpreferences.getString(id, "ghjk");  // Получаем значение как целое число
        if (!storedValue.equals("ghjk")) {
            RetrofitServis retrofitServis = new RetrofitServis();
            WhoiApi w = retrofitServis.getRetrofit().create(WhoiApi.class);
            w.getAllwhoi().enqueue(new Callback<List<whoi>>() {
                @Override
                public void onResponse(Call<List<whoi>> call, Response<List<whoi>> response) {
                    if (response.isSuccessful()) {
                        List<whoi> allw = response.body();
                        for (whoi it : allw) {
                            if (Objects.equals(it.getNick(), storedValue)) {
                                ni = storedValue;
                                if (it.getMan() == 1) {
                                    Intent intent = new Intent(MainActivity.this, ForMan.class);
                                    intent.putExtra("ni", ni);
                                    startActivity(intent);
                                    return;
                                } else {
                                    Intent intent = new Intent(MainActivity.this, ForProg.class);
                                    intent.putExtra("ni", ni);
                                    startActivity(intent);
                                    return;
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<whoi>> call, Throwable t) {
                }
            });
        }

        else {
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    linearLayout.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//нужна проверка есть ли пользователь в базе
                            vxod();
                        }
                    });
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reg();
                        }
                    });
                }

                EditText et1;
                EditText et2;

                private void reg() {
                    frameLayout.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.GONE);
                    linearLayout1.setVisibility(View.VISIBLE);
                    et1 = findViewById(R.id.ET1);
                    et2 = findViewById(R.id.ET2);
                    EditText et3 = findViewById(R.id.ET3);
                    EditText et4 = findViewById(R.id.ET4);
                    EditText et5 = findViewById(R.id.ET5);
                    EditText et6 = findViewById(R.id.ET6);
                    Button button = findViewById(R.id.BTT);
                    TextView tv = findViewById(R.id.TV3);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vxod();
                        }
                    });
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (et1.getText().toString().equals("") || et2.getText().toString().equals("") || et3.getText().toString().equals("") || et4.getText().toString().equals("") || et5.getText().toString().equals("") || et6.getText().toString().equals("")) {
                                Toast.makeText(MainActivity.this, "Введите данные во все поля", Toast.LENGTH_SHORT).show();
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
                                                Toast.makeText(MainActivity.this, "Такой пользователь уже существует", Toast.LENGTH_SHORT).show();
                                            } else {
                                                whoi who = new whoi(i, et1.getText().toString(), 1, 0);
                                                manage messag = new manage(i + 1, et1.getText().toString(), et4.getText().toString(),
                                                        et6.getText().toString(), et3.getText().toString(), et2.getText().toString(), et5.getText().toString(), "", "", "");
                                                RetrofitServis retrofitServis = new RetrofitServis();
                                                ManageApi messageApi = retrofitServis.getRetrofit().create(ManageApi.class);
                                                WhoiApi wApi = retrofitServis.getRetrofit().create(WhoiApi.class);
                                                wApi.save(who).enqueue(new Callback<whoi>() {
                                                    @Override
                                                    public void onResponse(Call<whoi> call, Response<whoi> response) {
                                                    }

                                                    @Override
                                                    public void onFailure(Call<whoi> call, Throwable t) {
                                                    }
                                                });
                                                TaskApi taskApi = retrofitServis.getRetrofit().create(TaskApi.class);
                                                taskApi.getAlltask().enqueue(new Callback<List<task>>() {
                                                    @Override
                                                    public void onResponse(Call<List<task>> call, Response<List<task>> response) {
                                                        List<task> allTask = response.body();
                                                        messageApi.save(messag).enqueue(new Callback<manage>() {
                                                            @Override
                                                            public void onResponse(@NonNull Call<manage> call, @NonNull Response<manage> response) {
                                                                RetrofitServis retrofitServis = new RetrofitServis();
                                                                TaskApi taskApi = retrofitServis.getRetrofit().create(TaskApi.class);
                                                                task task = new task(allTask.size(), messag.getNick(), 0, "Львы", "У дерева 4 льва, один ушёл... Расчитайте на какое расстояние он ушёл, применив формулу Лопиталя.", "8.08.2024");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+1, messag.getNick(), 0, "Пингвины", "Летели под землёй пингвины. Найти скорость, к соторой велосипед вырабатывал фотосинтез.", "30.04.2024");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+2, messag.getNick(), 0, "Мышь", "Мышь считала дырки в сыре, 3 + 2.. С какой вероятностью первое слово ребёнка будет попа?", "28.05.2024");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+3, messag.getNick(), 0, "Тучки", "Плыли по небу тучки, тучек.. Расчитать максимальное количество туч, которое может появиться на планете.", "1.05.2024");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+4, messag.getNick(), 0, "Пентагон", "Взломать Пентагон", "8.08.2028");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+5, messag.getNick(), 0, "Шахматы", "Выиграть шахмантого бота на уровне профи", "24.04.2025");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+6, messag.getNick(), 0, "Чип", "Создать чип, вживляющийся в человеческий мозг для вкачивания английского языка", "7.05.2024");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+7, messag.getNick(), 0, "Тормоз", "Написать программу для вычисления самого медленно работающего процессора в кабинете", "7.02.2024");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+8, messag.getNick(), 0, "Полёт", "Написать программу, моделирующую полёт человека с крыльями", "5.05.2025");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+9, messag.getNick(), 0, "Паспорт", "Смоделировать канадский паспорт с флагом РФ", "3.12.2024");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+10, messag.getNick(), 0, "Марс", "Смоделировать полёт на Марс", "14.06.2024");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+11, messag.getNick(), 0, "Конец_света", "Спрогнозировать конец света", "14.05.2024");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+12, messag.getNick(), 0, "Курсовая_01", "Написать курсовую работу Варваре Ерховой", "23.05.2024");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+13, messag.getNick(), 0, "Курсовая_02", "Защитить курсовую работу Ерховой Варваре", "2.06.2024");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                task = new task(allTask.size()+14, messag.getNick(), 0, "Учебный_план", "На основе поведения подростка спроектировать наиболее эффективный план по обучению ребёнка.", "14.07.2024");
                                                                taskApi.save(task).enqueue(new Callback<com.example.hofprog.model.task>() {
                                                                    @Override
                                                                    public void onResponse(Call<com.example.hofprog.model.task> call, Response<com.example.hofprog.model.task> response) {
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<com.example.hofprog.model.task> call, Throwable t) {
                                                                    }
                                                                });
                                                                Toast.makeText(MainActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                                                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                                                editor.putString(id, messag.getNick());
                                                                editor.apply();
                                                                ni = messag.getNick();
                                                                Intent intent = new Intent(MainActivity.this, ForMan.class);
                                                                intent.putExtra("mypreference", mypreference);
                                                                intent.putExtra("ni", ni);
                                                                startActivity(intent);
                                                                linearLayout.setVisibility(View.VISIBLE);
                                                                linearLayout1.setVisibility(View.GONE);
                                                            }

                                                            @Override
                                                            public void onFailure(Call<manage> call, Throwable t) {
                                                                Toast.makeText(MainActivity.this, "Упс, что-то пошло не так!", Toast.LENGTH_SHORT).show();
                                                                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, "Error", t);
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onFailure(Call<List<task>> call, Throwable t) {

                                                    }
                                                });
                                            }
                                        }
                                        else {
                                            Toast.makeText(MainActivity.this, "Error with id", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    public boolean isIdExist(List<manage> messages, String idToCheck) {
                                        for (manage msg : messages) {
                                            if (msg.getNick() != null && msg.getNick().equals(idToCheck)) {
                                                return true; // Найдено совпадение
                                            }
                                        }
                                        return false; // Совпадение не найдено
                                    }

                                    @Override
                                    public void onFailure(Call<List<manage>> call, Throwable t) {
                                    }
                                });
                            }
                        }
                    });
                }

                private void vxod() {
                    frameLayout.setVisibility(View.GONE);
                    linearLayout1.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    EditText et1 = findViewById(R.id.ETT1);
                    EditText et2 = findViewById(R.id.ETT2);
                    Button button = findViewById(R.id.BT);
                    TextView tv = findViewById(R.id.TV2);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reg();
                        }
                    });
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (et1.getText().toString().equals("") || et2.getText().toString().equals("")) {
                                Toast.makeText(MainActivity.this, "Введите данные во все поля", Toast.LENGTH_SHORT).show();
                            } else {
                                RetrofitServis retrofitServis = new RetrofitServis();
                                WhoiApi w = retrofitServis.getRetrofit().create(WhoiApi.class);
                                w.getAllwhoi().enqueue(new Callback<List<whoi>>() {
                                    @Override
                                    public void onResponse(Call<List<whoi>> call, Response<List<whoi>> response) {
                                        if (response.isSuccessful()) {
                                            List<whoi> allw = response.body();
                                            whoi msg;
                                            // Проверяем наличие определенного id в списке сообщений
                                            String idToCheck = et1.getText().toString();
                                            isIdExist(allw, idToCheck, et2.getText().toString());
                                        } else {
                                            Toast.makeText(MainActivity.this, "Error with id", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<whoi>> call, Throwable t) {
                                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    public void isIdExist(List<whoi> whoi, String idToCheck, String ps) {
                                        RetrofitServis retrofitServis = new RetrofitServis();
                                        ManageApi manageApi = retrofitServis.getRetrofit().create(ManageApi.class);
                                        manageApi.getAllmanage().enqueue(new Callback<List<manage>>() {
                                            @Override
                                            public void onResponse(Call<List<manage>> call, Response<List<manage>> response) {
                                                List<manage> allm = response.body();
                                                manage man;
                                                for (manage msg : allm) {
                                                    if (msg.getNick() != null && msg.getNick().equals(idToCheck) && msg.getPassword().equals(ps)) {
                                                        Toast.makeText(MainActivity.this, "Вы вошли в аккаунт", Toast.LENGTH_SHORT).show();
                                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                                        editor.putString(id, msg.getNick());
                                                        editor.apply();
                                                        String storedValue = sharedpreferences.getString(id, "mannn");
                                                        Intent intent = new Intent(MainActivity.this, ForMan.class);
                                                        intent.putExtra("mypreference", mypreference);
                                                        ni = msg.getNick();
                                                        intent.putExtra("nick", ni);
                                                        startActivity(intent);
                                                        et1.setText("");
                                                        i++;
                                                        et2.setText("");
                                                        return;
                                                    }
                                                }
                                                ProgerApi progerApi = retrofitServis.getRetrofit().create(ProgerApi.class);
                                                progerApi.getAllprogger().enqueue(new Callback<List<proger>>() {
                                                    @Override
                                                    public void onResponse(Call<List<proger>> call, Response<List<proger>> response) {
                                                        List<proger> allm = response.body();
                                                        for (proger mg : allm) {
                                                            if (mg.getNick() != null && mg.getNick().equals(idToCheck) && mg.getPassword().equals(ps)) {
                                                                Toast.makeText(MainActivity.this, "Вы вошли в аккаунт", Toast.LENGTH_SHORT).show();//отдельно для программистов сделать
                                                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                                                editor.putString(id, mg.getNick());
                                                                editor.apply();
                                                                Intent intent = new Intent(MainActivity.this, ForProg.class);
                                                                intent.putExtra("mypreference", mypreference);
                                                                ni = mg.getNick();
                                                                System.out.println(ni+"  ni_main");
                                                                intent.putExtra("nick", ni);
                                                                startActivity(intent);
                                                                et1.setText("");
                                                                et2.setText("");
                                                                return;
                                                            }
                                                        }
                                                        Toast.makeText(MainActivity.this, "Такого пользователя не существует", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<List<proger>> call, Throwable t) {
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure(Call<List<manage>> call, Throwable t) {
                                            }
                                        });

                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
    }
}