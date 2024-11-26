import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Banco {
    private Connection conexao;

    private boolean estaConectado;
    private String mensagemErro;

    public Banco(String servidor, String porta, String nomeBanco, String usuario, String senha) {
        try {
            String url = "jdbc:mysql://" + servidor + ":" + porta + "/" + nomeBanco;
            conexao = DriverManager.getConnection(url, usuario, senha);
            estaConectado = true;
        } catch (SQLException e) {
            estaConectado = false;
            mensagemErro = e.getMessage();
        }
    }


    public Banco(String nomeBanco, String usuario, String senha) {
        this("localhost", "3306", nomeBanco, usuario, senha); // Chamando o construtor detalhado com valores padrão
    }


    public Connection obterConexao() {
        return conexao;
    }

    public boolean conectado() {
        return estaConectado;
    }

    public String obterMensagemErro() {
        return mensagemErro;
    }

    public void encerraConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao encerrar a conexão: " + e.getMessage());
        }
    }

    public Connection getConexao() {
        return conexao;
    }
}