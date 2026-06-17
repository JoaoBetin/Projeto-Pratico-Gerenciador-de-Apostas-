# GerenciadorApostas — agora com H2

Sistema de gerenciamento de apostas esportivas em Java/Swing. Mesma interface e
mesmas regras de negócio da versão original — a única mudança é que os dados
agora são salvos num banco de dados H2 (arquivo local), em vez de arrays em
memória que se perdiam ao fechar o programa.

---

## O que mudou

- Os arrays estáticos que existiam em `MainFrame` (`clubes`, `campeonatos`,
  `partidas`, `grupos`, `apostas`, `participantes`, `administradores`) foram
  removidos.
- Cada entidade agora tem um DAO em `src/dao/` (`ClubeDAO`, `CampeonatoDAO`,
  `PartidaDAO`, `GrupoDAO`, `ParticipanteDAO`, `AdministradorDAO`, `ApostaDAO`)
  que conversa direto com o banco H2.
- A classe `util/ConexaoBD.java` cria as tabelas automaticamente na primeira
  execução e mantém a conexão com o banco.
- O administrador padrão (`admin@admin.com` / `1234`) é inserido
  automaticamente, igual à versão anterior.
- Layout das telas, fluxo de navegação, regras de pontuação, limites (8
  clubes, 5 grupos, 10 campeonatos, etc.) e mensagens — tudo igual.

## Como rodar

**1. Baixe o driver do H2** (versão 2.4.240, mas qualquer 2.x recente funciona):

   https://repo1.maven.org/maven2/com/h2database/h2/2.4.240/h2-2.4.240.jar

   Coloque o arquivo `.jar` baixado dentro da pasta `lib/` do projeto.

**2. Abra o projeto no IntelliJ IDEA.**

   O arquivo `GerenciadorApostas.iml` já está configurado para usar o jar em
   `lib/h2-2.4.240.jar`. Se o nome do arquivo baixado for diferente, ajuste o
   caminho em `GerenciadorApostas.iml` ou adicione o jar manualmente em
   *File → Project Structure → Libraries*.

**3. Execute `MainFrame.java`** (ou `Main.java`, que está vazio — o ponto de
   entrada real é `MainFrame`).

Na primeira execução, o sistema cria automaticamente uma pasta `bancodados/`
na raiz do projeto, contendo o arquivo do banco H2 (`apostas.mv.db`). Esse
arquivo persiste entre execuções — feche e abra o programa de novo e os dados
cadastrados continuam lá.

## Resetando o banco

Para começar do zero, basta apagar a pasta `bancodados/` e rodar o programa
novamente — as tabelas e o administrador padrão são recriados automaticamente.

## Limitações conhecidas (mantidas da versão original)

- O campo de senha no cadastro de usuários não usa `JPasswordField`.
- A pontuação acumulada por `setPontuacao` é somativa: registrar o resultado
  da mesma partida mais de uma vez duplica a pontuação.
- Os limites de entidades (clubes, grupos, campeonatos, etc.) continuam fixos
  no código.
