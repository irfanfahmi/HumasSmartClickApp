package com.example.irfanfahmiwijaya.humassmartclickapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.irfanfahmiwijaya.humassmartclickapp.Adapter.GalleryAdapter;
import com.example.irfanfahmiwijaya.humassmartclickapp.Adapter.MySingleton;
import com.example.irfanfahmiwijaya.humassmartclickapp.Model.GalleryKegiatan;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

public class modul_galeri extends AppCompatActivity {
    private RecyclerView rview ;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modul_galeri);

        toolbar = (Toolbar) findViewById(R.id.toolbarig);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        rview = findViewById(R.id.recylcerViewg);
        rview.setHasFixedSize(true);

        final GridLayoutManager manager = new GridLayoutManager(this,3);
        rview.setLayoutManager(manager);

        String url = "http://appro.probolinggokab.go.id/adminhumas/tampil_foto_kegiatan.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<GalleryKegiatan> kegiatangallerylist = new JsonConverter<GalleryKegiatan>().toArrayList(response, GalleryKegiatan.class);

                GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), kegiatangallerylist);
                rview.setLayoutManager(manager);
                rview.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),
                            "Koneksi Timeout , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(),
                            "Server Mengalami Masalah , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "Jaringan Mengalami Masalah, Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Error , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                }

            }
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }
}
