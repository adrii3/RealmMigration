package com.example.realmmigration;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {
    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if(oldVersion == 0){
            RealmObjectSchema entrenadorSchema =  schema.get("Entrenador");

            entrenadorSchema.addField("nombreCompleto", String.class, FieldAttribute.REQUIRED)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("nombreCompleto",obj.getString("nombre") + " " + obj.getString("apellido"));
                        }
                    }).addField("email", String.class)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("email", obj.getString("nombre").toLowerCase() + obj.getString("apellido").toLowerCase() + "@gmail.com");
                        }
                    }).removeField("nombre")
                    .removeField("apellido");
            oldVersion++;
        }

        if(oldVersion == 1){
            RealmObjectSchema jugadorSchema = schema.create("Jugador")
                    .addField("nombreCompletoJ", String.class, FieldAttribute.REQUIRED)
                    .addField("posicion", String.class, FieldAttribute.REQUIRED);

            schema.get("Entrenador")
                    .addRealmListField("jugadores", jugadorSchema)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            if(obj.getString("nombreCompleto").equals("Zinedine Zidane")){
                                DynamicRealmObject jugador = realm.createObject("Jugador");
                                jugador.setString("nombre","Gareth Bale");
                                jugador.setString("posicion","EI");
                                obj.getList("jugadores").add(jugador);
                            }else if(obj.getString("nombreCompleto").equals("Pep Guardiola")){
                                DynamicRealmObject jugador = realm.createObject("Jugador");
                                jugador.setString("nombre","Raheem Sterling");
                                jugador.setString("posicion","EI");
                                obj.getList("jugadores").add(jugador);
                            }
                        }
                    });
            oldVersion++;
        }
    }
}

