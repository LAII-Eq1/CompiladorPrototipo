package itc.automatas2.gui.view;

import itc.automatas2.gui.lib.TextLineNumber;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnLex = new javax.swing.JButton();
        btnSyn = new javax.swing.JButton();
        btnSem = new javax.swing.JButton();
        btnAll = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnSTable = new javax.swing.JButton();
        btnOutPane = new javax.swing.JButton();
        jSplitPane = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCode = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtOut = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuFileOpen = new javax.swing.JMenuItem();
        menuFileSave = new javax.swing.JMenuItem();
        menuFileSaveAs = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        btnMenuAyudaLex = new javax.swing.JMenuItem();
        btnMenuAyudaSyn = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("E1");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(480, 360));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setDoubleBuffered(true);

        btnLex.setText("Léxico");
        btnLex.setToolTipText("Análisis léxico (F5)");
        btnLex.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), null));
        btnLex.setDoubleBuffered(true);
        btnLex.setFocusable(false);
        btnLex.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLex.setMaximumSize(new java.awt.Dimension(80, 28));
        btnLex.setMinimumSize(new java.awt.Dimension(80, 28));
        btnLex.setPreferredSize(new java.awt.Dimension(80, 28));
        btnLex.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnLex);

        btnSyn.setText("Sintáctico");
        btnSyn.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), null));
        btnSyn.setDoubleBuffered(true);
        btnSyn.setFocusable(false);
        btnSyn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSyn.setMaximumSize(new java.awt.Dimension(80, 28));
        btnSyn.setMinimumSize(new java.awt.Dimension(80, 28));
        btnSyn.setPreferredSize(new java.awt.Dimension(80, 28));
        btnSyn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSyn);

        btnSem.setText("Semántico");
        btnSem.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), null));
        btnSem.setDoubleBuffered(true);
        btnSem.setFocusable(false);
        btnSem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSem.setMaximumSize(new java.awt.Dimension(80, 28));
        btnSem.setMinimumSize(new java.awt.Dimension(80, 28));
        btnSem.setPreferredSize(new java.awt.Dimension(80, 28));
        btnSem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSem);

        btnAll.setText("Todos");
        btnAll.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), null));
        btnAll.setDoubleBuffered(true);
        btnAll.setFocusable(false);
        btnAll.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAll.setMaximumSize(new java.awt.Dimension(80, 28));
        btnAll.setMinimumSize(new java.awt.Dimension(80, 28));
        btnAll.setPreferredSize(new java.awt.Dimension(80, 28));
        btnAll.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnAll);
        jToolBar1.add(filler1);

        btnSTable.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), null));
        btnSTable.setFocusable(false);
        btnSTable.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSTable.setLabel("Tabla de símbolos");
        btnSTable.setMaximumSize(new java.awt.Dimension(120, 28));
        btnSTable.setMinimumSize(new java.awt.Dimension(120, 28));
        btnSTable.setPreferredSize(new java.awt.Dimension(120, 28));
        btnSTable.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSTable);

        btnOutPane.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), null));
        btnOutPane.setFocusable(false);
        btnOutPane.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOutPane.setLabel("Salida");
        btnOutPane.setMaximumSize(new java.awt.Dimension(60, 28));
        btnOutPane.setMinimumSize(new java.awt.Dimension(60, 28));
        btnOutPane.setPreferredSize(new java.awt.Dimension(48, 28));
        btnOutPane.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnOutPane);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jSplitPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        jSplitPane.setDividerLocation(350);
        jSplitPane.setDividerSize(15);
        jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane.setResizeWeight(1.0);
        jSplitPane.setAutoscrolls(true);
        jSplitPane.setCursor(new java.awt.Cursor(java.awt.Cursor.N_RESIZE_CURSOR));
        jSplitPane.setDoubleBuffered(true);
        jSplitPane.setName(""); // NOI18N
        jSplitPane.setNextFocusableComponent(jScrollPane1);

        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setDoubleBuffered(true);

        txtCode.setContentType("text/plain;charset=utf-8"); // NOI18N
        txtCode.setDocument(new TabDocument());
        txtCode.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        txtCode.setDoubleBuffered(true);
        jScrollPane1.setViewportView(txtCode);
        TextLineNumber tln = new TextLineNumber(txtCode, 1);
        jScrollPane1.setRowHeaderView(tln);

        jSplitPane.setLeftComponent(jScrollPane1);

        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setDoubleBuffered(true);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(32767, 400));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(20, 0));

        txtOut.setEditable(false);
        txtOut.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        txtOut.setDoubleBuffered(true);
        txtOut.setMaximumSize(new java.awt.Dimension(2147483647, 400));
        jScrollPane2.setViewportView(txtOut);

        jSplitPane.setRightComponent(jScrollPane2);

        getContentPane().add(jSplitPane, java.awt.BorderLayout.CENTER);

        jMenuBar1.setDoubleBuffered(true);

        jMenu1.setText("Archivo");
        jMenu1.setDoubleBuffered(true);

        menuFileOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuFileOpen.setText("Abrir");
        menuFileOpen.setToolTipText("");
        menuFileOpen.setDoubleBuffered(true);
        jMenu1.add(menuFileOpen);

        menuFileSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuFileSave.setText("Guardar");
        menuFileSave.setDoubleBuffered(true);
        jMenu1.add(menuFileSave);

        menuFileSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuFileSaveAs.setText("Guardar como");
        menuFileSaveAs.setDoubleBuffered(true);
        jMenu1.add(menuFileSaveAs);

        jSeparator1.setDoubleBuffered(true);
        jMenu1.add(jSeparator1);

        menuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        menuExit.setText("Salir");
        menuExit.setToolTipText("");
        menuExit.setDoubleBuffered(true);
        jMenu1.add(menuExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        btnMenuAyudaLex.setText("Análisis léxico");
        jMenu2.add(btnMenuAyudaLex);

        btnMenuAyudaSyn.setText("Análisis sintáctico");
        jMenu2.add(btnMenuAyudaSyn);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtnAll() {
        return btnAll;
    }

    public JButton getBtnLex() {
        return btnLex;
    }

    public JButton getBtnOutPane() {
        return btnOutPane;
    }

    public JButton getBtnSTable() {
        return btnSTable;
    }

    public JButton getBtnSem() {
        return btnSem;
    }

    public JButton getBtnSyn() {
        return btnSyn;
    }

    public JSplitPane getjSplitPane() {
        return jSplitPane;
    }

    public JMenuItem getMenuExit() {
        return menuExit;
    }

    public JMenuItem getMenuFileOpen() {
        return menuFileOpen;
    }

    public JMenuItem getMenuFileSave() {
        return menuFileSave;
    }

    public JMenuItem getMenuFileSaveAs() {
        return menuFileSaveAs;
    }

    public JMenuItem getMenuAyudaLex() {
        return btnMenuAyudaLex;
    }

    public JMenuItem getMenuAyudaSyn() {
        return btnMenuAyudaSyn;
    }

    public JTextPane getTxtCode() {
        return txtCode;
    }

    public JTextPane getTxtOut() {
        return txtOut;
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAll;
    private javax.swing.JButton btnLex;
    private javax.swing.JMenuItem btnMenuAyudaLex;
    private javax.swing.JMenuItem btnMenuAyudaSyn;
    private javax.swing.JButton btnOutPane;
    private javax.swing.JButton btnSTable;
    private javax.swing.JButton btnSem;
    private javax.swing.JButton btnSyn;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenuItem menuFileOpen;
    private javax.swing.JMenuItem menuFileSave;
    private javax.swing.JMenuItem menuFileSaveAs;
    private javax.swing.JTextPane txtCode;
    private javax.swing.JTextPane txtOut;
    // End of variables declaration//GEN-END:variables

    private static class TabDocument extends DefaultStyledDocument {

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            str = str.replaceAll("\t", "  ");
            super.insertString(offs, str, a);
        }
    }
}
