/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatdesigntest;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import se.chalmers.ait.dat215.project.*;

/**
 *
 * @author Jakys
 */
public class MainController implements CardViewController, ItemPanelController, 
                                       BasketItemPanelController, BasketItemFixedController,
                                       OrderHistoryPanelController{

    public MainController(  JTextField infoFstName,
                            JTextField infoLstName,
                            JTextField infoMail,
                            JTextField infoPhone,
                            JTextField infoAdr,
                            JTextField infoZip,
                            JTextField infoCity,
                            JComboBox  infoCardCombo,
                            JTextField infoCardNum,
                            JTextField infoCardOwn,
                            JComboBox  infoMonth,
                            JComboBox  infoYear,
                            JTextField cvcField,
                            JCheckBox saveAdr,
                            JCheckBox saveCrd,
                            JDialog receiptDialog,
                            JDialog profileSavedDialog,
                            JDialog cannotSaveDialog,
                            JLabel basketTotalPrice,
                            JTable prevOrderTable,
                            JLabel dateLabel,
                            JLabel orderNrLabel,
                            JLabel totalPriceLabel,
                            JLabel errorMessageLabel,
                            JButton finalButton){
    this.infoFstName         = infoFstName;
    this.infoLstName      = infoLstName;
    this.infoMail         = infoMail;
    this.infoPhone        = infoPhone ;
    this.infoAdr          = infoAdr;
    this.infoZip          = infoZip;
    this.infoCity         = infoCity;
    this.infoCardCombo    = infoCardCombo;
    this.infoCardNum      = infoCardNum;
    this.infoCardOwn      = infoCardOwn;
    this.infoMonth        = infoMonth;
    this.infoYear         = infoYear;
    this.cvcField         = cvcField;
    this.saveAdr          = saveAdr;
    this.saveCrd          = saveCrd;
    this.receiptDialog    = receiptDialog;
    this.profileSavedDialog = profileSavedDialog;
    this.cannotSaveDialog = cannotSaveDialog;
    this.basketTotalPrice = basketTotalPrice;
    this.prevOrderTable   = prevOrderTable;
    this.dateLabel        = dateLabel;
    this.orderNrLabel     = orderNrLabel;
    this.totalPriceLabel  = totalPriceLabel;
    this.errorMessageLabel= errorMessageLabel;
    this.finalButton     = finalButton;
      
    }
    
    public void populateView(ProductCategory prd){
        this.contentPanel.removeAll();
        currentProductList = IMatDataHandler.getInstance().getProducts(prd);
               
        for(Product product : currentProductList){
           this.newItem(product);
        }
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
    }
    public void populateViewFavorites(){
        this.contentPanel.removeAll();
        currentProductList = IMatDataHandler.getInstance().favorites();
        
        for(Product product : currentProductList){
           this.newItemFav(product);
        }
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
    }
    public void populateViewDiscounted(){
        this.contentPanel.removeAll();
        currentProductList = IMatDataHandler.getInstance().getProducts();
        
        for(Product product : currentProductList){
           this.newItem(product);
        }   
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
    }
    public void populateViewSearch(String searchObj){
        this.contentPanel.removeAll();
        currentProductList = IMatDataHandler.getInstance().findProducts(searchObj);
        
        for(Product product : currentProductList){
           this.newItem(product);
        }
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
    }
    public void populateBasketView(){
        double temp = 0;
        this.contentPanel.removeAll();
        currentShoppingItemList = IMatDataHandler.getInstance().getShoppingCart().getItems();
        
        for(ShoppingItem shoppingItem: currentShoppingItemList){
            this.newBasketItem(shoppingItem);
            temp += shoppingItem.getTotal();
        }
        this.basketTotalPrice.setText(String.valueOf(temp));
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
    }  
    public void populateHistory(){
        this.contentPanel.removeAll();
        orderList = IMatDataHandler.getInstance().getOrders();
        
        for(Order order : orderList){
            this.newHistoryPanel(order);
        }
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
                    
    }
    public void populateProfile(){
        this.infoFstName.setText(IMatDataHandler.getInstance().getCustomer().getFirstName()); 
        this.infoLstName.setText(IMatDataHandler.getInstance().getCustomer().getLastName());           
        this.infoMail.setText(IMatDataHandler.getInstance().getCustomer().getEmail());
        this.infoPhone.setText(IMatDataHandler.getInstance().getCustomer().getPhoneNumber());
        this.infoAdr.setText(IMatDataHandler.getInstance().getCustomer().getAddress());
        this.infoZip.setText(IMatDataHandler.getInstance().getCustomer().getPostCode());
        
        this.infoCardCombo.setSelectedItem(IMatDataHandler.getInstance().getCreditCard().getCardType());
        this.infoCardNum.setText(IMatDataHandler.getInstance().getCreditCard().getCardNumber());       
        this.infoCardOwn.setText(IMatDataHandler.getInstance().getCreditCard().getHoldersName());
        this.infoMonth.setSelectedIndex(IMatDataHandler.getInstance().getCreditCard().getValidMonth());
        this.infoYear.setSelectedIndex(0);
        this.infoCity.setText(IMatDataHandler.getInstance().getCustomer().getPostAddress());
        this.infoYear.setSelectedItem(String.valueOf( IMatDataHandler.getInstance().getCreditCard().getValidYear()));
    }
    public void populateFinalBasketView(){   
        this.contentPanel.removeAll();
        currentShoppingItemList = IMatDataHandler.getInstance().getShoppingCart().getItems();
        
        for(ShoppingItem shoppingItem: currentShoppingItemList){
            System.out.println("Im here!");
            this.newBasketFixedItem(shoppingItem);
        }    
    }
    public void enableFinal(){
       if(!IMatDataHandler.getInstance().isCustomerComplete() && cvcField.getText().isEmpty() ){           
            finalButton.setEnabled(false);
            generateMessage("Fattas information i din profil!", this.contentPanel, Color.RED);
             
        } else if ((IMatDataHandler.getInstance().getShoppingCart().getItems().isEmpty())){
            finalButton.setEnabled(false);      
            generateMessage("Fattas information i din profil!", this.contentPanel, Color.RED);
            generateMessage("Varukorgen är tom!", this.contentPanel, Color.RED);
            
        } else {
            finalButton.setEnabled(true);
        }
              
    }
    
    public void saveProfile(){
        if (checkEmptyAdressFields() && checkEmptyCardFields()){
                   
        this.saveAdress();
        this.saveCard();
        
            generateMessage("Sparat!", this.infoYear, Color.GREEN);

        }
    }
    public void saveAdress(){     
        IMatDataHandler.getInstance().getCustomer().setFirstName(this.infoFstName.getText()); 
        IMatDataHandler.getInstance().getCustomer().setLastName(this.infoLstName.getText()); 
        IMatDataHandler.getInstance().getCustomer().setEmail(this.infoMail.getText());
        IMatDataHandler.getInstance().getCustomer().setPhoneNumber(this.infoPhone.getText());
        IMatDataHandler.getInstance().getCustomer().setPostAddress(this.infoCity.getText());
        IMatDataHandler.getInstance().getCustomer().setPostCode(this.infoZip.getText());
        IMatDataHandler.getInstance().getCustomer().setAddress(this.infoAdr.getText());
               
    }
    public void saveCard(){
        IMatDataHandler.getInstance().getCreditCard().setCardType(objToString(this.infoCardCombo.getSelectedItem()));
        IMatDataHandler.getInstance().getCreditCard().setCardNumber(this.infoCardNum.getText());
        IMatDataHandler.getInstance().getCreditCard().setHoldersName(this.infoCardOwn.getText());
        IMatDataHandler.getInstance().getCreditCard().setValidMonth(Integer.parseInt(objToString(this.infoMonth.getSelectedItem())));
        IMatDataHandler.getInstance().getCreditCard().setValidYear(Integer.parseInt(objToString(this.infoYear.getSelectedItem())));
        
    }
    private boolean checkEmptyAdressFields(){
         
               
        if(this.infoFstName.getText().isEmpty()){
            generateMessage("Var god ifyll ditt förnamn!", this.infoFstName, Color.RED);            
            return false;
            
        }else if (this.infoLstName.getText().isEmpty()){
            generateMessage("Var god ifyll ditt efternamn!", this.infoLstName, Color.RED);            
            return false;
        }
        else if (this.infoMail.getText().isEmpty()){
            generateMessage("Var god ifyll din email adress!", this.infoMail, Color.RED);
            return false;
            
        } else if (this.infoPhone.getText().isEmpty()){
            generateMessage("Var god ifyll telefonnummer!", this.infoPhone, Color.RED);
            return false;
            
        } else if (this.infoCity.getText().isEmpty()){
            generateMessage("var god ifyll Ort!", this.infoCity, Color.RED);
            return false;
            
        } else if (this.infoZip.getText().isEmpty()){
            generateMessage("var god ifyll Post nummer", this.infoZip, Color.RED);
            return false;
            
        } else if (this.infoAdr.getText().isEmpty()){
            generateMessage("Var god ifyll Post Adress!", this.infoAdr, Color.RED);
            return false;
        }
               
        return true;
    }
   
    private boolean checkEmptyCardFields(){
        if (this.infoCardCombo.getSelectedIndex() == 0){
            generateMessage("Var god välj kort typ!", this.infoCardCombo, Color.RED);
            return false;
            
        } else if (this.infoCardNum.getText().isEmpty()){
            generateMessage("Var god ifyll kortnummer!", this.infoCardNum, Color.RED);
            return false;
            
        } else if (this.infoCardOwn.getText().isEmpty()){
            generateMessage("Var god ifyll kort innehavarens namn!", this.infoCardOwn, Color.RED);
            return false;
            
        } else if (this.infoMonth.getSelectedIndex() == 0){
            generateMessage("Var god välj utgångsmånad!", this.infoMonth, Color.RED);
            return false;
            
        } else if (this.infoYear.getSelectedIndex() == 0){
            generateMessage("Var god välj utgångsår!", this.infoYear, Color.RED);
            return false;
        } 
        return true;
    }
    
    //Helper function, this negates the need to send several dialogs that only
    // really differ in message/color.
    private void generateMessage(String error, Component c, Color cl ){
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               cannotSaveDialog.setVisible(false);
               cannotSaveDialog.dispose();
                
            }
        });        
        cannotSaveDialog.setLocationRelativeTo(c);
            cannotSaveDialog.setSize(170,36);
            errorMessageLabel.setText(error);
            errorMessageLabel.getParent().setBackground(cl);
            timer.setRepeats(false);
            timer.start();
            cannotSaveDialog.setVisible(true);
    }
    
    
    public void checkSave(){
        if (this.saveAdr.isSelected()){
            this.saveAdress();
        }
        if (this.saveCrd.isSelected()){
            this.saveCard();
        }
    }
    public void flush(){
        this.infoAdr.setText(null);
        this.infoCardCombo.setSelectedIndex(0);
        this.infoCardNum.setText(null);
        this.infoCardOwn.setText(null);
        this.infoCity.setText(null);
        this.infoMail.setText(null);
        this.infoMonth.setSelectedIndex(0);
        this.infoYear.setSelectedIndex(0);
        this.infoFstName.setText(null);
        this.infoPhone.setText(null);
        this.infoZip.setText(null);
        
        IMatDataHandler.getInstance().getShoppingCart().clear();
    }
    public void generateReceipt(){     
        receiptDialog.setLocationRelativeTo(this.contentPanel);
        receiptDialog.setSize(350, 300);        
        receiptDialog.setVisible(true);
            
    }
    public void confirmOrder(){
            IMatDataHandler.getInstance().placeOrder(true);
            System.out.println("I came here! Ordered stuff");
            generateReceipt();
            checkSave();
            flush();
       
    }


    //General use variable declarations
    private List<Product> currentProductList;
    private List<ShoppingItem> currentShoppingItemList;
    private List<Order> orderList;
    private JPanel contentPanel;
    private  JPanel contentPanel2;
    private CardLayout cardLayout;
    private JButton    finalButton;
    
    
    
    private JTextField infoFstName;
    private JTextField infoLstName;
    private JTextField infoMail;
    private JTextField infoPhone;
    private JTextField infoAdr;
    private JTextField infoZip;
    private JTextField infoCity;
    private JTextField cvcField;
    private JComboBox  infoCardCombo;
    private JTextField infoCardNum;
    private JTextField infoCardOwn;
    private JComboBox  infoMonth;
    private JComboBox  infoYear;
    private JCheckBox  saveAdr;
    private JCheckBox  saveCrd;
    private JDialog    receiptDialog;
    private JDialog    profileSavedDialog;
    private JDialog    cannotSaveDialog;
    private JLabel     basketTotalPrice;
    private JTable     prevOrderTable;
    private JLabel     dateLabel;
    private JLabel     orderNrLabel;
    private JLabel     totalPriceLabel;
    private JLabel     errorMessageLabel;
    
    
    //End of variable declarations
    
    //CARDVIEW CONTROLLER OVERRIDES BLOCK
    @Override
    public void setCardView(JPanel cv){
        contentPanel = cv;
        cardLayout = (CardLayout)contentPanel.getLayout();
    };
    
    @Override
    public void nextCard(ActionEvent evt){
        cardLayout.next(contentPanel);
       
    };
    
    public void panelSwap(){
        JPanel temp = contentPanel;
        this.setContentPanel(contentPanel2);
        this.setContentPanel2(temp);
    }
    
    public final void setContentPanel(JPanel contentPanel) {
        this.contentPanel = contentPanel;
    }
    public final void setContentPanel2(JPanel contentPanel) {
        this.contentPanel2 = contentPanel;
    }
    //END OF CARDVIEW CONTROLLER OVERRIDE BLOCK
    
    
    @Override
    public void newItem(Product product){        
        final ItemPanel item = new ItemPanel(product);  
        this.contentPanel.add(item);
        this.contentPanel.validate();
        
    }
    @Override
    public void newItemFav(Product product){
        final ItemPanel item = new ItemPanel(product,true);  
        this.contentPanel.add(item);
        this.contentPanel.validate();
    }
    @Override
    public void newBasketItem(ShoppingItem shoppingItem){
        final BasketItem basketItem = new BasketItem(shoppingItem);
        this.contentPanel.add(basketItem);
        this.contentPanel.validate();
       
    }
    @Override
    public void newBasketFixedItem(ShoppingItem shoppingItem){
        final BasketItemFixed basketItemFixed = new BasketItemFixed(
                shoppingItem.getProduct().getName(),
                String.valueOf(shoppingItem.getAmount()), 
                String.valueOf(shoppingItem.getProduct().getPrice()),
                String.valueOf(shoppingItem.getTotal()));
        System.out.println("I generated: " + shoppingItem.getProduct().getName());
        this.contentPanel.add(basketItemFixed);
        this.contentPanel.validate();
    }
    @Override
    public void newHistoryPanel(Order order){
        final OrderHistoryPanel orderHistoryPanel = new OrderHistoryPanel(order);
        this.contentPanel.add(orderHistoryPanel);
        this.contentPanel.validate();
        this.setListener(orderHistoryPanel);
    }
    public void setListener(OrderHistoryPanel orderHistoryPanel ){
        orderHistoryPanel.addMouseListener(new MouseListener() {
           
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){}

            @Override
            public void mouseClicked(MouseEvent e) {
                panelSwap();
                System.out.println("I got E!");
                fillOrderTable(orderHistoryPanel.getOrder());
                nextCard( (new ActionEvent(e.getSource(), e.getID(), e.paramString())));
                panelSwap();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
        });
       
    }
    public void fillOrderTable(Order order){
        int j= 0;
        this.dateLabel.setText( (new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")).format(order.getDate()));
        this.orderNrLabel.setText(String.valueOf(order.getOrderNumber()));
        this.totalPriceLabel.setText("something");  //Need to do a forloop calculating everything because the total isnt supported by the backend.
        for(ShoppingItem shopItem : order.getItems()){
            
               this.prevOrderTable.getModel().setValueAt(shopItem.getProduct().getName(), j, 0);
               this.prevOrderTable.getModel().setValueAt(shopItem.getAmount(), j, 1);
               this.prevOrderTable.getModel().setValueAt(shopItem.getProduct().getPrice(), j, 2);
               this.prevOrderTable.getModel().setValueAt(shopItem.getTotal(), j, 3);
            
            j++;
        }
       
        
    }
    @Override
    public void specCard(String name){
        this.cardLayout.show(contentPanel, name);
    }
    
    @Override
    public void previousCard(ActionEvent evt){
        this.cardLayout.previous(this.contentPanel);
    }
    
    
    private String objToString(Object obj){
        
        if (obj == null){
           return null;
          
        }
        return obj.toString();
    }
}

    

