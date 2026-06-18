# Gerenciador de Apostas

Sistema de apostas em campeonatos de futebol, feito em Java com Swing. Permite cadastrar clubes, criar campeonatos, montar partidas, organizar grupos de apostadores, registrar palpites e acompanhar a classificação por pontos.

Os dados são armazenados em um banco H2 local, então tudo que você cadastrar continua salvo mesmo depois de fechar o programa.

## Pré-requisitos

- JDK 21 ou superior instalado
- IntelliJ IDEA (ou outra IDE de sua preferência)
- Driver JDBC do H2

## Passo 1 — Baixar o driver do H2

Baixe o arquivo `.jar` do driver H2 em:
https://repo1.maven.org/maven2/com/h2database/h2/2.4.240/h2-2.4.240.jar
Coloque esse arquivo dentro da pasta `lib/` na raiz do projeto.

## Passo 2 — Abrir o projeto

Abra a pasta do projeto no IntelliJ. O arquivo `GerenciadorApostas.iml` já está configurado para usar o jar em `lib/h2-2.4.240.jar`. Se você baixou uma versão com nome diferente, é só ajustar isso em *File → Project Structure → Libraries* e apontar para o jar correto.

## Passo 3 — Executar

Rode a classe `MainFrame.java`, que é o ponto de entrada do sistema.

Na primeira execução, o programa cria automaticamente uma pasta `bancodados/` na raiz do projeto, com o arquivo do banco H2 dentro. É só esperar a janela abrir.

## Primeiro acesso

O sistema já vem com um usuário administrador cadastrado por padrão:

- **Email:** admin@admin.com
- **Senha:** 1234

Use essas credenciais na tela de login para entrar como administrador. A partir daí você consegue:

1. Cadastrar clubes (aba Clubes)
2. Criar um campeonato e cadastrar partidas entre os clubes (aba Campeonato)
3. Criar grupos de apostadores (aba Grupos)
4. Cadastrar os participantes que vão apostar (aba Usuários)

Depois disso, cada participante pode fazer login com o email e senha cadastrados por você e entrar em algum grupo, fazer suas apostas e acompanhar a classificação. Quando as partidas acontecerem, volte como administrador para registrar os resultados na aba Resultados — é nesse momento que os pontos de cada aposta são calculados.

## Resetando os dados

Se quiser zerar tudo e começar do início, basta apagar a pasta `bancodados/` da raiz do projeto e rodar o programa de novo. As tabelas e o administrador padrão são recriados automaticamente.

## Algumas observações

- O limite de cadastro é de 8 clubes, 10 campeonatos, 5 grupos (com até 5 participantes cada) e 50 usuários.
- Uma partida só aceita apostas até 20 minutos antes do horário marcado.
- Acertar o placar exato vale 10 pontos; acertar só o resultado (vitória, derrota ou empate) vale 5 pontos.
