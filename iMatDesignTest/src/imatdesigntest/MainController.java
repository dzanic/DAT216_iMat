/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatdesigntest;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    public MainController(  JTextField infoName,
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
                            JCheckBox saveAdr,
                            JCheckBox saveCrd,
                            JDialog receiptDialog,
                            JDialog profileSavedDialog,
                            JLabel basketTotalPrice){
                     
    this.infoName         = infoName;
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
    this.saveAdr          = saveAdr;
    this.saveCrd          = saveCrd;
    this.receiptDialog    = receiptDialog;
    this.profileSavedDialog = profileSavedDialog;
    this.basketTotalPrice = basketTotalPrice;
    

  
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
        System.out.println(orderList.size());
        for(Order order : orderList){           
            this.newHistoryPanel(order);
        }
        
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
       
    }
    public void populateProfile(){
        //TODO
        this.infoName.setText(IMatDataHandler.getInstance().getCustomer().getFirstName() + " "
                            + IMatDataHandler.getInstance().getCustomer().getLastName());
        this.infoMail.setText(IMatDataHandler.getInstance().getCustomer().getEmail());
        this.infoPhone.setText(IMatDataHandler.getInstance().getCustomer().getPhoneNumber());
        this.infoAdr.setText(IMatDataHandler.getInstance().getCustomer().getPostAddress());
        this.infoZip.setText(IMatDataHandler.getInstance().getCustomer().getPostCode());
        this.infoCardCombo.setSelectedIndex(0); //Fix a check to set visa -> 0 ; mastercard -> 1 etc getCreditCard().getCardType()
        this.infoCardNum.setText(IMatDataHandler.getInstance().getCreditCard().getCardNumber());
        this.infoCardOwn.setText(IMatDataHandler.getInstance().getCreditCard().getHoldersName());
        this.infoMonth.setSelectedIndex(0);
        this.infoYear.setSelectedIndex(0);
        this.infoCity.setText(IMatDataHandler.getInstance().getCustomer().getPostAddress());
    }
    public void populateFinalBasketView(){   
        this.contentPanel.removeAll();
        currentShoppingItemList = IMatDataHandler.getInstance().getShoppingCart().getItems();
        
        for(ShoppingItem shoppingItem: currentShoppingItemList){
            System.out.println("Im here!");
            this.newBasketFixedItem(shoppingItem);
        }    
    }
    
    public void saveProfile(){     
        this.saveAdress();
        this.saveCard();
        
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               profileSavedDialog.setVisible(false);
               profileSavedDialog.dispose();
                
            }
        });
        profileSavedDialog.setLocation(300,400);
        profileSavedDialog.setSize(170,36);
        timer.setRepeats(false);
        timer.start();
        profileSavedDialog.setVisible(true);

          
    }
    public void saveAdress(){
        IMatDataHandler.getInstance().getCustomer().setFirstName(this.infoName.getText()); //saves the entire name to first name
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
    
    public void checkSave(){
        if (this.saveAdr.isSelected()){
            this.saveAdress();
        }
        if (this.saveCrd.isSelected()){
            this.saveCard();
        }
    }
    public void flush(){
        this.infoAdr.setText("");
        this.infoCardCombo.setSelectedIndex(0);
        this.infoCardNum.setText("");
        this.infoCardOwn.setText("");
        this.infoCity.setText("");
        this.infoMail.setText("");
        this.infoMonth.setSelectedIndex(0);
        this.infoYear.setSelectedIndex(0);
        this.infoName.setText("");
        this.infoPhone.setText("");
        this.infoZip.setText("");
        
        IMatDataHandler.getInstance().getShoppingCart().clear();
    }
    public void generateReceipt(){     
        receiptDialog.setLocationRelativeTo(this.contentPanel);
        receiptDialog.setSize(350, 300);        
        receiptDialog.setVisible(true);
            
    }
    public void confirmOrder(){
        IMatDataHandler.getInstance().placeOrder(true);
    }


    //General use variable declarations
    private List<Product> currentProductList;
    private List<ShoppingItem> currentShoppingItemList;
    private List<Order> orderList;
    private JPanel contentPanel;
    private  JPanel contentPanel2;
    private CardLayout cardLayout;

    
    
    
    private JTextField infoName;
    private JTextField infoMail;
    private JTextField infoPhone;
    private JTextField infoAdr;
    private JTextField infoZip;
    private JTextField infoCity;
    private JComboBox  infoCardCombo;
    private JTextField infoCardNum;
    private JTextField infoCardOwn;
    private JComboBox  infoMonth;
    private JComboBox  infoYear;
    private JCheckBox  saveAdr;
    private JCheckBox  saveCrd;
    private JDialog    receiptDialog;
    private JDialog    profileSavedDialog;
    private JLabel     basketTotalPrice;
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

    

