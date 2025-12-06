package modelo;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase que representa un usuario de la plataforma
 */
public class Usuario {
    private String id;
    private String nombre;
    private Set<String> gustos; // Conjunto de géneros favoritos

    public Usuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.gustos = new HashSet<>();
    }

    // Agregar un género a los gustos del usuario
    public void agregarGusto(String genero) {
        this.gustos.add(genero);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<String> getGustos() {
        return gustos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Nombre: %s | Gustos: %s", id, nombre, gustos);
    }
}