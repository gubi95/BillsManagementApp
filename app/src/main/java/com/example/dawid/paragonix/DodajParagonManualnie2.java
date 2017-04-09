package com.example.dawid.paragonix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DodajParagonManualnie2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_paragon_manualnie2);

    }

    // Metoda wywoływana po naciśnięciu przycisku "Zakończ dodawanie" - powrót do głównego menu
    public void dodajParagon(View view) {
        dodajProduktyDoBazy(view);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Metoda wywoływana po naciśnięciu przycisku "Dodaj następne" - powrót do głównego menu
    public void dodajProdukty(View view) {
        dodajProduktyDoBazy(view);
        Intent intent = new Intent(this, DodajParagonManualnie2.class);
        startActivity(intent);
    }

    public void dodajProduktyDoBazy(View view) {
        String product1 =((EditText)findViewById(R.id.editText3)).getText().toString();
        String price1 =((EditText)findViewById(R.id.editText4)).getText().toString();

        String product2 =((EditText)findViewById(R.id.editText7)).getText().toString();
        String price2 =((EditText)findViewById(R.id.editText12)).getText().toString();

        String product3 =((EditText)findViewById(R.id.editText10)).getText().toString();
        String price3 =((EditText)findViewById(R.id.editText9)).getText().toString();

        String product4 =((EditText)findViewById(R.id.editText13)).getText().toString();
        String price4 =((EditText)findViewById(R.id.editText15)).getText().toString();

        // dodanie produktów do bazy

        Intent intent = new Intent(this, DodajParagonManualnie2.class);
        startActivity(intent);
    }
}
