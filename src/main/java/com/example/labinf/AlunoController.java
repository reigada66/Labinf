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
    private ObservableList<AlunoModel> alunos;

    private List<AlunoModel> todosAlunos;
    private ObservableList<String> turmas;
    private int registoAtual = -1;


    @FXML
    private TableView<AlunoModel> alunosTableView;

    @FXML
    private ComboBox<String> lstTurma;

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
    private Spinner<Integer> spNumero;
    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 40, 1); // valores mínimo, máximo e passo

    @FXML
    private TextField txtTurma;

    @FXML
    void inserirAluno(ActionEvent event) {
        txtNome.setText("");
        spNumero.getValueFactory().setValue(50);
        txtTurma.setText("");
        txtContacto.setText("");
        permiteNavegar(false);
    }

    private void permiteNavegar(boolean ativa){
        btnInserir.setDisable(!ativa);
        btnEliminar.setDisable(!ativa);
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
        AlunoModel novoAluno = new AlunoModel();
        novoAluno.setNumero(spNumero.getValue());
        novoAluno.setNome(txtNome.getText());
        novoAluno.setContacto(txtContacto.getText());
        novoAluno.setTurma(txtTurma.getText());
        alunoDAO.insereNaBD(novoAluno);
        alunos.add(novoAluno);
        permiteNavegar(true);
        mostraAluno();
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
        alunoDAO.apagaNaBD(alunos.get(registoAtual));
        // elimina na lista em memória
        alunos.remove(registoAtual);
        if (registoAtual > alunos.size() - 1) {
            registoAtual = 0;
            if (!alunos.isEmpty())
                mostraAluno();
        }
        else
            mostraAluno();
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
        spNumero.getValueFactory().setValue(aluno.getNumero());
        txtNome.setText(aluno.getNome());
        txtTurma.setText(aluno.getTurma());
        txtContacto.setText(aluno.getContacto());
    }

    @FXML
    private void initialize() {
        spNumero.setValueFactory(valueFactory);

        alunoDAO = new AlunoDAO();
        todosAlunos = alunoDAO.sacaTodosAlunos();
        alunos = FXCollections.observableArrayList(alunoDAO.sacaTodosAlunos());
        if (!alunos.isEmpty()) {
            registoAtual = 0;
            mostraAluno();
        }

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

        alunosTableView.setItems(alunos);

        alunosTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Update the text fields with the selected AlunoModel's data
                registoAtual = alunos.indexOf(newSelection);
                mostraAluno();
            }
        });

    }

}

