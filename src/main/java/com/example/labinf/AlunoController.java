package com.example.labinf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import java.util.Comparator;

import java.util.List;
import java.util.stream.Collectors;

public class AlunoController {


    private AlunoDAO alunoDAO;
    private ObservableList<AlunoModel> alunos;

    private TableColumn<AlunoModel, ?> lastSortedColumn;


    private List<AlunoModel> todosAlunos;
    private ObservableList<String> turmas;
    private AlunoModel aluno = new AlunoModel();
    private boolean alteradoPorCodigo = true, novo = false;

    private int registoAtual = -1;
//    private boolean novo = false, alteradoPorCodigo = true, alteradoPeloUtilizador = false;


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
    private TextField txtPesquisa;

    @FXML
    private TextField txtNome;

    @FXML
    private Spinner<Integer> spNumero;
    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 40, 1); // valores mínimo, máximo e passo

    @FXML
    private TextField txtTurma;

    @FXML
    void inserirAluno(ActionEvent event) {
        aluno.setAluno(new AlunoModel());
        permiteNavegar(false);
        novo = true;
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
        if (novo) {
            alunoDAO.insereNaBD(aluno);
            alunos.add(aluno);
            novo = false;
        }
        else{
            alunoDAO.atualizaNaBD(aluno);
            alunos.set(registoAtual,aluno);
        }
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
        alteradoPorCodigo = true;
        aluno.setAluno(alunos.get(registoAtual));
        alteradoPorCodigo = false;
    }

    @FXML
    void escolheTurma(ActionEvent event) {
        String turma = lstTurma.getValue();
        if (turma.equals("Todas elas")){
            alunos.setAll(FXCollections.observableArrayList(todosAlunos));
            System.out.println(turma);
        }
        else
            alunos.setAll(FXCollections.observableArrayList(todosAlunos.stream().filter(a -> a.getTurma().equals(turma)).collect(Collectors.toList())));
    }

    @FXML
    private void initialize() {
        spNumero.setValueFactory(valueFactory);

        alunoDAO = new AlunoDAO();
        todosAlunos = alunoDAO.sacaTodosAlunos();
        alunos = FXCollections.observableArrayList(todosAlunos);
        if (!alunos.isEmpty()) {
            registoAtual = 0;
        }
        ObservableList<String> listaTurmas = FXCollections.observableArrayList(alunos.stream()  // Percorre a lista de alunos
                .map(AlunoModel::getTurma) // obtem a turma de cada aluno
                .distinct() // Remove duplicados, se houver
                .sorted()   // ordena a sequência por ordem alfabética natural
                .collect(Collectors.toList())); // converte o resultado numa lista

        listaTurmas.add("Todas elas");

        lstTurma.setItems(listaTurmas);

        System.out.println(listaTurmas);

        addChangeListener(txtNome);
        addChangeListener((txtContacto));
        addChangeListener(txtTurma);

        reageaPesquisa(txtPesquisa);


        spNumero.getValueFactory().valueProperty().bindBidirectional(aluno.numeroProperty().asObject());
        txtNome.textProperty().bindBidirectional(aluno.nomeProperty());
        txtTurma.textProperty().bindBidirectional(aluno.turmaProperty());
        txtContacto.textProperty().bindBidirectional(aluno.contactoProperty());
        mostraAluno();


        TableColumn<AlunoModel, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<AlunoModel, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setPrefWidth(140);
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<AlunoModel, Integer> numeroColumn = new TableColumn<>("Número");
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        TableColumn<AlunoModel, String> turmaColumn = new TableColumn<>("Turma");
        turmaColumn.setCellValueFactory(new PropertyValueFactory<>("turma"));
        TableColumn<AlunoModel, String> contactoColumn = new TableColumn<>("Contacto");
        contactoColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        lastSortedColumn = numeroColumn; //  "numeroColumn" é a ordem inicial

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
    private void addChangeListener(TextField textField) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!alteradoPorCodigo)
                permiteNavegar(false);
        });
    }

    private void reageaPesquisa(TextField textField) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            alunos.setAll(FXCollections.observableArrayList(alunos.stream().filter(a -> a.getNome().contains(newValue)).collect(Collectors.toList())));

        });
    }

}