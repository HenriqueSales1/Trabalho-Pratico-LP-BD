/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visual.itensVenda;

import dao.DAOItensVenda;
import dao.DAOProduto;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.ItensVenda;
import modelo.Produto;
import modelo.Venda;
import visual.venda.CadastroVenda;

/**
 *
 * @author Calebe
 */
public class CadastroItensVenda extends javax.swing.JFrame {
    private DAOItensVenda daoItensVenda = new DAOItensVenda();
    private List<ItensVenda> listaItens;
    private Venda venda = new Venda();
    private CadastroVenda cadVenda;

    /**
     * Creates new form CadastroItensVenda
     */
    public CadastroItensVenda(Venda venda, List<ItensVenda> listaItens, CadastroVenda cadVenda) {
        this();
        this.venda = venda;
        this.listaItens = listaItens;
        this.cadVenda = cadVenda;
    }
    
    public CadastroItensVenda(){
        initComponents();
        cbProduto.setModel(new DefaultComboBoxModel(new DAOProduto().consultarProdutos().toArray()));
        this.setLocationRelativeTo(null);
    }
    
    public void limpaTela(){
        txtQuantidade.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbProduto = new javax.swing.JLabel();
        cbProduto = new javax.swing.JComboBox<>();
        lbQuantidade = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        btnIncluir = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar Itens na Venda");

        lbProduto.setText("Produto");

        cbProduto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbQuantidade.setText("Quantidade");

        btnIncluir.setText("Incluir");
        btnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbProduto)
                    .addComponent(cbProduto, 0, 376, Short.MAX_VALUE)
                    .addComponent(lbQuantidade)
                    .addComponent(txtQuantidade))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(240, Short.MAX_VALUE)
                .addComponent(btnIncluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbQuantidade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIncluir)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        // TODO add your handling code here:
        ItensVenda itensVenda = new ItensVenda();
        boolean inserir = true;
        try {
            itensVenda.setQuantidadeProduto(Integer.parseInt(txtQuantidade.getText()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro na Quantidade de Produto");
            txtQuantidade.requestFocus();
            inserir = false;
        }
        itensVenda.setProduto((Produto) cbProduto.getSelectedItem());
        
        if(inserir){
            listaItens.add(itensVenda);
            cadVenda.valor += itensVenda.getProduto().getPreco()*itensVenda.getQuantidadeProduto();
            cadVenda.alterarValor();
            limpaTela();
        }      
        
    }//GEN-LAST:event_btnIncluirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JComboBox<String> cbProduto;
    private javax.swing.JLabel lbProduto;
    private javax.swing.JLabel lbQuantidade;
    private javax.swing.JTextField txtQuantidade;
    // End of variables declaration//GEN-END:variables
}
