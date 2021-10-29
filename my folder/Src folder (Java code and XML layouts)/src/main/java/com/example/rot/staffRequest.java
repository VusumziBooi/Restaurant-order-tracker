package com.example.rot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.palettes.RangeColors;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class staffRequest extends AppCompatActivity {

    private String email,username;
    String id,rating,instances,thumbs;
    private TextView details,username_display,Rating;
    AnyChartView anyChartView;
    String[] choice = {"Thumbs Up","Thumbs Down"};
    int[] proportion = {0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_request);



        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        details = findViewById(R.id.details);
        Rating = findViewById(R.id.rating);
        doQueryId();
    }

    public void doQueryId(){

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("email",email)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/idRequest.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                if(response.isSuccessful()){
                    final String responseData = response.body().string();

                    staffRequest.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ProcessData(responseData);
                        }
                    });
                }

            }
        });


    }


    public void ProcessData(String json){
        try {

            JSONArray ja = new JSONArray(json);
            JSONObject jo = ja.getJSONObject(0);
            id = jo.getString("STAFF_ID");
            rating = jo.getString("AVERAGE_RATING");
            if(rating.equals("null")){
                rating = "0";
            }
            instances = jo.getString("NO_OF_ORDER_INSTANCES");
            if(instances.equals("null")){
                instances = "0";
            }
            thumbs = jo.getString("NO_OF_THUMBS_UP");
            if(thumbs.equals("null")){
                thumbs = "0";
            }

            String d1 = "Staff  id:"+"\t" + id + "\n\n";
            String d2 = "Average rating is:"+"\t" + rating + "\n\n";
            String d3 = "Number of order instances:"+"\t" + instances + "\n\n";
            String d4 = "Number of thumbs up:"+"\t" + thumbs + "\n\n";
            String display = d1+d2+d3+d4;

            if(instances.equals("0") == false){
                anyChartView = findViewById(R.id.any_chart_view);
                anyChartView.setVisibility(View.VISIBLE);
                int Thumbs_u = Integer.parseInt(thumbs);
                int Thumbs_d = Integer.parseInt(instances)-Integer.parseInt(thumbs);
                proportion[0] = Thumbs_u;
                proportion[1] = Thumbs_d;
                Pie pie = AnyChart.pie();
                List<DataEntry> dataEntries = new ArrayList<>();
                for(int i = 0;i < choice.length;i++){
                    dataEntries.add(new ValueDataEntry(choice[i],proportion[i]));
                }
                pie.data(dataEntries);
                RangeColors palette = RangeColors.instantiate();
                palette.items("#83EDC7", "#AEF3DA");
                pie.palette(palette);
                anyChartView.setChart(pie);

            }
            d1 = "Staff id is : " + id;
            SpannableString ss = new SpannableString(d1);
            StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
            ss.setSpan(boldspan,0,d1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            details.setText(ss);

            d2 = "Average rating is : "+rating + " %";
            SpannableString ss2 = new SpannableString(d2);
            StyleSpan boldspan2 = new StyleSpan(Typeface.BOLD);
            ss2.setSpan(boldspan2,0,d2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            Rating.setText(ss2);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}




/*
*
*
*
*
*
* */