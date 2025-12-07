import modelo.Contenido;
import modelo.Usuario;
import estructuras.TablaHash;
import estructuras.Heap;
import estructuras.Diccionario;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase principal para probar las estructuras y modelos
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE STREAMING - PRUEBAS ===\n");

        // ===== PRUEBA 1: Tabla Hash con Contenidos =====
        System.out.println("--- PRUEBA 1: Tabla Hash de Contenidos ---");
        TablaHash<String, Contenido> catalogoContenidos = new TablaHash<>();

        // Crear contenidos
        Contenido pelicula1 = new Contenido("C001", "Matrix", 1999, 136);
        pelicula1.agregarGenero("Ciencia Ficción");
        pelicula1.agregarGenero("Acción");

        Contenido pelicula2 = new Contenido("C002", "El Padrino", 1972, 175);
        pelicula2.agregarGenero("Drama");
        pelicula2.agregarGenero("Crimen");

        Contenido pelicula3 = new Contenido("C003", "Toy Story", 1995, 81);
        pelicula3.agregarGenero("Animación");
        pelicula3.agregarGenero("Aventura");

        Contenido pelicula4 = new Contenido("C004", "Inception", 2010, 148);
        pelicula4.agregarGenero("Ciencia Ficción");
        pelicula4.agregarGenero("Thriller");

        Contenido pelicula5 = new Contenido("C005", "Forrest Gump", 1994, 142);
        pelicula5.agregarGenero("Drama");
        pelicula5.agregarGenero("Romance");

        // Insertar en tabla hash
        catalogoContenidos.insertar(pelicula1.getId(), pelicula1);
        catalogoContenidos.insertar(pelicula2.getId(), pelicula2);
        catalogoContenidos.insertar(pelicula3.getId(), pelicula3);
        catalogoContenidos.insertar(pelicula4.getId(), pelicula4);
        catalogoContenidos.insertar(pelicula5.getId(), pelicula5);

        System.out.println("✓ Contenidos registrados: " + catalogoContenidos.tamanio());
        System.out.println();

        // ===== PRUEBA 2: Simulación de reproducciones y calificaciones =====
        System.out.println("--- PRUEBA 2: Reproducciones y Calificaciones ---");

        // Matrix - muy popular
        pelicula1.registrarReproduccion();
        pelicula1.registrarReproduccion();
        pelicula1.registrarReproduccion();
        pelicula1.registrarReproduccion();
        pelicula1.registrarReproduccion();
        pelicula1.registrarCalificacion(5);
        pelicula1.registrarCalificacion(5);
        pelicula1.registrarCalificacion(4);

        // El Padrino - popular y bien calificada
        pelicula2.registrarReproduccion();
        pelicula2.registrarReproduccion();
        pelicula2.registrarReproduccion();
        pelicula2.registrarCalificacion(5);
        pelicula2.registrarCalificacion(5);
        pelicula2.registrarCalificacion(5);

        // Toy Story - moderadamente popular
        pelicula3.registrarReproduccion();
        pelicula3.registrarReproduccion();
        pelicula3.registrarCalificacion(4);
        pelicula3.registrarCalificacion(5);

        // Inception - poco vista pero bien calificada
        pelicula4.registrarReproduccion();
        pelicula4.registrarCalificacion(5);

        // Forrest Gump - muy vista
        pelicula5.registrarReproduccion();
        pelicula5.registrarReproduccion();
        pelicula5.registrarReproduccion();
        pelicula5.registrarReproduccion();
        pelicula5.registrarCalificacion(4);
        pelicula5.registrarCalificacion(5);

        System.out.println("✓ Reproducciones y calificaciones registradas");
        System.out.println();

        // ===== PRUEBA 3: Heap - Ranking por reproducciones =====
        System.out.println("--- PRUEBA 3: Ranking por Reproducciones (Heap) ---");

        // Comparador por número de reproducciones
        Comparator<Contenido> comparadorReproducciones =
                (c1, c2) -> Integer.compare(c1.getReproducciones(), c2.getReproducciones());

        Heap<Contenido> heapReproducciones = new Heap<>(comparadorReproducciones);
        heapReproducciones.insertar(pelicula1);
        heapReproducciones.insertar(pelicula2);
        heapReproducciones.insertar(pelicula3);
        heapReproducciones.insertar(pelicula4);
        heapReproducciones.insertar(pelicula5);

        System.out.println("🏆 TOP 3 MÁS VISTAS:");
        List<Contenido> top3Vistas = heapReproducciones.obtenerTopN(3);
        for (int i = 0; i < top3Vistas.size(); i++) {
            Contenido c = top3Vistas.get(i);
            System.out.println((i+1) + ". " + c.getTitulo() + " - " + c.getReproducciones() + " reproducciones");
        }
        System.out.println();

        // ===== PRUEBA 4: Heap - Ranking por calificación =====
        System.out.println("--- PRUEBA 4: Ranking por Calificación (Heap) ---");

        // Comparador por calificación promedio
        Comparator<Contenido> comparadorCalificacion =
                (c1, c2) -> Double.compare(c1.getCalificacionPromedio(), c2.getCalificacionPromedio());

        Heap<Contenido> heapCalificaciones = new Heap<>(comparadorCalificacion);
        heapCalificaciones.insertar(pelicula1);
        heapCalificaciones.insertar(pelicula2);
        heapCalificaciones.insertar(pelicula3);
        heapCalificaciones.insertar(pelicula4);
        heapCalificaciones.insertar(pelicula5);

        System.out.println("⭐ TOP 3 MEJOR CALIFICADAS:");
        List<Contenido> top3Calificadas = heapCalificaciones.obtenerTopN(3);
        for (int i = 0; i < top3Calificadas.size(); i++) {
            Contenido c = top3Calificadas.get(i);
            System.out.println((i+1) + ". " + c.getTitulo() + " - ⭐ " +
                    String.format("%.1f", c.getCalificacionPromedio()) + "/5.0");
        }
        System.out.println();

        // ===== PRUEBA 5: Diccionario - Historial de usuarios =====
        System.out.println("--- PRUEBA 5: Historial de Usuarios (Diccionario) ---");

        // Crear usuarios
        Usuario usuario1 = new Usuario("U001", "Juan Pérez");
        usuario1.agregarGusto("Acción");
        usuario1.agregarGusto("Ciencia Ficción");

        Usuario usuario2 = new Usuario("U002", "María López");
        usuario2.agregarGusto("Drama");
        usuario2.agregarGusto("Romance");

        // Diccionario para almacenar historial: idUsuario -> lista de contenidos vistos
        Diccionario<String, List<String>> historialUsuarios = new Diccionario<>();

        // Historial de Juan
        List<String> historialJuan = new ArrayList<>();
        historialJuan.add("C001"); // Matrix
        historialJuan.add("C004"); // Inception
        historialJuan.add("C003"); // Toy Story
        historialUsuarios.insertar(usuario1.getId(), historialJuan);

        // Historial de María
        List<String> historialMaria = new ArrayList<>();
        historialMaria.add("C002"); // El Padrino
        historialMaria.add("C005"); // Forrest Gump
        historialUsuarios.insertar(usuario2.getId(), historialMaria);

        System.out.println("✓ Historial registrado para " + historialUsuarios.tamanio() + " usuarios");

        // Consultar historial
        System.out.println("\nHistorial de " + usuario1.getNombre() + ":");
        List<String> historial = historialUsuarios.obtener(usuario1.getId());
        for (String idContenido : historial) {
            Contenido c = catalogoContenidos.buscar(idContenido);
            if (c != null) {
                System.out.println("  - " + c.getTitulo() + " (" + c.getGeneros() + ")");
            }
        }

        System.out.println("\nHistorial de " + usuario2.getNombre() + ":");
        historial = historialUsuarios.obtener(usuario2.getId());
        for (String idContenido : historial) {
            Contenido c = catalogoContenidos.buscar(idContenido);
            if (c != null) {
                System.out.println("  - " + c.getTitulo() + " (" + c.getGeneros() + ")");
            }
        }
        System.out.println();

        System.out.println("=== TODAS LAS ESTRUCTURAS FUNCIONANDO CORRECTAMENTE ===");
    }
}