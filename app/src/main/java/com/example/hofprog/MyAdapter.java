package com.example.hofprog;

import static com.example.hofprog.AdapterTask.dat;
import static com.example.hofprog.AdapterTask.opisanie;
import static com.example.hofprog.MainActivity.ni;
import static com.example.hofprog.ex.f;
import static com.example.hofprog.ForMan.nick;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hofprog.model.proger;
import com.example.hofprog.model.task;
import com.example.hofprog.retrofit.ProgerApi;
import com.example.hofprog.retrofit.RetrofitServis;
import com.example.hofprog.retrofit.TaskApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final ArrayList<String> st;
    private final String flag;
    private final Context context;
    public MyAdapter(Context context, ArrayList<String> st, String flag) {
        this.context = context;
        this.st = st;
        this.flag = flag;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_progger, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int y = position;
        holder.ordinalNumberTextView.setText(String.valueOf(position + 1));
        RetrofitServis retrofitServis = new RetrofitServis();
        ProgerApi progerApi = retrofitServis.getRetrofit().create(ProgerApi.class);
        progerApi.getAllprogger().enqueue(new Callback<List<proger>>() {
            @Override
            public void onResponse(Call<List<proger>> call, Response<List<proger>> response) {
                List<proger> prr = response.body();
                assert prr != null;
                for (proger p : prr) {
                    if (st.size() > 0 && Objects.equals(p.getNick(), st.get(y))) {
                        holder.ordinalNumberTextView.setText(String.valueOf(y + 1) + ". " + p.getName() + " " + p.getFam());
                        ArrayList<String> trr = new ArrayList<>(Arrays.asList(p.getExam().split(" ")));
                        while (trr.size() > 0 && Objects.equals(trr.get(0), "")) trr.remove(0);
                        holder.itemContentTextView.setText(trr.size() + " задач");
                    }

                }
            }

            @Override
            public void onFailure(Call<List<proger>> call, Throwable t) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(flag+ "    flagg");
                if (!Objects.equals(flag, null)) {
                    progerApi.getAllprogger().enqueue(new Callback<List<proger>>() {
                        @Override
                        public void onResponse(Call<List<proger>> call, Response<List<proger>> response) {
                            List<proger> tsk = response.body();
                            for (proger m : tsk) {

                                if (Objects.equals(m.getNick(), st.get(y))) {
                                    System.out.println(st.get(y)+"  st.get(y)pers");
                                    String a = m.getExam();
                                    if (m.getExam().length() != 0) a += " ";
                                    proger mm = new proger(m.getId(), m.getNick(), m.getName(), m.getFam(), m.getPochta(), m.getPassword(), m.getTel(), a + flag, m.getBadex());
                                    progerApi.update(m.getId(), mm).enqueue(new Callback<proger>() {
                                        @Override
                                        public void onResponse(Call<proger> call, Response<proger> response) {
                                        }

                                        @Override
                                        public void onFailure(Call<proger> call, Throwable t) {
                                        }
                                    });
                                    RetrofitServis retrofitServis1 = new RetrofitServis();
                                    TaskApi taskApi = retrofitServis1.getRetrofit().create(TaskApi.class);
                                    taskApi.getAlltask().enqueue(new Callback<List<task>>() {
                                        @Override
                                        public void onResponse(Call<List<task>> call, Response<List<task>> response) {
                                            List<task> tsk = response.body();
                                            for (task m: tsk)
                                                if (Objects.equals(m.getNick(), ni) && Objects.equals(m.getNam(), flag)) {
                                                    System.out.println(flag);
                                                    task mm = new task(m.getId(), m.getNick(), 1, m.getNam(), m.getOpis(), m.getSroki());
                                                    taskApi.update(m.getId(), mm).enqueue(new Callback<task>() {
                                                        @Override
                                                        public void onResponse(Call<task> call, Response<task> response) {
                                                        }

                                                        @Override
                                                        public void onFailure(Call<task> call, Throwable t) {
                                                        }
                                                    });
                                                    st.remove(y);
                                                    dat.remove(y);
                                                    opisanie.remove(y);
                                                }
                                        }

                                        @Override
                                        public void onFailure(Call<List<task>> call, Throwable t) {

                                        }
                                    });
                                    f = true;
                                    System.out.println(f+"  fla");
                                    Intent intent = new Intent(context, ForMan.class);
                                    context.startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<proger>> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return st.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ordinalNumberTextView;
        TextView itemContentTextView;
        ImageView itemImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ordinalNumberTextView = itemView.findViewById(R.id.user_name_text);
            itemContentTextView = itemView.findViewById(R.id.user_points_text);
            itemImageView = itemView.findViewById(R.id.user_avatar_image);
        }
    }
}