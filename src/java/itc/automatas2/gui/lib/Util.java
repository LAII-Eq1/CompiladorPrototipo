package itc.automatas2.gui.lib;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase de utilidades para el paquete.
 */
public class Util {
    /**
     * Obtiene el hash de una cadena por medio del algoritmo MD5
     *
     * @param text la cadena a hashear
     * @return el hash en formato hexadecimal
     */
    public static String md5(String text) {
        if (text.isEmpty())
            return "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Cambia el ancho de las columnas de una tabla de acuerdo a sus contenidos.
     *
     * @param table    una {@link JTable tabla}.
     * @param minWidth el ancho mínimo que deben tener las columnas.
     * @param padding  un espacio extra para dar al contenido más largo.
     */
    public static void autosizeColumns(JTable table, int minWidth, int padding) {
        TableColumnModel model = table.getColumnModel();
        for (int col = 0; col < table.getColumnCount(); col++) {
            int width = minWidth;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, col);
                Component comp = table.prepareRenderer(renderer, row, col);
                width = Math.max(comp.getPreferredSize().width + padding, width);
            }
            model.getColumn(col).setPreferredWidth(width);
        }
    }
}
