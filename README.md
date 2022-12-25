# Assembleia de votção
Desafio Técnico Sicredi

<b>Objetivo <br>
No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Imagine que você deve criar uma solução para dispositivos móveis para gerenciar e participar dessas sessões de votação.
Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:

<ul>
  <li>Cadastrar uma nova pauta</li>
  <li>Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default)</li>
  <li>Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta)</li>
  <li>Contabilizar os votos e dar o resultado da votação na pauta</li>
</ul>

Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as interfaces pode ser considerada como autorizada. A solução deve ser construída em java, usando Spring-boot, mas os frameworks e bibliotecas são de livre escolha (desde que não infrinja direitos de uso).
É importante que as pautas e os votos sejam persistidos e que não sejam perdidos com o restart da aplicação.

<hr>
<ol>
  <li>Banco de dados: MySQL. Informar no arquivo application.properties o <b>user_name e password.</li>
  <li>Para acessar a documentação da API: localhost:porta//swagger-ui-index.html</li>
</ol>
