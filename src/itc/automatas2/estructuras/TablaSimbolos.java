package itc.automatas2.estructuras;

import java.util.ArrayDeque;

/**
 * Clase que representa una tabla de símbolos.
 */
public class TablaSimbolos {
    private ArrayDeque<RegistroTS> tabla;

    /**
     * Constructor de la clase
     */
    public TablaSimbolos() {
        tabla = new ArrayDeque<>();
    }

    /**
     * Metodo para meter un nuevo registro a la tabla
     *
     * @param registro Un objeto del tipo {@link RegistroTS RegistroTS}.
     */
    public void meter(RegistroTS registro) {
        tabla.add(registro);
    }

    /**
     * Metodo para verificar la existencia de un registro en la tabla
     *
     * @param llave La llave del registro.
     * @return <code>true</code> si el registro existe, <code>false</code> si no.
     */
    public boolean contiene(String llave) {
        return tabla.stream().anyMatch(reg -> reg.NOMBRE.equals(llave));
    }

    /**
     * Metodo para obtner los valores de los registro en la tabla
     *
     * @return Un objeto del tipo {@link RegistroTS RegistroTS}.
     */
    public ArrayDeque<RegistroTS> registros() {
        return tabla.clone();
    }

    /**
     * Obtiene el tamaño de la tabla
     *
     * @return Un entero que representa el tamaño de la tabla
     */
    public int size() {
        return tabla.size();
    }

    /**
     * Obtiene un registro por su nombre o lexema
     *
     * @param nombre el nombre o lexema
     * @return el registro encontrado
     */
    public RegistroTS get(String nombre) {
        return tabla.stream().filter(reg -> nombre.equals(reg.NOMBRE)).findFirst().get();
    }

    /**
     * Obtiene un registro por su ID
     *
     * @param id el ID
     * @return el registro encontrado
     */
    public RegistroTS getByID(String id) {
        return tabla.stream().filter(reg -> id.equals(reg.ID)).findFirst().get();
    }

    /**
     * Regresa una cadena única como ID.
     *
     * @param token el token para obtener una cadena
     * @return una cadena de ID única
     */
    static String newID(String token) {
        return String.format("%x%08x", System.nanoTime(), Math.abs(token.hashCode()));
    }
}
