package com.example.labinf;

import java.sql.*;


public class AlunoDAO {

    private String url;
    public void AlunoDAO() {
        // SQLite database URL
        url = "jdbc:sqlite:C:/Users/pafro/labinf.db";
    }

    public void mandaprala(AlunoModel novoAluno){

        try {
            // Create a connection to the database
            Connection connection = DriverManager.getConnection(url);

            // Create a new table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Aluno ("
                    + "idAluno INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Nome VARCHAR(35) NOT NULL,"
                    + "Numero INTEGER,"
                    + "Turma VARCHAR(5) NOT NULL,"
                    + "Contacto	VARCHAR(9) NOT NULL)";

            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);

            String insertSQL = "INSERT INTO Alunos (Nome, Numero, Turma, Contacto) VALUES (?, ?, ?, ?)";

            PreparedStatement esqueletoInsert = connection.prepareStatement(insertSQL);
            esqueletoInsert.setString(1, novoAluno.getNome());
            esqueletoInsert.setInt(2, novoAluno.getNumero());
            esqueletoInsert.setString(3, novoAluno.getTurma());
            esqueletoInsert.setString(4, novoAluno.getContacto());
            esqueletoInsert.executeUpdate();
            esqueletoInsert.close();
            connection.close();

           // Close the connection
            connection.close();

            System.out.println("Tudo bem!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
