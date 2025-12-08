package sistema;

import modelo.Contenido;
import modelo.Usuario;
import estructuras.TablaHash;
import estructuras.Heap;
import estructuras.Diccionario;

import java.util.*;

/**
 * Sistema principal que gestiona la plataforma de streaming
 * Integra todas las estructuras de datos para ofrecer funcionalidades completas
 */
public class SistemaStreaming {

    // Estructuras de datos principales
    private TablaHash<String, Contenido> catalogoContenidos;
    private TablaHash<String, Usuario> catalogoUsuarios;
    private Diccionario<String, List<String>> historialUsuarios; // idUsuario -> lista de idContenidos vistos
    private Diccionario<String, Set<String>> contenidoVistoPor; // idContenido -> conjunto de idUsuarios

    /**
     * Constructor - inicializa todas las estructuras
     */
    public SistemaStreaming() {
        this.catalogoContenidos = new TablaHash<>();
        this.catalogoUsuarios = new TablaHash<>();
        this.historialUsuarios = new Diccionario<>();
        this.contenidoVistoPor = new Diccionario<>();
    }

    // ========== GESTIÓN DE USUARIOS ==========

    /**
     * Registrar un nuevo usuario en el sistema
     */
    public boolean registrarUsuario(String id, String nombre) {
        if (catalogoUsuarios.contiene(id)) {
            return false; // Usuario ya existe
        }

        Usuario nuevoUsuario = new Usuario(id, nombre);
        catalogoUsuarios.insertar(id, nuevoUsuario);
        historialUsuarios.insertar(id, new ArrayList<>());
        return true;
    }

    /**
     * Obtener un usuario por su ID
     */
    public Usuario obtenerUsuario(String idUsuario) {
        return catalogoUsuarios.buscar(idUsuario);
    }

    /**
     * Agregar un género a los gustos del usuario
     */
    public boolean agregarGustoUsuario(String idUsuario, String genero) {
        Usuario usuario = catalogoUsuarios.buscar(idUsuario);
        if (usuario == null) {
            return false;
        }
        usuario.agregarGusto(genero);
        return true;
    }

    // ========== GESTIÓN DE CONTENIDOS ==========

    /**
     * Registrar un nuevo contenido (película/serie)
     */
    public boolean registrarContenido(String id, String titulo, int anio, int duracion) {
        if (catalogoContenidos.contiene(id)) {
            return false; // Contenido ya existe
        }

        Contenido nuevoContenido = new Contenido(id, titulo, anio, duracion);
        catalogoContenidos.insertar(id, nuevoContenido);
        contenidoVistoPor.insertar(id, new HashSet<>());
        return true;
    }

    /**
     * Obtener un contenido por su ID
     */
    public Contenido obtenerContenido(String idContenido) {
        return catalogoContenidos.buscar(idContenido);
    }

    /**
     * Agregar un género a un contenido
     */
    public boolean agregarGeneroContenido(String idContenido, String genero) {
        Contenido contenido = catalogoContenidos.buscar(idContenido);
        if (contenido == null) {
            return false;
        }
        contenido.agregarGenero(genero);
        return true;
    }

    // ========== REPRODUCCIONES Y CALIFICACIONES ==========

    /**
     * Registrar que un usuario vio un contenido
     */
    public boolean registrarReproduccion(String idUsuario, String idContenido) {
        Usuario usuario = catalogoUsuarios.buscar(idUsuario);
        Contenido contenido = catalogoContenidos.buscar(idContenido);

        if (usuario == null || contenido == null) {
            return false;
        }

        // Registrar reproducción en el contenido
        contenido.registrarReproduccion();

        // Agregar al historial del usuario
        List<String> historial = historialUsuarios.obtener(idUsuario);
        if (!historial.contains(idContenido)) {
            historial.add(idContenido);
        }

        // Registrar usuario que vio el contenido
        Set<String> usuariosVieron = contenidoVistoPor.obtener(idContenido);
        usuariosVieron.add(idUsuario);

        return true;
    }

