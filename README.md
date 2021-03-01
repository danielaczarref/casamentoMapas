# Sistema de Computação em Nuvem para Casamento de Mapas

## Pré-requisitos
- Computador com 32GB de memória RAM: é bem inacessível, e é algo que dificulta muito os testes em um computador até mesmo de 16GB, mas é necessário;
- IDE do IntelliJ: tem a versão Community que é gratuita. Você vai precisar dela para rodar;
- Postman (ou aquele Imsonia, algo assim): você vai precisar para melhor testar as rotas desenvolvidas;
- Criar uma conta na AWS e remodelar o código para suas credenciais:
    - Cria uma conta com suas próprias configurações do AWS IAM. A documentação explica direitinho como fazer e é bem intuitivo: https://aws.amazon.com/iam/
    - Configurar a sua conta da AWS para o S3 para gerenciar seus buckets (arquivos de imagens). Para mais informações, a AWS também documenta super bem o serviço do S3: https://aws.amazon.com/s3/
    - Mudar o arquivo chamado applications.properties para AS SUAS credenciais do AWS: removi as minhas credenciais por questões de segurança; não posso ter ninguém acessando minha conta, que tem o meu cartão de crédito vinculado a ela e tudo mais. Então, você vai precisar utilizar suas próprias credenciais.
    
OBS.: É importante ressaltar que se você, assim como eu, tentar acessar a conta estudantil da AWS, você >NÃO VAI CONSEGUIR< rodar esse projeto em uma instância. Eu configurei e tudo mais, mas a instância para estudantes tem limitações de hardware, e uma delas é ter somente 1GB de memória RAM disponível. Por isso, todos os meus testes e resultados foram feitos rodando o projeto na minha máquina local e por isso você não vai precisar se preocupar com as configurações de instância e nem mesmo as de docker. Porém fiz as configurações certinhas, isto é, se tivéssemos recursos para adquirir uma instância apropriada, não teria problemas. Mas você não vai precisar se preocupar com isso: rode em sua máquina local! Você também pode fazer deploy na Heroku pra testar.

## Informações importantes

1) Você vai precisar entender os princípios básicos do Spring Boot. Eu posso tirar dúvidas particulares desse projeto, mas você vai ter que correr atrás do que é algo inerente à tecnologia, que pode ser resolvido com uma pesquisa rápida no Google. Mas juro como não é difícil!

2) Você pode revisar também um pouco sobre JPA: não é uma abordagem que eu recomendaria para uso profissional ou em projetos maiores, mas foi o que decidi adotar nesse projeto porque achei interessante e um bom estudo de caso. Mas separei aqui um material bacana sobre JPA: https://spring.io/projects/spring-data-jpa

3) Testar rotas no Postman é muuuuuito fácil e super intuitivo! Se você estiver rodando em máquina local, o Spring Boot vai te fornecer a porta em que o serviço está rodando e você vai apenas escolher o método (GET, por exemplo) e informar http://localhost:PORTA/nome-da-sua-rota;

4) Este projeto foi implementado utilizando o protocolo REST. É super simples implementar o REST com o Spring Boot e o IntelliJ ajuda muuuuuuuito. Estude a respeito! São os mesmos princípios da programação que estamos habituados.

## Sobre o projeto codificado

Coloquei algumas informações de cidades e estado no arquivo main da aplicação para não precisar ficar criando toda vez que fosse rodar meus testes (no arquivo de applications.properties, eu especifiquei para sempre recriar o banco quando rodasse a aplicação (por causa dos testes), porém você pode mudar para "none" como informado nos comentários do arquivo.

> NÃO ESQUEÇA < de colocar as suas credenciais no applications.properties do projeto. O projeto não vai rodar enquanto você não tiver realizado suas configurações. Isso porque o serviço da AWS S3 requer acesso a parâmetros que não estão commitados neste projeto por questões de segurança (como informei previamente).

Você deverá adicionar os parâmetros de POST nas seguintes rotas, respectivamente:

- Rota de usuário;
- Rota de tipo de local;
- Rota de dados do local;
- Rota de informações do mapa.

Seguindo os padrões de programação REST, todos os arquivos com as rotas que você precisa conhecer estão no Controller da aplicação. As regras são definidas no Service, mas as rotas são instanciadas no Controller. E é bem fácil você identificar o tipo de uma rota, sempre vem um "@" com um GET, POST, PUT... 

De resto, você só vai ter o trabalho mesmo de ler as documentações da AWS, talvez assistir a alguns vídeos com tutoriais... Mas faz parte do caminhar da vida. 

Já deixei as URLs de duas imagens como padrão (ao invés de passar por parâmetros) para fazer os testes, porém é só você passar como parâmetro as URLs que quiser no arquivo main da aplicação. Pode utilizar as minhas imagens também, sem problemas.

## Algumas observações

Este projeto foi dividido entre duas pessoas. A minha parte foi realizar a conexão com o serviço em nuvem. Um outro colega da faculdade foi responsável pelo casamento em si... 

Ele me forneceu o arquivo em C++ para os testes de validação de imagem (tem toda uma validação de pixels para verificar a qualidade do casamento) que não foi traduzida para este projeto porque ele utilizou o código produzido pelo André, e inclusive foi o André quem fez os testes para ele (porque o André que tem um computador com 32GB de RAM).

Portanto, a validação não está aqui neste projeto. Sem falar que foi feita nos últimos minutos do segundo tempo, e não deu tempo de traduzi-la. 

Os testes do outro integrante também foram realizados pelo André, também em código C++. O que eu fiz foi uma "tradução" do código para Java com os padrões do Spring Boot, mas que não tem o resultado comprovado dos relatórios (porque, como eu disse, o teste foi feito com um programa do André em C++).

O link deste GitHub com acesso ao código do projeto que eu produzi foi repassado para o outro integrante, antes mesmo dele tentar realizar os testes de validação. Não sei se ele chegou a fazer download do projeto, ou tentar executá-lo, ou mesmo integrar o código de casamento de mapas com os serviços de nuvem, mas creio que não.

Gostaria de ressaltar que não retiro minha responsabilidade dos erros e demais acontecimentos deste projeto. 


