package itc.automatas2.estructuras;

/**
 * Registro de la tabla de símbolos, que contiene el nombre, el ID del token, y el tipo.
 */
public class RegistroTS {
    public final String NOMBRE;
    public final int TOKEN_ID;
    public final int TIPO;

    /**
     * Constructor de la clase
     *
     * @param NOMBRE
     * @param TOKEN_ID
     * @param TIPO
     */
    public RegistroTS(String NOMBRE, int TOKEN_ID, int TIPO) {
        this.NOMBRE = NOMBRE;
        this.TOKEN_ID = TOKEN_ID;
        this.TIPO = TIPO;
    }
}
