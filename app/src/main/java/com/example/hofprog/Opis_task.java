package com.example.hofprog;

import static com.example.hofprog.AdapterTask.dat;
import static com.example.hofprog.AdapterTask.opisanie;
import static com.example.hofprog.AdapterTask.st;
import static com.example.hofprog.MainActivity.ni;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Opis_task extends AppCompatActivity {
    public static String progerri = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opis_task);
        int y = getIntent().getIntExtra("y", -1);
        System.out.println(y+"  yy");
        TextView num_z = findViewById(R.id.Number);
        TextView nam = findViewById(R.id.Name);
        TextView srok = findViewById(R.id.Srok);
        ImageView fon = findViewById(R.id.Fon);
        TextView opis = findViewById(R.id.Opis);
        Button exit = findViewById(R.id.Exit);
        LinearLayout lil = findViewById(R.id.LiL);
        LinearLayout pp = findViewById(R.id.pp);
        pp.setVisibility(View.VISIBLE);
        lil.setVisibility(View.GONE);
        num_z.setText("Зaдача №" + (y+1));
        nam.setText(st.get(y));
        opis.setText(opisanie.get(y));
        srok.setText(dat.get(y).split(" ")[0]);
        System.out.println(y+"   y");
        // Получаем текущую дату
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.SECOND, 0);
        Calendar y_zad = Calendar.getInstance();
        String[] arr = dat.get(y).split(" ")[0].split("\\.");
        y_zad.set(Calendar.MONTH, Integer.parseInt(arr[1]) - 1);
        y_zad.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arr[0]));
        y_zad.set(Calendar.YEAR, Integer.parseInt(arr[2]));
        // Вычисляем разницу
        long diffInMillis = y_zad.getTimeInMillis() - today.getTimeInMillis();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
        // Проверяем, что разница меньше недели
        System.out.println(y+"position "+ diffInDays);
        if (diffInDays < 0) {
            fon.setBackgroundColor(Color.BLACK);
        } else if (diffInDays < 7) {
            fon.setBackgroundColor(Color.RED);
        } else if (diffInDays < 14) {
            fon.setBackgroundColor(Color.YELLOW);
        } else {
            fon.setBackgroundColor(Color.GREEN);
        }
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button mn = findViewById(R.id.man);
        Button pr = findViewById(R.id.prog);
        RetrofitServis retrofitServis = new RetrofitServis();
        WhoiApi whoiApi = retrofitServis.getRetrofit().create(WhoiApi.class);
        whoiApi.getAllwhoi().enqueue(new Callback<List<whoi>>() {
            @Override
            public void onResponse(Call<List<whoi>> call, Response<List<whoi>> response) {
                List<whoi> wh = response.body();
                for (whoi i : wh){
                    System.out.println(ni);
                    if (Objects.equals(i.getNick(), ni)){
                        System.out.println(i.getMan()+ " " + i.getProg()+ " ppp");
                        if (i.getMan() == 0) {
                            mn.setVisibility(View.GONE);//смотрим статус задачи
                            RetrofitServis retrofitServis = new RetrofitServis();
                            TaskApi taskApi = retrofitServis.getRetrofit().create(TaskApi.class);
                            taskApi.getAlltask().enqueue(new Callback<List<task>>() {
                                @Override
                                public void onResponse(Call<List<task>> call, Response<List<task>> response) {
                                    List<task> tsk = response.body();
                                    for (task ipp: tsk) {//по всем задачам
                                        if (ipp.getNam().equals(String.valueOf(nam.getText()))){//название?
                                            ManageApi manageApi = retrofitServis.getRetrofit().create(ManageApi.class);
                                            manageApi.getAllmanage().enqueue(new Callback<List<manage>>() {
                                                @Override
                                                public void onResponse(Call<List<manage>> call, Response<List<manage>> response) {//по всем менеджерам
                                                    List<manage> sp = response.body();
                                                    for (manage ii : sp) {
                                                        if (Objects.equals(ii.getNick(), ipp.getNick())) {//если задача есть у менеджера
                                                            ArrayList<String> s = new ArrayList<>();
                                                            String arr = ii.getProgr_id() + " ";
                                                            String t = "";
                                                            for (int i = 0; i < arr.length(); i++) {
                                                                if (arr.charAt(i) != ' ')
                                                                    t += arr.charAt(i);
                                                                else {
                                                                    if (!t.equals("")) s.add(t);
                                                                    t = "";
                                                                }
                                                            }
                                                            for (String prr : s) {//создали список программистов менеждера
                                                                if (prr.equals(ni)) {//есть прогр у менеджера
                                                                    ProgerApi progerApi = retrofitServis.getRetrofit().create(ProgerApi.class);
                                                                    progerApi.getAllprogger().enqueue(new Callback<List<proger>>() {
                                                                        @Override
                                                                        public void onResponse(Call<List<proger>> call, Response<List<proger>> response) {
                                                                            List<proger> progg = response.body();
                                                                            for (proger uii : progg) {
                                                                                if (Objects.equals(uii.getNick(), ni)) {//если это наш программист
                                                                                    System.out.println(ni+"  ni");
                                                                                    String[] taskkk = uii.getExam().split(" ");
                                                                                    for (String element : taskkk) {//задача сходится с нашей
                                                                                        if (element.equals(String.valueOf(nam.getText()))) {
                                                                                            if (ipp.getState() == 3)
                                                                                                pr.setVisibility(View.GONE);
                                                                                        }
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

                                @Override
                                public void onFailure(Call<List<task>> call, Throwable t) {

                                }
                            });
                        }
                        if (i.getProg() == 0)
                            pr.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<whoi>> call, Throwable t) {

            }
        });
        mn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitServis retrofitServis = new RetrofitServis();
                ManageApi manageApi = retrofitServis.getRetrofit().create(ManageApi.class);
                manageApi.getAllmanage().enqueue(new Callback<List<manage>>() {
                    @Override
                    public void onResponse(Call<List<manage>> ca, Response<List<manage>> respose) {
                        List<manage> alm = respose.body();
                        for (manage msg : alm) {
                            if (msg.getId() != null && msg.getNick().equals(ni)) {
                                System.out.println(msg.getNick()+" "+ ni+"  nii");
                                progerri = msg.getProgr_id();
                                System.out.println(progerri+"progerri");
                                Intent intent = new Intent(Opis_task.this, ex.class);
                                intent.putExtra("opop", st.get(y));
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
        pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pp.setVisibility(View.GONE);
                lil.setVisibility(View.VISIBLE);
                EditText et = findViewById(R.id.ET1);
                TextView vx = findViewById(R.id.VX);
                final boolean[] f = {false};
                vx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        f[0] = true;
                        et.setText("");
                        lil.setVisibility(View.GONE);
                        pp.setVisibility(View.VISIBLE);
                    }
                });
                if (f[0]) return;
                Button btt = findViewById(R.id.BTT);
                btt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RetrofitServis retrofitServis = new RetrofitServis();
                        TaskApi taskApi = retrofitServis.getRetrofit().create(TaskApi.class);
                        taskApi.getAlltask().enqueue(new Callback<List<task>>() {
                            @Override
                            public void onResponse(Call<List<task>> call, Response<List<task>> response) {
                                List<task> tsk = response.body();
                                for (task ipp: tsk) {//по всем задачам
                                    System.out.println(String.valueOf(ipp.getNam()) + "   ipp.getNam()   "+ nam.getText());
                                    if (ipp.getNam().equals(String.valueOf(nam.getText()))){//название?
                                        System.out.println(ipp.getNam()+" наша задача - это");
                                        ManageApi manageApi = retrofitServis.getRetrofit().create(ManageApi.class);
                                        manageApi.getAllmanage().enqueue(new Callback<List<manage>>() {
                                            @Override
                                            public void onResponse(Call<List<manage>> call, Response<List<manage>> response) {//по всем менеджерам
                                                List<manage> sp = response.body();
                                                for (manage ii : sp) {
                                                    if (Objects.equals(ii.getNick(), ipp.getNick())) {//если задача есть у менеджера
                                                        System.out.println(ii.getNick()+" "+ ipp.getNick());
                                                        ArrayList<String> s = new ArrayList<>();
                                                        String arr = ii.getProgr_id() + " ";
                                                        String t = "";
                                                        for (int i = 0; i < arr.length(); i++) {
                                                            if (arr.charAt(i) != ' ')
                                                                t += arr.charAt(i);
                                                            else {
                                                                if (!t.equals("")) s.add(t);
                                                                t = "";
                                                            }
                                                        }
                                                        for (String prr : s) {//создали список программистов менеждера
                                                            if (prr.equals(ni)) {//есть прогр у менеджера
                                                                ProgerApi progerApi = retrofitServis.getRetrofit().create(ProgerApi.class);
                                                                progerApi.getAllprogger().enqueue(new Callback<List<proger>>() {
                                                                    @Override
                                                                    public void onResponse(Call<List<proger>> call, Response<List<proger>> response) {
                                                                        List<proger> progg = response.body();
                                                                        for (proger uii : progg) {
                                                                            if (Objects.equals(uii.getNick(), ni)) {//если это наш программист
                                                                                System.out.println(ni+"  ni");
                                                                                String[] taskkk = uii.getExam().split(" ");
                                                                                for (String element : taskkk) {//задача сходится с нашей
                                                                                    System.out.println(element+"  element");
                                                                                    if (element.equals(String.valueOf(nam.getText()))) {
                                                                                        System.out.println(nam.getText() + "  zadacha");
                                                                                        if (Objects.equals(uii.getNick(), ni)) {
                                                                                            task mm = new task(ipp.getId(), ipp.getNick(), 3, ipp.getNam(), ipp.getOpis() + "\n" + et.getText().toString(), ipp.getSroki());
                                                                                            taskApi.update(ipp.getId(), mm).enqueue(new Callback<task>() {
                                                                                                @Override
                                                                                                public void onResponse(Call<task> call, Response<task> response) {
                                                                                                }

                                                                                                @Override
                                                                                                public void onFailure(Call<task> call, Throwable t) {
                                                                                                }
                                                                                            });
                                                                                            Intent intent = new Intent(Opis_task.this, ForProg.class);
                                                                                            startActivity(intent);
                                                                                            finishAffinity();
                                                                                        }
                                                                                        break;
                                                                                    }
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

                            @Override
                            public void onFailure(Call<List<task>> call, Throwable t) {

                            }
                        });

                    }
                });
            }
        });
    }
}
