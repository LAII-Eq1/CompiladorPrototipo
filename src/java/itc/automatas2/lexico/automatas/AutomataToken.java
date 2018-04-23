package itc.automatas2.lexico.automatas;

/**
 * Clase prototipo para un autómata, que será extendida en cada autómata necesario.
 */
public abstract class AutomataToken {
    private final int TOKEN_ID;

    AutomataToken(int TOKEN_ID) {
        this.TOKEN_ID = TOKEN_ID;
    }

    /**
     * Ejecuta el autómata de acuerdo al token dado.
     *
     * @param token Un <code>String</code> que contiene el token.
     * @return El estado de aceptación <code>(true|false)</code>.
     */
    public abstract boolean ejecutar(String token);

    /**
     * Obtiene el ID del token que el autómata reconoce.
     *
     * @return El ID del token definido en {@link itc.automatas2.lexico.Tokens Tokens}.
     */
    public int getTokenId() {
        return TOKEN_ID;
    }
}