    /**
     * Registrar una calificación de un usuario a un contenido (1-5 estrellas)
     */
    public boolean registrarCalificacion(String idUsuario, String idContenido, int calificacion) {
        Usuario usuario = catalogoUsuarios.buscar(idUsuario);
        Contenido contenido = catalogoContenidos.buscar(idContenido);

        if (usuario == null || contenido == null) {
            return false;
        }

        if (calificacion < 1 || calificacion > 5) {
            return false;
        }

        contenido.registrarCalificacion(calificacion);
        return true;
    }

    // ========== RANKING ==========

    /**
     * Obtener top N contenidos más vistos
     */
    public List<Contenido> obtenerTopMasVistos(int n) {
        Comparator<Contenido> comparadorReproducciones =
                (c1, c2) -> Integer.compare(c1.getReproducciones(), c2.getReproducciones());

        Heap<Contenido> heap = new Heap<>(comparadorReproducciones);

        // Obtener todas las claves del catálogo
        List<String> claves = catalogoContenidos.obtenerClaves();
        for (String id : claves) {
            Contenido contenido = catalogoContenidos.buscar(id);
            if (contenido != null && contenido.getReproducciones() > 0) {
                heap.insertar(contenido);
            }
        }

        return heap.obtenerTopN(n);
    }

    /**
     * Obtener top N contenidos mejor calificados
     */
    public List<Contenido> obtenerTopMejorCalificados(int n) {
        Comparator<Contenido> comparadorCalificacion =
                (c1, c2) -> Double.compare(c1.getCalificacionPromedio(), c2.getCalificacionPromedio());

        Heap<Contenido> heap = new Heap<>(comparadorCalificacion);

        List<String> claves = catalogoContenidos.obtenerClaves();
        for (String id : claves) {
            Contenido contenido = catalogoContenidos.buscar(id);
            if (contenido != null && contenido.getTotalCalificaciones() > 0) {
                heap.insertar(contenido);
            }
        }

        return heap.obtenerTopN(n);
    }

    /**
     * Obtener contenidos menos vistos (min-heap invertido)
     */
    public List<Contenido> obtenerMenosVistos(int n) {
        Comparator<Contenido> comparadorInverso =
                (c1, c2) -> Integer.compare(c2.getReproducciones(), c1.getReproducciones());

        Heap<Contenido> heap = new Heap<>(comparadorInverso);

        List<String> claves = catalogoContenidos.obtenerClaves();
        for (String id : claves) {
            Contenido contenido = catalogoContenidos.buscar(id);
            if (contenido != null) {
                heap.insertar(contenido);
            }
        }

        return heap.obtenerTopN(n);
    }

    // ========== RECOMENDACIONES ==========

    /**
     * Recomendar contenidos al usuario según sus gustos (intersección de géneros)
     * Solo recomienda contenidos que NO ha visto
     */
    public List<Contenido> recomendarContenidos(String idUsuario, int cantidad) {
        Usuario usuario = catalogoUsuarios.buscar(idUsuario);
        if (usuario == null) {
            return new ArrayList<>();
        }

        Set<String> gustosUsuario = usuario.getGustos();
        List<String> historial = historialUsuarios.obtener(idUsuario);
        List<Contenido> recomendaciones = new ArrayList<>();

        List<String> claves = catalogoContenidos.obtenerClaves();
        for (String id : claves) {
            // No recomendar lo que ya vio
            if (historial.contains(id)) {
                continue;
            }

            Contenido contenido = catalogoContenidos.buscar(id);
            if (contenido == null) {
                continue;
            }

            // Calcular intersección de géneros
            Set<String> generosContenido = contenido.getGeneros();
            Set<String> interseccion = new HashSet<>(gustosUsuario);
            interseccion.retainAll(generosContenido);

            // Si hay al menos un género en común, es recomendable
            if (!interseccion.isEmpty()) {
                recomendaciones.add(contenido);
            }
        }

        // Limitar la cantidad de recomendaciones
        if (recomendaciones.size() > cantidad) {
            return recomendaciones.subList(0, cantidad);
        }

        return recomendaciones;
    }

