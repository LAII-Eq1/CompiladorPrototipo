package itc.automatas2.estructuras;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase para lectura de un archivo de texto. Se puede leer línea por línea o por completo.
 */
public class Archivo {
    private final String RUTA;
    private final File file;
    private BufferedReader reader;
    private int line;

    /**
     * Crea un objeto de la clase.
     *
     * @param ruta La ruta del archivo.
     * @throws FileNotFoundException Si el archivo no existe.
     */
    public Archivo(String ruta) throws FileNotFoundException {
        this.RUTA = ruta;
        this.file = new File(ruta);
        //Si el archivo no existe tira excepción, que otra clase se encargue
        if (!file.exists())
            throw new FileNotFoundException();
        this.reader = new BufferedReader(new FileReader(file));
        this.line = 0;
    }

    /**
     * Lee la siguiente línea del archivo.
     *
     * @return La siguiente línea, <code>null</code> si se llegó al final del archivo. Una vez que se
     * llega al final del archivo, el mismo se cierra.
     * @throws IOException Si ocurre un error en la lectura o el archivo ya se cerró.
     */
    public String leerLinea() throws IOException {
        String tmp = reader.readLine();
        line++;
        if (tmp == null)
            reader.close();
        return tmp;
    }

    /**
     * Obtiene el número de línea actual.
     *
     * @return El número de línea actual.
     */
    public int getNoLinea() {
        return line;
    }
}
