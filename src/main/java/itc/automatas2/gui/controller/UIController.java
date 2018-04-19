package itc.automatas2.gui.controller;

import itc.automatas2.gui.lib.Util;
import itc.automatas2.gui.model.ArchivoModel;
import itc.automatas2.gui.view.MainFrame;
import itc.automatas2.gui.view.TablaSimbolosDialog;
import itc.automatas2.lexico.AnalizadorLexico;
import itc.automatas2.lexico.Tipos;
import itc.automatas2.lexico.Tokens;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintStream;

public class UIController {
    private MainFrame frm;
    private TablaSimbolosDialog dlgTable;
    private JFileChooser fc;
    private String digest;
    private ArchivoModel handle;
    private AnalizadorLexico a;

    public UIController() {
        this.frm = new MainFrame();
        this.dlgTable = new TablaSimbolosDialog();
        this.fc = new JFileChooser();
        a = new AnalizadorLexico();
        // Se redirigen las salidas al textpane
        UIOutputStreamController out = new UIOutputStreamController(frm.getTxtOut());
        UIErrorStreamController err = new UIErrorStreamController(frm.getTxtOut());
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));

        fc.setFileHidingEnabled(true);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(new FileNameExtensionFilter("Programa E1", "e1", "E1"));
        initListeners();
        frm.getMenuFileSave().setEnabled(false);
        frm.getBtnLex().setEnabled(false);
        frm.getBtnSyn().setEnabled(false);
        frm.getBtnSem().setEnabled(false);
        frm.getBtnAll().setEnabled(false);
        frm.getBtnSTable().setEnabled(false);
        setOutPaneVisible(false);
        dlgTable.setLocationRelativeTo(frm);
        frm.setVisible(true);
        digest = "";
    }

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
                                lex();
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
        frm.getMenuExit().addActionListener(e -> salir());

        // Toolbar
        frm.getBtnLex().addActionListener(e -> lex());
        frm.getBtnSyn().addActionListener(e -> syn());
        frm.getBtnSem().addActionListener(e -> sem());
        frm.getBtnAll().addActionListener(e -> sem());
        frm.getBtnSTable().addActionListener(e -> toggleTSDialog());
        frm.getBtnOutPane().addActionListener(e -> toggleOutPane());
    }

    private void abrirArchivo() {
        fc.setDialogTitle("Abrir");
        fc.setCurrentDirectory(new File("."));
        if (fc.showOpenDialog(frm) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            handle = new ArchivoModel(file);
            SwingUtilities.invokeLater(() -> {
                JTextPane code = frm.getTxtCode();
                String content = handle.readAll();
                code.setText(content);
                digest = Util.md5(code.getText());
                frm.getMenuFileSave().setEnabled(true);
                updateTitle(handle.getFile());
                frm.getBtnLex().setEnabled(true);
                frm.getBtnSyn().setEnabled(false);
                frm.getBtnAll().setEnabled(false);
                frm.getBtnSTable().setEnabled(false);
                vaciarYOcultarTS();
            });
        }
    }

    private void guardarArchivo() {
        SwingUtilities.invokeLater(() -> {
            String content = frm.getTxtCode().getText();
            handle.write(content);
            digest = Util.md5(content);
            frm.getBtnSyn().setEnabled(false);
        });
    }

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
                frm.getBtnLex().setEnabled(true);
                frm.getBtnSyn().setEnabled(false);
                frm.getBtnAll().setEnabled(false);
            });
        }

    }

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

    private void setOutPaneVisible(boolean visible) {
        SwingUtilities.invokeLater(() -> {
            JSplitPane pane = frm.getjSplitPane();
            if (!visible) {
                pane.setDividerLocation(1.0d);
            } else {
                pane.setDividerLocation(pane.getHeight() - 200);
            }
            pane.setEnabled(visible);
        });
    }

    private void toggleOutPane() {
        setOutPaneVisible(!frm.getjSplitPane().isEnabled());
    }

    private void promptChanged() {
        JOptionPane.showMessageDialog(frm,
                "Los cambios deben ser guardados para proceder con el análisis.\n" +
                        "Por favor, guarde y vuelva a intentar.", "Advertencia",
                JOptionPane.WARNING_MESSAGE);
    }

    private void lex() {
        if (codeChanged()) {
            promptChanged();
        } else if (handle != null) {
            SwingUtilities.invokeLater(() -> {
                setOutPaneVisible(true);
                System.out.printf("ANÁLISIS DEL PROGRAMA \"%s\"\n", handle.getFile().toString());
                if (a.analizar(handle.getFile().toString())) {
                    System.out.println("El analizador declaró el código como válido");
                    frm.getBtnSyn().setEnabled(true);
                } else {
                    System.err.println("El analizador declaró el código como inválido");
                    frm.getBtnSyn().setEnabled(false);
                }
                poblarTS();
                frm.getBtnSTable().setEnabled(true);
                a.imprimirErrores();
                System.out.println();
            });
        }
    }

    private void syn() {
        setOutPaneVisible(true);
        System.err.println("No implementado aun!");
    }

    private void sem() {
        System.err.println("No implementado aun!");
    }

    private void all() {
        System.err.println("No implementado aun!");
    }

    private void vaciarYOcultarTS() {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel dtm = (DefaultTableModel) dlgTable.getSymTable().getModel();
            dtm.setRowCount(0);
            dlgTable.setVisible(false);
        });
    }

    private void poblarTS() {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel dtm = (DefaultTableModel) dlgTable.getSymTable().getModel();
            dtm.setRowCount(0);
            a.tS.registros().forEach(reg -> {
                dtm.addRow(new Object[]{
                        reg.ID,
                        reg.NOMBRE,
                        Tokens.nombres[reg.TOKEN_ID],
                        Tipos.nombres[reg.TIPO],
                        reg.VAL,
                        reg.LINE
                });
            });
            Util.autosizeColumns(dlgTable.getSymTable(), 72, 16);
        });
    }

    private void toggleTSDialog() {
        dlgTable.setVisible(!dlgTable.isVisible());
    }

    private void updateTitle(File activeFile) {
        frm.setTitle(String.format("%s [%s]", "E1", activeFile));
    }


    private boolean codeChanged() {
        String newDigest = Util.md5(frm.getTxtCode().getText());
        return !digest.equals(newDigest);
    }

}
