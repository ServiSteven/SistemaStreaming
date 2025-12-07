package estructuras;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementación de Heap (Montículo) - Max Heap
 * Se usa para mantener un ranking de contenidos ordenados por un criterio
 * (por ejemplo: más reproducciones o mejor calificación)
 */
public class Heap<T> {
    private List<T> elementos;
    private Comparator<T> comparador;

    /**
     * Constructor con comparador personalizado
     * @param comparador define el criterio de ordenamiento
     */
    public Heap(Comparator<T> comparador) {
        this.elementos = new ArrayList<>();
        this.comparador = comparador;
    }

    /**
     * Insertar un elemento en el heap
     */
    public void insertar(T elemento) {
        elementos.add(elemento);
        heapifyUp(elementos.size() - 1);
    }

    /**
     * Extraer el elemento máximo (raíz del heap)
     */
    public T extraerMax() {
        if (estaVacio()) {
            return null;
        }

        T max = elementos.get(0);
        int ultimoIndice = elementos.size() - 1;

        elementos.set(0, elementos.get(ultimoIndice));
        elementos.remove(ultimoIndice);

        if (!estaVacio()) {
            heapifyDown(0);
        }

        return max;
    }

    /**
     * Ver el elemento máximo sin extraerlo
     */
    public T verMax() {
        if (estaVacio()) {
            return null;
        }
        return elementos.get(0);
    }

    /**
     * Obtener el tamaño del heap
     */
    public int tamanio() {
        return elementos.size();
    }

    /**
     * Verificar si el heap está vacío
     */
    public boolean estaVacio() {
        return elementos.isEmpty();
    }

    /**
     * Obtener los N elementos principales sin extraerlos
     * @param n cantidad de elementos a obtener
     */
    public List<T> obtenerTopN(int n) {
        List<T> topN = new ArrayList<>();
        Heap<T> heapTemporal = new Heap<>(this.comparador);

        // Copiar elementos al heap temporal
        for (T elemento : elementos) {
            heapTemporal.insertar(elemento);
        }

        // Extraer los N mejores
        int cantidad = Math.min(n, heapTemporal.tamanio());
        for (int i = 0; i < cantidad; i++) {
            topN.add(heapTemporal.extraerMax());
        }

        return topN;
    }

    /**
     * Reconstruir el heap con nuevos elementos
     * Útil cuando cambian los valores (ej: nuevas reproducciones)
     */
    public void reconstruir(List<T> nuevosElementos) {
        elementos.clear();
        for (T elemento : nuevosElementos) {
            insertar(elemento);
        }
    }

    /**
     * Heapify hacia arriba - mantener propiedad del heap al insertar
     */
    private void heapifyUp(int indice) {
        while (indice > 0) {
            int indicePadre = (indice - 1) / 2;

            // Si el elemento actual es mayor que su padre, intercambiar
            if (comparador.compare(elementos.get(indice), elementos.get(indicePadre)) > 0) {
                intercambiar(indice, indicePadre);
                indice = indicePadre;
            } else {
                break;
            }
        }
    }

    /**
     * Heapify hacia abajo - mantener propiedad del heap al extraer
     */
    private void heapifyDown(int indice) {
        int tamanio = elementos.size();

        while (indice < tamanio) {
            int indiceMax = indice;
            int indiceIzq = 2 * indice + 1;
            int indiceDer = 2 * indice + 2;

            // Comparar con hijo izquierdo
            if (indiceIzq < tamanio &&
                    comparador.compare(elementos.get(indiceIzq), elementos.get(indiceMax)) > 0) {
                indiceMax = indiceIzq;
            }

            // Comparar con hijo derecho
            if (indiceDer < tamanio &&
                    comparador.compare(elementos.get(indiceDer), elementos.get(indiceMax)) > 0) {
                indiceMax = indiceDer;
            }

            // Si el máximo no es el nodo actual, intercambiar
            if (indiceMax != indice) {
                intercambiar(indice, indiceMax);
                indice = indiceMax;
            } else {
                break;
            }
        }
    }

    /**
     * Intercambiar dos elementos en el heap
     */
    private void intercambiar(int i, int j) {
        T temp = elementos.get(i);
        elementos.set(i, elementos.get(j));
        elementos.set(j, temp);
    }

    /**
     * Limpiar el heap
     */
    public void limpiar() {
        elementos.clear();
    }

    /**
     * Mostrar el contenido del heap (para debugging)
     */
    public void mostrar() {
        System.out.println("=== Contenido del Heap ===");
        System.out.println("Tamaño: " + tamanio());
        if (!estaVacio()) {
            System.out.println("Elemento máximo: " + verMax());
            System.out.println("Todos los elementos:");
            for (int i = 0; i < elementos.size(); i++) {
                System.out.println("  [" + i + "] " + elementos.get(i));
            }
        } else {
            System.out.println("El heap está vacío");
        }
    }
}