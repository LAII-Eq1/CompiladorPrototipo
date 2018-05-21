package itc.automatas2.estructuras;

import itc.automatas2.lexico.Tokens;
import itc.automatas2.misc.BaseErrores;
import itc.automatas2.misc.Error;

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

    public static void imprimirErrores() {
        if (pila.size() > 0)
            while (PilaErrores.size() > 0) {
                RegistroErr reg = PilaErrores.sacar();
                Error err = BaseErrores.getError(reg.ERR_ID);
                switch (reg.ERR_ID) {
                    //De sistema
                    case 10:
                    case 11:
                        err.setReason(String.format("(RUTA: %s)", reg.LEXEMA));
                        break;
                    case 12:
                        err.setReason("Verifique que todas las llaves estén cerradas o que la última sentencia termine en \";\".");
                        break;
                    //Léxicos
                    case 110:
                        err.setReason(String.format("No se pudo identificar el token \"%s\" en la línea %d", reg.LEXEMA, reg.LINEA_N));
                        break;
                    case 120:
                        err.setReason(String.format("El token '%s' en la línea %d contiene símbolos no reconocibles (fuera del código ASCII). ", reg.LEXEMA, reg.LINEA_N));
                        break;
                    //Sintacticos
                    case 210:
                    case 220:
                    case 230:
                    case 250:
                        err.setReason(String.format("(línea %d)", reg.LINEA_N));
                        break;
                    case 240:
                        err.setReason(String.format("No se esperaba el token %s ('%s') cerca de la línea %d.", Tokens.nombres[reg.TOKEN_ID], reg.LEXEMA, reg.LINEA_N));
                        break;
                    //Semánticos
                    case 310:
                    case 320:
                    case 330:
                    case 340:
                    case 350:
                    case 360:
                    case 370:
                        err.setReason(reg.LEXEMA);
                        break;
                }
                System.err.println(err);
            }
    }
}