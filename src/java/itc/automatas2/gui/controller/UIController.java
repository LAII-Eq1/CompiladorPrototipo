package itc.automatas2.gui.controller;

import itc.automatas2.estructuras.PilaErrores;
import itc.automatas2.gui.lib.Util;
import itc.automatas2.gui.model.ArchivoModel;
import itc.automatas2.gui.view.MainFrame;
import itc.automatas2.gui.view.TablaSimbolosDialog;
import itc.automatas2.lexico.AnalizadorLexico;
import itc.automatas2.lexico.Tipos;
import itc.automatas2.lexico.Tokens;
import itc.automatas2.sintactico.AnalizadorSintactico;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.folding.CurlyFoldParser;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Clase controladora de la ventana principal.
 */
public class UIController {
    private MainFrame frm;
    private TablaSimbolosDialog dlgTable;
    private JFileChooser fc;
    private String digest;
    private ArchivoModel handle;
    private AnalizadorLexico al;
    private AnalizadorSintactico as;


    /**
     * Constructor de la clase.
     * Muestra la vista automáticamente.
     */
    public UIController() {
        this.frm = new MainFrame();
        this.C_DEFAULT = frm.getBtnLex().getBackground();
        this.dlgTable = new TablaSimbolosDialog();
        this.fc = new JFileChooser();
        this.al = new AnalizadorLexico();
        this.as = new AnalizadorSintactico();
        dlgTable.setLocationRelativeTo(frm);
        initListeners();
        configView();
        setOutPaneVisible(false);
        frm.setVisible(true);
        digest = "";
    }

