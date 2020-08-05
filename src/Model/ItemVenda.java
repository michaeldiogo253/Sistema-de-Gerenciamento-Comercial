
package Model;


public class ItemVenda {
    private int id;
    private Venda venda;
    private Produto produto;
    private int quantidade;
    private double sutotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getSutotal() {
        return sutotal;
    }

    public void setSutotal(double sutotal) {
        this.sutotal = sutotal;
    }
    
    
}
