package com.example.bookapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bookapp.R;
import com.example.bookapp.databinding.ActivitySuccessFogotBinding;

public class SuccessFogotActivity extends AppCompatActivity {

    private ActivitySuccessFogotBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessFogotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.successBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuccessFogotActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}