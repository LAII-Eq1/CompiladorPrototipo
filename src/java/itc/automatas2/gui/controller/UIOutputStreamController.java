package itc.automatas2.gui.controller;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class UIOutputStreamController extends OutputStream {
    private final PrintStream STDOUT = System.out;
    private final PrintStream STDERR = System.err;
    private Document doc;
    private SimpleAttributeSet as;
    private byte last;

    public UIOutputStreamController(JTextPane textPane) {
        this.doc = textPane.getDocument();
        as = new SimpleAttributeSet();
        StyleConstants.setForeground(as, Color.BLACK);
    }

    @Override
    public void write(int b) {
        // STDOUT.write(b);
        if (b < 0 && last == 0) {
            last = (byte) b;
        } else {
            byte chr[];
            if (last != 0) {
                chr = new byte[]{
                        last, (byte) b
                };
                last = 0;
            }
            else {
                chr = new byte[]{
                        (byte) b
                };
            }
            String c = new String(chr);
            SwingUtilities.invokeLater(() -> {
                try {
                    doc.insertString(doc.getLength(), c, as);
                } catch (BadLocationException e) {
                    STDERR.println(e);
                }
            });
        }
    }
}
