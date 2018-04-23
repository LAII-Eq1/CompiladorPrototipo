package itc.automatas2.misc;

import java.util.Hashtable;

/**
 * Catálogo de errores internos. Esta clase contiene la definición de cada error posible.
 */
public class BaseErrores {
    private static Hashtable<Integer, Error> tablaErrores = new Hashtable<>();

    static {
        //Errores reservados para el sistema
        tablaErrores.put(10, new Error(10, "El archivo al que se hace referencia no existe."));
        tablaErrores.put(11, new Error(11, "Ocurrió un error de E/S al leer el código fuente."));
        tablaErrores.put(12, new Error(12, "Programa incompleto."));

        //Errores lexicos
        tablaErrores.put(110, new Error(110, "Token inválido."));
        tablaErrores.put(120, new Error(120, "Uso de símbolos no definidos en el alfabeto."));

        //Errores sintacticos
        tablaErrores.put(210, new Error(210, "Falta el delimitador ';' al final de la sentencia."));
        tablaErrores.put(220, new Error(220, "Return sólo acepta un parámetro seguido de ';'."));
        tablaErrores.put(230, new Error(230, "Apertura o cierre de llaves o corchetes erróneas."));
        tablaErrores.put(240, new Error(240, "Mala estructuración de sentencias según el lenguaje."));
        tablaErrores.put(250, new Error(250, "Estructura no reconocida por el lenguaje."));
    }

    /**
     * Obtiene un objeto {@link Error Error} de acuerdo al ID proporcionado.
     *
     * @param ID El identificador del error.
     * @return Un objeto {@link Error Error} si existe, <code>null</code> si no.
     */
    public static Error getError(int ID) {
        return tablaErrores.get(ID);
    }
}