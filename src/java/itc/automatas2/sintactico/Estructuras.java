package itc.automatas2.sintactico;

import itc.automatas2.estructuras.*;
import itc.automatas2.lexico.Tokens;


public class Estructuras {

    public static int cont;

    /**
     * Analiza la estructura sintáctica del if y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException    cuando se encuentra un token inesperado en la secuencia.
     * @throws EstructuraNoReconocidaException cuando no se puede identificar una sentencia o estructura dentro de un bloque.
     */
    public static ArbolSintactico r_if(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException, EstructuraNoReconocidaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_IF;
        a.raiz = actual;

        reg = ts.get(cont);
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_IF;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_LPAREN) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_LPAREN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        ArbolSintactico a2 = r_expr_rel(++cont, ts);
        a.meter(a.raiz, a2.raiz);

        reg = ts.get(cont);
        if (reg.TOKEN_ID != Tokens.T_RPAREN) {
            PilaErrores.meter(new RegistroErr(230, reg.LINE, reg.NOMBRE));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_RPAREN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        a2 = r_bloque(++cont, ts);
        a.meter(a.raiz, a2.raiz);
        reg = ts.get(cont);
        if (reg != null) {
            if (reg.TOKEN_ID != Tokens.T_ELSE) {
                return a;
            }
            actual = new NodoArbol();
            actual.TOKEN_ID = Tokens.T_ELSE;
            actual.REF = reg.ID;
            a.meter(a.raiz, actual);

            cont++;
            a2 = r_else(ts);
            a.meter(a.raiz, a2.raiz);
        }
        return a;
    }

    /**
     * Analiza la estructura sintáctica del else y regresa su árbol
     *
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException    cuando se encuentra un token inesperado en la secuencia.
     * @throws EstructuraNoReconocidaException cuando no se puede identificar una sentencia o estructura dentro de un bloque.
     */
    private static ArbolSintactico r_else(TablaSimbolos ts) throws SecuenciaIncorrectaException, EstructuraNoReconocidaException {
        RegistroTS reg = ts.get(cont);
        if (reg.TOKEN_ID == Tokens.T_IF) {
            return r_if(cont, ts);
        } else {
            return r_bloque(cont, ts);
        }
    }

