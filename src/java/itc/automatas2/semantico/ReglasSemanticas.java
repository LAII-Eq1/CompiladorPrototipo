package itc.automatas2.semantico;

import itc.automatas2.estructuras.*;
import itc.automatas2.lexico.Tipos;
import itc.automatas2.lexico.Tokens;
import itc.automatas2.semantico.excepciones.*;
import itc.automatas2.sintactico.ReglasProd;

import java.util.ArrayList;

/**
 * Esta clase se encargara de reconocer la estructura a la que se se hace uso en el codigo fuente
 */
class ReglasSemanticas {
    private static boolean returnFound;

    /**
     * Método encargado de análizar semánticamente la esturctura de la declaración de una función.
     *
     * @param ts  Tabla de símbolos
     * @param na  Nodo del árbol
     * @param tv  Tabla de nombres de variables
     * @param tf  Tabla de nombres de funciones
     * @param ctx Función a la que pertenece dicha variable
     * @throws IdExistenteException
     * @throws VariableNoDeclaradaException
     * @throws TipoIncorrectoException
     * @throws ParametrosIncorrectosException
     * @throws SentenciaInesperadaException
     * @throws FuncionSinRetornoException
     * @throws MalRetornoException
     */
    static void r_func(TablaSimbolos ts, NodoArbol na, TablaVar tv, TablaFunc tf, TablaVar ctx) throws IdExistenteException, VariableNoDeclaradaException, TipoIncorrectoException, ParametrosIncorrectosException, SentenciaInesperadaException, FuncionSinRetornoException, MalRetornoException {
        RegistroFunc reg = null;
        for (NodoArbol h : na.hijos) {
            if (h.TOKEN_ID == Tokens.T_FUN) {
                String nombre = ts.getByID(h.REF).NOMBRE;
                if (tf.contiene(nombre)) {
                    throw new IdExistenteException(
                            String.format("La función %s ya existe (línea %d).", nombre, ts.getByID(na.hijos.get(0).REF).LINE)
                    );
                } else {
                    reg = new RegistroFunc(h.REF, getTipo(ts, h.REF));
                    tf.meter(nombre, reg);
                }
            } else if (h.REGLA_ID == ReglasProd.R_ARGS) {
                for (NodoArbol arg : h.hijos) {
                    if (arg.TOKEN_ID == Tokens.T_VAR) {
                        String nombre = ts.getByID(arg.REF).NOMBRE;
                        ctx.meter(nombre, arg.REF);
                        reg.T_PARAMS.add(getTipo(ts, arg.REF));
                    }
                }
            } else if (h.REGLA_ID == ReglasProd.R_BLOQUE) {
                r_bloque(ts, h, tv, tf, ctx);
            }
        }
    }

