//Tugas By Group 4: AHMAD BARIQ S, ALEA KENEISHA, HAQI BUDI R

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class ProductPage extends javax.swing.JFrame {
    public Statement st;
    public ResultSet rs;
    public DefaultTableModel tabModel;
    Connection cn = Koneksi.Koneksi();
    
    public void judul() {
        Object[] judul = {
          "PRODUCTID", "NAME", "CATEGORY", "QUANTITY", "PRICE"
        };
        tabModel = new DefaultTableModel(null, judul);
        jtableprodlist.setModel(tabModel);
      }

public void showtable(String where) {
  try {
    st = cn.createStatement();
    tabModel.getDataVector().removeAllElements();
    tabModel.fireTableDataChanged();
    rs = st.executeQuery("SELECT * FROM product " + where);
    
    while (rs.next()) {
      Object[] data = {
        rs.getString("productid"),
        rs.getString("name"),
        rs.getString("category"),
        rs.getString("quantity"),
        rs.getString("price"),
      }; 
        tabModel.addRow(data);
    }
  } catch(SQLException e) {
  }
}

private void Searchdata(String key){
        try{
            Object[] judul_kolom = {"PRODUCTID", "NAME", "CATEGORY", "QUANTITY", "PRICE"};
            jtableprodlist.setModel(tabModel);
            
            Connection cn = Koneksi.Koneksi();
            st = cn.createStatement();
            tabModel.getDataVector().removeAllElements();
            
            rs = st.executeQuery("SELECT * FROM product WHERE productid LIKE '%"+key+"%' OR name LIKE '%"+key+"%'");  
            while(rs.next()){
                Object[] data={
                    rs.getString("productid"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getString("quantity"),
                    rs.getString("price"),        
                    };
               tabModel.addRow(data);
            }                
        } catch (SQLException e) {
        }
    }

public void reset() {
    jtfprodid.setText("");
    jtfprodname.setText("");
    jcbCategory.setSelectedIndex(0);
    jtfquantity.setText("");
    jtfprice.setText("");
}
    
    public ProductPage() {
        initComponents();
        judul();
        showtable("");

        jbtndelete.setEnabled(false);
        jbtnupdate.setEnabled(false);
    }
  
  //resetbutton
  private void jbtnresetActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        reset();
    }  
  
    //deletebutton
    private void jbtndeleteActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    try {
    int jawab;
    
        if ((jawab = JOptionPane.showConfirmDialog(null, "do you want delete this data?", "confirmation", JOptionPane.YES_NO_OPTION)) == 0) {
          st = cn.createStatement();
          st.executeUpdate("DELETE FROM product WHERE productid='"
              + tabModel.getValueAt(jtableprodlist.getSelectedRow(), 0) + "'");
          showtable("");
          reset();
        }
      } catch (HeadlessException | SQLException e) {
      }
    }                                          

    //addbutton
    private void jbtnaddActionPerformed(java.awt.event.ActionEvent evt) {                                        
    try {
        st = cn.createStatement();
        st.executeUpdate("INSERT INTO product VALUES ('" + jtfprodid.getText()
                                                  +"','"+jtfprodname.getText()
                                                  +"','"+jcbCategory.getSelectedItem()
                                                  +"','"+jtfquantity.getText()
                                                  +"','"+jtfprice.getText()+"')");
        showtable("");
         JOptionPane.showMessageDialog(null, "Data has been success to be upload");
         jtfprodid.setText("");
         jtfprodname.setText("");
         jcbCategory.setSelectedItem("");
         jtfquantity.setText("");
         jtfprice.setText("");
        
        } catch (HeadlessException | SQLException e) {
        }
    }   
  
  //table
  private void jtableprodlistMouseClicked(java.awt.event.MouseEvent evt) {                                            
        // TODO add your handling code here:
         jtfprodid.setText(jtableprodlist.getValueAt(jtableprodlist.getSelectedRow(), 0).toString());
         jtfprodname.setText(jtableprodlist.getValueAt(jtableprodlist.getSelectedRow(), 1).toString());
         jcbCategory.setSelectedItem(jtableprodlist.getValueAt(jtableprodlist.getSelectedRow(), 2).toString());
         jtfquantity.setText(jtableprodlist.getValueAt(jtableprodlist.getSelectedRow(),3).toString());
         jtfprice.setText(jtableprodlist.getValueAt(jtableprodlist.getSelectedRow(), 4).toString());
         jbtnadd.setEnabled(true);
         jbtnupdate.setEnabled(true);
         jbtndelete.setEnabled(true);
    }  
  
  //updatebutton
  private void jbtnupdateActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        try {
    
            st = cn.createStatement();
            st.executeUpdate("UPDATE  product SET productid='" + jtfprodid.getText() 
                + "', name='" + jtfprodname.getText() 
                + "', category='" + jcbCategory.getSelectedItem() 
                + "', quantity='" + jtfquantity.getText() 
                + "', price='" + jtfprice.getText() + "' WHERE productid='" +jtfprodid.getText() + "'"); 
            showtable("");
            JOptionPane.showMessageDialog(null, "Update Success");
            reset();
       
        } catch (HeadlessException | SQLException e) {
        }
    }   
  
  //searchbox
  String key;
    private void jtfsearchKeyReleased(java.awt.event.KeyEvent evt) {                                      
        // TODO add your handling code here: 
        key = jtfsearch.getText();
        System.out.println(key);
        
        if(key!=""){
            Searchdata(key);
        }else{
           showtable("") ;
        }
    }   