    /**
     * Analiza la estructura sintáctica del bloque y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException    cuando se encuentra un token inesperado en la secuencia.
     * @throws EstructuraNoReconocidaException cuando no se puede identificar una sentencia o estructura dentro de un bloque.
     */
    public static ArbolSintactico r_bloque(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException, EstructuraNoReconocidaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_BLOQUE;
        a.raiz = actual;

        reg = ts.get(cont);
        if (reg.TOKEN_ID != Tokens.T_LBRACE) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        }

        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_LBRACE;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);
        ArbolSintactico a2;
        cont++;

        reg = ts.get(cont + 1);
        if (reg.TOKEN_ID == Tokens.T_RBRACE) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        }

        do {
            switch (ObtenerReglaProd(cont, ts)) {
                case ReglasProd.R_CALL:
                    a2 = r_call(cont, ts);
                    a.meter(a.raiz, a2.raiz);
                    break;
                case ReglasProd.R_RETORNO:
                    a2 = r_retorno(cont, ts);
                    a.meter(a.raiz, a2.raiz);
                    break;
                case ReglasProd.R_IF:
                    a2 = r_if(cont, ts);
                    a.meter(a.raiz, a2.raiz);
                    break;
                case ReglasProd.R_FOR:
                    a2 = r_for(cont, ts);
                    a.meter(a.raiz, a2.raiz);
                    break;
                case ReglasProd.R_WHILE:
                    a2 = r_while(cont, ts);
                    a.meter(a.raiz, a2.raiz);
                    break;
                case ReglasProd.R_LECTURA:
                    a2 = r_lectura(cont, ts);
                    a.meter(a.raiz, a2.raiz);
                    break;
                case ReglasProd.R_IMPRESION:
                    a2 = r_impresion(cont, ts);
                    a.meter(a.raiz, a2.raiz);
                    break;
                case ReglasProd.R_ASIGNACION:
                    a2 = r_asignacion(cont, ts);
                    a.meter(a.raiz, a2.raiz);
                    break;
            }
            reg = ts.get(cont);
        } while (reg.TOKEN_ID != Tokens.T_RBRACE);

        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_RBRACE;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        cont++;
        return a;
    }

    /**
     * Analiza la estructura sintáctica del call y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException cuando se encuentra un token inesperado en la secuencia.
     */
    public static ArbolSintactico r_call(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_CALL;
        a.raiz = actual;

        reg = ts.get(cont);
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_FUN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_LPAREN) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_LPAREN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_RPAREN) {
            ArbolSintactico a2 = r_params(cont, ts);
            a.meter(a.raiz, a2.raiz);
        }
        reg = ts.get(cont);
        if (reg.TOKEN_ID != Tokens.T_RPAREN) {
            PilaErrores.meter(new RegistroErr(230, reg.LINE, reg.NOMBRE));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_RPAREN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);
        cont++;
        return a;
    }

    /**
     * Analiza la estructura sintáctica del metodo y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException    cuando se encuentra un token inesperado en la secuencia.
     * @throws EstructuraNoReconocidaException cuando no se puede identificar una sentencia o estructura dentro de un bloque.
     */
    public static ArbolSintactico r_metodo(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException, EstructuraNoReconocidaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_METODO;
        a.raiz = actual;

        reg = ts.get(cont);
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_FUNC;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_FUN) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_FUN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_LPAREN) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_LPAREN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_RPAREN) {
            ArbolSintactico a2 = r_args(cont, ts);
            a.meter(a.raiz, a2.raiz);
        }
        reg = ts.get(cont);
        if (reg.TOKEN_ID != Tokens.T_RPAREN) {
            PilaErrores.meter(new RegistroErr(230, reg.LINE, reg.NOMBRE));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_RPAREN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID == Tokens.T_COLON) {
            actual = new NodoArbol();
            actual.TOKEN_ID = Tokens.T_COLON;
            actual.REF = reg.ID;
            a.meter(a.raiz, actual);
            reg = ts.get(++cont);
            switch (reg.TOKEN_ID) {
                case Tokens.T_INT:
                case Tokens.T_REAL:
                case Tokens.T_BOOL:
                case Tokens.T_STRING:
                    actual = new NodoArbol();
                    actual.TOKEN_ID = reg.TOKEN_ID;
                    actual.REF = reg.ID;
                    a.meter(a.raiz, actual);
                    break;
                default:
                    PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                    throw new SecuenciaIncorrectaException();
            }
            cont++;
        }
        ArbolSintactico a2 = r_bloque(cont, ts);
        a.meter(a.raiz, a2.raiz);
        return a;
    }

    /**
     * Analiza la estructura sintáctica del retorno y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException cuando se encuentra un token inesperado en la secuencia.
     */
    public static ArbolSintactico r_retorno(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_RETORNO;
        a.raiz = actual;

        reg = ts.get(cont);
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_RETURN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        switch (reg.TOKEN_ID) {
            case Tokens.T_VAR:
            case Tokens.T_INT_CONST:
            case Tokens.T_REAL_CONST:
            case Tokens.T_BOOL_CONST:
            case Tokens.T_STR_CONST:
                actual = new NodoArbol();
                actual.TOKEN_ID = reg.TOKEN_ID;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                break;
            default:
                PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                throw new SecuenciaIncorrectaException();
        }

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_SEMICOLON) {
            PilaErrores.meter(new RegistroErr(220, reg.LINE, reg.NOMBRE));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_SEMICOLON;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);
        cont++;
        return a;
    }

    /**
     * Analiza la estructura sintáctica del for y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException    cuando se encuentra un token inesperado en la secuencia.
     * @throws EstructuraNoReconocidaException cuando no se puede identificar una sentencia o estructura dentro de un bloque.
     */
    public static ArbolSintactico r_for(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException, EstructuraNoReconocidaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_FOR;
        a.raiz = actual;

        reg = ts.get(cont);
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_FOR;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_LPAREN) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_LPAREN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_VAR) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_VAR;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_IN) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_IN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        switch (reg.TOKEN_ID) {
            case Tokens.T_VAR:
            case Tokens.T_INT_CONST:
                actual = new NodoArbol();
                actual.TOKEN_ID = reg.TOKEN_ID;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                break;
            default:
                PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                throw new SecuenciaIncorrectaException();
        }

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_RANGE) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_RANGE;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        switch (reg.TOKEN_ID) {
            case Tokens.T_VAR:
            case Tokens.T_INT_CONST:
                actual = new NodoArbol();
                actual.TOKEN_ID = reg.TOKEN_ID;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                break;
            default:
                PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                throw new SecuenciaIncorrectaException();
        }

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_RPAREN) {
            PilaErrores.meter(new RegistroErr(230, reg.LINE, reg.NOMBRE));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_RPAREN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);


        ArbolSintactico a2 = r_bloque(++cont, ts);
        a.meter(a.raiz, a2.raiz);
        return a;
    }

    /**
     * Analiza la estructura sintáctica del while y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException    cuando se encuentra un token inesperado en la secuencia.
     * @throws EstructuraNoReconocidaException cuando no se puede identificar una sentencia o estructura dentro de un bloque.
     */
    public static ArbolSintactico r_while(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException, EstructuraNoReconocidaException {

        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_WHILE;
        a.raiz = actual;

        reg = ts.get(cont);
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_WHILE;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_LPAREN) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_LPAREN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        ArbolSintactico a2 = r_expr_rel(++cont, ts);
        a.meter(a.raiz, a2.raiz);

        reg = ts.get(cont);
        if (reg.TOKEN_ID != Tokens.T_RPAREN) {
            PilaErrores.meter(new RegistroErr(230, reg.LINE, reg.NOMBRE));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_RPAREN;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        a2 = r_bloque(++cont, ts);
        a.meter(a.raiz, a2.raiz);

        return a;

    }

    /**
     * Analiza la estructura sintáctica de la lectura y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException cuando se encuentra un token inesperado en la secuencia.
     */
    public static ArbolSintactico r_lectura(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_LECTURA;
        a.raiz = actual;

        reg = ts.get(cont);
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_READ;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_VAR) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_VAR;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_SEMICOLON) {
            PilaErrores.meter(new RegistroErr(210, reg.LINE, reg.NOMBRE));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_SEMICOLON;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);
        cont++;
        return a;
    }

    /**
     * Analiza la estructura sintáctica de la impresion y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException cuando se encuentra un token inesperado en la secuencia.
     */
    public static ArbolSintactico r_impresion(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_IMPRESION;
        a.raiz = actual;

        reg = ts.get(cont);
        actual = new NodoArbol();
        actual.TOKEN_ID = reg.TOKEN_ID;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);


        reg = ts.get(++cont);
        switch (reg.TOKEN_ID) {
            case Tokens.T_VAR:
            case Tokens.T_STR_CONST:
                actual = new NodoArbol();
                actual.TOKEN_ID = reg.TOKEN_ID;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                break;
            default:
                PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                throw new SecuenciaIncorrectaException();
        }

        reg = ts.get(++cont);
        if (reg.TOKEN_ID != Tokens.T_SEMICOLON) {
            PilaErrores.meter(new RegistroErr(210, reg.LINE, reg.NOMBRE));
            throw new SecuenciaIncorrectaException();
        } else {
            actual = new NodoArbol();
            actual.TOKEN_ID = reg.TOKEN_ID;
            actual.REF = reg.ID;
            a.meter(a.raiz, actual);
        }
        cont++;
        return a;
    }

    /**
     * Analiza la estructura sintáctica de los parametros y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException cuando se encuentra un token inesperado en la secuencia.
     */
    public static ArbolSintactico r_params(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_PARAMS;
        a.raiz = actual;

        do {
            reg = ts.get(cont);
            switch (reg.TOKEN_ID) {
                case Tokens.T_VAR:
                case Tokens.T_INT_CONST:
                case Tokens.T_REAL_CONST:
                case Tokens.T_BOOL_CONST:
                case Tokens.T_STR_CONST:
                    actual = new NodoArbol();
                    actual.TOKEN_ID = reg.TOKEN_ID;
                    actual.REF = reg.ID;
                    a.meter(a.raiz, actual);
                    break;
                default:
                    PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                    throw new SecuenciaIncorrectaException();
            }

            reg = ts.get(++cont);
            if (reg.TOKEN_ID == Tokens.T_COMMA) {
                actual = new NodoArbol();
                actual.TOKEN_ID = Tokens.T_COMMA;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                cont++;
            }
        } while (reg.TOKEN_ID == Tokens.T_COMMA);
        return a;
    }

    /**
     * Analiza la estructura sintáctica de los argumentos y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException cuando se encuentra un token inesperado en la secuencia.
     */
    public static ArbolSintactico r_args(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_ARGS;
        a.raiz = actual;

        do {
            reg = ts.get(cont);
            switch (reg.TOKEN_ID) {
                case Tokens.T_INT:
                case Tokens.T_REAL:
                case Tokens.T_BOOL:
                case Tokens.T_STRING:
                    actual = new NodoArbol();
                    actual.TOKEN_ID = reg.TOKEN_ID;
                    actual.REF = reg.ID;
                    a.meter(a.raiz, actual);
                    break;
                default:
                    PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                    throw new SecuenciaIncorrectaException();
            }

            reg = ts.get(++cont);
            if (reg.TOKEN_ID != Tokens.T_VAR) {
                PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                throw new SecuenciaIncorrectaException();
            } else {
                actual = new NodoArbol();
                actual.TOKEN_ID = Tokens.T_VAR;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
            }

            reg = ts.get(++cont);
            if (reg.TOKEN_ID == Tokens.T_COMMA) {
                actual = new NodoArbol();
                actual.TOKEN_ID = Tokens.T_COMMA;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                cont++;
            }
        } while (reg.TOKEN_ID == Tokens.T_COMMA);
        return a;

    }

    /**
     * Analiza la estructura sintáctica de la asignacion y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException cuando se encuentra un token inesperado en la secuencia.
     */
    public static ArbolSintactico r_asignacion(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_ASIGNACION;
        a.raiz = actual;

        reg = ts.get(cont);

        switch (reg.TOKEN_ID) {
            case Tokens.T_INT:
            case Tokens.T_REAL:
            case Tokens.T_BOOL:
            case Tokens.T_STRING:
                actual = new NodoArbol();
                actual.TOKEN_ID = reg.TOKEN_ID;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                reg = ts.get(++cont);
                break;
        }

        if (reg.TOKEN_ID != Tokens.T_VAR) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        } else {
            actual = new NodoArbol();
            actual.TOKEN_ID = Tokens.T_VAR;
            actual.REF = reg.ID;
            a.meter(a.raiz, actual);
        }

        reg = ts.get(++cont);

        if (reg.TOKEN_ID == Tokens.T_SEMICOLON) {
            actual = new NodoArbol();
            actual.TOKEN_ID = Tokens.T_SEMICOLON;
            actual.REF = reg.ID;
            a.meter(a.raiz, actual);
            cont++;
            return a;
        } else if (reg.TOKEN_ID != Tokens.T_ASSIGN) {
            PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
            throw new SecuenciaIncorrectaException();
        } else {
            actual = new NodoArbol();
            actual.TOKEN_ID = Tokens.T_ASSIGN;
            actual.REF = reg.ID;
            a.meter(a.raiz, actual);
        }

        cont++;
        reg = ts.get(cont + 1);
        ArbolSintactico a2;
        switch (reg.TOKEN_ID) {
            case Tokens.T_PLUS:
            case Tokens.T_MINUS:
            case Tokens.T_STAR:
            case Tokens.T_DIV:
                a2 = r_expr_aritm(cont, ts);
                a.meter(a.raiz, a2.raiz);
                break;
            case Tokens.T_EQ:
            case Tokens.T_LT:
            case Tokens.T_GT:
            case Tokens.T_NE:
            case Tokens.T_LTE:
            case Tokens.T_GTE:
                a2 = r_expr_rel(cont, ts);
                a.meter(a.raiz, a2.raiz);
                break;
            default:
                reg = ts.get(cont);
                switch (reg.TOKEN_ID) {
                    case Tokens.T_INT_CONST:
                    case Tokens.T_REAL_CONST:
                    case Tokens.T_BOOL_CONST:
                    case Tokens.T_STR_CONST:
                    case Tokens.T_TRUE:
                    case Tokens.T_FALSE:
                    case Tokens.T_VAR:
                        actual = new NodoArbol();
                        actual.TOKEN_ID = reg.TOKEN_ID;
                        actual.REF = reg.ID;
                        a.meter(a.raiz, actual);
                        cont++;
                        break;
                    case Tokens.T_FUN:
                        a2 = r_call(cont, ts);
                        a.meter(a.raiz, a2.raiz);
                        break;
                }
        }
        reg = ts.get(cont);
        if (reg.TOKEN_ID != Tokens.T_SEMICOLON) {
            PilaErrores.meter(new RegistroErr(210, reg.LINE, reg.NOMBRE));
            throw new SecuenciaIncorrectaException();
        }
        actual = new NodoArbol();
        actual.TOKEN_ID = Tokens.T_SEMICOLON;
        actual.REF = reg.ID;
        a.meter(a.raiz, actual);
        cont++;
        return a;

    }

    /**
     * Analiza la estructura sintáctica de la exrasion relacional y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException    cuando se encuentra un token inesperado en la secuencia.
     * @throws EstructuraNoReconocidaException cuando no se puede identificar una sentencia o estructura dentro de un bloque.
     */
    public static ArbolSintactico r_expr_rel(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_EXPR_REL;
        a.raiz = actual;

        reg = ts.get(cont);

        switch (reg.TOKEN_ID) {
            case Tokens.T_INT_CONST:
            case Tokens.T_REAL_CONST:
            case Tokens.T_VAR:
            case Tokens.T_STR_CONST:
                actual = new NodoArbol();
                actual.TOKEN_ID = reg.TOKEN_ID;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                break;
            default:
                PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                throw new SecuenciaIncorrectaException();
        }
        reg = ts.get(++cont);

        switch (reg.TOKEN_ID) {
            case Tokens.T_GTE:
            case Tokens.T_LTE:
            case Tokens.T_GT:
            case Tokens.T_LT:
            case Tokens.T_EQ:
            case Tokens.T_NE:
                actual = new NodoArbol();
                actual.TOKEN_ID = reg.TOKEN_ID;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                break;
            default:
                PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                throw new SecuenciaIncorrectaException();
        }
        reg = ts.get(++cont);

        switch (reg.TOKEN_ID) {
            case Tokens.T_INT_CONST:
            case Tokens.T_REAL_CONST:
            case Tokens.T_VAR:
            case Tokens.T_STR_CONST:
                actual = new NodoArbol();
                actual.TOKEN_ID = reg.TOKEN_ID;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                break;
            default:
                PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                throw new SecuenciaIncorrectaException();
        }
        cont++;
        return a;
    }

    /**
     * Analiza la estructura sintáctica de la expresion aritmetica y regresa su árbol
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException cuando se encuentra un token inesperado en la secuencia.
     */
    public static ArbolSintactico r_expr_aritm(int i, TablaSimbolos ts) throws SecuenciaIncorrectaException {
        cont = i;
        ArbolSintactico a = new ArbolSintactico();
        RegistroTS reg;
        NodoArbol actual;
        actual = new NodoArbol();
        actual.REGLA_ID = ReglasProd.R_EXPR_ARITM;
        a.raiz = actual;

        reg = ts.get(cont);

        switch (reg.TOKEN_ID) {
            case Tokens.T_INT_CONST:
            case Tokens.T_REAL_CONST:
            case Tokens.T_VAR:
            case Tokens.T_STR_CONST:
                actual = new NodoArbol();
                actual.TOKEN_ID = reg.TOKEN_ID;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                break;
            default:
                PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                throw new SecuenciaIncorrectaException();
        }
        reg = ts.get(++cont);

        switch (reg.TOKEN_ID) {
            case Tokens.T_PLUS:
            case Tokens.T_MINUS:
            case Tokens.T_STAR:
            case Tokens.T_DIV:
                actual = new NodoArbol();
                actual.TOKEN_ID = reg.TOKEN_ID;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                break;
            default:
                PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                throw new SecuenciaIncorrectaException();
        }
        reg = ts.get(++cont);

        switch (reg.TOKEN_ID) {
            case Tokens.T_INT_CONST:
            case Tokens.T_REAL_CONST:
            case Tokens.T_VAR:
            case Tokens.T_STR_CONST:
                actual = new NodoArbol();
                actual.TOKEN_ID = reg.TOKEN_ID;
                actual.REF = reg.ID;
                a.meter(a.raiz, actual);
                break;
            default:
                PilaErrores.meter(new RegistroErr(240, reg.LINE, reg.NOMBRE, reg.TOKEN_ID));
                throw new SecuenciaIncorrectaException();
        }
        cont++;
        return a;
    }


    /**
     * Obtiene la regla de produccion a la que pertenece, con base en el primer token
     *
     * @param i  el índice del primer token en la tabla
     * @param ts la tabla de símbolos
     * @return el árbol sintáctico de la estructura
     * @throws SecuenciaIncorrectaException    cuando se encuentra un token inesperado en la secuencia.
     * @throws EstructuraNoReconocidaException cuando no se puede identificar una sentencia o estructura dentro de un bloque.
     */
    public static int ObtenerReglaProd(int i, TablaSimbolos ts) throws EstructuraNoReconocidaException {
        RegistroTS reg;
        cont = i;
        reg = ts.get(cont);

        switch (reg.TOKEN_ID) {
            case Tokens.T_FUN:
                return ReglasProd.R_CALL;
            case Tokens.T_FUNC:
                return ReglasProd.R_METODO;
            case Tokens.T_RETURN:
                return ReglasProd.R_RETORNO;
            case Tokens.T_IF:
                return ReglasProd.R_IF;
            case Tokens.T_FOR:
                return ReglasProd.R_FOR;
            case Tokens.T_WHILE:
                return ReglasProd.R_WHILE;
            case Tokens.T_READ:
                return ReglasProd.R_LECTURA;
            case Tokens.T_PRINT:
            case Tokens.T_PRINTLN:
                return ReglasProd.R_IMPRESION;
            case Tokens.T_VAR:
            case Tokens.T_REAL:
            case Tokens.T_INT:
            case Tokens.T_BOOL:
            case Tokens.T_STRING:
                return ReglasProd.R_ASIGNACION;
        }
        throw new EstructuraNoReconocidaException();
    }
}