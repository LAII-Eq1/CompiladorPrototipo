package itc.automatas2.sintactico;

/**
 * Catálogo de ID's de las reglas de produccion.
 */
public class ReglasProd {

    //Reglas de produccion
    public static final int R_BLOQUE = 100;
    public static final int R_CALL = 101;
    public static final int R_METODO = 102;
    public static final int R_PARAMS = 103;
    public static final int R_ARGS = 104;
    public static final int R_RETORNO = 105;
    public static final int R_IF = 106;
    public static final int R_FOR = 107;
    public static final int R_WHILE = 108;
    public static final int R_LECTURA = 109;
    public static final int R_IMPRESION = 110;
    public static final int R_ASIGNACION = 111;
    public static final int R_EXPR_REL = 112;
    public static final int R_EXPR_ARITM = 113;

    public static final String[] nombres = new String[]{
            "R_BLOQUE",
            "R_CALL",
            "R_METODO",
            "R_PARAMS",
            "R_ARGS",
            "R_RETORNO",
            "R_IF",
            "R_FOR",
            "R_WHILE",
            "R_LECTURA",
            "R_IMPRESION",
            "R_ASIGNACION",
            "R_EXPR_REL",
            "R_EXPR_ARITM"
    };
}