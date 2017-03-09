/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatdesigntest;

import se.chalmers.ait.dat215.project.IMatDataHandler;

/**
 *
 * @author kamil
 */
public class IMatDesignTest {

    /**
     * @param args the command line arguments
     */  
    public static void main(String[] args) {
        System.getProperty("user.home");
        System.out.println("Starting Imat");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }      
        new ImatView().setVisible(true);
        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                //This code will run before shutdown
                IMatDataHandler.getInstance().shutDown();
                System.out.println("Closing Imat");
            }
        }));
    }
}
