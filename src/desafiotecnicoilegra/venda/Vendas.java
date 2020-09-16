package desafiotecnicoilegra.venda;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Objeto responsável por fazer o processamento dos dados referente à venda realizada. 
 * @author Mácio Bruce
 */
public class Vendas {

    public static final String IDENTIFICADOR_VENDA = "003";
    public static final String SEPARADOR_ITENS_VENDA = "-";
    public static final String SEPARADOR_ITENS = ",";
    private String idMaiorVenda = "-";
    private double valorMaiorVenda = 0;
    private Map<String, Double> valorDeVendasPorVendedor = new HashMap<>();
    private static Vendas vendas = new Vendas();

    private Vendas() {}

    public static Vendas getInstance() {
        return vendas;
    }

    public String getIdMaiorVenda() {
        return idMaiorVenda;
    }

    public String getPiorVendedor() {
        String nomePiorVendedor = null;
        // Encontra o par Vendedor x Valor Vendido com o menor valor para Valor Vendido
        Optional<Map.Entry<String, Double>> piorVendedor = valorDeVendasPorVendedor.entrySet().stream().collect(Collectors.minBy(Map.Entry.comparingByValue()));
        if (piorVendedor.isPresent()) {
            nomePiorVendedor = piorVendedor.get().getKey();
        }
        return nomePiorVendedor;
    }

    public void processaDadosVenda(String[] dadosVenda, String nomeArquivo, String linhaAtual) {
        if (dadosVenda.length != 4) {
            System.out.println("Linha com dados inválido para venda: arquivo=" + nomeArquivo + " - linha=" + linhaAtual);
            return;
        }
        String idVenda = dadosVenda[1];
        String listaDeItens = dadosVenda[2];
        // Valida se está no formato [Item ID-Item Quantity-Item Price], onde Item Price é um número inteiro ou um decimal com 2 casas e o separador o "." (ponto)
        if (!listaDeItens.matches("\\[\\d+-\\d+-(\\d+|\\d+\\.\\d{2})(,\\d+-\\d+-(\\d+|\\d+\\.\\d{2}))*\\]")) {
            System.out.println("Linha com dados inválido nos itens de venda: arquivo=" + nomeArquivo + " - linha=" + linhaAtual);
            return;
        }
        // Valida se nome contém apenas letras e espaço (cada nome ao menos com 2 letras e sem acentos)
        if (!dadosVenda[3].matches("[a-zA-Z]{2,}( [a-zA-Z ]{2,})*")) {
            System.out.println("Linha com nome inválido para vendedor: arquivo=" + nomeArquivo + " - linha=" + linhaAtual);
            return;
        }

        double valorVenda = processaItensVendidos(listaDeItens, idVenda);
        associaValorVendasAoVendedor(dadosVenda[3], valorVenda);
        
    }

    /**
     * Faz o processamento da lista de intens vendidos.
     * @return valor da venda
     */
    private double processaItensVendidos(String listaDeItens, String idVenda) {
        String[] itensDaVenda = listaDeItens.substring(1, listaDeItens.lastIndexOf("]")).split(SEPARADOR_ITENS);
        String[] infosItemVendido;
        int quantidade;
        double preco;
        double valorVenda = 0D;
        for (String venda : itensDaVenda) {
            infosItemVendido = venda.split(SEPARADOR_ITENS_VENDA);

            quantidade = Integer.parseInt(infosItemVendido[1]);
            preco = Double.parseDouble(infosItemVendido[2]);
            valorVenda += (quantidade * preco);

        }

        // Registra se é a maior venda realizada
        if (valorVenda > valorMaiorVenda) {
            idMaiorVenda = idVenda;
            valorMaiorVenda = valorVenda;
        }

        return valorVenda;
    }

    /**
     * O valor das vendas realizadas por um vendedor é associado em um Map através do nome do vendedor que vem na última coluna da linha da venda.
     * Não é realizada a verificação do nome do vendedor junto a relação de vendedores porque esta última armazena apenas o CPF. Para implementar
     * essa verificação seria melhor que o CPF do vendedor fosse informado na linha da venda.
     * @param nomeVendedor
     * @param valorVenda 
     */
    private void associaValorVendasAoVendedor(String nomeVendedor, double valorVenda) {
        Double totalDoVendedor = valorDeVendasPorVendedor.get(nomeVendedor);
        if (totalDoVendedor == null) {
            totalDoVendedor = 0D;
        }
        totalDoVendedor += valorVenda;
        valorDeVendasPorVendedor.put(nomeVendedor, totalDoVendedor);
    }

    public void reiniciaVariaveis() {
        idMaiorVenda = "-";
        valorMaiorVenda = 0;
        valorDeVendasPorVendedor.clear();
    }
}
