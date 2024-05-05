package com.example.hofprog;

import static com.example.hofprog.ForMan.nick;
import static com.example.hofprog.ForProg.f;
import static com.example.hofprog.MainActivity.ni;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hofprog.model.manage;
import com.example.hofprog.model.proger;
import com.example.hofprog.model.task;
import com.example.hofprog.model.whoi;
import com.example.hofprog.retrofit.ManageApi;
import com.example.hofprog.retrofit.ProgerApi;
import com.example.hofprog.retrofit.RetrofitServis;
import com.example.hofprog.retrofit.TaskApi;
import com.example.hofprog.retrofit.WhoiApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Exesize extends AppCompatActivity {
    Button buton;
    LinearLayout ll;
    DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actovity_exersize);
        ArrayList<String> st = new ArrayList<>();
        ArrayList<String> dat = new ArrayList<>();
        ArrayList<String> opisanie = new ArrayList<>();
        RetrofitServis retrofitServis = new RetrofitServis();
        TaskApi taskApi = retrofitServis.getRetrofit().create(TaskApi.class);
        RecyclerView recyclerView = findViewById(R.id.RvV);
        taskApi.getAlltask().enqueue(new Callback<List<task>>() {
            private void updateRecyclerView(ArrayList<String> st, ArrayList<String> dat, ArrayList<String> opisanie) {
                System.out.println(Arrays.toString(st.toArray())+" qqq");
                if (st.size() > 0) {
                    while (Objects.equals(st.get(0), " ") || Objects.equals(st.get(0), "")) st.remove(0);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(Exesize.this));
                AdapterTask adapter = new AdapterTask(Exesize.this, st, dat, opisanie);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onResponse(Call<List<task>> call, Response<List<task>> response) {
                System.out.println(f);
                if (f == 0) {
                    List<task> t = response.body();
                    for (task ipp : t) {
                        if (Objects.equals(ipp.getNick(), ni) && ipp.getStat() == 0) {
                            st.add(ipp.getNam());
                            dat.add(ipp.getSroki() +" "+ ipp.getState());
                            opisanie.add(ipp.getOpis());
                        }
                    }
                    updateRecyclerView(st, dat, opisanie);
                }
                else if (f == 1) {
                    List<task> t = response.body();
                    for (task ipp : t) {
                        if (ipp.getStat() == 1) {
                            ManageApi manageApi = retrofitServis.getRetrofit().create(ManageApi.class);
                            manageApi.getAllmanage().enqueue(new Callback<List<manage>>() {
                                @Override
                                public void onResponse(Call<List<manage>> call, Response<List<manage>> response) {
                                    List<manage> sp = response.body();
                                    for (manage ii : sp) {
                                        System.out.println(ipp.getNick() + " ipp.getNick())");
                                        if (Objects.equals(ii.getNick(), ipp.getNick())) {
                                            ArrayList<String> s = new ArrayList<>();
                                            String arr = ii.getProgr_id() + " ";
                                            String t = "";
                                            for (int i = 0; i < arr.length(); i++) {
                                                if (arr.charAt(i) != ' ') t += arr.charAt(i);
                                                else {
                                                    if (!t.equals("")) s.add(t);
                                                    t = "";
                                                }
                                            }
                                            for (String prr : s) {
                                                if (prr.equals(ni)) {
                                                    ProgerApi progerApi = retrofitServis.getRetrofit().create(ProgerApi.class);
                                                    progerApi.getAllprogger().enqueue(new Callback<List<proger>>() {
                                                        @Override
                                                        public void onResponse(Call<List<proger>> call, Response<List<proger>> response) {
                                                            List<proger> progg = response.body();
                                                            for (proger ui : progg) {
                                                                if (Objects.equals(ui.getNick(), ni)) {
                                                                    String[] taskkk = ui.getExam().split(" ");
                                                                    System.out.println(Arrays.toString(taskkk));
                                                                    boolean found = false;
                                                                    for (String element : taskkk) {
                                                                        if (element.equals(ipp.getNam())) {
                                                                            found = true;
                                                                            break;
                                                                        }
                                                                    }
                                                                    if (found) {
                                                                        st.add(ipp.getNam());
                                                                        dat.add(ipp.getSroki() + " " + ipp.getState());
                                                                        opisanie.add(ipp.getOpis());
                                                                        System.out.println(Arrays.toString(st.toArray()) + " wertyuio");
                                                                        updateRecyclerView(st, dat, opisanie);
                                                                    }
                                                                }

                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<List<proger>> call, Throwable t) {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<manage>> call, Throwable t) {

                                }
                            });
                        }
                    }
                }
                else if (f == 2){
                    List<task> t = response.body();
                    for (task ipp : t) {
                        if (ipp.getStat() == 3) {
                            ManageApi manageApi = retrofitServis.getRetrofit().create(ManageApi.class);
                            manageApi.getAllmanage().enqueue(new Callback<List<manage>>() {
                                @Override
                                public void onResponse(Call<List<manage>> call, Response<List<manage>> response) {
                                    List<manage> sp = response.body();
                                    for (manage ii : sp) {
                                        if (Objects.equals(ii.getNick(), ipp.getNick())) {
                                            ArrayList<String> s = new ArrayList<>();
                                            String arr = ii.getProgr_id() + " ";
                                            String t = "";
                                            for (int i = 0; i < arr.length(); i++) {
                                                if (arr.charAt(i) != ' ') t += arr.charAt(i);
                                                else {
                                                    if (!t.equals("")) s.add(t);
                                                    t = "";
                                                }
                                            }
                                            for (String prr : s) {
                                                if (prr.equals(ni)) {
                                                    ProgerApi progerApi = retrofitServis.getRetrofit().create(ProgerApi.class);
                                                    progerApi.getAllprogger().enqueue(new Callback<List<proger>>() {
                                                        @Override
                                                        public void onResponse(Call<List<proger>> call, Response<List<proger>> response) {
                                                            List<proger> progg = response.body();
                                                            for (proger ui : progg) {
                                                                if (Objects.equals(ui.getNick(), ni)) {
                                                                    String[] taskkk = ui.getExam().split(" ");
                                                                    boolean found = false;
                                                                    for (String element : taskkk) {
                                                                        if (element.equals(ipp.getNam())) {
                                                                            found = true;
                                                                            break;
                                                                        }
                                                                    }
                                                                    if (found) {
                                                                        st.add(ipp.getNam());
                                                                        dat.add(ipp.getSroki() + " "+ipp.getState());
                                                                        opisanie.add(ipp.getOpis());
                                                                        updateRecyclerView(st, dat, opisanie);
                                                                    }
                                                                }

                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<List<proger>> call, Throwable t) {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<manage>> call, Throwable t) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<task>> call, Throwable t) {

            }
        });
        ll = findViewById(R.id.LiL1);
        ll.setVisibility(View.GONE);
        datePicker = findViewById(R.id.DT);
        buton = findViewById(R.id.new_task);
        if (f != 0)
            buton.setVisibility(View.GONE);
        Button bt = findViewById(R.id.Back);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhoiApi whoiApi = retrofitServis.getRetrofit().create(WhoiApi.class);
                whoiApi.getAllwhoi().enqueue(new Callback<List<whoi>>() {
                    @Override
                    public void onResponse(Call<List<whoi>> call, Response<List<whoi>> response) {
                        List<whoi> uu= response.body();
                        for (whoi u: uu){
                            if (u.getNick().equals(ni))
                                if (u.getProg() == 1){
                                    Intent intent = new Intent(Exesize.this, ForProg.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }
                            else {
                                Intent intent = new Intent(Exesize.this, ForMan.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<whoi>> call, Throwable t) {

                    }
                });
            }
        });
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                buton.setVisibility(View.GONE);
                TextView vx = findViewById(R.id.VX);
                final boolean[] f = {false};
                vx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        f[0] = true;
                        ll.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        buton.setVisibility(View.VISIBLE);
                    }
                });
                if (f[0]) return;
                EditText et1 = findViewById(R.id.ET1);
                EditText et2 = findViewById(R.id.ET2);
                Button button = findViewById(R.id.BTT);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (et1.getText().toString().equals("") || et2.getText().toString().equals("")) {
                            Toast.makeText(Exesize.this, "Введите данные во все поля", Toast.LENGTH_SHORT).show();
                        } else {
                            RetrofitServis retrofitServis = new RetrofitServis();
                            TaskApi taskApi = retrofitServis.getRetrofit().create(TaskApi.class);
                            taskApi.getAlltask().enqueue(new Callback<List<task>>() {
                                @Override
                                public void onResponse(Call<List<task>> call, Response<List<task>> response) {
                                    if (response.isSuccessful()) {
                                        List<task> allTask = response.body();
                                        String idToCheck = et1.getText().toString();
                                        boolean idExists = isIdExist(allTask, idToCheck);
                                        if (idExists) {
                                            et1.setText("");
                                            Toast.makeText(Exesize.this, "Такая задача уже существует", Toast.LENGTH_SHORT).show();
                                        } else {
                                            final String[] data = {""};
                                            // Получаем календарь и устанавливаем его на 2024 год
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.set(Calendar.HOUR_OF_DAY, 0);
                                            calendar.set(Calendar.MINUTE, 0);
                                            calendar.set(Calendar.SECOND, 0);
                                            calendar.set(Calendar.MILLISECOND, 0);
                                            datePicker.setMinDate(calendar.getTimeInMillis());
                                            // Устанавливаем максимальную дату (на 5 лет вперед от текущей даты)
                                            calendar.add(Calendar.YEAR, 5);
                                            datePicker.setMaxDate(calendar.getTimeInMillis());
                                            data[0] = datePicker.getDayOfMonth() + "."+ (datePicker.getMonth()+1) + "."+ datePicker.getYear();
                                            if (Objects.equals(data[0], ""))
                                                Toast.makeText(Exesize.this, "Выберите дату", Toast.LENGTH_SHORT).show();
                                            else{
                                                System.out.println(allTask.size());
                                                task tac = new task(allTask.size(), nick, 0, et1.getText().toString(), et2.getText().toString(), data[0]);
                                                RetrofitServis retrofitServis = new RetrofitServis();
                                                TaskApi taskApi1 = retrofitServis.getRetrofit().create(TaskApi.class);
                                                taskApi1.save(tac).enqueue(new Callback<task>() {
                                                    @Override
                                                    public void onResponse(Call<task> cal, Response<task> response) {
                                                        ManageApi manageApi = retrofitServis.getRetrofit().create(ManageApi.class);
                                                        manageApi.getAllmanage().enqueue(new Callback<List<manage>>() {
                                                            @Override
                                                            public void onResponse(Call<List<manage>> ca, Response<List<manage>> respose) {
                                                                List<manage> alm = respose.body();
                                                                for (manage msg : alm) {
                                                                    if (msg.getId() != null && msg.getNick().equals(nick)) {
                                                                        String a = msg.getNew_task_id();
                                                                        if (msg.getNew_task_id().length() != 0)
                                                                            a += " ";
                                                                        manage m = new manage(msg.getId(), msg.getNick(), msg.getName(), msg.getFam(), msg.getPochta(), msg.getPassword(), msg.getTel(), msg.getProgr_id(), a + allTask.size(), "");
                                                                        manageApi.update(msg.getId(), m).enqueue(new Callback<manage>() {
                                                                            @Override
                                                                            public void onResponse(Call<manage> call, Response<manage> response) {
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<manage> call, Throwable t) {
                                                                            }
                                                                        });
                                                                        Toast.makeText(Exesize.this, "Задача добавлена", Toast.LENGTH_SHORT).show();
                                                                        st.add(String.valueOf(et1.getText()));
                                                                        opisanie.add(String.valueOf(et2.getText()));
                                                                        System.out.println((String.valueOf(data[0]) + " 0")+"      [poiuytrewasdfghjkl;/.,mnbvcx");
                                                                        dat.add((String.valueOf(data[0]) + " 0"));
                                                                        et1.setText("");
                                                                        et2.setText("");
                                                                        ll.setVisibility(View.GONE);
                                                                        recyclerView.setVisibility(View.VISIBLE);
                                                                        buton.setVisibility(View.VISIBLE);
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<List<manage>> call, Throwable t) {
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onFailure(Call<task> call, Throwable t) {
                                                        Toast.makeText(Exesize.this, "Упс, что-то пошло не так!", Toast.LENGTH_SHORT).show();
                                                        Logger.getLogger(ForMan.class.getName()).log(Level.SEVERE, "Error", t);
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<List<task>> call, Throwable t) {}
                            });
                        }
                    }

                    public boolean isIdExist(List<task> messages, String idToCheck) {
                        for (task msg : messages) {
                            if (msg.getNick() != null && msg.getNick().equals(idToCheck)) {
                                return true;
                            }
                        }
                        return false; // Совпадение не найдено
                    }
                });
            }
        });
    }
}
