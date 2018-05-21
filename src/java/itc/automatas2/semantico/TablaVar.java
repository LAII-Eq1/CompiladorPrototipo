package itc.automatas2.semantico;

import java.util.Hashtable;

/**
 * Tabla donde se llevara la relacion de las variablas definidas en el codigo fuente.
 */
public class TablaVar {
    private Hashtable<String, String> tabla = new Hashtable<>();
    public String contexto;

    public TablaVar() {

    }

    /**
     * Metodo para establecer el contexto de una variable si esta pertenece a alguna funcion
     *
     * @param contexto es la funcion a la que pertenece dicha variable si es que pertenece
     */
    public TablaVar(String contexto) {
        this.contexto = contexto;
    }

    /**
     * Metodo para obtener un registro especifico en la tabla de variables
     *
     * @param nombre es el nombre de la variable
     * @return regresara la variable con el nombre especificado
     */
    public String get(String nombre) {
        return tabla.get(nombre);
    }

    /**
     * Metodo para insertar un nuevo registro a la tabla de variables
     *
     * @param nombre es el nombre de la variable
     * @param id     es el ID unico de la variable
     */
    public void meter(String nombre, String id) {
        tabla.put(nombre, id);
    }

    /**
     * Metodo donde se validara la existencia de una variable dentro de la tabla de simbolos
     *
     * @param nombre es el nombre de la variable
     * @return regresa un valor boleano que indicara si la variable es existente dentro de la tabla de variables
     */
    public boolean contiene(String nombre) {
        return tabla.containsKey(nombre);
    }
}
