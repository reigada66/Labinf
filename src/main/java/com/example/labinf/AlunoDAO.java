package com.example.labinf;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AlunoDAO {

    private String url;
    private List<AlunoModel> alunos ;
    public AlunoDAO() {
        // SQLite database URL
        url = "jdbc:sqlite:C:/Users/pafro/labinf.db";
        alunos = new ArrayList<>();

    }

    public void guardaNaBD(AlunoModel novoAluno) {

        try {
            // Create a connection to the database
            Connection connection = DriverManager.getConnection(url);

            // Create a new table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Aluno ("
                    + "idAluno INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Nome VARCHAR(35) NOT NULL,"
                    + "Numero INTEGER,"
                    + "Turma VARCHAR(5) NOT NULL,"
                    + "Contacto VARCHAR(9) NOT NULL)";

            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);

            String insertSQL = "INSERT INTO Aluno (Nome, Numero, Turma, Contacto) VALUES (?, ?, ?, ?)";

            // Specify that you want to retrieve the generated keys
            connection.setAutoCommit(false); // Starts transaction.
            PreparedStatement esqueletoInsert = connection.prepareStatement(insertSQL);
            esqueletoInsert.setString(1, novoAluno.getNome());
            esqueletoInsert.setInt(2, novoAluno.getNumero());
            esqueletoInsert.setString(3, novoAluno.getTurma());
            esqueletoInsert.setString(4, novoAluno.getContacto());
            esqueletoInsert.executeUpdate();
            int generatedKey=-1;
            statement = connection.createStatement();
            ResultSet generatedKeys = statement.executeQuery("SELECT last_insert_rowid()");
            if (generatedKeys.next()) {
                generatedKey = generatedKeys.getInt(1);
                novoAluno.setId(generatedKey); // Set the ID in your AlunoModel
                connection.commit(); // Commits transaction.
            }
            else {
                throw new SQLException("Falhou a criação do aluno.");
            }

            System.out.println(generatedKey);
            // Retrieve the auto-generated ID(s)
            esqueletoInsert.close();
            connection.close();

            System.out.println("Tudo bem!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void apagaNaBD(AlunoModel aluno) {
        try {
            // Create a connection to the database
            Connection connection = DriverManager.getConnection(url);

            String deleteSQL = "DELETE FROM Aluno WHERE idAluno = ?";

            // Specify that you want to retrieve the generated keys
            connection.setAutoCommit(false); // Starts transaction.
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL);
            deleteStatement.setInt(1, aluno.getId()); // Assuming getId() returns the student's ID
            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit(); // Commits transaction.
            } else {
                throw new SQLException("Falhou a exclusão do aluno.");
            }

            deleteStatement.close();
            connection.close();

            System.out.println("Aluno eliminado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AlunoModel> sacaTodosAlunos() {

        try {
            Connection connection = DriverManager.getConnection(url);

            // Create a SELECT query to retrieve all records
            String selectSQL = "SELECT * FROM Aluno ORDER BY Numero";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                AlunoModel aluno = new AlunoModel();
                aluno.setId(resultSet.getInt("idAluno"));
                aluno.setNome(resultSet.getString("Nome"));
                aluno.setNumero(resultSet.getInt("Numero"));
                aluno.setTurma(resultSet.getString("Turma"));
                aluno.setContacto(resultSet.getString("Contacto"));
                alunos.add(aluno);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alunos;
    }

    public void inserirAluno(AlunoModel novoAluno)
    {
        guardaNaBD(novoAluno);
        alunos.add(novoAluno);
    }

    public void apagaAluno(AlunoModel saiAluno, int indiceLista)
    {
        // elimina aluno da base de dados
        apagaNaBD(saiAluno);
        // elimina na lista em memória
        alunos.remove(indiceLista);

    }

}
