package com.example.dawid.paragonix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DodajParagonManualnie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_paragon_manualnie);
    }

    // Metoda wywoływana po naciśnięciu przycisku "Dalej"
    public void przejdzDalej(View view) {
        Intent intent = new Intent(this, DodajParagonManualnie2.class);
        startActivity(intent);
    }
}
