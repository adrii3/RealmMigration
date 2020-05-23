package com.example.realmmigration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.realmmigration.model.Entrenador;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private RealmConfiguration realmConfiguration;
    private RealmResults<Entrenador> listaEntrenadores;

    private ListView listView;
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configuracionRealm();
        initData();
        query();
        addData();
        mostrarEnLista();
    }

    private void mostrarEnLista() {
        listView = findViewById(R.id.list_item);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
    }

    private void addData() {
        for (Entrenador entrenador : listaEntrenadores){
            data.add(entrenador.toString());
        }
    }

    private void query() {
        listaEntrenadores = realm.where(Entrenador.class).findAll();
    }

    private void initData() {
        Entrenador entrenador = null;

        realm.beginTransaction();

        entrenador = new Entrenador("Quique", "Setién", 53);
        realm.copyToRealm(entrenador);

        entrenador = new Entrenador("Pep", "Guardiola", 48);
        realm.copyToRealm(entrenador);

        entrenador = new Entrenador("Zinedine", "Zidane", 89);
        realm.copyToRealm(entrenador);

        realm.commitTransaction();
    }

    private void configuracionRealm() {

        Realm.init(getApplicationContext());
        realmConfiguration = new RealmConfiguration.Builder()
                .name("Migration.Realm")
                //.schemaVersion(1)
                //.migration(new Migration())
                .build();
        realm = Realm.getInstance(realmConfiguration);
    }
}
