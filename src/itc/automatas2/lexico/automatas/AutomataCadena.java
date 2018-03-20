package itc.automatas2.lexico.automatas;

import itc.automatas2.lexico.Tokens;

/**
 * Autómata que reconoce un identificador (variable).
 */
public class AutomataCadena extends AutomataToken {

    public AutomataCadena() {
        super(Tokens.T_STR_CONST);
    }

    public boolean ejecutar(String token) {
        return q0(0, token.toCharArray());
    }

    private boolean q0(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("\"")) {
                return this.q1(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q1(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        }
        if (Character.toString(car[cont]).matches("[A-Za-z0-9{-~:-@#-/ -!]")) {
            return this.q1(cont + 1, car);
        }
        if (Character.toString(car[cont]).equals("\"")) {
            return this.q2(cont + 1, car);
        }
        return false;

    }

    private boolean q2(int cont, char[] car) {
        return cont == car.length;
    }
}