    /**
     * Método encargado de análizar semánticamente la esturctura de la asignación de valores y
     * la existencia de estas.
     *
     * @param ts  Tabla de símbolos
     * @param na  Nodo del árbol
     * @param tv  Tabla de nombres de variables
     * @param tf  Tabla de nombres de funciones
     * @param ctx Función a la que pertenece dicha variable
     * @throws IdExistenteException
     * @throws VariableNoDeclaradaException
     * @throws TipoIncorrectoException
     * @throws ParametrosIncorrectosException
     */
    static void r_asig(TablaSimbolos ts, NodoArbol na, TablaVar tv, TablaFunc tf, TablaVar ctx) throws IdExistenteException, VariableNoDeclaradaException, TipoIncorrectoException, ParametrosIncorrectosException {
        boolean declarando = esReservadaTipo(na.hijos.get(0).TOKEN_ID);
        boolean asignando = false;
        int tipoVar = getTipo(ts, na.hijos.get(0).REF);
        int tipoAsig = 0;

        for (NodoArbol h : na.hijos) {
            if (h.REGLA_ID == ReglasProd.R_EXPR_ARITM || h.REGLA_ID == ReglasProd.R_EXPR_REL) {
                tipoAsig = r_exp(ts, h, tv, tf, ctx);
            } else if (h.REGLA_ID == ReglasProd.R_CALL) {
                tipoAsig = r_llamada(ts, h, tv, tf, ctx);
            } else {
                switch (h.TOKEN_ID) {
                    case Tokens.T_VAR:
                        String nombre = ts.getByID(h.REF).NOMBRE;
                        if (declarando) {
                            if (variableDeclarada(nombre, tv, ctx))
                                throw new IdExistenteException(
                                        String.format("La variable \"%s\" ya existe (línea %d).",
                                                nombre, ts.getByID(h.REF).LINE)
                                );
                            if (ctx != null)
                                ctx.meter(nombre, h.REF);
                            else
                                tv.meter(nombre, h.REF);
                        } else if (!variableDeclarada(nombre, tv, ctx))
                            throw new VariableNoDeclaradaException(
                                    String.format("La variable \"%s\" no existe (línea %d).",
                                            nombre, ts.getByID(h.REF).LINE)
                            );
                        break;
                    case Tokens.T_ASSIGN:
                        asignando = true;
                        break;
                    case Tokens.T_INT_CONST:
                    case Tokens.T_REAL_CONST:
                    case Tokens.T_TRUE:
                    case Tokens.T_FALSE:
                    case Tokens.T_STR_CONST:
                        tipoAsig = getTipo(ts, h.REF);
                        break;
                }
            }
        }

        if (asignando && tipoVar != tipoAsig)
            throw new TipoIncorrectoException(
                    String.format("No se puede asignar un valor del tipo %s a una variable del tipo %s (línea %d)",
                            Tipos.nombres[tipoAsig], Tipos.nombres[tipoVar], ts.getByID(na.hijos.get(0).REF).LINE)
            );
    }

    /**
     * Método encargado de análizar semánticamente la esturctura de la llamada de un método.
     *
     * @param ts  Tabla de símbolos
     * @param na  Nodo del árbol
     * @param tv  Tabla de nombres de variables
     * @param tf  Tabla de nombres de funciones
     * @param ctx Función a la que pertenece dicha variable
     * @return
     * @throws ParametrosIncorrectosException
     */
    static int r_llamada(TablaSimbolos ts, NodoArbol na, TablaVar tv, TablaFunc tf, TablaVar ctx) throws ParametrosIncorrectosException {
        ArrayList<Integer> params = new ArrayList<>();
        RegistroFunc reg = tf.get(ts.getByID(na.hijos.get(0).REF).NOMBRE);
        for (NodoArbol h : na.hijos) {
            if (h.REGLA_ID == ReglasProd.R_PARAMS) {
                for (NodoArbol param : h.hijos) {
                    if (param.TOKEN_ID != Tokens.T_COMMA) {
                        params.add(getTipo(ts, param.REF));
                    }
                }
            }
        }
        if (params.size() != reg.T_PARAMS.size())
            throw new ParametrosIncorrectosException(
                    String.format("El número de parámetros en la llamada a la función \"%s\" no coincide con su declaración (línea %d).",
                            ts.getByID(na.hijos.get(0).REF).NOMBRE, ts.getByID(na.hijos.get(0).REF).LINE)
            );
        for (int i = 0; i < params.size(); i++) {
            if (!params.get(i).equals(reg.T_PARAMS.get(i)))
                throw new ParametrosIncorrectosException(
                        String.format("El tipo del parámetro %d en la llamada a la función \"%s\" no coincide con el de su declaración (línea %d).",
                                i + 1, ts.getByID(na.hijos.get(0).REF).NOMBRE, ts.getByID(na.hijos.get(0).REF).LINE)
                );
        }
        return getTipo(ts, na.hijos.get(0).REF);
    }

