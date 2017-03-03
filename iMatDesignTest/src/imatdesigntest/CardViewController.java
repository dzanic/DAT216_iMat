/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatdesigntest;

import java.awt.event.ActionEvent;
import javax.swing.JPanel;

/**
 *
 * @author Jakys
 */
public interface CardViewController {
    
      
    public void setCardView(JPanel cv);
    public void nextCard(ActionEvent evt);
    public void specCard(String name);
    public void previousCard(ActionEvent evt);
}
