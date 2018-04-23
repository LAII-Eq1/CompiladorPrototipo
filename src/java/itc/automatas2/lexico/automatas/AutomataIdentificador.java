package itc.automatas2.lexico.automatas;

import itc.automatas2.lexico.Tokens;

/**
 * Autómata que reconoce un identificador (variable).
 */
public class AutomataIdentificador extends AutomataToken {

    public AutomataIdentificador() {
        super(Tokens.T_VAR);
    }

    public boolean ejecutar(String token) {
        return q0(0, token.toCharArray());
    }

    private boolean q0(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).matches("[A-Za-z_$]")) {
                return this.q1(cont + 1, car);
            } else {
                return false;
            }
        }
    }

    private boolean q1(int cont, char[] car) {
        if (cont == car.length) {
            return true;
        }
        if (Character.toString(car[cont]).matches("[A-Za-z0-9_$]")) {
            return this.q1(cont + 1, car);
        }
        return false;

    }

}
