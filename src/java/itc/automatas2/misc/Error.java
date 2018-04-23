package itc.automatas2.misc;


/**
 * TDA para contener la descripción de un error.
 */
public class Error {
    public final int ID;
    public final String DESC;
    public String REASON;


    Error(int ID, String DESC) {
        this.ID = ID;
        this.DESC = DESC;
        this.REASON = "";
    }

    /**
     * Establece la razón del error. Útil para imprimirlo en pantalla.
     *
     * @param reason La razón del error.
     * @return El mismo error, por conveniencia.
     */
    public Error setReason(String reason) {
        this.REASON = reason;
        return this;
    }

    /**
     * @return Una representación del error de la forma "ID: DESC REASON"
     */
    @Override
    public String toString() {
        return String.format("ERROR %d: %s %s", ID, DESC, REASON);
    }
}
