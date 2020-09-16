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
        }
        String cnpjVendedor = dadosVendedor[1];
        // Valida se o nome tem apenas letras (menos o ç), espaço e algumas letras acentudadas
        if (cnpjVendedor.matches("/^[a-záàâãéèêíïóôõöúñ ]+$/i")) {
            System.out.println("Nome inválido para vendedor: arquivo=" + nomeArquivo + " - linha=" + linhaAtual);
        }
        relacaoDevendedores.add(cnpjVendedor);
    }

    public void reiniciaVariaveis() {
        relacaoDevendedores.clear();
    }
    
}
