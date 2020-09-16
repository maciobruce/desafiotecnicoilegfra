# desafiotecnicoilegra
DECISÔES:
- As informações da quantidade de clientes, vendedores e valores de venda serão mantidos em memória por não ter
 conhecimento se serão muitas informações que possam prejudicar o tratamento desta forma.
- Os erros encontrados na manipulação das linhas dos arquivos de venda serão sinalizados com mensagens no console.
-- Os erros acima não impedem a continuidade da avaliação das demais linhas do arquivo
-- São validados:
--- CPF - um número com 11 digitos
--- CNPJ - um número com 14 digitos
--- Lista de itens vendidos - no formato [Item ID-Item Quantity-Item Price], onde Item Price é um número inteiro ou
 um decimal com 2 casas e o separador o "." (ponto). Os outros valores são números inteiros.
--- Nome do vendedor da linha de venda - nome contém apenas letras e espaço (cada nome ao menos com 2 letras e sem acentos)
- Todos os arquivos no diretório de entrada são processados para a geração de um arquivo de saída
-- O processamento dos arquivos ocorre somente quando um novo arquivo é criado no diretório