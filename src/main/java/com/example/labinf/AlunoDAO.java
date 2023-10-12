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

    public void mandaprala(AlunoModel novoAluno) {

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
            PreparedStatement esqueletoInsert = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            esqueletoInsert.setString(1, novoAluno.getNome());
            esqueletoInsert.setInt(2, novoAluno.getNumero());
            esqueletoInsert.setString(3, novoAluno.getTurma());
            esqueletoInsert.setString(4, novoAluno.getContacto());

            // Execute the INSERT statement
            int affectedRows = esqueletoInsert.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating aluno failed, no rows affected.");
            }

            // Retrieve the auto-generated ID(s)
            try (ResultSet generatedKeys = esqueletoInsert.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1); // Get the generated ID
                    novoAluno.setId(generatedId); // Set the ID in your AlunoModel
                } else {
                    throw new SQLException("Creating aluno failed, no ID obtained.");
                }
            }

            esqueletoInsert.close();
            connection.close();

            System.out.println("Tudo bem!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AlunoModel> sacaTodosAlunos() {

        try {
            Connection connection = DriverManager.getConnection(url);

            // Create a SELECT query to retrieve all records
            String selectSQL = "SELECT * FROM Aluno";

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
        alunos.add(novoAluno);
        mandaprala(novoAluno);
    }

}
