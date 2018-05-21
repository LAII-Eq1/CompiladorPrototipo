package itc.automatas2.semantico;

import itc.automatas2.estructuras.*;
import itc.automatas2.semantico.excepciones.*;
import itc.automatas2.sintactico.ReglasProd;

import java.util.ArrayList;

/**
 * Ejecuta el analisis semantico del programa haciendo uso del arbol sintactico y la tabla de simbolos.
 */
public class AnalizadorSemantico {
    TablaVar vars;
    TablaFunc funcs;

    /**
     * Hace el análisis semántico a partir de los árboles generados
     * en el análisis sintáctico.
     *
     * @param ts      La tabla de simbolos,
     * @param arboles Los arboles creados por el analizador sintactico.
     * @return <code>true</code> si el análisis no generó errores.
     */
    public boolean analizar(TablaSimbolos ts, ArrayList<ArbolSintactico> arboles) {
        vars = new TablaVar();
        funcs = new TablaFunc();
        try {
            for (ArbolSintactico a : arboles) {
                NodoArbol actual = a.raiz;
                if (actual.REGLA_ID > 0) {
                    switch (actual.REGLA_ID) {
                        case ReglasProd.R_METODO:
                            ReglasSemanticas.r_func(ts, actual, vars, funcs,
                                    new TablaVar(ts.getByID(actual.hijos.get(1).REF).NOMBRE)
                            );
                            break;
                        case ReglasProd.R_ASIGNACION:
                            ReglasSemanticas.r_asig(ts, actual, vars, funcs, null);
                            break;
                        case ReglasProd.R_CALL:
                            ReglasSemanticas.r_llamada(ts, actual, vars, funcs, null);
                            break;
                        case ReglasProd.R_IF:
                            ReglasSemanticas.r_if(ts, actual, vars, funcs, null);
                            break;
                        case ReglasProd.R_WHILE:
                            ReglasSemanticas.r_while(ts, actual, vars, funcs, null);
                            break;
                        case ReglasProd.R_FOR:
                            ReglasSemanticas.r_for(ts, actual, vars, funcs, null);
                            break;
                        case ReglasProd.R_LECTURA:
                        case ReglasProd.R_IMPRESION:
                            ReglasSemanticas.r_leer_imp(ts, actual, vars, funcs, null);
                            break;
                    }
                }
            }
        } catch (VariableNoDeclaradaException e) {
            PilaErrores.meter(new RegistroErr(310, -1, e.getMessage()));
            return false;
        } catch (TipoIncorrectoException e) {
            PilaErrores.meter(new RegistroErr(320, -1, e.getMessage()));
            return false;
        } catch (ParametrosIncorrectosException e) {
            PilaErrores.meter(new RegistroErr(330, -1, e.getMessage()));
            return false;
        } catch (IdExistenteException e) {
            PilaErrores.meter(new RegistroErr(340, -1, e.getMessage()));
            return false;
        } catch (MalRetornoException e) {
            PilaErrores.meter(new RegistroErr(350, -1, e.getMessage()));
            return false;
        } catch (FuncionSinRetornoException e) {
            PilaErrores.meter(new RegistroErr(360, -1, e.getMessage()));
            return false;
        } catch (SentenciaInesperadaException e) {
            PilaErrores.meter(new RegistroErr(370, -1, e.getMessage()));
            return false;
        }
        return true;
    }
}
