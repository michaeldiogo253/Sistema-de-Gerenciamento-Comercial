package Controler;

import BancodeDados.Conexao;
import Model.Produto;
import Model.Usuario;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author michael
 */
public class ProdutoDAO {

    Conexao conexao = new Conexao();

    public void cadastrarProduto(Produto obj) {
        conexao.conecta();
        try {
            PreparedStatement pst = conexao.conex.prepareStatement("insert into tb_produtos (nome,preco , qtd_estoque, descricao) values( ?,?,?,?)");
            pst.setString(1, obj.getNome());
            pst.setFloat(2, obj.getPreco()); // preciso converter o conteudo de string para double
            pst.setInt(3, obj.getQuantidade());
            pst.setString(4, obj.getDescricao());

            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Dados inseridos com Sucesso !");
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Produto já existe na base de dados ! ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na inserção de dados ! \n Erro : " + ex);
        }
        
    }

    public void excluirProduto(Produto obj) {
        conexao.conecta();
        try {

            String SQL = "delete from tb_produtos where id = ?";
            PreparedStatement pst = conexao.conex.prepareStatement(SQL);
            pst.setInt(1, obj.getId());
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Produto excluido  com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir !");
        }

    }

    public void alterarProduto(Produto obj) {
        conexao.conecta();

        try {
            PreparedStatement pst = conexao.conex.prepareStatement("update tb_produtos set nome= ?,preco = ? , qtd_estoque = ?, descricao = ? where id = ?");
            pst.setString(1, obj.getNome());
            pst.setFloat(2, obj.getPreco());
            pst.setInt(3, obj.getQuantidade());
            pst.setString(4, obj.getDescricao());
            pst.setInt(5, obj.getId());

            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar!" + e);

        }

    }

    public List<Produto> listarProduto() {
        conexao.conecta();
        try {
            List<Produto> lista = new ArrayList<>();  // criar a lista

            String SQL = "select * from tb_produtos";
            PreparedStatement stmt = conexao.conex.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                Produto obj = new Produto();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setPreco(rs.getFloat("preco"));
                obj.setQuantidade(rs.getInt("qtd_estoque"));
                obj.setDescricao(rs.getString("descricao"));
                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao passar a lista" + erro);
            return null;
        }

    }

    public List<Produto> buscaProdutoPorNome(String nome) {
        conexao.conecta();
        try {
            List<Produto> lista = new ArrayList<>();  // criar a lista
            String SQL = "select * from tb_produtos where nome like '%" + nome + "%'";
            PreparedStatement stmt = conexao.conex.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                Produto obj = new Produto();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setPreco(rs.getFloat("preco"));
                obj.setQuantidade(rs.getInt("qtd_estoque"));
                obj.setDescricao(rs.getString("descricao"));
                lista.add(obj);

            }

            return lista;

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Produto já existe na base de dados ! ");
            return null;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro" + erro);
            return null;
        }

    }

    public String converteValorDinheiro(String valor) {
        String valor1 = valor.replace("R$", "").replace(" ", "").replace(",", ".");
        BigDecimal valorDec = new BigDecimal(valor1);
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String valorFormat = nf.format(valorDec).toString();
        return valorFormat;
    }

    public Float converteValorSalvarBanco(String valor) {

        String valorSemEspacos = valor.replaceAll(" ", "");
        String valor1 = valorSemEspacos.replace("R$", "").replace(".", "");
        String valor2 = valor1.replace(",", ".");
        return Float.valueOf(valor2);
    }

}
