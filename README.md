# GerenciadorApostas

Sistema de gerenciamento de apostas esportivas desenvolvido em Java com interface grafica Swing. Permite que administradores configurem campeonatos, clubes, partidas e usuarios, enquanto participantes realizam apostas e acompanham classificacoes em grupos.

---

## Tecnologias utilizadas

- Java (JDK 25)
- Swing (interface grafica)
- IntelliJ IDEA (ambiente de desenvolvimento)

---

## Estrutura do projeto

```
GerenciadorApostas/
    src/
        Main.java
        interfaces/
            Pontuavel.java
        model/
            CampeonatoModel/
                Campeonato.java
                Classificacao.java
                Clube.java
                Partida.java
            PessoaModel/
                Administrador.java
                Aposta.java
                Grupo.java
                Participante.java
                Permissao.java
                Pessoa.java
        view/
            MainFrame.java
            PaineisAdmin/
                CampeonatoPanel.java
                ClubesPanel.java
                ResultadosPanel.java
                UsuariosPanel.java
            PainelAmbos/
                ClassificacaoPanel.java
                GrupoPanel.java
                LoginPanel.java
            PainelUser/
                ApostasPanel.java
                MeusGruposPanel.java
```

---

## Funcionalidades

### Administrador

- Cadastrar clubes com nome, sigla e cidade
- Criar campeonatos e registrar partidas com data e hora
- Cadastrar usuarios participantes
- Criar grupos de apostadores
- Registrar resultados de partidas encerradas
- Visualizar classificacao dos grupos

### Participante

- Entrar em grupos de apostadores
- Realizar apostas em partidas abertas (ate 20 minutos antes do inicio)
- Visualizar classificacao do grupo

---

## Regras de negocio

**Apostas**

- So e possivel apostar em partidas que ainda nao foram encerradas e cujo horario de inicio esteja a mais de 20 minutos no futuro.
- Cada aposta registra o placar previsto pelo participante.
- Ao registrar o resultado real de uma partida, os pontos sao calculados automaticamente para todas as apostas relacionadas.

**Pontuacao**

A logica de pontuacao segue a interface `Pontuavel`, implementada pela classe `Aposta`:

- 10 pontos: acerto do placar exato
- 5 pontos: acerto apenas do resultado (vitoria do mandante, vitoria do visitante ou empate)
- 0 pontos: erro total

**Grupos**

- Cada grupo comporta no maximo 5 participantes.
- Um participante nao pode entrar no mesmo grupo mais de uma vez.
- O sistema suporta ate 5 grupos simultaneos.

**Campeonatos e clubes**

- O sistema suporta ate 8 clubes cadastrados globalmente.
- Cada campeonato aceita ate 8 clubes distintos.
- O limite de campeonatos e 10.
- O limite de partidas e 100.

---

## Controle de acesso

O sistema usa a enumeracao `Permissao` com dois niveis: `ADMINISTRADOR` e `USUARIO`.

Ao realizar login, a classe `MainFrame` habilita as abas conforme o tipo de usuario autenticado:

- Administrador: acesso a cadastro de clubes, campeonatos, partidas, usuarios, resultados e classificacao
- Participante: acesso a grupos, apostas e classificacao

O administrador padrao do sistema e criado automaticamente com as credenciais abaixo:

- Email: `admin@admin.com`
- Senha: `1234`

---

## Modelo de dados

**Pessoa (abstrata):** base para `Administrador` e `Participante`, com nome, email, senha e permissao.

**Participante:** estende `Pessoa`, acumula pontuacao ao longo das partidas apostadas.

**Clube:** entidade com nome, sigla e cidade.

**Campeonato:** agrega ate 8 clubes e serve de referencia para as partidas.

**Partida:** representa um jogo entre dois clubes dentro de um campeonato, com data, hora e placar real apos encerramento.

**Aposta:** vincula um participante a uma partida com placar previsto, implementando a interface `Pontuavel`.

**Grupo:** agrega ate 5 participantes para fins de classificacao comparativa.

**Classificacao:** ordena os participantes de um grupo por pontuacao usando bubble sort.

---

## Como executar

1. Clone o repositorio
2. Abra o projeto no IntelliJ IDEA ou em qualquer IDE compativel com Java
3. Configure o JDK 25 (ou versao compativel)
4. Execute a classe `Main.java` ou `MainFrame.java` diretamente
5. A janela do sistema sera aberta; use as credenciais do administrador padrao para o primeiro acesso

---

## Limitacoes conhecidas

- Os dados sao mantidos apenas em memoria durante a execucao; nao ha persistencia em banco de dados ou arquivo.
- O campo de senha no cadastro de usuarios nao usa `JPasswordField`, exibindo o texto em claro.
- Os limites de entidades (clubes, grupos, partidas etc.) sao fixos e definidos em tempo de compilacao.
- A pontuacao acumulada por `setPontuacao` e somativa: chamar o metodo mais de uma vez para a mesma aposta resulta em pontuacao duplicada.
