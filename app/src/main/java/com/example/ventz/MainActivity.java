package com.example.ventz;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ventz.databinding.ActivityMainBinding;
import com.example.ventz.model.Dados;
import com.example.ventz.model.Deck;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//         EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//         binding = ActivityMainBinding.inflate(getLayoutInflater());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.main;
        setContentView(view);

        replaceFragment(new HomeFragment());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
        }, 0);


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    replaceFragment(new HomeFragment());
                }, 0);

            } else if (itemId == R.id.profile) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    replaceFragment(new ProfileFragment());
                }, 0);
            } else if (itemId == R.id.decks) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    replaceFragment(new DecksFragment());
                }, 0);
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }
}