package com.example.labinf;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.util.List;

public class AlunoController {


    private AlunoDAO alunoDAO;
    private List<AlunoModel> alunos;
    private int registoAtual = -1;

    @FXML
    private TableView<AlunoModel> alunosTableView;

    @FXML
    private Button btnAnterior;

    @FXML
    private Button btnInserir;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancelar;


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
        btnCancelar.setVisible(true);
        btnCancelar.setDisable(false);
        btnAnterior.setDisable(true);
        btnSeguinte.setDisable(true);

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

    @FXML
    void cancelaInserir(ActionEvent event) {
        btnCancelar.setVisible(false);
        btnCancelar.setDisable(true);
        btnAnterior.setDisable(false);
        btnSeguinte.setDisable(false);
        mostraAluno();

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


    @FXML
    private void initialize() {
        alunoDAO = new AlunoDAO();
        alunos = alunoDAO.sacaTodosAlunos();
        if (!alunos.isEmpty()) {
            registoAtual = 0;
            mostraAluno();
        }
        ChangeListener<String> userTextChangeListener = new ChangeListener<String>() {
            boolean changedByUser = false;

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (changedByUser) {
                    btnSeguinte.setDisable(true);
                    btnAnterior.setDisable(true);
                }
            }
        };

        txtNome.textProperty().addListener(userTextChangeListener);
        txtTurma.textProperty().addListener(userTextChangeListener);
        txtContacto.textProperty().addListener(userTextChangeListener);
        txtNumero.textProperty().addListener(userTextChangeListener);

        // Add a listener to the text fields to mark changes made by the user
        txtNome.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)) {
                userTextChangeListener.changed(observable, oldValue, newValue);
                userTextChangeListener.changed(null, null, null);
            }
        });

        txtTurma.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)) {
                userTextChangeListener.changed(observable, oldValue, newValue);
                userTextChangeListener.changed(null, null, null);
            }
        });

        txtContacto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)) {
                userTextChangeListener.changed(observable, oldValue, newValue);
                userTextChangeListener.changed(null, null, null);
            }
        });

        txtNumero.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)) {
                userTextChangeListener.changed(observable, oldValue, newValue);
                userTextChangeListener.changed(null, null, null);
            }
        });

        TableColumn<AlunoModel, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<AlunoModel, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<AlunoModel, Integer> numeroColumn = new TableColumn<>("Número");
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        TableColumn<AlunoModel, String> turmaColumn = new TableColumn<>("Turma");
        turmaColumn.setCellValueFactory(new PropertyValueFactory<>("turma"));
        TableColumn<AlunoModel, String> contactoColumn = new TableColumn<>("Contacto");
        contactoColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));

        // Add columns to the TableView
        alunosTableView.getColumns().setAll(idColumn, nomeColumn, numeroColumn, turmaColumn, contactoColumn);

        // Populate the TableView with data from alunos list
        ObservableList<AlunoModel> observableAlunos = FXCollections.observableArrayList(alunos);
        alunosTableView.setItems(observableAlunos);

        alunosTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Update the text fields with the selected AlunoModel's data
                txtNumero.setText(String.valueOf(newSelection.getNumero()));
                txtNome.setText(newSelection.getNome());
                txtTurma.setText(newSelection.getTurma());
                txtContacto.setText(newSelection.getContacto());
            }
        });

    }

}

