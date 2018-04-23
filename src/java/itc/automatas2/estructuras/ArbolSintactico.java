package itc.automatas2.estructuras;

/**
 * Árbol sintáctico para usarse en el análisis.
 */
public class ArbolSintactico {
    public NodoArbol raiz;

    /**
     * Agrega un nodo a la lista de hijos de otro
     *
     * @param padre el {@link NodoArbol nodo} padre.
     * @param hijo  el {@link NodoArbol nodo} hijo.
     */
    public void meter(NodoArbol padre, NodoArbol hijo) {
        padre.hijos.add(hijo);
        hijo.padre = padre;
    }

    public String toString() {
        return toString(raiz, 0);
    }

    private String toString(NodoArbol nodo, int niv) {
        StringBuilder sb = new StringBuilder();
        if (niv > 0) {
            sb.append("|");
            for (int i = 0; i < niv; i++) {
                sb.append("   ");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("|--");
        sb.append(nodo.toString());
        sb.append("\n");
        if (nodo.hijos.size() > 0) {
            for (NodoArbol hijo : nodo.hijos) {
                sb.append(toString(hijo, niv + 1));
            }
        }
        return sb.toString();
    }
}
