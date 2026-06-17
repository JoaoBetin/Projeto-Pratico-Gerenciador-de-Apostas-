package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBD {

    private static final String URL =
            "jdbc:h2:./bancodados/apostas;AUTO_SERVER=TRUE";
    private static final String USUARIO = "sa";
    private static final String SENHA = "";

    private static Connection conexao;

    private ConexaoBD() {
    }

    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                Class.forName("org.h2.Driver");
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "Driver do H2 não encontrado. Verifique se o h2-2.4.240.jar "
                            + "está adicionado nas bibliotecas do projeto.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados H2.", e);
        }
        return conexao;
    }

    public static void inicializarBanco() {
        try (Statement st = getConexao().createStatement()) {

            st.execute("""
                CREATE TABLE IF NOT EXISTS clube (
                    id IDENTITY PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    sigla VARCHAR(10) NOT NULL,
                    cidade VARCHAR(100) NOT NULL
                )
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS campeonato (
                    id IDENTITY PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL
                )
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS partida (
                    id IDENTITY PRIMARY KEY,
                    clube_mandante_id BIGINT NOT NULL,
                    clube_visitante_id BIGINT NOT NULL,
                    campeonato_id BIGINT NOT NULL,
                    data_hora TIMESTAMP NOT NULL,
                    gols_mandante INT NOT NULL DEFAULT -1,
                    gols_visitante INT NOT NULL DEFAULT -1,
                    encerrada BOOLEAN NOT NULL DEFAULT FALSE,
                    FOREIGN KEY (clube_mandante_id) REFERENCES clube(id),
                    FOREIGN KEY (clube_visitante_id) REFERENCES clube(id),
                    FOREIGN KEY (campeonato_id) REFERENCES campeonato(id)
                )
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS administrador (
                    id IDENTITY PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(150) NOT NULL UNIQUE,
                    senha VARCHAR(100) NOT NULL
                )
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS participante (
                    id IDENTITY PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(150) NOT NULL UNIQUE,
                    senha VARCHAR(100) NOT NULL,
                    pontuacao INT NOT NULL DEFAULT 0
                )
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS grupo (
                    id IDENTITY PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL
                )
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS grupo_participante (
                    grupo_id BIGINT NOT NULL,
                    participante_id BIGINT NOT NULL,
                    PRIMARY KEY (grupo_id, participante_id),
                    FOREIGN KEY (grupo_id) REFERENCES grupo(id),
                    FOREIGN KEY (participante_id) REFERENCES participante(id)
                )
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS aposta (
                    id IDENTITY PRIMARY KEY,
                    participante_id BIGINT NOT NULL,
                    partida_id BIGINT NOT NULL,
                    gols_mandante_previsto INT NOT NULL,
                    gols_visitante_previsto INT NOT NULL,
                    resultado_previsto VARCHAR(20) NOT NULL,
                    FOREIGN KEY (participante_id) REFERENCES participante(id),
                    FOREIGN KEY (partida_id) REFERENCES partida(id)
                )
            """);

            st.execute("""
                MERGE INTO administrador (id, nome, email, senha)
                KEY (email)
                VALUES (1, 'Admin', 'admin@admin.com', '1234')
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar o esquema do banco H2.", e);
        }
    }
}
