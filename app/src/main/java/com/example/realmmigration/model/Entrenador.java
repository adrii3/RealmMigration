package com.example.realmmigration.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Entrenador extends RealmObject {

    @Required
    private String nombre;
    private String apellido;
    private int edad;

    public Entrenador(String nombre, String apellido, int edad){
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public Entrenador(){

    }
}
