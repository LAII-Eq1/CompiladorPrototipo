package itc.automatas2.lexico.automatas;

import itc.automatas2.lexico.Tokens;

/**
 * Autómata que reconoce una constante decimal.
 */
public class AutomataDecimal extends AutomataToken {

    public AutomataDecimal() {
        super(Tokens.T_REAL_CONST);
    }

    public boolean ejecutar(String token) {
        return q0(0, token.toCharArray());
    }

    private boolean q0(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        }
        if (Character.toString(car[cont]).matches("[0-9]")) {
            return this.q1(cont + 1, car);
        }
        return false;
    }

    private boolean q1(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        }
        if (Character.toString(car[cont]).matches("[0-9]")) {
            return this.q1(cont + 1, car);
        }
        if (Character.toString(car[cont]).equals(".")) {
            return this.q2(cont + 1, car);
        }
        return false;
    }

    private boolean q2(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        }
        if (Character.toString(car[cont]).matches("[0-9]")) {
            return this.q3(cont + 1, car);
        }
        return false;

    }

    private boolean q3(int cont, char[] car) {
        if (cont == car.length) {
            return true;
        }
        if (Character.toString(car[cont]).matches("[0-9]")) {
            return this.q3(cont + 1, car);
        }
        return false;
    }
}
