package itc.automatas2.estructuras;

/**
 * Registro de la tabla de símbolos, que contiene el nombre, el ID del token, y el tipo.
 */
public class RegistroTS {
    public final String ID;
    public final String NOMBRE;
    public final int TOKEN_ID;
    public final int TIPO;
    public String VAL;

    /**
     * Constructor de la clase
     *
     * @param NOMBRE
     * @param TOKEN_ID
     * @param TIPO
     */
    public RegistroTS(String NOMBRE, int TOKEN_ID, int TIPO) {
        this.ID = TablaSimbolos.newID(NOMBRE);
        this.NOMBRE = NOMBRE;
        this.TOKEN_ID = TOKEN_ID;
        this.TIPO = TIPO;
        this.VAL = null;
    }

    /**
     * Constructor de la clase
     *
     * @param NOMBRE
     * @param TOKEN_ID
     * @param TIPO
     * @param VAL
     */
    public RegistroTS(String NOMBRE, int TOKEN_ID, int TIPO, String VAL) {
        this.ID = TablaSimbolos.newID(NOMBRE);
        this.NOMBRE = NOMBRE;
        this.TOKEN_ID = TOKEN_ID;
        this.TIPO = TIPO;
        this.VAL = VAL;
    }
}
