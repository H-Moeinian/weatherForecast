package com.example.weatherforecast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherforecast.todayWeatherForecast.OpenWeather;
import com.example.weatherforecast.todayWeatherForecast.Weather;
import com.example.weatherforecast.weeklyWeatherForecast.LList;
import com.example.weatherforecast.weeklyWeatherForecast.OpenWeeklyWeather;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;



public class MainActivity extends AppCompatActivity {

    int dayOfWeek;
    OpenWeather openWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button btnSelectCity = findViewById(R.id.btnSelectCity);
        btnSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SelectCityActivity.class);
                startActivityForResult(intent,1234);
                DrawerLayout navDrawer = findViewById(R.id.drawer);
                  navDrawer.closeDrawer(Gravity.LEFT);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==1234)
            if(resultCode ==Activity.RESULT_OK){
                String nameOfCity = data.getStringExtra("city");

                AsyncHttpClient client = new AsyncHttpClient() ;
                client.get("https://api.openweathermap.org/data/2.5/weather?q="+nameOfCity+",ir&APPID=67cc986c870226bd537104e7ec1a5cb8&units=metric", new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        System.out.println(response.toString());
                        Gson gson = new Gson();
                        openWeather = gson.fromJson(response.toString(),OpenWeather.class);
                        List<Weather> weather =  openWeather.getWeather();
                        String description = weather.get(0).getDescription();
                        double temparature = openWeather.getMain().getTemp();
                        int humidity = openWeather.getMain().getHumidity();
                        double windSpeed = openWeather.getWind().getSpeed();
                        String cityName = openWeather.getName();

                        //Unix seconds
                        long unix_seconds = openWeather.getDt();
                        //convert seconds to milliseconds
                        Date date = new Date(unix_seconds*1000L);
                        // format of the date
                        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        jdf.setTimeZone(TimeZone.getTimeZone("GMT+4:30"));
                        String java_date = jdf.format(date);
                        java_date = java_date.substring(0,10);
                        Calendar calendar = jdf.getCalendar();
                        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                        int month = calendar.get(Calendar.MONTH);
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                        String monthName;

                        switch (month){
                            case 0:
                                monthName ="January";
                                break;
                            case 1:
                                monthName ="February";
                                break;
                            case 2:
                                monthName ="March";
                                break;
                            case 3:
                                monthName ="April";
                                break;
                            case 4:
                                monthName ="May";
                                break;
                            case 5:
                                monthName ="June";
                                break;
                            case 6:
                                monthName ="July";
                                break;
                            case 7:
                                monthName ="August";
                                break;
                            case 8:
                                monthName ="September";
                                break;
                            case 9:
                                monthName ="October";
                                break;
                            case 10:
                                monthName ="November";
                                break;
                            case 11:
                                monthName ="December";
                                break;

                            default:
                                monthName ="Unknown";

                        }

                        String weekDayName;

                        switch (dayOfWeek){
                            case 1:
                                weekDayName ="Sun";
                                break;
                            case 2:
                                weekDayName ="Mon";
                                break;
                            case 3:
                                weekDayName ="Tue";
                                break;
                            case 4:
                                weekDayName ="Wed";
                                break;
                            case 5:
                                weekDayName ="Thu";
                                break;
                            case 6:
                                weekDayName ="Fri";
                                break;
                            case 7:
                                weekDayName ="Sat";
                                break;

                                default:
                                    weekDayName ="Unknown";
                                    break;

                        }

                        ImageView img =findViewById(R.id.img);
                        TextView txt1 = findViewById(R.id.txt1);
                        txt1.setText(description);
                        TextView txt2 = findViewById(R.id.txt2);
                        txt2.setText(""+Math.round(temparature)+"°C");
                        TextView txt3 = findViewById(R.id.txt3);
                        txt3.setText(humidity+"%");
                        TextView txt4 = findViewById(R.id.txt4);
                        txt4.setText(windSpeed+" m/s");
                        TextView txt5 = findViewById(R.id.txt5);
                        txt5.setText(cityName);
                        TextView txt6 = findViewById(R.id.txt6);
                        txt6.setText(weekDayName+", "+ monthName+" "+dayOfMonth);
                        TextView txtHumidity = findViewById(R.id.txtHumidity);
                        txtHumidity.setText("Humidity");
                        TextView txtWind = findViewById(R.id.txtWind);
                        txtWind.setText("Wind Speed");


                        switch (openWeather.getWeather().get(0).getId()){

                            case 200:
                            case 201:
                            case 202:
                            case 210:
                            case 211:
                            case 212:
                            case 221:
                            case 231:
                            case 232:
                                img.setImageResource(R.drawable.d11);
                                break;
                            case 300:
                            case 301:
                            case 302:
                            case 310:
                            case 311:
                            case 312:
                            case 313:
                            case 314:
                            case 321:
                            case 520:
                            case 521:
                            case 522:
                            case 531:
                                img.setImageResource(R.drawable.d09);
                                break;
                            case 500:
                            case 501:
                            case 502:
                            case 503:
                            case 504:
                                img.setImageResource(R.drawable.d10);
                                break;
                            case 511:
                            case 600:
                            case 601:
                            case 602:
                            case 611:
                            case 612:
                            case 613:
                            case 615:
                            case 616:
                            case 620:
                            case 621:
                            case 622:
                                img.setImageResource(R.drawable.d13);
                                break;
                            case 701:
                            case 711:
                            case 721:
                            case 731:
                            case 741:
                            case 751:
                            case 761:
                            case 762:
                            case 771:
                            case 781:
                                img.setImageResource(R.drawable.d50);
                                break;
                            case 800:
                                img.setImageResource(R.drawable.clearsky);
                                break;
                            case 801:
                                img.setImageResource(R.drawable.d02);
                                break;
                            case 802:
                                img.setImageResource(R.drawable.d03);
                                break;
                            case 803:
                            case 804:
                                img.setImageResource(R.drawable.d04);
                                break;

                    }
                    if(openWeather.getDt()>openWeather.getSys().getSunset() ||
                    openWeather.getDt()<openWeather.getSys().getSunrise()) {
                        if(openWeather.getWeather().get(0).getId()==800)
                            img.setImageResource(R.drawable.n01);
                        else if(openWeather.getWeather().get(0).getId()==801)
                            img.setImageResource(R.drawable.n02);
                        else if(openWeather.getWeather().get(0).getId()==500 ||
                                openWeather.getWeather().get(0).getId()==501 ||
                                openWeather.getWeather().get(0).getId()==502 ||
                                openWeather.getWeather().get(0).getId()==503 ||
                                openWeather.getWeather().get(0).getId()==504)
                            img.setImageResource(R.drawable.n10);



                    }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });


                final RecyclerView recyclerView = findViewById(R.id.recycler);
                AsyncHttpClient client1 = new AsyncHttpClient();
                client1.get("https://api.openweathermap.org/data/2.5/forecast?q=" + nameOfCity + ",ir&APPID=67cc986c870226bd537104e7ec1a5cb8&units=metric", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        System.out.println(response.toString());
                        Gson gson = new Gson();
                        OpenWeeklyWeather yahooWeather = gson.fromJson(response.toString(), OpenWeeklyWeather.class);

                        int sunRise = yahooWeather.getCity().getSunrise();
                        int sunSet = yahooWeather.getCity().getSunset();
                        List<LList> list = yahooWeather.getList();
                        List<Integer> dt = new ArrayList<>();
                        List<Double> temp = new ArrayList<>();
                        List<String> ID = new ArrayList<>();
                        String s = list.get(0).getDtTxt().substring(0, 10);
                        String ss = list.get(0).getDtTxt().substring(11, 12);
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getDtTxt().substring(0, 10).equals(s) || !list.get(i).getDtTxt().substring(11, 12).equals(ss))
                                continue;
                            else {
                                s = list.get(i).getDtTxt().substring(0, 10);
                                temp.add(list.get(i).getMain().getTemp());
                               ID.add((list.get(i).getWeather().get(0).getId()).toString());
                               dt.add(list.get(i).getDt());

                            }
                        }
                        RecyclerAdapter adapter = new RecyclerAdapter(temp,dayOfWeek,ID,openWeather.getSys().getSunrise(),openWeather.getSys().getSunset(),openWeather.getDt());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                                RecyclerView.HORIZONTAL, false));

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            }
    }
}
