package com.example.dawid.paragonix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Metoda wywoływana po naciśnięciu przycisku "Dodaj paragon"
    public void dodajParagon(View view) {
        Intent intent = new Intent(this, DodajParagonManualnie.class);
        startActivity(intent);
    }

    public void addBillManually(View view) {
        Intent intent = new Intent(this, addBill.class);
        startActivity(intent);
    }
}
