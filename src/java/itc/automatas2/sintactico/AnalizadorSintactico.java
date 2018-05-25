package itc.automatas2.sintactico;

import itc.automatas2.estructuras.*;

import java.util.ArrayList;


public class AnalizadorSintactico {

    public ArrayList<ArbolSintactico> arboles;

    /**
     * Ejecuta el analisis sintactico del programa a partir de su tabla de simbolos.
     *
     * @param ts la tabla de símbolos.
     * @return <code>true</code> si el análisis no generó errores.
     */
    public boolean analizar(TablaSimbolos ts) {
        arboles = new ArrayList<>();
        Estructuras.cont = 0;
        try {
            do {
                switch (Estructuras.ObtenerReglaProd(Estructuras.cont, ts)) {
                    case ReglasProd.R_CALL:
                        arboles.add(
                                Estructuras.r_call(Estructuras.cont, ts, false)
                        );
                        break;
                    case ReglasProd.R_METODO:
                        arboles.add(
                                Estructuras.r_metodo(Estructuras.cont, ts)
                        );
                        break;
                    case ReglasProd.R_IF:
                        arboles.add(
                                Estructuras.r_if(Estructuras.cont, ts)
                        );
                        break;
                    case ReglasProd.R_FOR:
                        arboles.add(
                                Estructuras.r_for(Estructuras.cont, ts)
                        );
                        break;
                    case ReglasProd.R_WHILE:
                        arboles.add(
                                Estructuras.r_while(Estructuras.cont, ts)
                        );
                        break;
                    case ReglasProd.R_LECTURA:
                        arboles.add(
                                Estructuras.r_lectura(Estructuras.cont, ts)
                        );
                        break;
                    case ReglasProd.R_IMPRESION:
                        arboles.add(
                                Estructuras.r_impresion(Estructuras.cont, ts)
                        );
                        break;
                    case ReglasProd.R_ASIGNACION:
                        arboles.add(
                                Estructuras.r_asignacion(Estructuras.cont, ts)
                        );
                        break;
                    case ReglasProd.R_RETORNO:
                        RegistroTS reg = ts.get(Estructuras.cont);
                        PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                        arboles = null;
                        return false;
                }
            } while (Estructuras.cont < ts.size());
        } catch (EstructuraNoReconocidaException | SecuenciaIncorrectaException e) {
            arboles = null;
            return false;
        } catch (NullPointerException e) {
            PilaErrores.meter(new RegistroErr(12, -1, ""));
            arboles = null;
            return false;
        }
        return true;
    }

    /**
     * Imprime los arboles generados despues de la validacion de la secuencia de los tokens
     */
    public void imprimirArboles() {
        if (arboles != null)
            for (ArbolSintactico arbol : arboles) {
                System.out.println(arbol.toString());
                System.out.println();
            }
    }

    public boolean tieneArboles() {
        return arboles != null && arboles.size() > 0;
    }
}