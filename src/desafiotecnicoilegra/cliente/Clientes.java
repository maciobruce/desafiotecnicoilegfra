package desafiotecnicoilegra.cliente;

import java.util.HashSet;
import java.util.Set;

/**
 * Objeto responsável por fazer o processamento dos dados do cliente. 
 * @author Mácio Bruce
 */
public class Clientes {

    public static final String IDENTIFICADOR_CLIENTE = "002";
    public static Clientes clientes = new Clientes();
    private Set<String> relacaoDeClientes = new HashSet<>();

    private Clientes() {}

    public static Clientes getInstance() {
        return clientes;
    }

    public int getQuantidadeDeClientes() {
        return relacaoDeClientes.size();
    }

    public void processaDadosCliente(String[] dadosCliente, String nomeArquivo, String linhaAtual) {
        if (dadosCliente.length != 4) {
            System.out.println("Linha com dados inválido para cliente: arquivo=" + nomeArquivo + " - linha=" + linhaAtual);
        }
        String cpfVendedor = dadosCliente[1];
        // Valida se o nome tem apenas letras (menos o ç), espaço e algumas letras acentudadas
        if (cpfVendedor.matches("/^[a-záàâãéèêíïóôõöúñ ]+$/i")) {
            System.out.println("Nome inválido para cliente: arquivo=" + nomeArquivo + " - linha=" + linhaAtual);
        }
        relacaoDeClientes.add(cpfVendedor);
    }

    public void reiniciaVariaveis() {
        relacaoDeClientes.clear();
    }
}
