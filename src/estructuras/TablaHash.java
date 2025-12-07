package estructuras;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de Tabla Hash con manejo de colisiones mediante encadenamiento
 * Se usa para almacenar contenidos con acceso rápido por ID
 */
public class TablaHash<K, V> {

    // Nodo para manejar colisiones con listas enlazadas
    private class Nodo {
        K clave;
        V valor;
        Nodo siguiente;

        Nodo(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = null;
        }
    }

    private List<Nodo>[] tabla; // Usamos List para evitar problemas con genéricos
    private int capacidad;
    private int tamanio;

    /**
     * Constructor con capacidad inicial
     */
    @SuppressWarnings("unchecked")
    public TablaHash(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = (List<Nodo>[]) new ArrayList[capacidad];
        // Inicializar cada posición con una lista vacía
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new ArrayList<>();
        }
        this.tamanio = 0;
    }

    /**
     * Constructor por defecto
     */
    public TablaHash() {
        this(16); // Capacidad por defecto
    }

    /**
     * Función hash para obtener el índice donde guardar el elemento
     */
    private int hash(K clave) {
        return Math.abs(clave.hashCode()) % capacidad;
    }

    /**
     * Insertar o actualizar un par clave-valor
     * Si la clave ya existe, actualiza el valor
     */
    public void insertar(K clave, V valor) {
        int indice = hash(clave);
        List<Nodo> lista = tabla[indice];

        // Buscar si la clave ya existe y actualizar
        for (Nodo nodo : lista) {
            if (nodo.clave.equals(clave)) {
                nodo.valor = valor; // Actualizar valor existente
                return;
            }
        }

        // Si no existe, agregar nuevo nodo
        lista.add(new Nodo(clave, valor));
        tamanio++;
    }

    /**
     * Buscar un valor por su clave
     * Retorna null si no se encuentra
     */
    public V buscar(K clave) {
        int indice = hash(clave);
        List<Nodo> lista = tabla[indice];

        for (Nodo nodo : lista) {
            if (nodo.clave.equals(clave)) {
                return nodo.valor;
            }
        }

        return null; // No encontrado
    }

    /**
     * Eliminar un par clave-valor
     * Retorna true si se eliminó, false si no existía
     */
    public boolean eliminar(K clave) {
        int indice = hash(clave);
        List<Nodo> lista = tabla[indice];

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).clave.equals(clave)) {
                lista.remove(i);
                tamanio--;
                return true;
            }
        }

        return false; // No encontrado
    }

    /**
     * Verificar si existe una clave
     */
    public boolean contiene(K clave) {
        return buscar(clave) != null;
    }

    /**
     * Obtener el tamaño de la tabla (cantidad de elementos)
     */
    public int tamanio() {
        return tamanio;
    }

    /**
     * Verificar si la tabla está vacía
     */
    public boolean estaVacia() {
        return tamanio == 0;
    }

    /**
     * Limpiar toda la tabla
     */
    public void limpiar() {
        for (int i = 0; i < capacidad; i++) {
            tabla[i].clear();
        }
        tamanio = 0;
    }

    /**
     * Obtener todas las claves almacenadas
     */
    public List<K> obtenerClaves() {
        List<K> claves = new ArrayList<>();
        for (int i = 0; i < capacidad; i++) {
            for (Nodo nodo : tabla[i]) {
                claves.add(nodo.clave);
            }
        }
        return claves;
    }

    /**
     * Mostrar el contenido de la tabla (para debugging)
     */
    public void mostrar() {
        System.out.println("=== Contenido de la Tabla Hash ===");
        for (int i = 0; i < capacidad; i++) {
            if (!tabla[i].isEmpty()) {
                System.out.print("Índice " + i + ": ");
                for (Nodo nodo : tabla[i]) {
                    System.out.print("[" + nodo.clave + " -> " + nodo.valor + "] ");
                }
                System.out.println();
            }
        }
        System.out.println("Tamaño total: " + tamanio);
    }
}