/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatdesigntest;

/**
 *
 * @author Jakys
 */
public class spinnerPanel extends javax.swing.JPanel{
    
    /**
     * Creates new form spinnerPanel
     */
    public spinnerPanel() {
        initComponents();
        quantity = 0;
        this.quantityField.setText(Integer.toString(quantity));
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addiButton = new javax.swing.JButton();
        quantityField = new javax.swing.JTextField();
        subButton = new javax.swing.JButton();

        addiButton.setText("+");
        addiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addiButtonActionPerformed(evt);
            }
        });

        quantityField.setText("0");

        subButton.setText("-");
        subButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(subButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(quantityField, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addiButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(subButton)
                .addComponent(quantityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(addiButton))
        );
    }// </editor-fold>//GEN-END:initComponents
    

    private void addiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addiButtonActionPerformed
        cpy =  Integer.parseInt(this.quantityField.getText());
        cpy += 1;
        this.quantityField.setText(Integer.toString(cpy));
        System.out.println("I got the event: ADD - " + this.quantityField.getText());
    }//GEN-LAST:event_addiButtonActionPerformed

 
    private void subButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subButtonActionPerformed
        cpy =  Integer.parseInt(this.quantityField.getText());
        if (cpy <= 0){ 
        } else {
            cpy -= 1;
            this.quantityField.setText(Integer.toString(cpy));
        }
        
        System.out.println("I got the event: SUB - " + this.quantityField.getText());
        
    }//GEN-LAST:event_subButtonActionPerformed

    public void setQuantity (int qty){
        this.quantity = qty;
        this.quantityField.setText(Integer.toString(qty));
    }
    public String getQuantity (){
        return this.quantityField.getText();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addiButton;
    private javax.swing.JTextField quantityField;
    private javax.swing.JButton subButton;
    // End of variables declaration//GEN-END:variables
    public int quantity;
    private int cpy;
}
