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
        }
        String idVenda = dadosVenda[1];
        String listaDeItens = dadosVenda[2];
        // Valida se está no formato [Item ID-Item Quantity-Item Price]
        if (listaDeItens.matches("/^[a-záàâãéèêíïóôõöúñ ]+$/i")) {
            System.out.println("Linha com dados inválido para venda: arquivo=" + nomeArquivo + " - linha=" + linhaAtual);
        }

        double valorVenda = processaItensVendidos(listaDeItens, nomeArquivo, linhaAtual, idVenda);

        associaValorVendasAoVendedor(dadosVenda[3], valorVenda);
        
    }

    /**
     * Faz o processamento da lista de intens vendidos.
     * @return valor da venda
     */
    private double processaItensVendidos(String listaDeItens, String nomeArquivo, String linhaAtual, String idVenda) {
        String[] itensDaVenda = listaDeItens.substring(1, listaDeItens.lastIndexOf("]")).split(",");
        String[] infosItemVendido;
        int quantidade;
        double preco;
        double valorVenda = 0D;
        for (String venda : itensDaVenda) {
            infosItemVendido = venda.split(SEPARADOR_ITENS_VENDA);

            quantidade = 0;
            try {
                quantidade = Integer.parseInt(infosItemVendido[1]);
            } catch (NumberFormatException ex) {
                System.out.println("Linha com dado inválido para quantidade de itens na venda: arquivo=" + nomeArquivo + " - linha=" + linhaAtual);
            }

            preco = 0D;
            try {
                preco = Double.parseDouble(infosItemVendido[2]);
            } catch (NumberFormatException ex) {
                System.out.println("Linha com dado inválido para o preço do item: arquivo=" + nomeArquivo + " - linha=" + linhaAtual);
            }

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
