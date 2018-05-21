package itc.automatas2.semantico.excepciones;

public class VariableNoDeclaradaException extends Exception {
    public VariableNoDeclaradaException() {
        super();
    }

    public VariableNoDeclaradaException(String message) {
        super(message);
    }
}
