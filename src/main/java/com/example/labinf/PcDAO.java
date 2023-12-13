package com.example.labinf;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PcDAO {

    private String url;
    public PcDAO() {
        // SQLite database URL
        url = "jdbc:sqlite:C:/Users/pafro/labinf.db";

    }

    public void insereNaBD(PcModel novoPc) {

        try {
            // Create a connection to the database
            Connection connection = DriverManager.getConnection(url);

            // Create a new table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS PC ("
                    + "idPc INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Marca VARCHAR(35) NOT NULL,"
                    + "Numero INTEGER,"
                    + "Sala VARCHAR(5) NOT NULL,"
                    + "NrSerie VARCHAR(9) NOT NULL)";

            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);

            String insertSQL = "INSERT INTO PC (Marca, Numero, Sala, NrSerie) VALUES (?, ?, ?, ?)";

            // Specify that you want to retrieve the generated keys
            connection.setAutoCommit(false); // Starts transaction.
            PreparedStatement esqueletoInsert = connection.prepareStatement(insertSQL);
            esqueletoInsert.setString(1, novoPc.getMarca());
            esqueletoInsert.setInt(2, novoPc.getNumero());
            esqueletoInsert.setString(3, novoPc.getSala());
            esqueletoInsert.setString(4, novoPc.getNrSerie());
            esqueletoInsert.executeUpdate();
            int generatedKey=-1;
            statement = connection.createStatement();
            ResultSet generatedKeys = statement.executeQuery("SELECT last_insert_rowid()");
            if (generatedKeys.next()) {
                generatedKey = generatedKeys.getInt(1);
                novoPc.setId(generatedKey); // Set the ID in your PcModel
                connection.commit(); // Commits transaction.
            }
            else {
                throw new SQLException("Falhou a criação do pc.");
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

    public void apagaNaBD(PcModel pc) {
        try {
            // Create a connection to the database
            Connection connection = DriverManager.getConnection(url);

            String deleteSQL = "DELETE FROM PC WHERE IDPC = ?";

            // Specify that you want to retrieve the generated keys
            connection.setAutoCommit(false); // Starts transaction.
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL);
            deleteStatement.setInt(1, pc.getId()); // Assuming getId() returns the pc's ID
            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit(); // Commits transaction.
            } else {
                throw new SQLException("Falhou a exclusão do pc.");
            }

            deleteStatement.close();
            connection.close();

            System.out.println("Pc eliminado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PcModel> sacaTodosPcs() {
        List<PcModel> pcs = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url);

            // Create a SELECT query to retrieve all records
            String selectSQL = "SELECT * FROM PC ORDER BY Numero";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                PcModel pc = new PcModel();
                pc.setId(resultSet.getInt("idPc"));
                pc.setMarca(resultSet.getString("Marca"));
                pc.setNumero(resultSet.getInt("Numero"));
                pc.setSala(resultSet.getString("Sala"));
                pc.setNrSerie(resultSet.getString("NrSerie"));
                pcs.add(pc);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pcs;
    }


    public void atualizaNaBD(PcModel pcAtualizado) {
        try {
            // Create a connection to the database
            Connection connection = DriverManager.getConnection(url);

            String updateSQL = "UPDATE PC SET Marca = ?, Numero = ?, Sala = ?, NrSerie = ? WHERE IDPC = ?";

            // Specify that you want to retrieve the generated keys
            connection.setAutoCommit(false); // Starts transaction.
            PreparedStatement esqueletoUpdate = connection.prepareStatement(updateSQL);
            esqueletoUpdate.setString(1, pcAtualizado.getMarca());
            esqueletoUpdate.setInt(2, pcAtualizado.getNumero());
            esqueletoUpdate.setString(3, pcAtualizado.getSala());
            esqueletoUpdate.setString(4, pcAtualizado.getNrSerie());
            esqueletoUpdate.setInt(5, pcAtualizado.getId()); // Assuming getId() returns the student's ID

            int rowsUpdated = esqueletoUpdate.executeUpdate();

            if (rowsUpdated > 0) {
                connection.commit(); // Commits transaction.
            } else {
                throw new SQLException("Falhou a atualização do pc.");
            }

            // Close the PreparedStatement and connection
            esqueletoUpdate.close();
            connection.close();

            System.out.println("Pc atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
