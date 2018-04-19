package itc.automatas2.estructuras;

/**
 * Registro de la pila de errores, que contiene el ID del error, el número de línea, y el lexema que lo causó.
 */
public class RegistroErr {
    public final int ERR_ID;
    public final int LINEA_N;
    public final String LEXEMA;

    /**
     * Constructor de la clase
     * @param ERR_ID
     * @param LINEA_N
     * @param LEXEMA
     */
    public RegistroErr(int ERR_ID, int LINEA_N, String LEXEMA) {
        this.ERR_ID = ERR_ID;
        this.LINEA_N = LINEA_N;
        this.LEXEMA = LEXEMA;
    }
}
