package itc.automatas2.sintactico;

import itc.automatas2.estructuras.*;
import itc.automatas2.lexico.Tokens;
import itc.automatas2.misc.BaseErrores;
import itc.automatas2.misc.Error;

import java.util.ArrayList;


public class AnalizadorSintactico {

    private ArrayList<ArbolSintactico> arboles;

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
                                Estructuras.r_call(Estructuras.cont, ts)
                        );
                        break;
                    case ReglasProd.R_METODO:
                        arboles.add(
                                Estructuras.r_metodo(Estructuras.cont, ts)
                        );
                        break;
                    case ReglasProd.R_RETORNO:
                        arboles.add(
                                Estructuras.r_retorno(Estructuras.cont, ts)
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
     * Se encarga de imprimir lo errores obtenidos del analisis sintactico y errores que se presentaron en esta fase.
     */
    public void imprimirErrores() {
        if (PilaErrores.size() > 0) {
            System.err.println("Se encontraron errores durante el análisis sintáctico:");
            while (PilaErrores.size() > 0) {
                RegistroErr reg = PilaErrores.sacar();
                Error err = BaseErrores.getError(reg.ERR_ID);
                switch (reg.ERR_ID) {
                    //De sistema
                    case 12:
                        err.setReason("Verifique que todas las llaves estén cerradas.");
                        break;
                    //Sintacticos
                    case 210:
                        err.setReason(String.format("(línea %d)", reg.LINEA_N));
                        break;
                    case 220:
                        err.setReason(String.format("(línea %d)", reg.LINEA_N));
                        break;
                    case 230:
                        err.setReason(String.format("(línea %d)", reg.LINEA_N));
                        break;
                    case 240:
                        err.setReason(String.format("No se esperaba el token %s ('%s') en la línea %d.", Tokens.nombres[reg.TOKEN_ID], reg.LEXEMA, reg.LINEA_N));
                        break;
                    case 250:
                        err.setReason(String.format("(línea %d)", reg.LINEA_N));
                        break;
                }
                System.err.println(err);
            }
        } else {
            System.out.println("No se encontraron errores durante el análisis sintáctico");
        }
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