    /**
     * Método encargado de análizar semánticamente la esturctura de un ciclo for
     *
     * @param ts  Tabla de símbolos
     * @param na  Nodo del árbol
     * @param tv  Tabla de nombres de variables
     * @param tf  Tabla de nombres de funciones
     * @param ctx Función a la que pertenece dicha variable
     * @throws VariableNoDeclaradaException
     * @throws TipoIncorrectoException
     * @throws IdExistenteException
     * @throws ParametrosIncorrectosException
     * @throws SentenciaInesperadaException
     * @throws FuncionSinRetornoException
     * @throws MalRetornoException
     */
    static void r_for(TablaSimbolos ts, NodoArbol na, TablaVar tv, TablaFunc tf, TablaVar ctx) throws VariableNoDeclaradaException, TipoIncorrectoException, IdExistenteException, ParametrosIncorrectosException, SentenciaInesperadaException, FuncionSinRetornoException, MalRetornoException {
        NodoArbol actual = na.hijos.get(2);
        String nombre = ts.getByID(actual.REF).NOMBRE;
        if (!variableDeclarada(nombre, tv, ctx)) {
            throw new VariableNoDeclaradaException(
                    String.format("La variable \"%s\" no existe (línea %d).",
                            nombre, ts.getByID(na.hijos.get(0).REF).LINE)
            );
        }
        if (getTipo(ts, na.hijos.get(2).REF) != Tipos.INT) {
            throw new TipoIncorrectoException(
                    String.format("El contador en un ciclo for debe ser de tipo INT (línea %d).",
                            ts.getByID(na.hijos.get(0).REF).LINE)
            );
        }
        int tipoL = getTipo(ts, na.hijos.get(4).REF);
        int tipoR = getTipo(ts, na.hijos.get(6).REF);
        if (tipoL != Tipos.INT || tipoR != Tipos.INT)
            throw new TipoIncorrectoException(
                    String.format("Los límites del rango en un ciclo for deben ser de tipo INT (línea %d).",
                            ts.getByID(na.hijos.get(0).REF).LINE)
            );
        r_bloque(ts, na.hijos.get(8), tv, tf, ctx);
    }

    /**
     * Método encargado de análizar semánticamente la esturctura de un ciclo while.
     *
     * @param ts  Tabla de símbolos
     * @param na  Nodo del árbol
     * @param tv  Tabla de nombres de variables
     * @param tf  Tabla de nombres de funciones
     * @param ctx Función a la que pertenece dicha variable
     * @throws TipoIncorrectoException
     * @throws IdExistenteException
     * @throws VariableNoDeclaradaException
     * @throws ParametrosIncorrectosException
     * @throws SentenciaInesperadaException
     * @throws FuncionSinRetornoException
     * @throws MalRetornoException
     */
    static void r_while(TablaSimbolos ts, NodoArbol na, TablaVar tv, TablaFunc tf, TablaVar ctx) throws TipoIncorrectoException, IdExistenteException, VariableNoDeclaradaException, ParametrosIncorrectosException, SentenciaInesperadaException, FuncionSinRetornoException, MalRetornoException {
        for (NodoArbol h : na.hijos) {
            switch (na.REGLA_ID) {
                case ReglasProd.R_EXPR_REL:
                    r_exp(ts, h, tv, tf, ctx);
                    break;
                case ReglasProd.R_BLOQUE:
                    r_bloque(ts, h, tv, tf, ctx);
                    break;
            }
        }
    }

    /**
     * Método encargado de análizar semánticamente la esturctura de la instruccion de leer o imprimir.
     *
     * @param ts  Tabla de símbolos
     * @param na  Nodo del árbol
     * @param tv  Tabla de nombres de variables
     * @param tf  Tabla de nombres de funciones
     * @param ctx Función a la que pertenece dicha variable
     * @throws VariableNoDeclaradaException
     */
    static void r_leer_imp(TablaSimbolos ts, NodoArbol na, TablaVar tv, TablaFunc tf, TablaVar ctx) throws VariableNoDeclaradaException {
        for (NodoArbol h : na.hijos) {
            if (h.TOKEN_ID == Tokens.T_VAR) {
                String nombre = ts.getByID(h.REF).NOMBRE;
                if (!variableDeclarada(nombre, tv, ctx)) {
                    throw new VariableNoDeclaradaException(
                            String.format("La variable \"%s\" no existe (línea %d).",
                                    nombre, ts.getByID(h.REF).LINE)
                    );
                }
            }
        }
    }

