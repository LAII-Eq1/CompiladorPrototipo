package itc.automatas2.estructuras;

import itc.automatas2.lexico.Tokens;
import itc.automatas2.sintactico.ReglasProd;

import java.util.ArrayList;

/**
 * Clase nodo que forma parte del árbol sintáctico.
 */
public class NodoArbol {
    public NodoArbol padre;
    public ArrayList<NodoArbol> hijos;
    public String REF;
    public int TOKEN_ID;
    public int REGLA_ID;

    public NodoArbol() {
        hijos = new ArrayList<>();
    }

    @Override
    public String toString() {
        String s = TOKEN_ID != 0 ? Tokens.nombres[TOKEN_ID] : "<" + ReglasProd.nombres[REGLA_ID - 100] + ">";
        s += REF != null && !REF.isEmpty() ? ": " + REF : "";
        return s;
    }
}
