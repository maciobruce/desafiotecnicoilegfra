package desafiotecnicoilegra.relatorio;

import desafiotecnicoilegra.cliente.Clientes;
import static desafiotecnicoilegra.cliente.Clientes.IDENTIFICADOR_CLIENTE;
import desafiotecnicoilegra.venda.Vendas;
import static desafiotecnicoilegra.venda.Vendas.IDENTIFICADOR_VENDA;
import desafiotecnicoilegra.vendedor.Vendedores;
import static desafiotecnicoilegra.vendedor.Vendedores.IDENTIFICADOR_VENDEDOR;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Objeto responsável por manipular os arquivos de venda (em HOMEPATH/data/in) e criar o relatório (em HOMEPATH/data/out).
 * O relatório será nomeado como "relatorio_venda.txt".
 * @author Mácio Bruce
 */
public class RelatorioDeVendas {

    private static final String ARQUIVO_SAIDA = System.getenv("HOMEPATH") + "\\data\\out\\relatorio_vendas.txt";
    private static final String SEPARADOR_DADOS = "ç";

    public void processaArquivosDeVenda(Path diretorioDosArquivosDeVenda) {
        try {
            Files.list(diretorioDosArquivosDeVenda).forEach(RelatorioDeVendas::carregaArquivoDeVendas);
        } catch (IOException ex) {
            System.out.println("Ocorreu um problema na listagem dos arquivos de venda.\nErro: " + ex);
        }
    }

    private static void carregaArquivoDeVendas(Path arquivo) {
        try (BufferedReader reader = Files.newBufferedReader(arquivo)) {
            String linhaAtual;
            String nomeArquivo = arquivo.getFileName().toString();
            while ((linhaAtual = reader.readLine()) != null) {
                String[] dados = linhaAtual.split(SEPARADOR_DADOS);
                switch(dados[0]) {
                    case IDENTIFICADOR_VENDEDOR: Vendedores.getInstance().processaDadosVendedor(dados, nomeArquivo, linhaAtual);
                        break;
                    case IDENTIFICADOR_CLIENTE: Clientes.getInstance().processaDadosCliente(dados, nomeArquivo, linhaAtual);
                        break;
                    case IDENTIFICADOR_VENDA: Vendas.getInstance().processaDadosVenda(dados, nomeArquivo, linhaAtual);
                        break;
                    // Se arquivo apresentar linha com identificador inválido
                    default: System.out.println("Linha com identificador inválido: arquivo=" + arquivo.getFileName() + " - linha=" + linhaAtual);
                }
            }
        } catch (IOException ex) {
            System.out.println("Ocorreu um problema na leitura do arquivo: " + arquivo.getFileName() + "\nErro: " + ex);
        }
    }

    public void criarRelatorio() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_SAIDA))) {
            writer.write("Quantidade de vendedor = " + Vendedores.getInstance().getQuantidadeDeVendedores());
            writer.newLine();
            writer.write("Quantidade de clientes = " + Clientes.getInstance().getQuantidadeDeClientes());
            writer.newLine();
            writer.write("ID maior venda = " + Vendas.getInstance().getIdMaiorVenda());
            writer.newLine();
            writer.write("Pior vendedor = " + Vendas.getInstance().getPiorVendedor());
            writer.newLine();
        } catch (IOException ex) {
            System.out.println("Ocorreu um problema na criação do arquivo de saída: " + ex);
        }
    }
}
