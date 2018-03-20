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

            boolean flag = false;
            StringBuilder sb = new StringBuilder();
            char[] str = linea.toCharArray();
            for (int i = 0; i < str.length; i++) {
                if (i == str.length - 1) {   //El último caracter
                    //Si no se está leyendo un string y no es delimitador, o si se está leyendo un string
                    if ((!flag && !Character.toString(str[i]).matches("[ \t]")) || flag)
                        sb.append(str[i]);
                    if (sb.length() > 0)
                        tokens.add(sb.toString());
                } else if (!flag) { //No se está leyendo un string
                    switch (str[i]) {
                        case ' ':
                        case '\t':
                            if (sb.length() > 0) {
                                tokens.add(sb.toString());
                                sb.setLength(0);
                            }
                            break;
                        case '"':
                            flag = true;
                            sb.append(str[i]);
                            break;
                        default:
                            sb.append(str[i]);
                            break;
                    }
                } else if (flag) {  //Se está leyendo un string
                    sb.append(str[i]);
                    if (str[i] == '"') {
                        flag = false;
                        tokens.add(sb.toString());
                        sb.setLength(0);
                    }
                }
            }
        }
        return true;
    }
}