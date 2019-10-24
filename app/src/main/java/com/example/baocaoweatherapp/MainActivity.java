package com.example.baocaoweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView tv_cloud, tv_thanhpho, tv_ngay, tv_trangthai, tv_nhietdo, tv_nhietdomin, tv_nhietdomax, tv_sunrise, tv_sunset, tv_wind, tv_presuare, tv_water;
    String city = "Ha noi";
    ArrayList<ThoiTiet> listThoiTiet = new ArrayList<>();
    ShopAdapter shopAdapter;
    String temp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        initRecyclerView();
        getCurentDataWeather(city);
        getdata7day(city);
        Log.d("kiemtra", "TP: " + city);
    }

    public void anhxa() {
        tv_ngay = (TextView) findViewById(R.id.textview_ngay);
        tv_nhietdo = (TextView) findViewById(R.id.textview_nhietdo);
        tv_nhietdomax = (TextView) findViewById(R.id.textview_maxTemp);
        tv_nhietdomin = (TextView) findViewById(R.id.textview_minTemp);
        tv_presuare = (TextView) findViewById(R.id.textview_presure);
        tv_sunrise = (TextView) findViewById(R.id.textview_sunrise);
        tv_sunset = (TextView) findViewById(R.id.textview_sunset);
        tv_thanhpho = (TextView) findViewById(R.id.textview_thanhPho);
        tv_trangthai = (TextView) findViewById(R.id.textview_trangthai);
        tv_water = (TextView) findViewById(R.id.textview_water);
        tv_wind = (TextView) findViewById(R.id.textview_win);
        tv_cloud = (TextView) findViewById(R.id.textview_cloud);
    }

    public void getCurentDataWeather(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&cnt=7&appid=92aef109f7cdfdf09cc609d3e0f4f659";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("ketqua", response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject sys = jsonObject.getJSONObject("sys");
                    JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = jsonObject.getJSONObject("main");
                    JSONObject wind = jsonObject.getJSONObject("wind");
                    JSONObject cloud = jsonObject.getJSONObject("clouds");

                    String day = jsonObject.getString("dt");
                    Long l = Long.valueOf(day);

                    String ngay = "Update at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(l * 1000));
                    tv_ngay.setText(ngay);

                    String name = jsonObject.getString("name") + ", " + sys.getString("country");
                    tv_thanhpho.setText(name);

                    String mota = weather.getString("description");
                    tv_trangthai.setText(mota);

                    String nhietdo = main.getString("temp") + "°C";
                    tv_nhietdo.setText(nhietdo);
                    String nhietdomin = "Min temp: " + main.getString("temp_min") + "°C";
                    tv_nhietdomin.setText(nhietdomin);
                    String nhietdomax = "Max temp: " + main.getString("temp_max") + "°C";
                    tv_nhietdomax.setText(nhietdomax);

                    String presuare = main.getString("pressure");
                    tv_presuare.setText(presuare);

                    String nuoc = main.getString("humidity") + "%";
                    tv_water.setText(nuoc);

                    Long sunrise = sys.getLong("sunrise");
                    tv_sunrise.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                    Long sunset = sys.getLong("sunset");
                    tv_sunset.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));

                    String gio = wind.getString("speed");
                    tv_wind.setText(gio);

                    String may = cloud.getString("all");
                    tv_cloud.setText(may);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);


    }

    public void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvList);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        shopAdapter = new ShopAdapter(getApplicationContext(), listThoiTiet);
        recyclerView.setAdapter(shopAdapter);

    }

    private void getdata7day(final String city) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&units=metric&appid=92aef109f7cdfdf09cc609d3e0f4f659";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArrayList.length(); i++) {

                        JSONObject jsonObjectlist = jsonArrayList.getJSONObject(i);

                        String ngay = jsonObjectlist.getString("dt");
                        long l = Long.valueOf(ngay);
//                        Log.d("ngay1", "Long: " + ngay);
//                        Date date = new Date(l * 1000L);
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE yyyy-MM-dd");
//                        String Day = simpleDateFormat.format(date);
                        String Day = new SimpleDateFormat("EEE dd/MM hh:mm a", Locale.ENGLISH).format(new Date(l * 1000));


                        JSONObject jsonObjectMain = jsonObjectlist.getJSONObject("main");
                        String max = jsonObjectMain.getString("temp_max");
                        String min = jsonObjectMain.getString("temp_min");
                        Double a = Double.valueOf(max);
                        String NhietDoMax = String.valueOf(a.intValue());
                        Double b = Double.valueOf(min);
                        String NhietDoMin = String.valueOf(b.intValue());

                        JSONArray jsonArrayWeather = jsonObjectlist.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        String status = jsonObjectWeather.getString("description");
                        String icon = jsonObjectWeather.getString("icon");
                        listThoiTiet.add(new ThoiTiet(Day, status, icon, NhietDoMax, NhietDoMin));
                    }
                    shopAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_them:
                showDialog();
                Log.d("kiemtra", "sao khong hien");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.my_dialog);
        dialog.show();
        dialog.setCancelable(false);

        final EditText ed_search;
        final Button bt_search, bt_exits;
        ed_search = (EditText) dialog.findViewById(R.id.edit_cityName);
        bt_exits = (Button) dialog.findViewById(R.id.button_exit);
        bt_search = (Button) dialog.findViewById(R.id.button_search);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = ed_search.getText().toString().trim();
                Log.d("kiemtra", "thanh pho " + city);
                if (city.length() > 0) {
                    getCurentDataWeather(city);
                    getdata7day(city);
                    dialog.cancel();
                }
                else {
                    Toast.makeText(MainActivity.this,"Please Enter City Name!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_exits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }
}