    /**
     * Método encargado de análizar semánticamente la estructura  de un if.
     *
     * @param ts  Tabla de símbolos
     * @param na  Nodo del árbol
     * @param tv  Tabla de nombres de variables
     * @param tf  Tabla de nombres de funciones
     * @param ctx Función a la que pertenece dicha variable
     * @throws TipoIncorrectoException
     * @throws IdExistenteException
     * @throws VariableNoDeclaradaException
     * @throws ParametrosIncorrectosException
     * @throws SentenciaInesperadaException
     * @throws FuncionSinRetornoException
     * @throws MalRetornoException
     */
    static void r_if(TablaSimbolos ts, NodoArbol na, TablaVar tv, TablaFunc tf, TablaVar ctx) throws TipoIncorrectoException, IdExistenteException, VariableNoDeclaradaException, ParametrosIncorrectosException, SentenciaInesperadaException, FuncionSinRetornoException, MalRetornoException {
        for (NodoArbol h : na.hijos) {
            switch (h.REGLA_ID) {
                case ReglasProd.R_EXPR_REL:
                    r_exp(ts, h, tv, tf, ctx);
                    break;
                case ReglasProd.R_IF:
                    r_if(ts, h, tv, tf, ctx);
                    break;
                case ReglasProd.R_BLOQUE:
                    r_bloque(ts, h, tv, tf, ctx);
                    break;
            }
        }
    }

    /**
     * Método encargado de análizar semánticamente la esturctura  de una expresion.
     *
     * @param ts  Tabla de símbolos
     * @param na  Nodo del árbol
     * @param tv  Tabla de nombres de variables
     * @param tf  Tabla de nombres de funciones
     * @param ctx Función a la que pertenece dicha variable
     * @return
     * @throws TipoIncorrectoException
     */
    private static int r_exp(TablaSimbolos ts, NodoArbol na, TablaVar tv, TablaFunc tf, TablaVar ctx) throws TipoIncorrectoException, VariableNoDeclaradaException {
        NodoArbol l = na.hijos.get(0);
        NodoArbol r = na.hijos.get(2);

        String nombreL = ts.getByID(l.REF).NOMBRE;
        String nombreR = ts.getByID(r.REF).NOMBRE;
        if (l.TOKEN_ID == Tokens.T_VAR && !variableDeclarada(nombreL, tv, ctx))
            throw new VariableNoDeclaradaException(
                    String.format("La variable \"%s\" no existe (línea %d).",
                            nombreL, ts.getByID(l.REF).LINE)
            );
        if (r.TOKEN_ID == Tokens.T_VAR && !variableDeclarada(nombreR, tv, ctx))
            throw new VariableNoDeclaradaException(
                    String.format("La variable \"%s\" no existe (línea %d).",
                            nombreR, ts.getByID(r.REF).LINE)
            );

        int tipoL = getTipo(ts, l.REF);
        int tipoR = getTipo(ts, r.REF);
        if (tipoL != tipoR)
            throw new TipoIncorrectoException(
                    String.format("Los operandos en una expresión aritmética o relacional deben ser del mismo tipo (línea %d).",
                            ts.getByID(l.REF).LINE)
            );
        return tipoL;
    }

    /**
     * Método encargado de análizar semánticamente la esturctura de la declaración de un retorno.
     *
     * @param ts  Tabla de símbolos
     * @param na  Nodo del árbol
     * @param tv  Tabla de nombres de variables
     * @param tf  Tabla de nombres de funciones
     * @param ctx Función a la que pertenece dicha variable
     * @throws TipoIncorrectoException
     * @throws MalRetornoException
     */
    private static void r_retorno(TablaSimbolos ts, NodoArbol na, TablaVar tv, TablaFunc tf, TablaVar ctx) throws TipoIncorrectoException, MalRetornoException {
        int tipoFunc = tf.get(ctx.contexto).T_RETURN;
        int tipoRet = getTipo(ts, na.hijos.get(1).REF);
        if (tipoFunc != tipoRet)
            throw new MalRetornoException(
                    String.format("No se puede retornar un tipo %s en una función del tipo %s (línea %d).",
                            Tipos.nombres[tipoRet], Tipos.nombres[tipoFunc], ts.getByID(na.hijos.get(0).REF).LINE)
            );
    }

