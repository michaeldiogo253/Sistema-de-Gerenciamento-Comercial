package Controler;

import BancodeDados.Conexao;
import Model.ItemVenda;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ItemVendaDAO {

    Conexao conexao = new Conexao();

    public void CadastrarItensVenda(ItemVenda obj) {
        conexao.conecta();
        try {
            PreparedStatement pst = conexao.conex.prepareStatement("insert into tb_itensvendas (venda_id,produto_id,"
                    + " qtd, subtotal)values(?,?,?,?)");
            pst.setInt(1, obj.getVenda().getId());
            pst.setInt(2, obj.getProduto().getId());
            pst.setInt(3, obj.getQuantidade());
            pst.setDouble(4, obj.getSutotal());

            pst.execute();
            pst.close();

           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar itens ! \n Erro : " + ex);
        }
    }

}
