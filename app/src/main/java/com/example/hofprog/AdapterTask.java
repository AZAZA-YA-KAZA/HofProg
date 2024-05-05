package com.example.hofprog;

import static com.example.hofprog.MainActivity.ni;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hofprog.model.whoi;
import com.example.hofprog.retrofit.RetrofitServis;
import com.example.hofprog.retrofit.WhoiApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.ViewHolder> {
    static ArrayList<String> dat;
    static ArrayList<String> st;
    static ArrayList<String> opisanie;
    private final Context context;
    public AdapterTask(Context context, ArrayList<String> st, ArrayList<String> dat, ArrayList<String> opisanie) {
        this.context = context;
        this.st = st;
        this.dat = dat;
        this.opisanie = opisanie;
    }
    @NonNull
    @Override
    public AdapterTask.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_ex, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTask.ViewHolder holder, int position) {
        int y = position;
        final boolean[] f = {true};
        RetrofitServis retrofitServis = new RetrofitServis();
        WhoiApi whoiApi = retrofitServis.getRetrofit().create(WhoiApi.class);
        whoiApi.getAllwhoi().enqueue(new Callback<List<whoi>>() {
            @Override
            public void onResponse(Call<List<whoi>> call, Response<List<whoi>> response) {
                List<whoi> wh = response.body();
                for (whoi i : wh)
                    if (Objects.equals(i.getNick(), ni)) {
                        System.out.println(i.getProg());
                        if (i.getProg() == 1)
                            f[0] = false;
                    }
                System.out.println(f[0] + "  ff");
                holder.ordinalNumberTextView.setText(String.valueOf(y + 1) + ". " + st.get(y));
                System.out.println(Arrays.toString(dat.toArray()));
                if (f[0]) {
                    if (st.size() > 0 && !Objects.equals(st.get(0), "") && Objects.equals(dat.get(y).split(" ")[1], "0")) {
                        System.out.println(String.valueOf(y + 1) + ". " + st.get(y));
                        holder.itemContentTextView.setText(String.valueOf(dat.get(y).split(" ")[0]));
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
                        System.out.println(y + "position " + diffInDays);
                        if (diffInDays < 0) {
                            holder.itemImageView.setBackgroundColor(Color.BLACK);
                        } else if (diffInDays < 7) {
                            holder.itemImageView.setBackgroundColor(Color.RED);
                        } else if (diffInDays < 14) {
                            holder.itemImageView.setBackgroundColor(Color.YELLOW);
                        } else {
                            holder.itemImageView.setBackgroundColor(Color.GREEN);
                        }
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, Opis_task.class);
                            System.out.println(y + "  yy1");
                            intent.putExtra("y", y);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    if (st.size() > 0 && !Objects.equals(st.get(0), "")/* && Objects.equals(dat.get(y).split(" ")[1], "1")*/) {
                        System.out.println("poiu");
                        System.out.println(String.valueOf(y + 1) + ". " + st.get(y));
                        holder.itemContentTextView.setText(String.valueOf(dat.get(y).split(" ")[0]));
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
                        System.out.println(y + "position " + diffInDays);
                        if (diffInDays < 0) {
                            holder.itemImageView.setBackgroundColor(Color.BLACK);
                        } else if (diffInDays < 7) {
                            holder.itemImageView.setBackgroundColor(Color.RED);
                        } else if (diffInDays < 14) {
                            holder.itemImageView.setBackgroundColor(Color.YELLOW);
                        } else {
                            holder.itemImageView.setBackgroundColor(Color.GREEN);
                        }

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, Opis_task.class);
                                System.out.println(y + "  yy1");
                                intent.putExtra("y", y);
                                context.startActivity(intent);
                            }
                        });
                    }//change
                }
            }

            @Override
            public void onFailure(Call<List<whoi>> call, Throwable t) {
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
            ordinalNumberTextView = itemView.findViewById(R.id.name);
            itemContentTextView = itemView.findViewById(R.id.value);
            itemImageView = itemView.findViewById(R.id.picture);
        }
    }
}