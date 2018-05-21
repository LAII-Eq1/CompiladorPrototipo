package itc.automatas2.estructuras;

/**
 * Registro de la tabla de símbolos, que contiene el nombre, el ID del token, el tipo, el valor y el número de línea.
 */
public class RegistroTS {
    public final String ID;
    public final String NOMBRE;
    public final int TOKEN_ID;
    public int TIPO;
    public String VAL;
    public final int LINE;

    /**
     * Constructor de la clase
     *
     * @param NOMBRE
     * @param TOKEN_ID
     * @param TIPO
     * @param LINE
     */
    public RegistroTS(String NOMBRE, int TOKEN_ID, int TIPO, int LINE) {
        this.ID = TablaSimbolos.newID(NOMBRE);
        this.NOMBRE = NOMBRE;
        this.TOKEN_ID = TOKEN_ID;
        this.TIPO = TIPO;
        this.VAL = null;
        this.LINE = LINE;
    }

    /**
     * Constructor de la clase
     *
     * @param NOMBRE
     * @param TOKEN_ID
     * @param TIPO
     * @param LINE
     * @param VAL
     */
    public RegistroTS(String NOMBRE, int TOKEN_ID, int TIPO, int LINE, String VAL) {
        this.ID = TablaSimbolos.newID(NOMBRE);
        this.NOMBRE = NOMBRE;
        this.TOKEN_ID = TOKEN_ID;
        this.TIPO = TIPO;
        this.VAL = VAL;
        this.LINE = LINE;
    }
}
