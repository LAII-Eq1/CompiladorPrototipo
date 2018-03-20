package itc.automatas2.estructuras;

import java.util.ArrayDeque;
import java.util.Hashtable;

/**
 * Clase que representa una tabla de símbolos.
 */
public class TablaSimbolos {
    private Hashtable<String, RegistroTS> tabla;

    /**
     * Constructor de la clase
     */
    public TablaSimbolos() {
        tabla = new Hashtable<>();
    }

    /**
     * Metodo para meter un nuevo registro al HashTable
     *
     * @param llave    La llave del nuevo registro.
     * @param registro Un objeto del tipo {@link RegistroTS RegistroTS}.
     */
    public void meter(String llave, RegistroTS registro) {
        tabla.put(llave, registro);
    }

    /**
     * Metodo para verificar la existencia de un registro en el HashTable
     *
     * @param llave La llave del registro.
     * @return <code>true</code> si el registro existe, <code>false</code> si no.
     */
    public boolean contiene(String llave) {
        return tabla.containsKey(llave);
    }

    /**
     * Metodo para obtner los valores de los registro en el HashTable
     *
     * @return Un objeto del tipo {@link RegistroTS RegistroTS}.
     */
    public ArrayDeque<RegistroTS> registros() {
        return new ArrayDeque<>(tabla.values());
    }

    /**
     * Obtiene el tamaño de la tabla
     * @return Un entero que representa el tamaño de la tabla
     */
    public int size() {
        return tabla.size();
    }
}
