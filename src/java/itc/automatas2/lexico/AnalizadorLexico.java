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
    public TablaSimbolos tS;
    private ArrayList<AutomataToken> automatas;
    private AutomataReservadas automataReservadas;

    /**
     * Constructor de la clase
     */
    public AnalizadorLexico() {
        automatas = new ArrayList<>();
        automatas.add(new AutomataCadena());
        automatas.add(new AutomataDecimal());
        automatas.add(new AutomataNumero());
        automatas.add(new AutomataIdentificador());
        automataReservadas = new AutomataReservadas();
    }

    /**
     * Analiza un archivo de código fuente.
     *
     * @param ruta La ruta del archivo.
     * @return <code>true</code> si se aceptó el código, <code>false</code> si fue rechazado.
     */
    public boolean analizar(String ruta) {
        tS = new TablaSimbolos();
        String token;
        int ultimoTipo = Tipos.NONE;
        boolean foundVar = false;
        boolean foundAssign = false;
        boolean foundFunc = false;
        boolean noValFound = false;
        String lastIdentRef = null;
        boolean error = false;
        try {
            Tokenizador tok = new Tokenizador(ruta);
            while ((token = tok.siguienteToken()) != null) {
                // Bandera para saber si el token ya fue reconocido
                boolean found = false;

                // Si el token empieza como comentario se ignora por completo el resto de la línea
                if (token.startsWith("//") || token.startsWith("#")) {
                    tok.siguienteLinea();
                    continue;
                }

                if (!token.matches("[\u0020-\u007E]+")) { // Se verifica que el token contenga símbolos del alfabeto (ascii)
                    PilaErrores.meter(new RegistroErr(120, tok.archivo.getNoLinea(), token)); // Si no, se crea un nuevo error
                    error = true;
                    continue;
                }

                // Se reconocen palabras reservadas primero
                if (automataReservadas.ejecutar(token)) {
                    switch (token) {
                        case "if":
                            tS.meter(new RegistroTS(token, Tokens.T_IF, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "else":
                            tS.meter(new RegistroTS(token, Tokens.T_ELSE, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "while":
                            tS.meter(new RegistroTS(token, Tokens.T_WHILE, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "for":
                            tS.meter(new RegistroTS(token, Tokens.T_FOR, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "in":
                            tS.meter(new RegistroTS(token, Tokens.T_IN, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "func":
                            tS.meter(new RegistroTS(token, Tokens.T_FUNC, Tipos.NONE, tok.archivo.getNoLinea()));
                            foundFunc = true;
                            found = true;
                            break;
                        case "return":
                            tS.meter(new RegistroTS(token, Tokens.T_RETURN, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "true":
                            tS.meter(new RegistroTS(token, Tokens.T_TRUE, Tipos.NONE, tok.archivo.getNoLinea()));
                            if (foundVar && foundAssign) {
                                tS.getByID(lastIdentRef).VAL = token;
                                foundVar = false;
                                foundAssign = false;
                            }
                            found = true;
                            break;
                        case "false":
                            tS.meter(new RegistroTS(token, Tokens.T_FALSE, Tipos.NONE, tok.archivo.getNoLinea()));
                            if (foundVar && foundAssign) {
                                tS.getByID(lastIdentRef).VAL = token;
                                foundVar = false;
                                foundAssign = false;
                            }
                            found = true;
                            break;
                        case "null":
                            tS.meter(new RegistroTS(token, Tokens.T_NULL, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "int":
                            tS.meter(new RegistroTS(token, Tokens.T_INT, Tipos.NONE, tok.archivo.getNoLinea()));
                            ultimoTipo = Tipos.INT;
                            foundFunc = false;
                            found = true;
                            break;
                        case "real":
                            tS.meter(new RegistroTS(token, Tokens.T_REAL, Tipos.NONE, tok.archivo.getNoLinea()));
                            ultimoTipo = Tipos.REAL;
                            foundFunc = false;
                            found = true;
                            break;
                        case "bool":
                            tS.meter(new RegistroTS(token, Tokens.T_BOOL, Tipos.NONE, tok.archivo.getNoLinea()));
                            ultimoTipo = Tipos.BOOL;
                            foundFunc = false;
                            found = true;
                            break;
                        case "string":
                            tS.meter(new RegistroTS(token, Tokens.T_STRING, Tipos.NONE, tok.archivo.getNoLinea()));
                            ultimoTipo = Tipos.STR;
                            foundFunc = false;
                            found = true;
                            break;
                        case "print":
                            tS.meter(new RegistroTS(token, Tokens.T_PRINT, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "println":
                            tS.meter(new RegistroTS(token, Tokens.T_PRINTLN, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "read":
                            tS.meter(new RegistroTS(token, Tokens.T_READ, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                    }
                }

                //Símbolos
                if (!found) {
                    switch (token) {
                        case ":":
                            tS.meter(new RegistroTS(token, Tokens.T_COLON, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case ";":
                            tS.meter(new RegistroTS(token, Tokens.T_SEMICOLON, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "(":
                            tS.meter(new RegistroTS(token, Tokens.T_LPAREN, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case ")":
                            tS.meter(new RegistroTS(token, Tokens.T_RPAREN, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "{":
                            tS.meter(new RegistroTS(token, Tokens.T_LBRACE, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "}":
                            tS.meter(new RegistroTS(token, Tokens.T_RBRACE, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "[":
                            tS.meter(new RegistroTS(token, Tokens.T_LBRACKET, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "]":
                            tS.meter(new RegistroTS(token, Tokens.T_RBRACKET, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "<=":
                            tS.meter(new RegistroTS(token, Tokens.T_LTE, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case ">=":
                            tS.meter(new RegistroTS(token, Tokens.T_GTE, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "!=":
                            tS.meter(new RegistroTS(token, Tokens.T_NE, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "<":
                            tS.meter(new RegistroTS(token, Tokens.T_LT, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case ">":
                            tS.meter(new RegistroTS(token, Tokens.T_GT, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "==":
                            tS.meter(new RegistroTS(token, Tokens.T_EQ, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "=":
                            tS.meter(new RegistroTS(token, Tokens.T_ASSIGN, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "--":
                            tS.meter(new RegistroTS(token, Tokens.T_DEC, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "++":
                            tS.meter(new RegistroTS(token, Tokens.T_INC, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "!":
                            tS.meter(new RegistroTS(token, Tokens.T_NOT, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "+":
                            tS.meter(new RegistroTS(token, Tokens.T_PLUS, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "-":
                            tS.meter(new RegistroTS(token, Tokens.T_MINUS, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "*":
                            tS.meter(new RegistroTS(token, Tokens.T_STAR, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "/":
                            tS.meter(new RegistroTS(token, Tokens.T_DIV, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "->":
                            tS.meter(new RegistroTS(token, Tokens.T_RANGE, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case ",":
                            tS.meter(new RegistroTS(token, Tokens.T_COMMA, Tipos.NONE, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                    }

                    if (!foundAssign && foundVar && token.equals("=")) {
                        foundAssign = true;
                    }
                    if (noValFound) {
                        foundVar = false;
                        foundAssign = false;
                    }
                }


                // Se prosigue con la evaluacion en los autómatas
                if (!found) {
                    for (AutomataToken a : automatas) {
                        if (!found && a.ejecutar(token)) {
                            RegistroTS reg;
                            switch (a.getTokenId()) {
                                // Identificador
                                case Tokens.T_VAR:
                                    if (!foundFunc) {
                                        if (!tS.contiene(token)) {
                                            reg = new RegistroTS(token, Tokens.T_VAR, ultimoTipo, tok.archivo.getNoLinea());
                                            switch (ultimoTipo) {
                                                case Tipos.INT:
                                                    reg.VAL = "0";
                                                    break;
                                                case Tipos.REAL:
                                                    reg.VAL = "0.0";
                                                    break;
                                                case Tipos.BOOL:
                                                    reg.VAL = "false";
                                                    break;
                                            }
                                            ultimoTipo = Tipos.NONE;
                                            foundVar = true;
                                            lastIdentRef = reg.ID;
                                            tS.meter(reg);
                                        } else {
                                            RegistroTS prev = tS.get(token);
                                            reg = new RegistroTS(token, prev.TOKEN_ID, Tipos.REF, tok.archivo.getNoLinea(), prev.ID);
                                            tS.meter(reg);
                                            lastIdentRef = prev.ID;
                                        }
                                    } else {
                                        tS.meter(new RegistroTS(token, Tokens.T_FUN, Tipos.NONE, tok.archivo.getNoLinea()));
                                        foundFunc = false;
                                    }
                                    found = true;
                                    break;

                                // Constantes
                                case Tokens.T_STR_CONST:
                                    reg = new RegistroTS(token, Tokens.T_STR_CONST, Tipos.NONE, tok.archivo.getNoLinea());
                                    if (foundVar && foundAssign) {
                                        tS.getByID(lastIdentRef).VAL = token.replaceAll("\"", "");
                                        foundVar = false;
                                        foundAssign = false;
                                    }
                                    tS.meter(reg);
                                    found = true;
                                    break;
                                case Tokens.T_INT_CONST:
                                    reg = new RegistroTS(token, Tokens.T_INT_CONST, Tipos.NONE, tok.archivo.getNoLinea());
                                    if (foundVar && foundAssign) {
                                        tS.getByID(lastIdentRef).VAL = token;
                                        foundVar = false;
                                        foundAssign = false;
                                    }
                                    tS.meter(reg);
                                    found = true;
                                    break;
                                case Tokens.T_REAL_CONST:
                                    reg = new RegistroTS(token, Tokens.T_REAL_CONST, Tipos.NONE, tok.archivo.getNoLinea());
                                    if (foundVar && foundAssign) {
                                        tS.getByID(lastIdentRef).VAL = token;
                                        foundVar = false;
                                        foundAssign = false;
                                    }
                                    tS.meter(reg);
                                    found = true;
                                    break;
                            }
                        }
                    }
                }
                noValFound = !(foundVar || foundAssign);
                // Si el token no fue reconocido, se marca error.
                if (!found) {
                    PilaErrores.meter(new RegistroErr(110, tok.archivo.getNoLinea(), token));
                    error = true;
                }
            }
        } catch (FileNotFoundException e) {
            PilaErrores.meter(new RegistroErr(10, 0, ruta));
            error = true;
        } catch (IOException e) {
            PilaErrores.meter(new RegistroErr(11, 0, ruta));
            error = true;
        }
        return !error;
    }

    /**
     * Imprime la pila de errores en la salida estándar.
     */
    public void imprimirErrores() {
        if (PilaErrores.size() > 0) {
            System.err.println("Se encontraron errores durante el análisis léxico:");
            while (PilaErrores.size() > 0) {
                RegistroErr reg = PilaErrores.sacar();
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
        if (tS != null && tS.size() > 0) {
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.println("|                                                 TABLA DE SÍMBOLOS                                                  |");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %24s | %-30s | %-12s | %-12s | %24s |\n", "ID", "NOMBRE O LEXEMA", "TOKEN ID", "TIPO DE DATO", "VALOR");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            for (RegistroTS reg : tS.registros()) {
                System.out.printf("| %24s | %-30s | %-12s | %-12s | %24s |\n", reg.ID, reg.NOMBRE, Tokens.nombres[reg.TOKEN_ID], Tipos.nombres[reg.TIPO], reg.VAL);
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("No se encontraron símbolos en el código");
        }
    }
}