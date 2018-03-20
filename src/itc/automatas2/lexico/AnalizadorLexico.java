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
                boolean flag = false;

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
                    case "else":
                    case "while":
                    case "for":
                    case "func":
                    case "return":
                    case "true":
                    case "false":
                    case "null":
                    case "int":
                    case "real":
                    case "bool":
                    case "string":
                    case "print":
                    case "println":
                    case "read":
                    case ":":
                    case ";":
                    case "(":
                    case ")":
                    case "{":
                    case "}":
                    case "[":
                    case "]":
                    case "<=":
                    case ">=":
                    case "!=":
                    case "<":
                    case ">":
                    case "==":
                    case "=":
                    case "--":
                    case "++":
                    case "!":
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "->":
                    case ",":
                        flag = true;
                        break;
                }

                // Se prosigue con la evaluacion en los autómatas
                if (!flag) {
                    for (AutomataToken a : automatas) {
                        if (!flag && a.ejecutar(token)) {
                            switch (a.getTokenId()) {
                                case Tokens.T_VAR:
                                    if (!tS.contiene(token)) {
                                        tS.meter(token, new RegistroTS(token, Tokens.T_VAR, 0));
                                    }
                                    flag = true;
                                    break;
                                case Tokens.T_STR_CONST:
                                case Tokens.T_INT_CONST:
                                case Tokens.T_REAL_CONST:
                                    flag = true;
                                    break;
                            }
                        }
                    }
                }
                // Si el token no fue reconocido, se marca error.
                if (!flag) {
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
                        err.setReason(String.format("El token \"%s\" en la línea %d contiene símbolos no reconocibles (fuera del código ASCII). ", reg.LEXEMA, reg.LINEA_N));
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
            System.out.printf("| %30s | %8s | %12s |\n", "NOMBRE", "TOKEN ID", "TIPO DE DATO");
            System.out.println("------------------------------------------------------------");
            for (RegistroTS reg : tS.registros()) {
                System.out.printf("| %30s | %8d | %12d |\n", reg.NOMBRE, reg.TOKEN_ID, reg.TIPO);
            }
            System.out.println("------------------------------------------------------------");
        } else {
            System.out.println("No se encontraron símbolos en el código");
        }
    }
}