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
        int ultimoTipo = Tipos.VOID;
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
                            tS.meter(new RegistroTS(token, Tokens.T_IF, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "else":
                            tS.meter(new RegistroTS(token, Tokens.T_ELSE, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "while":
                            tS.meter(new RegistroTS(token, Tokens.T_WHILE, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "for":
                            tS.meter(new RegistroTS(token, Tokens.T_FOR, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "in":
                            tS.meter(new RegistroTS(token, Tokens.T_IN, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "func":
                            tS.meter(new RegistroTS(token, Tokens.T_FUNC, Tipos.VOID, tok.archivo.getNoLinea()));
                            foundFunc = true;
                            found = true;
                            break;
                        case "return":
                            tS.meter(new RegistroTS(token, Tokens.T_RETURN, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "true":
                            tS.meter(new RegistroTS(token, Tokens.T_TRUE, Tipos.BOOL, tok.archivo.getNoLinea()));
                            if (foundVar && foundAssign) {
                                tS.getByID(lastIdentRef).VAL = token;
                                foundVar = false;
                                foundAssign = false;
                            }
                            found = true;
                            break;
                        case "false":
                            tS.meter(new RegistroTS(token, Tokens.T_FALSE, Tipos.BOOL, tok.archivo.getNoLinea()));
                            if (foundVar && foundAssign) {
                                tS.getByID(lastIdentRef).VAL = token;
                                foundVar = false;
                                foundAssign = false;
                            }
                            found = true;
                            break;
                        case "null":
                            tS.meter(new RegistroTS(token, Tokens.T_NULL, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "int":
                            tS.meter(new RegistroTS(token, Tokens.T_INT, Tipos.INT, tok.archivo.getNoLinea()));
                            ultimoTipo = Tipos.INT;
                            foundFunc = false;
                            found = true;
                            break;
                        case "real":
                            tS.meter(new RegistroTS(token, Tokens.T_REAL, Tipos.REAL, tok.archivo.getNoLinea()));
                            ultimoTipo = Tipos.REAL;
                            foundFunc = false;
                            found = true;
                            break;
                        case "bool":
                            tS.meter(new RegistroTS(token, Tokens.T_BOOL, Tipos.BOOL, tok.archivo.getNoLinea()));
                            ultimoTipo = Tipos.BOOL;
                            foundFunc = false;
                            found = true;
                            break;
                        case "string":
                            tS.meter(new RegistroTS(token, Tokens.T_STRING, Tipos.STR, tok.archivo.getNoLinea()));
                            ultimoTipo = Tipos.STR;
                            foundFunc = false;
                            found = true;
                            break;
                        case "print":
                            tS.meter(new RegistroTS(token, Tokens.T_PRINT, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "println":
                            tS.meter(new RegistroTS(token, Tokens.T_PRINTLN, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "read":
                            tS.meter(new RegistroTS(token, Tokens.T_READ, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                    }
                }

                //Símbolos
                if (!found) {
                    switch (token) {
                        case ":":
                            tS.meter(new RegistroTS(token, Tokens.T_COLON, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case ";":
                            tS.meter(new RegistroTS(token, Tokens.T_SEMICOLON, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "(":
                            tS.meter(new RegistroTS(token, Tokens.T_LPAREN, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case ")":
                            tS.meter(new RegistroTS(token, Tokens.T_RPAREN, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "{":
                            tS.meter(new RegistroTS(token, Tokens.T_LBRACE, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "}":
                            tS.meter(new RegistroTS(token, Tokens.T_RBRACE, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "[":
                            tS.meter(new RegistroTS(token, Tokens.T_LBRACKET, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "]":
                            tS.meter(new RegistroTS(token, Tokens.T_RBRACKET, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "<=":
                            tS.meter(new RegistroTS(token, Tokens.T_LTE, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case ">=":
                            tS.meter(new RegistroTS(token, Tokens.T_GTE, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "!=":
                            tS.meter(new RegistroTS(token, Tokens.T_NE, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "<":
                            tS.meter(new RegistroTS(token, Tokens.T_LT, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case ">":
                            tS.meter(new RegistroTS(token, Tokens.T_GT, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "==":
                            tS.meter(new RegistroTS(token, Tokens.T_EQ, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "=":
                            tS.meter(new RegistroTS(token, Tokens.T_ASSIGN, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "--":
                            tS.meter(new RegistroTS(token, Tokens.T_DEC, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "++":
                            tS.meter(new RegistroTS(token, Tokens.T_INC, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "!":
                            tS.meter(new RegistroTS(token, Tokens.T_NOT, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "+":
                            tS.meter(new RegistroTS(token, Tokens.T_PLUS, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "-":
                            tS.meter(new RegistroTS(token, Tokens.T_MINUS, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "*":
                            tS.meter(new RegistroTS(token, Tokens.T_STAR, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "/":
                            tS.meter(new RegistroTS(token, Tokens.T_DIV, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case "->":
                            tS.meter(new RegistroTS(token, Tokens.T_RANGE, Tipos.VOID, tok.archivo.getNoLinea()));
                            found = true;
                            break;
                        case ",":
                            tS.meter(new RegistroTS(token, Tokens.T_COMMA, Tipos.VOID, tok.archivo.getNoLinea()));
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
                                            ultimoTipo = Tipos.VOID;
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
                                        tS.meter(new RegistroTS(token, Tokens.T_FUN, Tipos.VOID, tok.archivo.getNoLinea()));
                                        foundFunc = false;
                                    }
                                    found = true;
                                    break;

                                // Constantes
                                case Tokens.T_STR_CONST:
                                    reg = new RegistroTS(token, Tokens.T_STR_CONST, Tipos.STR, tok.archivo.getNoLinea());
                                    if (foundVar && foundAssign) {
                                        tS.getByID(lastIdentRef).VAL = token.replaceAll("\"", "");
                                        foundVar = false;
                                        foundAssign = false;
                                    }
                                    tS.meter(reg);
                                    found = true;
                                    break;
                                case Tokens.T_INT_CONST:
                                    reg = new RegistroTS(token, Tokens.T_INT_CONST, Tipos.INT, tok.archivo.getNoLinea());
                                    if (foundVar && foundAssign) {
                                        tS.getByID(lastIdentRef).VAL = token;
                                        foundVar = false;
                                        foundAssign = false;
                                    }
                                    tS.meter(reg);
                                    found = true;
                                    break;
                                case Tokens.T_REAL_CONST:
                                    reg = new RegistroTS(token, Tokens.T_REAL_CONST, Tipos.REAL, tok.archivo.getNoLinea());
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