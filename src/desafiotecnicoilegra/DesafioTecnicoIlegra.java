package desafiotecnicoilegra;

import desafiotecnicoilegra.relatorio.RelatorioDeVendas;
import desafiotecnicoilegra.cliente.Clientes;
import desafiotecnicoilegra.venda.Vendas;
import desafiotecnicoilegra.vendedor.Vendedores;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class DesafioTecnicoIlegra {

    private static final String DIRETORIO_ENTRADA = System.getenv("USERPROFILE") + "\\data\\in\\";

    public static void main(String[] args) {
        iniciaMonitoramentoDosArquivosDeVenda();
    }

    private static void iniciaMonitoramentoDosArquivosDeVenda() {
        // Cria o serviço de observação do diretório
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            // Define o diterório a ser monitorado
            Path pathDosArquivosDeVenda = Paths.get(DIRETORIO_ENTRADA);

            // Define o evento que será monitorado no diretório, neste caso, a criação de novos arquivos
            pathDosArquivosDeVenda.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            RelatorioDeVendas relatorioDeVendas = new RelatorioDeVendas();
            WatchKey notificaNovoArquivoDeVenda;
            // Aguarda o surgimento de um novo arquivo de venda
            while ((notificaNovoArquivoDeVenda = watchService.take()) != null) {
                for (WatchEvent<?> event : notificaNovoArquivoDeVenda.pollEvents()) {
                    relatorioDeVendas.processaArquivosDeVenda(pathDosArquivosDeVenda);
                    relatorioDeVendas.criarRelatorio();
                }
                // reinicia variáveis para contabilozação da próxima leitura de arquivos
                reiniciaVariaveisDeRelatorio();
                // Para aguardar novas notificações de arquivos de venda
                notificaNovoArquivoDeVenda.reset();
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println("Ocorreu um problema no monitoramento dos arquivos de venda: " + ex.getMessage());
        }
    }

    private static void reiniciaVariaveisDeRelatorio() {
        Vendedores.getInstance().reiniciaVariaveis();
        Clientes.getInstance().reiniciaVariaveis();
        Vendas.getInstance().reiniciaVariaveis();
    }

}
