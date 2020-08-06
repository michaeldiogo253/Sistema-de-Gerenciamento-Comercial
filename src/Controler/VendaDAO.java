package Controler;

import BancodeDados.Conexao;
import Model.Produto;
import Model.Venda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class VendaDAO {

    Conexao conexao = new Conexao();

    public void cadastrarVenda(Venda obj) {
        conexao.conecta();
        try {
            PreparedStatement pst = conexao.conex.prepareStatement("insert into tb_vendas (nome_usuario,data_venda,"
                    + "hora_venda,total_venda, observacoes,tipo_pagamento)values(?,?,?,?,?,?)");
            pst.setString(1, obj.getNomeUsuario());
            pst.setString(2, obj.getDataVenda());
            pst.setString(3, obj.getHoraVenda());
            pst.setDouble(4, obj.getTotalVenda());
            pst.setString(5, obj.getObsVenda());
            pst.setString(6, obj.getTipoPagamento());

            pst.execute();
            pst.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na inserção de dados ! \n Erro : " + ex);
        }
    }

    public Produto buscaProdutoPorNome(String nome) {
        conexao.conecta();
        try {

            String SQL = "select * from tb_produtos where nome = '" + nome + "'";
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

    public int retornaUltimaVenda() {

        try {

            int idVenda = 0;
            String sql = "select max(id) id from tb_vendas"; // retorna maior id da tabela
            PreparedStatement pst = conexao.conex.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Venda p = new Venda();
                p.setId(rs.getInt("id"));
                idVenda = p.getId();
            }
            return idVenda;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
