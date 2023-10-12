package com.example.labinf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.List;

public class AlunoController {


    private AlunoDAO alunoDAO;
    private List<AlunoModel> alunos;
    private int registoAtual = -1;

    @FXML
    private Button btnAnterior;

    @FXML
    private Button btnInserir;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnSeguinte;

    @FXML
    private ImageView imgAnterior;

    @FXML
    private ImageView imgNovo;

    @FXML
    private ImageView imgSeguinte;

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
        txtNome.setText("");
        txtNumero.setText("");
        txtTurma.setText("");
        txtContacto.setText("");
        btnInserir.setDisable(true);
        btnInserir.setVisible(false);
        btnOk.setDisable(false);
        btnOk.setVisible(true);

    }
    @FXML
    void confirmaInserir(ActionEvent event) {
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
            aluno.setContacto(txtContacto.getText());
            aluno.setTurma(txtTurma.getText());
            alunoDAO.inserirAluno(aluno);
            btnInserir.setDisable(false);
            btnInserir.setVisible(true);
            btnOk.setDisable(true);
            btnOk.setVisible(false);

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
    void seguinte(ActionEvent event) {
        if (registoAtual < alunos.size() - 1) {
            registoAtual++;
            mostraAluno();
        }
    }

    @FXML
    void anterior(ActionEvent event) {
        if (registoAtual > 0) {
            registoAtual--;
            mostraAluno();
        }
    }

    private void mostraAluno() {
        AlunoModel aluno = alunos.get(registoAtual);
        txtNumero.setText(String.valueOf(aluno.getNumero()));
        txtNome.setText(aluno.getNome());
        txtTurma.setText(aluno.getTurma());
        txtContacto.setText(aluno.getContacto());
    }

    // ... Existing code ...

    @FXML
    private void initialize() {
        alunoDAO = new AlunoDAO();
        alunos = alunoDAO.sacaTodosAlunos();
        if (!alunos.isEmpty()) {
            registoAtual = 0;
            mostraAluno();
        }
    }

}
