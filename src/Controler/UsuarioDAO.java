package Controler;

import BancodeDados.Conexao;
import Model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    Conexao conexao = new Conexao();

    public void CadastrarUsuario(Usuario obj) {

        conexao.conecta();
        try {
            PreparedStatement pst = conexao.conex.prepareStatement("insert into tb_usuario (nome,tipo_conta,rg,cpf,email,cargo,celular,usuario_login, senha_login) values(?,?,?,?,?,?,?,?,?)");
            pst.setString(1, obj.getNome());
            pst.setString(2, obj.getTipoConta());
            pst.setString(3, obj.getRg());
            pst.setString(4, obj.getCpf());
            pst.setString(5, obj.getEmail());
            pst.setString(6, obj.getCargo());
            pst.setString(7, obj.getCelular());
            pst.setString(8, obj.getUsuarioLogin());
            pst.setString(9, obj.getSenhaLogin());
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Dados inseridos com Sucesso !");
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Usuario já existe na base de dados ! ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na inserção de dados ! \n Erro : " + ex);
        }

    }

    public void AlterarUsuario(Usuario obj) {
        conexao.conecta();
        try {

            PreparedStatement pst = conexao.conex.prepareStatement("update tb_usuario set nome=?,tipo_conta =?,rg =?, cpf =?,email =?,cargo =?,celular =?, usuario_login = ?, senha_login = ? where id=?");
            pst.setString(1, obj.getNome());
            pst.setString(2, obj.getTipoConta());
            pst.setString(3, obj.getRg());
            pst.setString(4, obj.getCpf());
            pst.setString(5, obj.getEmail());
            pst.setString(6, obj.getCargo());
            pst.setString(7, obj.getCelular());
            pst.setString(8, obj.getUsuarioLogin());
            pst.setString(9, obj.getSenhaLogin());
            pst.setInt(10, obj.getId());

            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar !" + e);

        }

    }

    public void excluirUsuario(Usuario obj) {
        conexao.conecta();
        try {
            String SQL = "delete from tb_usuario where id = ?";

            PreparedStatement pst = conexao.conex.prepareStatement(SQL);
            pst.setInt(1, obj.getId());

            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Excluido  com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir !" + e);

        }

    }

    public List<Usuario> listarUsuario() {
        conexao.conecta();
        try {
            List<Usuario> lista = new ArrayList<>();  // criar a lista

            String SQL = "select * from tb_usuario";
            PreparedStatement stmt = conexao.conex.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                Usuario obj = new Usuario();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setTipoConta(rs.getString("tipo_conta"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setCargo(rs.getString("cargo"));
                obj.setCelular(rs.getString("celular"));
                obj.setUsuarioLogin(rs.getString("usuario_login"));
                obj.setSenhaLogin(rs.getString("senha_login"));
                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao passar a lista" + erro);
            return null;
        }

    }

    public List<Usuario> buscaClientePorNome(String nome) {
        conexao.conecta();
        try {
            List<Usuario> lista = new ArrayList<>();  // criar a lista
            String SQL = "select * from tb_usuario where nome like '%" + nome + "%'";
            PreparedStatement stmt = conexao.conex.prepareStatement(SQL);
            // stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                Usuario obj = new Usuario();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setTipoConta(rs.getString("tipo_conta"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setCargo(rs.getString("cargo"));
                obj.setCelular(rs.getString("celular"));
                obj.setUsuarioLogin(rs.getString("usuario_login"));
                obj.setSenhaLogin(rs.getString("senha_login"));

                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro" + erro);
            return null;
        }

    }

}
