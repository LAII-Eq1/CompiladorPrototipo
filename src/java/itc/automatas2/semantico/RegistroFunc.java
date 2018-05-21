package itc.automatas2.semantico;

import java.util.ArrayList;

/**
 * Esta clase representara el registro de las funciones que seran almacenadas en la tabla de funciones (TablaFunc)
 */
public class RegistroFunc {
    public final String ID;
    public final int T_RETURN;
    public final ArrayList<Integer> T_PARAMS;

    /**
     * Este sera el constructor de nuestros registros de funcion
     *
     * @params id
     * @params t_return
     */
    public RegistroFunc(String id, int t_return) {
        this.ID = id;
        this.T_RETURN = t_return;
        this.T_PARAMS = new ArrayList<>();
    }
}
