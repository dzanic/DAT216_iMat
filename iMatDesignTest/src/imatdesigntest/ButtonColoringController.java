/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatdesigntest;

import java.awt.Color;
import javax.swing.JButton;

/**
 *
 * @author Jakys
 */
public class ButtonColoringController {
    
    private JButton focusedButton1;
    private JButton focusedButton2;
    
    
    public ButtonColoringController(){
        
    }
    //For null value safety, always init with 2 arbitrary buttons
    public ButtonColoringController(JButton focusedButton1, 
                                    JButton focusedButton2){
        this();
        this.setButtonFocus(focusedButton1);
        this.setButtonFocus2(focusedButton2);
        
    }
    
    //Wrapping method
     public void buttonRecolor(JButton focusedButton){
        this.setButtonFocus(focusedButton);
        buttonReColorLogic();
    }
     
    public final void setButtonFocus(JButton focusedButton) {
        this.focusedButton1 = focusedButton;
    }
    public final void setButtonFocus2(JButton focusedButton) {
        this.focusedButton2 = focusedButton;
    }
    
    //Swap the two buttons, effectively meaning you only ever
    //act upon focusButton1, leaving 2 as a history-marker
    private void buttonSwap(){
        JButton temp = focusedButton1;
        
        this.setButtonFocus(focusedButton2);  
        this.setButtonFocus2(temp);
              
    }
       
    //This is just a helper method and should never be called 
    //outside of the controller, as this may destroy the logic flow.
    private void buttonReColorLogic(){
        Color c = new Color(51, 51, 51);                                        //Just the color we want to match against
                                                 
        if(this.focusedButton1.getBackground().equals(c)){           
            this.focusedButton1.setBackground(Color.green); 
        }
            
        buttonSwap();
        
        if(this.focusedButton1.getBackground().equals(Color.green)){
            this.focusedButton1.setBackground(c);
        }
    }
  
}
