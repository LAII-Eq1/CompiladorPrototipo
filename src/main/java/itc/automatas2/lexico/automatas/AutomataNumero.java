package itc.automatas2.lexico.automatas;

import itc.automatas2.lexico.Tokens;

/**
 * Autómata que reconoce una constante numérica (entero).
 */
public class AutomataNumero extends AutomataToken {

    public AutomataNumero() {
        super(Tokens.T_INT_CONST);
    }

    public boolean ejecutar(String token) {
        return q0(0, token.toCharArray());
    }

    private boolean q0(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).matches("[0-9]")) {
                return q1(cont + 1, car);

            } else {
                return false;
            }

        }
    }

    private boolean q1(int cont, char[] car) {
        if (cont == car.length) {
            return true;
        }
        if (Character.toString(car[cont]).matches("[0-9]")) {
            return q1(cont + 1, car);
        } else {
            return false;
        }

    }

}
