package itc.automatas2.misc;

import java.util.Hashtable;

/**
 * Catálogo de errores internos. Esta clase contiene la definición de cada error posible.
 */
public class BaseErrores {
    private static Hashtable<Integer, Error> tablaErrores = new Hashtable<>();

    static {
        tablaErrores.put(10, new Error(10, "El archivo al que se hace referencia no existe."));
        tablaErrores.put(11, new Error(11, "Ocurrió un error de E/S al leer el código fuente."));
        tablaErrores.put(110, new Error(110, "Token inválido."));
        tablaErrores.put(120, new Error(120, "Uso de símbolos no definidos en el alfabeto."));
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