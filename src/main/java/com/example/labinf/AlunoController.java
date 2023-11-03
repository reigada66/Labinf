package com.example.labinf;

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
    private boolean alteradoPorCodigo = false;
    private String valorOriginal;
    @FXML
    private Button btnAnterior;

    @FXML
    private Button btnInserir;

    @FXML
    private Button btnEliminar;


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
        permiteNavegar(false);

    }

    private void permiteNavegar(boolean ativa){
        btnInserir.setDisable(!ativa);
        btnEliminar.setDisable(!ativa);
//        btnInserir.setVisible(false);
        btnOk.setDisable(ativa);
        btnOk.setVisible(!ativa);
        btnCancelar.setVisible(!ativa);
        btnCancelar.setDisable(ativa);
        btnAnterior.setDisable(!ativa);
        btnSeguinte.setDisable(!ativa);
        alunosTableView.setDisable(!ativa);

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
            permiteNavegar(true);
        }
    }

    @FXML
    void cancelaInserir(ActionEvent event) {
        permiteNavegar(true);
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
    void eliminarAluno(ActionEvent event) {
        alunoDAO.apagaAluno(alunos.get(registoAtual),registoAtual);
        mostraAluno();  // problema se eliminar o último aluno da lista

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
        alteradoPorCodigo = true;
        valorOriginal = String.valueOf(aluno.getNumero());
        txtNumero.setText(String.valueOf(aluno.getNumero()));
        alteradoPorCodigo = true;
        valorOriginal = aluno.getNome();
        txtNome.setText(aluno.getNome());
        alteradoPorCodigo = true;
        valorOriginal = aluno.getTurma();
        txtTurma.setText(aluno.getTurma());
        alteradoPorCodigo = true;
        valorOriginal = aluno.getContacto();
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
/*        addChangeListener(txtNome);
        addChangeListener(txtTurma);
        addChangeListener(txtContacto);
        addChangeListener(txtNumero);
*/
        addFocusChangeListener(txtNome);
        addFocusChangeListener(txtTurma);
        addFocusChangeListener(txtContacto);
        addFocusChangeListener(txtNumero);

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
                registoAtual = alunos.indexOf(newSelection);
                mostraAluno();
            }
        });

    }

 /*   private void addChangeListener(TextField textField) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println(obs.getValue());
            if (!alteradoPorCodigo) { // foi alterado pelo utilizador
                btnSeguinte.setDisable(true);
                btnAnterior.setDisable(true);
            }
            System.out.println(alteradoPorCodigo);
            // Repor a bandeira
            alteradoPorCodigo = false;
        });
    }
*/

    private void addFocusChangeListener(TextField textField) {

        textField.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
            if (!newFocus) { // Focus is lost
                System.out.println(textField.getText());
                if(!alteradoPorCodigo) {
                    String newValue = textField.getText();
                    if (!newValue.equals(valorOriginal)) { // Value has changed
                        btnSeguinte.setDisable(true);
                        btnAnterior.setDisable(true);
                    }
                }
                System.out.println(alteradoPorCodigo);

                // Repor a bandeira
                alteradoPorCodigo = false;

            }
        });
    }
}

