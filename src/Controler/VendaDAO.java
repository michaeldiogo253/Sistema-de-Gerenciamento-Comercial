package Controler;

import BancodeDados.Conexao;
import Model.Produto;
import Model.Usuario;
import Model.Venda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public List<Venda> RelatorioUsuario(String nome) {
        conexao.conecta();
        try {

            Ferramentas f = new Ferramentas();
            String data = f.DataAmericana();
            List<Venda> lista = new ArrayList<>();  // criar a lista
            String SQL = "select * from tb_vendas where nome_usuario = '" + nome + "' and data_venda = '" + data + "'";
            PreparedStatement stmt = conexao.conex.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                Venda obj = new Venda();

                obj.setHoraVenda(rs.getString("hora_venda"));
                obj.setTotalVenda(rs.getDouble("total_venda"));
                obj.setObsVenda(rs.getString("observacoes"));
                obj.setTipoPagamento(rs.getString("tipo_pagamento"));

                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro" + erro);
            return null;
        }

    }

    public List<Venda> listarVendasPorPeriodo(LocalDate data_inicio, LocalDate data_final) {
        conexao.conecta();
        try {
            List<Venda> lista = new ArrayList<>();  // criar a lista
            String SQL = "select v.id, date_format(v.data_venda,'%d/%m/%Y') as data_formatada ,v.hora_venda, v.nome_usuario, "
                    + "v.total_venda , v.observacoes , v.tipo_pagamento from tb_vendas as v "
                    + "inner join tb_usuario as u on (v.nome_usuario = u.usuario_login) where v.data_venda BETWEEN ' "
                    + data_inicio.toString() + "' AND '" + data_final.toString() + "' order by v.data_venda,v.hora_venda";

            PreparedStatement stmt = conexao.conex.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                Venda obj = new Venda();
                Usuario c = new Usuario();

                obj.setId(rs.getInt("v.id"));
                obj.setDataVenda(rs.getString("data_formatada"));
                obj.setHoraVenda(rs.getString("v.hora_venda"));
                obj.setNomeUsuario(rs.getString("v.nome_usuario"));
                obj.setTotalVenda(rs.getDouble("v.total_venda"));
                obj.setObsVenda(rs.getString("v.observacoes"));
                obj.setTipoPagamento(rs.getString("v.tipo_pagamento"));

                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro" + erro);
            return null;
        }

    }

    public List <Produto> listarProdutosMaisVendidos() {
        conexao.conecta();
        try {
            List<Produto> lista = new ArrayList<>();  // criar a lista
            String SQL = "select p.nome , sum(i.qtd) from  tb_produtos as p join  "
                    + " tb_itensvendas as i on (p.id = i.produto_id) group by p.nome order by sum(i.qtd) desc ;";
            PreparedStatement stmt = conexao.conex.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                Produto obj = new Produto();

                obj.setNome(rs.getString("p.nome"));
                obj.setQuantidade(rs.getInt("sum(i.qtd)"));
                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro" + erro);
            return null;
        }
    }
}
