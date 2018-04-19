package itc.automatas2.lexico;

import itc.automatas2.estructuras.Archivo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;

/**
 * Clase que genera los tokens que seran utilizados por el analizador.
 */
public class Tokenizador {
    public Archivo archivo;
    private ArrayDeque<String> tokens;

    /**
     * Constructor de la clase
     *
     * @param ruta
     * @throws FileNotFoundException
     */
    public Tokenizador(String ruta) throws FileNotFoundException {
        archivo = new Archivo(ruta);
        tokens = new ArrayDeque<>();
    }

    /**
     * Obtiene un token de la cola que la misma clase mantiene.
     *
     * @return Un token de la cola, <code>null</code> si se alcanzó el final del archivo.
     * @throws IOException Si ocurre un error de lectura del archivo.
     */
    public String siguienteToken() throws IOException {
        if (tieneTokens()) {
            return tokens.remove();
        }

        return null;
    }

    /**
     * Se salta a la siguiente línea, ignorando los tokens que existan en la cola.
     */
    public void siguienteLinea() {
        tokens.clear();
    }

    /**
     * El metodo utiliza la linea que la clase Archivo le envia, separa la linea en tokens usando el espacio como delimitador.
     *
     * @return Regresa VERDADERO si aun hay tokens y regresa FALSO si ya no hay tokens con los que trabajar.
     * @throws IOException Si ocurre un error en la lectura o el archivo ya se cerró.
     */
    private boolean tieneTokens() throws IOException {
        if (tokens.isEmpty()) {//Si tokens esta vacia se llenara con tokens
            String linea = archivo.leerLinea();
            while (linea != null && linea.trim().length() == 0) {
                linea = archivo.leerLinea();
            }

            if (linea == null)
                return false;

            //Simbolo especial
            boolean special = false;
            char lastSpecial = 0;
            //Bandera para saber si se está leyendo una cadena
            boolean string = false;

            StringBuilder sb = new StringBuilder();
            char[] str = linea.toCharArray();
            for (int i = 0; i < str.length; i++) {
                if (!string) { //No se está leyendo un string
                    switch (str[i]) {
                        //Delimitadores
                        case ' ':
                        case '\t':
                            if (sb.length() > 0) {
                                tokens.add(sb.toString());
                                sb.setLength(0);
                            }
                            special = false;
                            break;
                        case ';':
                            if (sb.length() > 0) {
                                tokens.add(sb.toString());
                                sb.setLength(0);
                            }
                            tokens.add(Character.toString(str[i]));
                            special = false;
                            break;
                        //Cadena
                        case '"':
                            if (sb.length() > 0) {
                                tokens.add(sb.toString());
                                sb.setLength(0);
                            }
                            string = true;
                            special = false;
                            lastSpecial = 0;
                            sb.append(str[i]);
                            break;
                        //Tokens de un caracter
                        case ':':
                        case '(':
                        case ')':
                        case '{':
                        case '}':
                        case '[':
                        case ']':
                        case '*':
                        case ',':
                            if (sb.length() > 0) {
                                tokens.add(sb.toString());
                                sb.setLength(0);
                            }
                            tokens.add(Character.toString(str[i]));
                            special = false;
                            break;
                        //Símbolos especiales
                        case '!':
                            break;
                        case '=':
                            if (!special) {
                                special = true;
                                if (sb.length() > 0) {
                                    tokens.add(sb.toString());
                                    sb.setLength(0);
                                }
                                sb.append(str[i]);
                                lastSpecial = str[i];
                            } else {
                                special = false;
                                switch (lastSpecial) {
                                    case '=':
                                    case '>':
                                    case '<':
                                    case '!':
                                        sb.append(str[i]);
                                        tokens.add(sb.toString());
                                        sb.setLength(0);
                                        break;
                                    default:
                                        if (sb.length() > 0) {
                                            tokens.add(sb.toString());
                                            sb.setLength(0);
                                        }
                                        sb.append(str[i]);
                                        break;
                                }
                                lastSpecial = 0;
                            }
                            break;
                        case '-':
                        case '>':
                            if (!special) {
                                special = true;
                                if (sb.length() > 0) {
                                    tokens.add(sb.toString());
                                    sb.setLength(0);
                                }
                                sb.append(str[i]);
                                lastSpecial = str[i];
                            } else {
                                special = false;
                                switch (lastSpecial) {
                                    case '-':
                                        sb.append(str[i]);
                                        tokens.add(sb.toString());
                                        sb.setLength(0);
                                        break;
                                    default:
                                        if (sb.length() > 0) {
                                            tokens.add(sb.toString());
                                            sb.setLength(0);
                                        }
                                        sb.append(str[i]);
                                        break;
                                }
                                lastSpecial = 0;
                            }
                            break;
                        case '<':
                            special = true;
                            if (sb.length() > 0) {
                                tokens.add(sb.toString());
                                sb.setLength(0);
                            }
                            sb.append(str[i]);
                            lastSpecial = str[i];
                            break;
                        case '+':
                            if (!special) {
                                special = true;
                                if (sb.length() > 0) {
                                    tokens.add(sb.toString());
                                    sb.setLength(0);
                                }
                                sb.append(str[i]);
                                lastSpecial = str[i];
                            } else {
                                special = false;
                                switch (lastSpecial) {
                                    case '+':
                                        sb.append(str[i]);
                                        tokens.add(sb.toString());
                                        sb.setLength(0);
                                        break;
                                    default:
                                        if (sb.length() > 0) {
                                            tokens.add(sb.toString());
                                            sb.setLength(0);
                                        }
                                        sb.append(str[i]);
                                        break;
                                }
                                lastSpecial = 0;
                            }
                            break;
                        case '/':
                            if (!special) {
                                special = true;
                                if (sb.length() > 0) {
                                    tokens.add(sb.toString());
                                    sb.setLength(0);
                                }
                                sb.append(str[i]);
                                lastSpecial = str[i];
                            } else {
                                special = false;
                                switch (lastSpecial) {
                                    case '/':
                                        sb.append(str[i]);
                                        tokens.add(sb.toString());
                                        sb.setLength(0);
                                        break;
                                    default:
                                        if (sb.length() > 0) {
                                            tokens.add(sb.toString());
                                            sb.setLength(0);
                                        }
                                        sb.append(str[i]);
                                        break;
                                }
                                lastSpecial = 0;
                            }
                            break;
                        //Cualquier otro símbolo
                        default:
                            if (special && sb.length() > 0) {
                                tokens.add(sb.toString());
                                sb.setLength(0);
                                special = false;
                                lastSpecial = 0;
                            }
                            sb.append(str[i]);
                            break;
                    }
                    if (i == str.length - 1 && sb.length() > 0) { // El ultimo caracter
                        tokens.add(sb.toString());
                    }
                } else {  //Se está leyendo una cadena
                    sb.append(str[i]);
                    if (str[i] == '"') {    //Si la cadena termina, se completa el token
                        string = false;
                        tokens.add(sb.toString());
                        sb.setLength(0);
                    }
                    if (i == str.length - 1 && sb.length() > 0) { // El ultimo caracter
                        tokens.add(sb.toString());
                    }
                }
            }
        }
        return true;
    }
}