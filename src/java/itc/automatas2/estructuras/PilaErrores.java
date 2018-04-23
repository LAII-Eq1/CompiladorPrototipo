package itc.automatas2.estructuras;

import java.util.ArrayDeque;

/**
 *
 */
public class PilaErrores {
    private static ArrayDeque<RegistroErr> pila = new ArrayDeque<>();

    /**
     * Inserta un nuevo error a la pila
     *
     * @param error Un objeto del tipo {@link RegistroErr RegistroErr}
     */
    public static void meter(RegistroErr error) {
        pila.push(error);
    }

    /**
     * Remueve el último objeto de la pila
     *
     * @return Un objeto del tipo {@link RegistroErr RegistroErr}
     */
    public static RegistroErr sacar() {
        return pila.pop();
    }

    /**
     * Obtiene el tamaño de la pila
     *
     * @return Un entero que representa el tamaño de la pila
     */
    public static int size() {
        return pila.size();
    }
}