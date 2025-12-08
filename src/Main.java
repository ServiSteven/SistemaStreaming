import sistema.SistemaStreaming;
import modelo.Contenido;
import modelo.Usuario;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Clase principal con menú interactivo
 */
public class Main {

    private static SistemaStreaming sistema = new SistemaStreaming();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        cargarDatosPrueba(); // Cargar datos iniciales para probar

        boolean salir = false;

        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    menuGestionUsuarios();
                    break;
                case 2:
                    menuGestionContenidos();
                    break;
                case 3:
                    menuReproduccionesCalificaciones();
                    break;
                case 4:
                    menuRecomendaciones();
                    break;
                case 5:
                    menuRanking();
                    break;
                case 6:
                    menuReportes();
                    break;
                case 0:
                    salir = true;
                    System.out.println("\n¡Gracias por usar el Sistema de Streaming!");
                    break;
                default:
                    System.out.println("\n❌ Opción inválida");
            }
        }

        scanner.close();
    }

    // ========== MENÚS ==========

    private static void mostrarMenuPrincipal() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║ SISTEMA DE STREAMING - MENÚ PRINCIPAL  ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Gestión de Usuarios");
        System.out.println("2. Gestión de Contenidos");
        System.out.println("3. Reproducciones y Calificaciones");
        System.out.println("4. Recomendaciones");
        System.out.println("5. Ranking");
        System.out.println("6. Reportes");
        System.out.println("0. Salir");
        System.out.println("═════════════════════════════════════════");
    }

    private static void menuGestionUsuarios() {
        System.out.println("\n--- GESTIÓN DE USUARIOS ---");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Consultar usuario");
        System.out.println("3. Agregar gusto a usuario");
        System.out.println("0. Volver");

        int opcion = leerEntero("Opción: ");

        switch (opcion) {
            case 1:
                registrarUsuario();
                break;
            case 2:
                consultarUsuario();
                break;
            case 3:
                agregarGustoUsuario();
                break;
        }
    }

    private static void menuGestionContenidos() {
        System.out.println("\n--- GESTIÓN DE CONTENIDOS ---");
        System.out.println("1. Registrar contenido");
        System.out.println("2. Consultar contenido");
        System.out.println("3. Agregar género a contenido");
        System.out.println("0. Volver");

        int opcion = leerEntero("Opción: ");

        switch (opcion) {
            case 1:
                registrarContenido();
                break;
            case 2:
                consultarContenido();
                break;
            case 3:
                agregarGeneroContenido();
                break;
        }
    }

    private static void menuReproduccionesCalificaciones() {
        System.out.println("\n--- REPRODUCCIONES Y CALIFICACIONES ---");
        System.out.println("1. Registrar reproducción");
        System.out.println("2. Registrar calificación");
        System.out.println("0. Volver");

        int opcion = leerEntero("Opción: ");

        switch (opcion) {
            case 1:
                registrarReproduccion();
                break;
            case 2:
                registrarCalificacion();
                break;
        }
    }

    private static void menuRecomendaciones() {
        System.out.println("\n--- RECOMENDACIONES ---");
        System.out.println("1. Recomendar contenidos a usuario");
        System.out.println("2. Encontrar contenidos similares");
        System.out.println("0. Volver");

        int opcion = leerEntero("Opción: ");

        switch (opcion) {
            case 1:
                recomendarContenidos();
                break;
            case 2:
                encontrarSimilares();
                break;
        }
    }

    private static void menuRanking() {
        System.out.println("\n--- RANKING ---");
        System.out.println("1. Top N más vistos");
        System.out.println("2. Top N mejor calificados");
        System.out.println("3. Menos vistos");
        System.out.println("0. Volver");

        int opcion = leerEntero("Opción: ");

        switch (opcion) {
            case 1:
                mostrarTopMasVistos();
                break;
            case 2:
                mostrarTopMejorCalificados();
                break;
            case 3:
                mostrarMenosVistos();
                break;
        }
    }

    private static void menuReportes() {
        System.out.println("\n--- REPORTES ---");
        System.out.println("1. Historial de usuario");
        System.out.println("2. Usuarios que vieron un contenido");
        System.out.println("3. Estadísticas del sistema");
        System.out.println("4. Contenidos por género");
        System.out.println("0. Volver");

        int opcion = leerEntero("Opción: ");

        switch (opcion) {
            case 1:
                mostrarHistorialUsuario();
                break;
            case 2:
                mostrarUsuariosQueVieron();
                break;
            case 3:
                mostrarEstadisticas();
                break;
            case 4:
                mostrarContenidosPorGenero();
                break;
        }
    }

    // ========== FUNCIONES DE GESTIÓN ==========

    private static void registrarUsuario() {
        System.out.println("\n--- Registrar Usuario ---");
        String id = leerTexto("ID del usuario: ");
        String nombre = leerTexto("Nombre: ");

        if (sistema.registrarUsuario(id, nombre)) {
            System.out.println("✓ Usuario registrado exitosamente");
        } else {
            System.out.println("❌ Error: El usuario ya existe");
        }
    }

    private static void consultarUsuario() {
        String id = leerTexto("ID del usuario: ");
        Usuario usuario = sistema.obtenerUsuario(id);

        if (usuario != null) {
            System.out.println("\n" + usuario);
        } else {
            System.out.println("❌ Usuario no encontrado");
        }
    }

    private static void agregarGustoUsuario() {
        String id = leerTexto("ID del usuario: ");
        String genero = leerTexto("Género favorito: ");

        if (sistema.agregarGustoUsuario(id, genero)) {
            System.out.println("✓ Gusto agregado exitosamente");
        } else {
            System.out.println("❌ Usuario no encontrado");
        }
    }

    private static void registrarContenido() {
        System.out.println("\n--- Registrar Contenido ---");
        String id = leerTexto("ID del contenido: ");
        String titulo = leerTexto("Título: ");
        int anio = leerEntero("Año: ");
        int duracion = leerEntero("Duración (minutos): ");

        if (sistema.registrarContenido(id, titulo, anio, duracion)) {
            System.out.println("✓ Contenido registrado exitosamente");
        } else {
            System.out.println("❌ Error: El contenido ya existe");
        }
    }

    private static void consultarContenido() {
        String id = leerTexto("ID del contenido: ");
        Contenido contenido = sistema.obtenerContenido(id);

        if (contenido != null) {
            System.out.println("\n" + contenido);
        } else {
            System.out.println("❌ Contenido no encontrado");
        }
    }

    private static void agregarGeneroContenido() {
        String id = leerTexto("ID del contenido: ");
        String genero = leerTexto("Género: ");

        if (sistema.agregarGeneroContenido(id, genero)) {
            System.out.println("✓ Género agregado exitosamente");
        } else {
            System.out.println("❌ Contenido no encontrado");
        }
    }

    private static void registrarReproduccion() {
        String idUsuario = leerTexto("ID del usuario: ");
        String idContenido = leerTexto("ID del contenido: ");

        if (sistema.registrarReproduccion(idUsuario, idContenido)) {
            System.out.println("✓ Reproducción registrada");
        } else {
            System.out.println("❌ Error: Usuario o contenido no encontrado");
        }
    }

    private static void registrarCalificacion() {
        String idUsuario = leerTexto("ID del usuario: ");
        String idContenido = leerTexto("ID del contenido: ");
        int calificacion = leerEntero("Calificación (1-5 estrellas): ");

        if (sistema.registrarCalificacion(idUsuario, idContenido, calificacion)) {
            System.out.println("✓ Calificación registrada");
        } else {
            System.out.println("❌ Error: Datos inválidos");
        }
    }

    private static void recomendarContenidos() {
        String idUsuario = leerTexto("ID del usuario: ");
        int cantidad = leerEntero("Cantidad de recomendaciones: ");

        List<Contenido> recomendaciones = sistema.recomendarContenidos(idUsuario, cantidad);

        System.out.println("\n🎬 RECOMENDACIONES:");
        if (recomendaciones.isEmpty()) {
            System.out.println("No hay recomendaciones disponibles");
        } else {
            for (Contenido c : recomendaciones) {
                System.out.println("  • " + c.getTitulo() + " " + c.getGeneros());
            }
        }
    }

    private static void encontrarSimilares() {
        String idContenido = leerTexto("ID del contenido: ");
        int cantidad = leerEntero("Cantidad de similares: ");

        List<Contenido> similares = sistema.encontrarSimilares(idContenido, cantidad);

        System.out.println("\n🎭 CONTENIDOS SIMILARES:");
        if (similares.isEmpty()) {
            System.out.println("No se encontraron contenidos similares");
        } else {
            for (Contenido c : similares) {
                System.out.println("  • " + c.getTitulo() + " " + c.getGeneros());
            }
        }
    }

    private static void mostrarTopMasVistos() {
        int n = leerEntero("Top N más vistos: ");
        List<Contenido> top = sistema.obtenerTopMasVistos(n);

        System.out.println("\n🏆 TOP " + n + " MÁS VISTOS:");
        for (int i = 0; i < top.size(); i++) {
            Contenido c = top.get(i);
            System.out.println((i + 1) + ". " + c.getTitulo() + " - " + c.getReproducciones() + " reproducciones");
        }
    }

    private static void mostrarTopMejorCalificados() {
        int n = leerEntero("Top N mejor calificados: ");
        List<Contenido> top = sistema.obtenerTopMejorCalificados(n);

        System.out.println("\n⭐ TOP " + n + " MEJOR CALIFICADOS:");
        for (int i = 0; i < top.size(); i++) {
            Contenido c = top.get(i);
            System.out.println((i + 1) + ". " + c.getTitulo() + " - ⭐ " +
                    String.format("%.1f", c.getCalificacionPromedio()) + "/5.0");
        }
    }

    private static void mostrarMenosVistos() {
        int n = leerEntero("Cantidad: ");
        List<Contenido> menos = sistema.obtenerMenosVistos(n);

        System.out.println("\n📉 " + n + " MENOS VISTOS:");
        for (int i = 0; i < menos.size(); i++) {
            Contenido c = menos.get(i);
            System.out.println((i + 1) + ". " + c.getTitulo() + " - " + c.getReproducciones() + " reproducciones");
        }
    }

    private static void mostrarHistorialUsuario() {
        String idUsuario = leerTexto("ID del usuario: ");
        List<Contenido> historial = sistema.obtenerHistorialUsuario(idUsuario);

        System.out.println("\n📜 HISTORIAL:");
        if (historial.isEmpty()) {
            System.out.println("El usuario no ha visto contenido");
        } else {
            for (Contenido c : historial) {
                System.out.println("  • " + c.getTitulo() + " (" + c.getAnio() + ")");
            }
        }
    }

    private static void mostrarUsuariosQueVieron() {
        String idContenido = leerTexto("ID del contenido: ");
        List<Usuario> usuarios = sistema.obtenerUsuariosQueVieron(idContenido);

        System.out.println("\n👥 USUARIOS QUE VIERON ESTE CONTENIDO:");
        if (usuarios.isEmpty()) {
            System.out.println("Ningún usuario ha visto este contenido");
        } else {
            for (Usuario u : usuarios) {
                System.out.println("  • " + u.getNombre() + " (" + u.getId() + ")");
            }
        }
    }

    private static void mostrarEstadisticas() {
        Map<String, Integer> stats = sistema.obtenerEstadisticas();

        System.out.println("\n📊 ESTADÍSTICAS DEL SISTEMA:");
        System.out.println("Total de usuarios: " + stats.get("totalUsuarios"));
        System.out.println("Total de contenidos: " + stats.get("totalContenidos"));
        System.out.println("Géneros disponibles: " + stats.get("generosDisponibles"));
    }

    private static void mostrarContenidosPorGenero() {
        String genero = leerTexto("Género: ");
        int cantidad = sistema.contarContenidosPorGenero(genero);

        System.out.println("\n📚 Contenidos de '" + genero + "': " + cantidad);
    }

    // ========== DATOS DE PRUEBA ==========

    private static void cargarDatosPrueba() {
        // Usuarios
        sistema.registrarUsuario("U001", "Juan Pérez");
        sistema.agregarGustoUsuario("U001", "Acción");
        sistema.agregarGustoUsuario("U001", "Ciencia Ficción");

        sistema.registrarUsuario("U002", "María López");
        sistema.agregarGustoUsuario("U002", "Drama");
        sistema.agregarGustoUsuario("U002", "Romance");

        // Contenidos
        sistema.registrarContenido("C001", "Matrix", 1999, 136);
        sistema.agregarGeneroContenido("C001", "Ciencia Ficción");
        sistema.agregarGeneroContenido("C001", "Acción");

        sistema.registrarContenido("C002", "El Padrino", 1972, 175);
        sistema.agregarGeneroContenido("C002", "Drama");
        sistema.agregarGeneroContenido("C002", "Crimen");

        sistema.registrarContenido("C003", "Toy Story", 1995, 81);
        sistema.agregarGeneroContenido("C003", "Animación");
        sistema.agregarGeneroContenido("C003", "Aventura");

        // Reproducciones
        sistema.registrarReproduccion("U001", "C001");
        sistema.registrarCalificacion("U001", "C001", 5);

        System.out.println("✓ Datos de prueba cargados\n");
    }

    // ========== UTILIDADES ==========

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Ingrese un número válido: ");
        }
        int numero = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return numero;
    }
}