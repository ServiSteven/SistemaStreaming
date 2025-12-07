package estructuras;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de Diccionario (estructura clave-valor)
 * Se usa para almacenar el historial de reproducciones por usuario
 */
public class Diccionario<K, V> {

    // Entrada del diccionario (par clave-valor)
    private class Entrada {
        K clave;
        V valor;

        Entrada(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    private List<Entrada> entradas;

    /**
     * Constructor
     */
    public Diccionario() {
        this.entradas = new ArrayList<>();
    }

    /**
     * Insertar o actualizar un par clave-valor
     * Si la clave existe, actualiza el valor
     */
    public void insertar(K clave, V valor) {
        // Buscar si la clave ya existe
        for (Entrada entrada : entradas) {
            if (entrada.clave.equals(clave)) {
                entrada.valor = valor; // Actualizar
                return;
            }
        }

        // Si no existe, agregar nueva entrada
        entradas.add(new Entrada(clave, valor));
    }

    /**
     * Obtener el valor asociado a una clave
     * Retorna null si no existe
     */
    public V obtener(K clave) {
        for (Entrada entrada : entradas) {
            if (entrada.clave.equals(clave)) {
                return entrada.valor;
            }
        }
        return null;
    }

    /**
     * Eliminar una entrada por su clave
     * Retorna true si se eliminó, false si no existía
     */
    public boolean eliminar(K clave) {
        for (int i = 0; i < entradas.size(); i++) {
            if (entradas.get(i).clave.equals(clave)) {
                entradas.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Verificar si existe una clave
     */
    public boolean contiene(K clave) {
        return obtener(clave) != null;
    }

    /**
     * Obtener todas las claves del diccionario
     */
    public List<K> obtenerClaves() {
        List<K> claves = new ArrayList<>();
        for (Entrada entrada : entradas) {
            claves.add(entrada.clave);
        }
        return claves;
    }

    /**
     * Obtener todos los valores del diccionario
     */
    public List<V> obtenerValores() {
        List<V> valores = new ArrayList<>();
        for (Entrada entrada : entradas) {
            valores.add(entrada.valor);
        }
        return valores;
    }

    /**
     * Obtener el tamaño del diccionario
     */
    public int tamanio() {
        return entradas.size();
    }

    /**
     * Verificar si el diccionario está vacío
     */
    public boolean estaVacio() {
        return entradas.isEmpty();
    }

    /**
     * Limpiar el diccionario
     */
    public void limpiar() {
        entradas.clear();
    }

    /**
     * Mostrar el contenido del diccionario (para debugging)
     */
    public void mostrar() {
        System.out.println("=== Contenido del Diccionario ===");
        System.out.println("Tamaño: " + tamanio());
        if (!estaVacio()) {
            for (Entrada entrada : entradas) {
                System.out.println("Clave: " + entrada.clave + " -> Valor: " + entrada.valor);
            }
        } else {
            System.out.println("El diccionario está vacío");
        }
    }
}