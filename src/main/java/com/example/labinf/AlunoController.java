package com.example.labinf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AlunoController {

    AlunoDAO alunoDAO;
    @FXML
    private Button btnInserir;

    @FXML
    private Label lblMensagem;

    @FXML
    private TextField txtContacto;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtTurma;

    @FXML
    void inserirAluno(ActionEvent event) {
        int nr = validaNumero(txtNumero.getText());
        if (nr == 0)
        {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Gestão de Alunos");
            alert.setContentText("Número de aluno inválido!");
            alert.show();
        }
        else{
            AlunoModel aluno = new AlunoModel();
            aluno.setNumero(nr);
            aluno.setNome(txtNome.getText());
            alunoDAO.mandaprala(aluno);
        }
    }

    int validaNumero(String snumero){
        int valor;
        try {
            valor = Integer.parseInt(snumero);
            valor = valor > 0 ? valor : 0;
        } catch (NumberFormatException e) {
            valor = 0;
        }
        return valor;
    }

    @FXML
    private void initialize () {
        alunoDAO = new AlunoDAO();
    }

}
