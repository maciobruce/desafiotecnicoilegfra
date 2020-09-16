package desafiotecnicoilegra.vendedor;

import java.util.HashSet;
import java.util.Set;

/**
 * Objeto responsável por fazer o processamento dos dados do vendedor. 
 * @author Mácio Bruce
 */
public class Vendedores {

    public static final String IDENTIFICADOR_VENDEDOR = "001";
    public static Vendedores vendedores = new Vendedores();
    private Set<String> relacaoDevendedores = new HashSet<>();

    private Vendedores() {}

    public static Vendedores getInstance() {
        return vendedores;
    }

    public int getQuantidadeDeVendedores() {
        return relacaoDevendedores.size();
    }

    public void processaDadosVendedor(String[] dadosVendedor, String nomeArquivo, String linhaAtual) {
        if (dadosVendedor.length != 4) {
            System.out.println("Linha com dados inválido para vendedor: arquivo=" + nomeArquivo + " - linha=" + linhaAtual);
            return;
        }
        String cpfVendedor = dadosVendedor[1];
        // Valida se o CPF tem apenas os 11 números
        if (!cpfVendedor.matches("\\d{11}")) {
            System.out.println("CPF inválido para vendedor: arquivo=" + nomeArquivo + " - linha=" + linhaAtual);
            return;
        }
        relacaoDevendedores.add(cpfVendedor);
    }

    public void reiniciaVariaveis() {
        relacaoDevendedores.clear();
    }
    
}
