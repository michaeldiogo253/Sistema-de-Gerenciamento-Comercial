package Controler;

import BancodeDados.Conexao;
import Model.ItemVenda;
import Model.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public List<ItemVenda> listarItensdaVenda(int venda_id) {
        conexao.conecta();
        try {
            List<ItemVenda> lista = new ArrayList<>();  // criar a lista
            String SQL = "select  p.nome, i.qtd, p.preco , i.subtotal  from tb_itensvendas as i "
                    + " inner join tb_produtos as p on (i.produto_id = p.id) where i.venda_id = '" + venda_id + "'";
            PreparedStatement stmt = conexao.conex.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                ItemVenda item = new ItemVenda();
                Produto p = new Produto();

                p.setNome(rs.getString("p.nome"));
                item.setQuantidade(rs.getInt("i.qtd"));
                p.setPreco(rs.getFloat("p.preco"));
                item.setSutotal(rs.getDouble("i.subtotal"));
                item.setProduto(p);

                lista.add(item);
            }

            return lista;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro" + erro);
            return null;
        }

    }

}
