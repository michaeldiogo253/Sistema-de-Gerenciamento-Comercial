package Controler;

import BancodeDados.Conexao;
import Model.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class VendaDAO {

    Conexao conexao = new Conexao();

    public Produto buscaProdutoPorNome(String nome) {
        conexao.conecta();
        try {

            String SQL = "select * from tb_produtos where nome like '%" + nome + "%'";
            PreparedStatement stmt = conexao.conex.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery(SQL);
            Produto obj = new Produto();

            while (rs.next()) {

                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setPreco(rs.getFloat("preco"));
                obj.setQuantidade(rs.getInt("qtd_estoque"));
                obj.setDescricao(rs.getString("descricao"));

            }

            return obj;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro" + erro);
            return null;
        }

    }

}
