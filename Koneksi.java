//Tugas By Group 4: AHMAD BARIQ S, ALEA KENEISHA, HAQI BUDI R

public class Koneksi {
     Connection koneksi;
    
    public static Connection Koneksi() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/rubyminimarket", "root", "");
            
            return koneksi;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
            
            return null;
        }
    }  
}
