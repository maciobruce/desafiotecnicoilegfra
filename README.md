# desafiotecnicoilegra
DECIS�ES:
- As informa��es da quantidade de clientes, vendedores e valores de venda ser�o mantidos em mem�ria por n�o ter
 conhecimento se ser�o muitas informa��es que possam prejudicar o tratamento desta forma.
- Os erros encontrados na manipula��o das linhas dos arquivos de venda ser�o sinalizados com mensagens no console. Poder�o evoluir para uso de uma lib de logger, algo mais estruturado.
- Os erros acima n�o impedem a continuidade da avalia��o das demais linhas do arquivo
- S�o validados:
- CPF - um n�mero com 11 digitos
- CNPJ - um n�mero com 14 digitos
- Lista de itens vendidos - no formato [Item ID-Item Quantity-Item Price], onde Item Price � um n�mero inteiro ou
 um decimal com 2 casas e o separador o "." (ponto). Os outros valores s�o n�meros inteiros.
- Nome do vendedor da linha de venda - nome cont�m apenas letras e espa�o (cada nome ao menos com 2 letras e sem acentos)
- O nome do vendedor n�o � validado junto a lista de vendedores, pois essa �ltima � gerada a partir do CPF. Para essa valida��o sugeriria informar o CPF do vendedor na linha relativa � venda.
- Todos os arquivos no diret�rio de entrada s�o processados para a gera��o de um arquivo de sa�da (relatorio_vendas.txt)
- O processamento dos arquivos ocorre somente quando um novo arquivo � criado no diret�rio