    /**
     * Inicializa los listeners de la vista.
     */
    private void initListeners() {
        // Frame
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                salir();
            }
        });

        // Editor
        frm.getTxtCode().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_F5:
                        if (frm.getBtnLex().isEnabled()) {
                            if ((e.getModifiers() & ActionEvent.SHIFT_MASK) > 0)
                                all();
                            else
                                lex(true);
                        }
                        break;
                    case KeyEvent.VK_F6:
                        if (frm.getBtnSyn().isEnabled()) {
                            syn(true);
                        }
                        break;
                    case KeyEvent.VK_F7:
                        if (frm.getBtnSem().isEnabled()) {
                            sem(true);
                        }
                        break;
                    case KeyEvent.VK_T:
                        if (frm.getBtnSTable().isEnabled())
                            if ((e.getModifiers() & ActionEvent.CTRL_MASK) > 0)
                                toggleTSDialog();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        // Menu archivo
        frm.getMenuFileOpen().addActionListener(e -> abrirArchivo());
        frm.getMenuFileSave().addActionListener(e -> guardarArchivo());
        frm.getMenuFileSaveAs().addActionListener(e -> guardarComo());
        frm.getMenuAyudaLex().addActionListener(e -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(new File("docs/lexico.pdf"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frm.getMenuAyudaSyn().addActionListener(e -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(new File("docs/sintactico.pdf"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frm.getMenuExit().addActionListener(e -> salir());

        // Toolbar
        frm.getBtnLex().addActionListener(e -> lex(true));
        frm.getBtnSyn().addActionListener(e -> syn(true));
        frm.getBtnSem().addActionListener(e -> sem(true));
        frm.getBtnAll().addActionListener(e -> all());
        frm.getBtnSTable().addActionListener(e -> toggleTSDialog());
        frm.getBtnOutPane().addActionListener(e -> toggleOutPane());
    }

    /**
     * Configura los objetos de la vista.
     */
    private void configView() {
        // Se redirigen las salidas al textpane
        UIOutputStreamController out = new UIOutputStreamController(frm.getTxtOut());
        UIErrorStreamController err = new UIErrorStreamController(frm.getTxtOut());
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));

        // Configuración del FileChooser
        fc.setFileHidingEnabled(true);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(new FileNameExtensionFilter("Programa E1", "e1", "E1"));

        // Botones
        frm.getMenuFileSave().setEnabled(false);
        btnState(S_INITIAL);
        frm.getBtnSTable().setEnabled(false);

        //Editor
        AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
        atmf.putMapping("text/E1", "itc.automatas2.gui.lib.E1TokenMaker");
        FoldParserManager.get().addFoldParserMapping("text/E1", new CurlyFoldParser());
        frm.getTxtCode().setSyntaxEditingStyle("text/E1");
        frm.getTxtCodeScrollPane().setLineNumbersEnabled(true);
        frm.getTxtCode().setCodeFoldingEnabled(true);
    }

    /**
     * Abre un archivo utilizando un {@link JFileChooser FileChooser}.
     */
    private void abrirArchivo() {
        fc.setDialogTitle("Abrir");
        fc.setCurrentDirectory(new File("."));
        if (fc.showOpenDialog(frm) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            handle = new ArchivoModel(file);
            SwingUtilities.invokeLater(() -> {
                RSyntaxTextArea code = frm.getTxtCode();
                String content = handle.readAll();
                code.setText(content);
                digest = Util.md5(code.getText());
                code.discardAllEdits();
                frm.getMenuFileSave().setEnabled(true);
                updateTitle(handle.getFile());
                btnState(S_READY);
                frm.getBtnSTable().setEnabled(false);
                vaciarYOcultarTS();
            });
        }
    }

    /**
     * Guarda el archivo actual.
     */
    private void guardarArchivo() {
        SwingUtilities.invokeLater(() -> {
            String content = frm.getTxtCode().getText();
            handle.write(content);
            digest = Util.md5(content);
            btnState(S_READY);
        });
    }

    /**
     * Guarda un archivo utilizando un {@link JFileChooser FileChooser}.
     */
    private void guardarComo() {
        fc.setDialogTitle("Guardar como");
        if (fc.showSaveDialog(frm) == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().toString().endsWith(".e1")
                    ? fc.getSelectedFile().toString()
                    : fc.getSelectedFile().toString() + ".e1";
            File file = new File(path);
            handle = new ArchivoModel(file);
            SwingUtilities.invokeLater(() -> {
                if (codeChanged()) {
                    String content = frm.getTxtCode().getText();
                    handle.write(content);
                    digest = Util.md5(content);
                }
                frm.getMenuFileSave().setEnabled(true);
                updateTitle(handle.getFile());
                btnState(S_READY);
                frm.getBtnSTable().setEnabled(false);
            });
        }

    }

    /**
     * Maneja el cierre del programa y los cambios al código.
     */
    private void salir() {
        if (codeChanged()) {
            int option = JOptionPane.showConfirmDialog(frm,
                    "¿Desea guardar los cambios?", "Salir",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE
            );
            switch (option) {
                case JOptionPane.YES_OPTION:
                    guardarArchivo();
                case JOptionPane.NO_OPTION:
                    System.exit(0);
                    break;
                case JOptionPane.CANCEL_OPTION:
                case JOptionPane.CLOSED_OPTION:
                    System.out.println("Cancelled");
                    break;
            }
        } else System.exit(0);
    }

    /**
     * Muestra u oculta el panel de la salida del programa.
     *
     * @param visible la visibilidad del panel.
     */
    private void setOutPaneVisible(boolean visible) {
        SwingUtilities.invokeLater(() -> {
            JSplitPane pane = frm.getjSplitPane();
            if (!visible) {
                pane.setDividerLocation(1.0d);
                frm.getTxtOut().setText("");
            } else {
                pane.setDividerLocation(pane.getHeight() - 200);
            }
            pane.setEnabled(visible);
        });
    }

    /**
     * Alterna la visibilidad del panel de salida.
     */
    private void toggleOutPane() {
        setOutPaneVisible(!frm.getjSplitPane().isEnabled());
    }

    /**
     * Alerta al usuario que hubo cambios al código.
     */
    private void promptChanged() {
        JOptionPane.showMessageDialog(frm,
                "Los cambios deben ser guardados para proceder con el análisis.\n" +
                        "Por favor, guarde y vuelva a intentar.", "Advertencia",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Ejecuta el análisis léxico del programa actual.
     */
    private void lex(boolean imprimirErrores) {
        if (codeChanged()) {
            promptChanged();
        } else if (handle != null) {
            if (!frm.getjSplitPane().isEnabled())
                setOutPaneVisible(true);
            System.out.printf("ANÁLISIS LÉXICO DEL PROGRAMA \"%s\"\n", handle.getFile().toString());
            if (al.analizar(handle.getFile().toString())) {
                System.out.println("El analizador léxico declaró el código como válido");
                btnState(S_LEX_GOOD);
            } else {
                System.err.println("El analizador léxico declaró el código como inválido");
                btnState(S_LEX_ERR);
            }
            poblarTS();
            if (imprimirErrores)
                PilaErrores.imprimirErrores();
            frm.getBtnSTable().setEnabled(true);
            System.out.println();
        }
    }

    /**
     * Ejecuta el análisis sintáctico del programa actual.
     */
    private void syn(boolean imprimirErrores) {
        if (codeChanged()) {
            promptChanged();
        } else if (al.tS.size() > 0) {
            if (!frm.getjSplitPane().isEnabled())
                setOutPaneVisible(true);
            System.out.printf("ANÁLISIS SINTÁCTICO DEL PROGRAMA \"%s\"\n", handle.getFile().toString());
            if (as.analizar(al.tS)) {
                System.out.println("El analizador sintáctico declaró el código como válido");
                btnState(S_SYN_GOOD);
            } else {
                System.err.println("El analizador sintáctico declaró el código como inválido");
                btnState(S_SYN_ERR);
            }
            if (as.tieneArboles()) {
                System.out.println("Árboles construidos:");
                as.imprimirArboles();
            }
            if (imprimirErrores)
                PilaErrores.imprimirErrores();
            System.out.println();
        }
    }

    /**
     * Ejecuta el análisis semántico del programa actual.
     */
    private void sem(boolean imprimirErrores) {
        System.err.println("Análisis semántico no implementado aún!");
    }

    /**
     * Ejecuta todos los análisis en cadena.
     */
    private void all() {
        lex(false);
        syn(false);
        //sem();
        PilaErrores.imprimirErrores();

    }

    /**
     * Limpia la tabla de símbolos y oculta su ventana.
     */
    private void vaciarYOcultarTS() {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel dtm = (DefaultTableModel) dlgTable.getSymTable().getModel();
            dtm.setRowCount(0);
            dlgTable.setVisible(false);
        });
    }

    /**
     * Llena la tabla de símbolos.
     */
    private void poblarTS() {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel dtm = (DefaultTableModel) dlgTable.getSymTable().getModel();
            dtm.setRowCount(0);
            al.tS.registros().forEach(reg -> dtm.addRow(new Object[]{
                    reg.ID,
                    reg.NOMBRE,
                    Tokens.nombres[reg.TOKEN_ID],
                    Tipos.nombres[reg.TIPO],
                    reg.VAL,
                    reg.LINE
            }));
            Util.autosizeColumns(dlgTable.getSymTable(), 72, 16);
        });
    }

    /**
     * Alterna la visibilidad de la tabla de símbolos.
     */
    private void toggleTSDialog() {
        dlgTable.setVisible(!dlgTable.isVisible());
    }

    /**
     * Actualiza el título de la ventana a partir del nombre del archivo activo.
     *
     * @param activeFile el archivo activo.
     */
    private void updateTitle(File activeFile) {
        frm.setTitle(String.format("%s [%s]", "E1", activeFile));
    }


    /**
     * Verifica si hubo cambios en el código.
     *
     * @return <code>true</code> si se hicieron cambios.
     */
    private boolean codeChanged() {
        String newDigest = Util.md5(frm.getTxtCode().getText());
        return !digest.equals(newDigest);
    }

    private void btnState(int state) {
        SwingUtilities.invokeLater(() -> {
            frm.getBtnLex().setEnabled(state >= S_READY);
            frm.getBtnSyn().setEnabled(state >= S_LEX_GOOD);
            frm.getBtnSem().setEnabled(state >= S_SYN_GOOD);
            frm.getBtnAll().setEnabled(state >= S_READY);
            switch (state) {
                case S_INITIAL:
                    frm.getBtnLex().setBackground(C_DEFAULT);
                    frm.getBtnSyn().setBackground(C_DEFAULT);
                    frm.getBtnSem().setBackground(C_DEFAULT);
                    break;
                case S_READY:
                    frm.getBtnLex().setBackground(C_YELLOW);
                    frm.getBtnSyn().setBackground(C_DEFAULT);
                    frm.getBtnSem().setBackground(C_DEFAULT);
                    break;
                case S_LEX_ERR:
                    frm.getBtnLex().setBackground(C_RED);
                    break;
                case S_LEX_GOOD:
                    frm.getBtnLex().setBackground(C_GREEN);
                    frm.getBtnSyn().setBackground(C_YELLOW);
                    break;
                case S_SYN_ERR:
                    frm.getBtnSyn().setBackground(C_RED);
                    break;
                case S_SYN_GOOD:
                    frm.getBtnSyn().setBackground(C_GREEN);
                    frm.getBtnSem().setBackground(C_YELLOW);
                    break;
                case S_SEM_ERR:
                    frm.getBtnLex().setBackground(C_GREEN);
                    frm.getBtnSyn().setBackground(C_GREEN);
                    frm.getBtnSem().setBackground(C_RED);
                    break;
                case S_SEM_GOOD:
                    frm.getBtnLex().setBackground(C_GREEN);
                    frm.getBtnSyn().setBackground(C_GREEN);
                    frm.getBtnSem().setBackground(C_GREEN);
                    break;
            }
        });
    }


    private static final int S_INITIAL = 0;
    private static final int S_READY = 10;
    private static final int S_LEX_ERR = 11;
    private static final int S_LEX_GOOD = 12;
    private static final int S_SYN_ERR = 21;
    private static final int S_SYN_GOOD = 22;
    private static final int S_SEM_ERR = 31;
    private static final int S_SEM_GOOD = 32;

    private final Color C_RED = new Color(255, 105, 97);
    private final Color C_YELLOW = new Color(253, 253, 150);
    private final Color C_GREEN = new Color(119, 221, 119);
    private final Color C_DEFAULT;
}
