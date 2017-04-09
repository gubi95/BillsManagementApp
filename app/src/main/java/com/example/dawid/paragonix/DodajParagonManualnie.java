package com.example.dawid.paragonix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class DodajParagonManualnie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_paragon_manualnie);
    }

    // Metoda wywoływana po naciśnięciu przycisku "Dalej"
    public void przejdzDalej(View view) {
        String shop_name =((EditText)findViewById(R.id.editText)).getText().toString();
        String sum =((EditText)findViewById(R.id.editText2)).getText().toString();

        // dodanie nazwy sklepu i ceny do BD

        Intent intent = new Intent(this, DodajParagonManualnie2.class);
        startActivity(intent);
    }
}
