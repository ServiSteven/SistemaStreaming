import modelo.Contenido;
import modelo.Usuario;
import estructuras.TablaHash;

/**
 * Clase principal para probar las estructuras y modelos
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DEL SISTEMA DE STREAMING ===\n");

        // ===== PRUEBA 1: Tabla Hash con Contenidos =====
        System.out.println("--- PRUEBA 1: Tabla Hash de Contenidos ---");
        TablaHash<String, Contenido> catalogoContenidos = new TablaHash<>();

        // Crear algunos contenidos
        Contenido pelicula1 = new Contenido("C001", "Matrix", 1999, 136);
        pelicula1.agregarGenero("Ciencia Ficción");
        pelicula1.agregarGenero("Acción");

        Contenido pelicula2 = new Contenido("C002", "El Padrino", 1972, 175);
        pelicula2.agregarGenero("Drama");
        pelicula2.agregarGenero("Crimen");

        Contenido pelicula3 = new Contenido("C003", "Toy Story", 1995, 81);
        pelicula3.agregarGenero("Animación");
        pelicula3.agregarGenero("Aventura");
        pelicula3.agregarGenero("Comedia");

        // Insertar en la tabla hash
        catalogoContenidos.insertar(pelicula1.getId(), pelicula1);
        catalogoContenidos.insertar(pelicula2.getId(), pelicula2);
        catalogoContenidos.insertar(pelicula3.getId(), pelicula3);

        System.out.println("Contenidos registrados: " + catalogoContenidos.tamanio());
        System.out.println();

        // Buscar contenido por ID
        System.out.println("--- Buscar contenido por ID ---");
        Contenido buscado = catalogoContenidos.buscar("C002");
        if (buscado != null) {
            System.out.println("Encontrado: " + buscado);
        }
        System.out.println();

        // ===== PRUEBA 2: Sistema de Calificaciones =====
        System.out.println("--- PRUEBA 2: Sistema de Calificaciones (1-5 estrellas) ---");
        pelicula1.registrarCalificacion(5); // Usuario 1 da 5 estrellas
        pelicula1.registrarCalificacion(4); // Usuario 2 da 4 estrellas
        pelicula1.registrarCalificacion(5); // Usuario 3 da 5 estrellas
        pelicula1.registrarCalificacion(3); // Usuario 4 da 3 estrellas

        System.out.println(pelicula1);
        System.out.println();

        // ===== PRUEBA 3: Sistema de Reproducciones =====
        System.out.println("--- PRUEBA 3: Sistema de Reproducciones ---");
        pelicula1.registrarReproduccion();
        pelicula1.registrarReproduccion();
        pelicula1.registrarReproduccion();

        pelicula2.registrarReproduccion();

        pelicula3.registrarReproduccion();
        pelicula3.registrarReproduccion();

        System.out.println(pelicula1);
        System.out.println(pelicula2);
        System.out.println(pelicula3);
        System.out.println();

        // ===== PRUEBA 4: Usuarios con Gustos =====
        System.out.println("--- PRUEBA 4: Usuarios y sus Gustos ---");
        Usuario usuario1 = new Usuario("U001", "Juan Pérez");
        usuario1.agregarGusto("Acción");
        usuario1.agregarGusto("Ciencia Ficción");
        usuario1.agregarGusto("Aventura");

        Usuario usuario2 = new Usuario("U002", "María López");
        usuario2.agregarGusto("Drama");
        usuario2.agregarGusto("Romance");

        System.out.println(usuario1);
        System.out.println(usuario2);
        System.out.println();

        // ===== PRUEBA 5: Actualizar datos =====
        System.out.println("--- PRUEBA 5: Actualizar datos en Tabla Hash ---");
        Contenido peliculaActualizada = new Contenido("C001", "The Matrix Reloaded", 2003, 138);
        peliculaActualizada.agregarGenero("Ciencia Ficción");
        catalogoContenidos.insertar("C001", peliculaActualizada); // Actualiza el contenido existente

        Contenido verificar = catalogoContenidos.buscar("C001");
        System.out.println("Contenido actualizado: " + verificar);
        System.out.println();

        // ===== PRUEBA 6: Verificar existencia =====
        System.out.println("--- PRUEBA 6: Verificar si existe contenido ---");
        System.out.println("¿Existe C002? " + catalogoContenidos.contiene("C002"));
        System.out.println("¿Existe C999? " + catalogoContenidos.contiene("C999"));
        System.out.println();

        System.out.println("=== FIN DE LAS PRUEBAS ===");
    }
}