    /**
     * Encontrar contenidos similares a uno dado (por intersección de géneros)
     */
    public List<Contenido> encontrarSimilares(String idContenido, int cantidad) {
        Contenido contenidoBase = catalogoContenidos.buscar(idContenido);
        if (contenidoBase == null) {
            return new ArrayList<>();
        }

        Set<String> generosBase = contenidoBase.getGeneros();
        List<Contenido> similares = new ArrayList<>();

        List<String> claves = catalogoContenidos.obtenerClaves();
        for (String id : claves) {
            if (id.equals(idContenido)) {
                continue; // No incluir el mismo contenido
            }

            Contenido contenido = catalogoContenidos.buscar(id);
            if (contenido == null) {
                continue;
            }

            // Calcular intersección de géneros
            Set<String> generosContenido = contenido.getGeneros();
            Set<String> interseccion = new HashSet<>(generosBase);
            interseccion.retainAll(generosContenido);

            // Si comparten géneros, son similares
            if (!interseccion.isEmpty()) {
                similares.add(contenido);
            }
        }

        // Limitar la cantidad
        if (similares.size() > cantidad) {
            return similares.subList(0, cantidad);
        }

        return similares;
    }

    // ========== REPORTES ==========

    /**
     * Obtener historial de reproducciones de un usuario
     */
    public List<Contenido> obtenerHistorialUsuario(String idUsuario) {
        List<String> historial = historialUsuarios.obtener(idUsuario);
        if (historial == null) {
            return new ArrayList<>();
        }

        List<Contenido> contenidos = new ArrayList<>();
        for (String idContenido : historial) {
            Contenido contenido = catalogoContenidos.buscar(idContenido);
            if (contenido != null) {
                contenidos.add(contenido);
            }
        }

        return contenidos;
    }

    /**
     * Obtener lista de usuarios que han visto un contenido específico
     */
    public List<Usuario> obtenerUsuariosQueVieron(String idContenido) {
        Set<String> idsUsuarios = contenidoVistoPor.obtener(idContenido);
        if (idsUsuarios == null) {
            return new ArrayList<>();
        }

        List<Usuario> usuarios = new ArrayList<>();
        for (String idUsuario : idsUsuarios) {
            Usuario usuario = catalogoUsuarios.buscar(idUsuario);
            if (usuario != null) {
                usuarios.add(usuario);
            }
        }

        return usuarios;
    }

    /**
     * Obtener estadísticas básicas del sistema
     */
    public Map<String, Integer> obtenerEstadisticas() {
        Map<String, Integer> stats = new HashMap<>();

        stats.put("totalUsuarios", catalogoUsuarios.tamanio());
        stats.put("totalContenidos", catalogoContenidos.tamanio());

        // Contar géneros únicos
        Set<String> generosUnicos = new HashSet<>();
        List<String> claves = catalogoContenidos.obtenerClaves();
        for (String id : claves) {
            Contenido c = catalogoContenidos.buscar(id);
            if (c != null) {
                generosUnicos.addAll(c.getGeneros());
            }
        }
        stats.put("generosDisponibles", generosUnicos.size());

        return stats;
    }

    /**
     * Contar cuántos contenidos hay de un género específico
     */
    public int contarContenidosPorGenero(String genero) {
        int contador = 0;
        List<String> claves = catalogoContenidos.obtenerClaves();

        for (String id : claves) {
            Contenido contenido = catalogoContenidos.buscar(id);
            if (contenido != null && contenido.getGeneros().contains(genero)) {
                contador++;
            }
        }

        return contador;
    }
}