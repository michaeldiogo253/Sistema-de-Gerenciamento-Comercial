
package BancodeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author michael
 */
public class Conexao {
        
    public Statement stm;
    public ResultSet rs;
    public Connection conex;
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String CAMINHO = "jdbc:mysql://localhost:3306/SGC";
    private static final String USUARIO = "michael";
    private static final String SENHA = "kelly253";
    
     public void conecta(){ // metodo responsavel de realizar a conexão com banco
       
        try {
             System.setProperty("jdbc.Drivers",DRIVER); // seta a propriedade do driver de conexão
            conex = DriverManager.getConnection(CAMINHO , USUARIO,SENHA); // realiza a coneão com o banco de dados
            System.setProperty("jdbc.Drivers",DRIVER);
           // JOptionPane.showMessageDialog(null, "Conectado com sucesso !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro de Conexão ! \n Erro :"+ ex.getMessage());
        } 
        
    }
    
    public void executaSQL(String sql){
        try {
            stm= conex.createStatement(rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_READ_ONLY);
            int rs = stm.executeUpdate(sql);
                        
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro de ExecutaSQL ! \n Erro :"+ ex.getMessage());
        }
    }
    public void executaSQLPesquisa(String sql){
        try {
            stm= conex.createStatement(rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_READ_ONLY);
             rs = stm.executeQuery(sql);
                        
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro de ExecutaSQL ! \n Erro :"+ ex.getMessage());
        }
    }
    public void desconecta () throws SQLException{
        try {
            conex.close();
            JOptionPane.showMessageDialog(null, "Desconectado com sucesso !");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro ao fechar a  conexão ! \n Erro :"+ ex.getMessage());
        } finally {
            conex.close();
        } 
    }
    
    
    
    
}
