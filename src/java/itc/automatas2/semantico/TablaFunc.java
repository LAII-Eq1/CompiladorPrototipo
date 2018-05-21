package itc.automatas2.semantico;

import java.util.Hashtable;

/**
 * Tabla donde se llevara el registro de las funciones ya establecidas en el codigo fuente.
 */
public class TablaFunc {
    private Hashtable<String, RegistroFunc> tabla = new Hashtable<>();

    /**
     * Metodo para obtener un registro especifico en la tabla de variables
     *
     * @param nombre es el nombre de la funcion
     * @return regresa la funcion con el nombre especificado
     */
    public RegistroFunc get(String nombre) {
        return tabla.get(nombre);
    }

    /**
     * Metodo para insertar un nuevo registro a la tabla de funcion
     *
     * @param nombre es el nombre de la funcion
     * @param rf     es el registro de la funcion que se generara
     */
    public void meter(String nombre, RegistroFunc rf) {
        tabla.put(nombre, rf);
    }

    /**
     * Metodo donde se validara la existencia de una funcion dentro de la tabla de simbolos
     *
     * @param nombre es el nombre de la funcion
     * @return regresa un valor boleano que indica si dicha funcion existe dentro de TablaFunc
     */
    public boolean contiene(String nombre) {
        return tabla.containsKey(nombre);
    }
}