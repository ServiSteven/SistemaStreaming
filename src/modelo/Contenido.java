package modelo;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase que representa un contenido (película o serie) en la plataforma
 */
public class Contenido {
    private String id;
    private String titulo;
    private int anio;
    private Set<String> generos; // Conjunto de géneros
    private int duracion; // En minutos
    private int reproducciones;
    private double calificacionPromedio;
    private int totalCalificaciones;

    public Contenido(String id, String titulo, int anio, int duracion) {
        this.id = id;
        this.titulo = titulo;
        this.anio = anio;
        this.duracion = duracion;
        this.generos = new HashSet<>();
        this.reproducciones = 0;
        this.calificacionPromedio = 0.0;
        this.totalCalificaciones = 0;
    }

    // Agregar un género al conjunto
    public void agregarGenero(String genero) {
        this.generos.add(genero);
    }

    // Registrar una reproducción
    public void registrarReproduccion() {
        this.reproducciones++;
    }

    /**
     * Registrar una calificación del usuario (sistema de 1 a 5 estrellas)
     * @param calificacion valor entre 1 y 5
     */
    public void registrarCalificacion(int calificacion) {
        if (calificacion < 1 || calificacion > 5) {
            System.out.println("Error: La calificación debe ser entre 1 y 5 estrellas");
            return;
        }

        // Calcular nuevo promedio
        double sumaTotal = calificacionPromedio * totalCalificaciones;
        totalCalificaciones++;
        calificacionPromedio = (sumaTotal + calificacion) / totalCalificaciones;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAnio() {
        return anio;
    }

    public Set<String> getGeneros() {
        return generos;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getReproducciones() {
        return reproducciones;
    }

    public double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public int getTotalCalificaciones() {
        return totalCalificaciones;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | %s (%d) | Géneros: %s | Reproducciones: %d | Calificación: %.1f",
                id, titulo, anio, generos, reproducciones, calificacionPromedio);
    }
}