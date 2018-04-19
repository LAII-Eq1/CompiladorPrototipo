package itc.automatas2.gui.view;

import javax.swing.JTable;

public class TablaSimbolosDialog extends javax.swing.JFrame {

    public TablaSimbolosDialog() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        symTable = new javax.swing.JTable();

        setTitle("Tabla de símbolos");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(480, 540));

        symTable.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        symTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "TOKEN_ID", "TIPO", "VALOR", "LINEA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        symTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        symTable.setFillsViewportHeight(true);
        symTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(symTable);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JTable getSymTable() {
        return symTable;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable symTable;
    // End of variables declaration//GEN-END:variables
}
