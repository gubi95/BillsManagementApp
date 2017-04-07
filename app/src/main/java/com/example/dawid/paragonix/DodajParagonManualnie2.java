package com.example.dawid.paragonix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DodajParagonManualnie2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_paragon_manualnie2);

    }

    // Metoda wywoływana po naciśnięciu przycisku "Dodaj" - powrót do głównego menu
    public void dodajParagon(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
