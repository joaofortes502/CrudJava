
import java.sql.*;
import java.util.Scanner;

public class Contato {

    private int idContato;
    private String nome;
    private String email;
    private String telefone;
    final private Connection conexao;

    public Contato(Connection conexao) {
        this.conexao = conexao;
    }

    public int getIdContato() {
        return idContato;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public Connection getConexao() {
        return conexao;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void gravarContato() throws SQLException {
        String sql = "INSERT INTO tb_contato (nome, e_mail, telefone) VALUES (?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, nome);
        stmt.setString(2, email);
        stmt.setString(3, telefone);
        stmt.executeUpdate();

        ResultSet resultado = stmt.getGeneratedKeys();

        if (resultado.next()) {
            setIdContato(resultado.getInt(1));
            System.out.println("Contato gravado com o id " + idContato);
        }

    }

    public void atualizarContato() throws SQLException {
        String sql = "UPDATE tb_contato SET nome = ?, e_mail = ?, telefone = ? WHERE contato_id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setString(2, email);
        stmt.setString(3, telefone);
        stmt.setInt(4, idContato);
        stmt.executeUpdate();
    }

    public void deletarContato() throws SQLException {
        String sql = "DELETE FROM tb_contato WHERE contato_id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, idContato);
        stmt.executeUpdate();
    }

    public void deletarContato(int idContato) throws SQLException {
        String sql = "DELETE FROM tb_contato WHERE contato_id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, idContato);
        stmt.executeUpdate();
    }

    public boolean obterContatoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tb_contato WHERE contato_id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();

        if (result.next()) {
            setIdContato(result.getInt("contato_id"));
            setNome(result.getString("nome"));
            setEmail(result.getString("e_mail"));
            setTelefone(result.getString("telefone"));
            return true;
        }

        return false;
    }

    public ResultSet obterContatos() throws SQLException {

        String sql = "SELECT * FROM tb_contato";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        return stmt.executeQuery();
    }

    public void listarContatos() throws SQLException {
        try {
            ResultSet contatos = obterContatos();
            while (contatos.next()) {
                System.out.println("id: " + contatos.getInt("contato_id"));
                System.out.println("Nome: " + contatos.getString("nome"));
                System.out.println("E-mail: " + contatos.getString("e_mail"));
                System.out.println("Telefone: " + contatos.getString("telefone"));
                System.out.println("----------------------------------------");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String getTexto() {
        Scanner ler = new Scanner(System.in);
        System.out.print("Informe o termo a ser buscado: ");
        return ler.next();
    }

    public void pesquisarContato(String termo) throws SQLException {
        String sql = "select contato_id, nome, e_mail, telefone from tb_contato where nome like ? or e_mail like ? or telefone like ?";

        PreparedStatement req = conexao.prepareStatement(sql);

        req.setString(1, "%" + termo + "%");
        req.setString(2, "%" + termo + "%");
        req.setString(3, "%" + termo + "%");

        ResultSet contatos = req.executeQuery();
        int contador = 0;
        while (contatos.next()) {
            contador++;
            System.out.println("id: " + contatos.getInt("contato_id"));
            System.out.println("Nome: " + contatos.getString("nome"));
            System.out.println("E-mail: " + contatos.getString("e_mail"));
            System.out.println("Telefone: " + contatos.getString("telefone"));
            System.out.println("----------------------------------------");
        }
        if (contador == 0) {
            System.out.println("A Pesquisa pelo termo não encontrou resultados. ");
            System.out.println("----------------------------------------");
        }

    }

}