    /**
     * Método encargado de análizar semánticamente la estuctura del contenido de un bloque.
     *
     * @param ts  Tabla de símbolos
     * @param na  Nodo del árbol
     * @param tv  Tabla de nombres de variables
     * @param tf  Tabla de nombres de funciones
     * @param ctx Función a la que pertenece dicha variable
     * @throws TipoIncorrectoException
     * @throws VariableNoDeclaradaException
     * @throws IdExistenteException
     * @throws ParametrosIncorrectosException
     * @throws SentenciaInesperadaException
     * @throws FuncionSinRetornoException
     * @throws MalRetornoException
     */
    private static void r_bloque(TablaSimbolos ts, NodoArbol na, TablaVar tv, TablaFunc tf, TablaVar ctx) throws TipoIncorrectoException, VariableNoDeclaradaException, IdExistenteException, ParametrosIncorrectosException, SentenciaInesperadaException, FuncionSinRetornoException, MalRetornoException {
        returnFound = false;
        for (NodoArbol actual : na.hijos) {
            if (actual.REGLA_ID > 0) {
                switch (actual.REGLA_ID) {
                    case ReglasProd.R_ASIGNACION:
                        r_asig(ts, actual, tv, tf, ctx);
                        break;
                    case ReglasProd.R_CALL:
                        r_llamada(ts, actual, tv, tf, ctx);
                        break;
                    case ReglasProd.R_IF:
                        r_if(ts, actual, tv, tf, ctx);
                        break;
                    case ReglasProd.R_WHILE:
                        r_while(ts, actual, tv, tf, ctx);
                        break;
                    case ReglasProd.R_FOR:
                        r_for(ts, actual, tv, tf, ctx);
                        break;
                    case ReglasProd.R_LECTURA:
                    case ReglasProd.R_IMPRESION:
                        r_leer_imp(ts, actual, tv, tf, ctx);
                        break;
                    case ReglasProd.R_RETORNO:
                        if (ctx != null)
                            r_retorno(ts, actual, tv, tf, ctx);
                        else
                            throw new SentenciaInesperadaException(
                                    String.format("No se puede retornar un valor fuera de una función (línea %d).",
                                            ts.getByID(actual.hijos.get(1).REF).LINE)
                            );
                        returnFound = true;
                        break;
                    case ReglasProd.R_METODO:
                        throw new SentenciaInesperadaException("No se puede declarar una nueva función dentro de un bloque.");
                }
            }
        }
        if (!returnFound && ctx != null && tf.get(ctx.contexto).T_RETURN != Tipos.VOID) {
            throw new FuncionSinRetornoException(
                    String.format("La función \"%s\" retorna un tipo %s, sin embargo no se encontró la sentencia RETURN en su declaración.",
                            ctx.contexto, Tipos.nombres[tf.get(ctx.contexto).T_RETURN])
            );
        }
    }

    /**
     * Metodo para verificar si una variable ya ha sido declarada
     *
     * @param nombre El nombre de la variable
     * @param tv     Tabla de nombres de variables
     * @param ctx    Función a la que pertenece dicha variable
     * @return TRUE si la variable ha sido declarada
     */
    private static boolean variableDeclarada(String nombre, TablaVar tv, TablaVar ctx) {
        return (ctx != null && ctx.contiene(nombre)) || tv.contiene(nombre);
    }

    /**
     * Metodo con el cual obtendremos el tipo de Token a analizar
     *
     * @param ts  Tabla de simbolos
     * @param ref Referencia de la variable
     * @return Retorna el tipo del token analizado
     */
    private static int getTipo(TablaSimbolos ts, String ref) {
        RegistroTS reg = ts.getByID(ref);
        int tipo = reg.TIPO;
        if (tipo == Tipos.REF)
            tipo = ts.getByID(reg.VAL).TIPO;
        return tipo;
    }

    /**
     * Metodo para identificar si el token es una palaabra reservada
     *
     * @param tokenId La referencia en la tabla de símbolos
     * @return regresara el Token ID si es una palabra reservada
     */
    private static boolean esReservadaTipo(int tokenId) {
        return tokenId == Tokens.T_INT
                | tokenId == Tokens.T_REAL
                | tokenId == Tokens.T_BOOL
                | tokenId == Tokens.T_STRING;
    }
}