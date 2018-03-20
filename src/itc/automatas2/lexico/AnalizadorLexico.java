package itc.automatas2.lexico;

import itc.automatas2.estructuras.PilaErrores;
import itc.automatas2.estructuras.RegistroErr;
import itc.automatas2.estructuras.RegistroTS;
import itc.automatas2.estructuras.TablaSimbolos;
import itc.automatas2.lexico.automatas.*;
import itc.automatas2.misc.BaseErrores;
import itc.automatas2.misc.Error;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AnalizadorLexico {
    private Tokenizador tok;
    private TablaSimbolos tS;
    private PilaErrores pErr;
    private ArrayList<AutomataToken> automatas;

    /**
     * Constructor de la clase
     */
    public AnalizadorLexico() {
        automatas = new ArrayList<>();
        automatas.add(new AutomataCadena());
        automatas.add(new AutomataDecimal());
        automatas.add(new AutomataNumero());
        automatas.add(new AutomataIdentificador());
        tS = new TablaSimbolos();
        pErr = new PilaErrores();
    }


    /**
     * Analiza un archivo de código fuente.
     *
     * @param ruta La ruta del archivo.
     * @return <code>true</code> si se aceptó el código, <code>false</code> si fue rechazado.
     */
    public boolean analizar(String ruta) {
        String token;
        boolean error = false;
        try {
            tok = new Tokenizador(ruta);
            while ((token = tok.siguienteToken()) != null) {
                // Bandera para saber si el token ya fue reconocido
                boolean found = false;

                // Si el token empieza como comentario se ignora por completo el resto de la línea
                if (token.startsWith("//") || token.startsWith("#")) {
                    tok.siguienteLinea();
                    continue;
                }

                if (!token.matches("[\u0020-\u007E]+")) { // Se verifica que el token contenga símbolos del alfabeto (ascii)
                    pErr.meter(new RegistroErr(120, tok.archivo.getNoLinea(), token)); // Si no, se crea un nuevo error
                    error = true;
                    continue;
                }

                // Se reconocen palabras reservadas y símbolos primero
                switch (token) {
                    case "if":
                        tS.meter(token, new RegistroTS(token, Tokens.T_IF, 0));
                        found = true;
                        break;
                    case "else":
                        tS.meter(token, new RegistroTS(token, Tokens.T_ELSE, 0));
                        found = true;
                        break;
                    case "while":
                        tS.meter(token, new RegistroTS(token, Tokens.T_WHILE, 0));
                        found = true;
                        break;
                    case "for":
                        tS.meter(token, new RegistroTS(token, Tokens.T_FOR, 0));
                        found = true;
                        break;
                    case "func":
                        tS.meter(token, new RegistroTS(token, Tokens.T_FUNC, 0));
                        found = true;
                        break;
                    case "return":
                        tS.meter(token, new RegistroTS(token, Tokens.T_RETURN, 0));
                        found = true;
                        break;
                    case "true":
                        tS.meter(token, new RegistroTS(token, Tokens.T_TRUE, 0));
                        found = true;
                        break;
                    case "false":
                        tS.meter(token, new RegistroTS(token, Tokens.T_FALSE, 0));
                        found = true;
                        break;
                    case "null":
                        tS.meter(token, new RegistroTS(token, Tokens.T_NULL, 0));
                        found = true;
                        break;
                    case "int":
                        tS.meter(token, new RegistroTS(token, Tokens.T_INT, 0));
                        found = true;
                        break;
                    case "real":
                        tS.meter(token, new RegistroTS(token, Tokens.T_REAL, 0));
                        found = true;
                        break;
                    case "bool":
                        tS.meter(token, new RegistroTS(token, Tokens.T_BOOL, 0));
                        found = true;
                        break;
                    case "string":
                        tS.meter(token, new RegistroTS(token, Tokens.T_STRING, 0));
                        found = true;
                        break;
                    case "print":
                        tS.meter(token, new RegistroTS(token, Tokens.T_PRINT, 0));
                        found = true;
                        break;
                    case "println":
                        tS.meter(token, new RegistroTS(token, Tokens.T_PRINTLN, 0));
                        found = true;
                        break;
                    case "read":
                        tS.meter(token, new RegistroTS(token, Tokens.T_REAL, 0));
                        found = true;
                        break;
                    case ":":
                        tS.meter(token, new RegistroTS(token, Tokens.T_COLON, 0));
                        found = true;
                        break;
                    case ";":
                        tS.meter(token, new RegistroTS(token, Tokens.T_SEMICOLON, 0));
                        found = true;
                        break;
                    case "(":
                        tS.meter(token, new RegistroTS(token, Tokens.T_LPAREN, 0));
                        found = true;
                        break;
                    case ")":
                        tS.meter(token, new RegistroTS(token, Tokens.T_RPAREN, 0));
                        found = true;
                        break;
                    case "{":
                        tS.meter(token, new RegistroTS(token, Tokens.T_LBRACE, 0));
                        found = true;
                        break;
                    case "}":
                        tS.meter(token, new RegistroTS(token, Tokens.T_RBRACE, 0));
                        found = true;
                        break;
                    case "[":
                        tS.meter(token, new RegistroTS(token, Tokens.T_LBRACKET, 0));
                        found = true;
                        break;
                    case "]":
                        tS.meter(token, new RegistroTS(token, Tokens.T_RBRACKET, 0));
                        found = true;
                        break;
                    case "<=":
                        tS.meter(token, new RegistroTS(token, Tokens.T_LTE, 0));
                        found = true;
                        break;
                    case ">=":
                        tS.meter(token, new RegistroTS(token, Tokens.T_GTE, 0));
                        found = true;
                        break;
                    case "!=":
                        tS.meter(token, new RegistroTS(token, Tokens.T_NE, 0));
                        found = true;
                        break;
                    case "<":
                        tS.meter(token, new RegistroTS(token, Tokens.T_LT, 0));
                        found = true;
                        break;
                    case ">":
                        tS.meter(token, new RegistroTS(token, Tokens.T_GT, 0));
                        found = true;
                        break;
                    case "==":
                        tS.meter(token, new RegistroTS(token, Tokens.T_EQ, 0));
                        found = true;
                        break;
                    case "=":
                        tS.meter(token, new RegistroTS(token, Tokens.T_ASSIGN, 0));
                        found = true;
                        break;
                    case "--":
                        tS.meter(token, new RegistroTS(token, Tokens.T_DEC, 0));
                        found = true;
                        break;
                    case "++":
                        tS.meter(token, new RegistroTS(token, Tokens.T_INC, 0));
                        found = true;
                        break;
                    case "!":
                        tS.meter(token, new RegistroTS(token, Tokens.T_NOT, 0));
                        found = true;
                        break;
                    case "+":
                        tS.meter(token, new RegistroTS(token, Tokens.T_PLUS, 0));
                        found = true;
                        break;
                    case "-":
                        tS.meter(token, new RegistroTS(token, Tokens.T_MINUS, 0));
                        found = true;
                        break;
                    case "*":
                        tS.meter(token, new RegistroTS(token, Tokens.T_STAR, 0));
                        found = true;
                        break;
                    case "/":
                        tS.meter(token, new RegistroTS(token, Tokens.T_DIV, 0));
                        found = true;
                        break;
                    case "->":
                        tS.meter(token, new RegistroTS(token, Tokens.T_RANGE, 0));
                        found = true;
                        break;
                    case ",":
                        tS.meter(token, new RegistroTS(token, Tokens.T_COMMA, 0));
                        found = true;
                        break;
                }

                // Se prosigue con la evaluacion en los autómatas
                if (!found) {
                    for (AutomataToken a : automatas) {
                        if (!found && a.ejecutar(token)) {
                            switch (a.getTokenId()) {
                                case Tokens.T_VAR:
                                    if (!tS.contiene(token)) {
                                        tS.meter(token, new RegistroTS(token, Tokens.T_VAR, 0));
                                    }
                                    found = true;
                                    break;
                                case Tokens.T_STR_CONST:
                                    tS.meter(token, new RegistroTS(token, Tokens.T_STR_CONST, 0));
                                    found = true;
                                    break;
                                case Tokens.T_INT_CONST:
                                    tS.meter(token, new RegistroTS(token, Tokens.T_INT_CONST, 0));
                                    found = true;
                                    break;
                                case Tokens.T_REAL_CONST:
                                    tS.meter(token, new RegistroTS(token, Tokens.T_REAL_CONST, 0));
                                    found = true;
                                    break;
                            }
                        }
                    }
                }
                // Si el token no fue reconocido, se marca error.
                if (!found) {
                    pErr.meter(new RegistroErr(110, tok.archivo.getNoLinea(), token));
                    error = true;
                }
            }
        } catch (FileNotFoundException e) {
            pErr.meter(new RegistroErr(10, 0, ruta));
            error = true;
        } catch (IOException e) {
            pErr.meter(new RegistroErr(11, 0, ruta));
            error = true;
        }
        return !error;
    }

    /**
     * Imprime la pila de errores en la salida estándar.
     */
    public void imprimirErrores() {
        if (pErr.size() > 0) {
            System.err.println("Se encontraron errores durante el análisis léxico:");
            while (pErr.size() > 0) {
                RegistroErr reg = pErr.sacar();
                Error err = BaseErrores.getError(reg.ERR_ID);
                switch (reg.ERR_ID) {
                    //Genéricos
                    case 10:
                        err.setReason(String.format("(RUTA: %s)", reg.LEXEMA));
                        break;
                    case 11:
                        err.setReason(String.format("(RUTA: %s)", reg.LEXEMA));
                        break;
                    //Léxicos
                    case 110:
                        err.setReason(String.format("No se pudo identificar el token \"%s\" en la línea %d", reg.LEXEMA, reg.LINEA_N));
                        break;
                    case 120:
                        err.setReason(String.format("El token '%s' en la línea %d contiene símbolos no reconocibles (fuera del código ASCII). ", reg.LEXEMA, reg.LINEA_N));
                        break;
                }
                System.err.println(err);
            }
        } else {
            System.out.println("No se encontraron errores durante el análisis léxico");
        }
    }

    /**
     * Imprime la tabla de símbolos en la salida estándar.
     */
    public void imprimirSimbolos() {
        if (tS.size() > 0) {
            System.out.println("------------------------------------------------------------");
            System.out.println("|                    TABLA DE SÍMBOLOS                     |");
            System.out.println("------------------------------------------------------------");
            System.out.printf("| %-30s | %8s | %12s |\n", "NOMBRE O LEXEMA", "TOKEN ID", "TIPO DE DATO");
            System.out.println("------------------------------------------------------------");
            for (RegistroTS reg : tS.registros()) {
                System.out.printf("| %-30s | %8d | %12d |\n", reg.NOMBRE, reg.TOKEN_ID, reg.TIPO);
            }
            System.out.println("------------------------------------------------------------");
        } else {
            System.out.println("No se encontraron símbolos en el código");
        }
    }
}