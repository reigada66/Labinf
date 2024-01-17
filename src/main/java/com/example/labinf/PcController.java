package com.example.labinf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.util.stream.Collectors;

public class PcController {


    private PcDAO pcDAO;
    private ObservableList<PcModel> pcs;

    private TableColumn<OcupacaoModel, ?> lastSortedColumn;
    private ObservableList<AlunoModel> alunosList;

    // other methods


    private ObservableList<String> salas;
    private ObservableList<OcupacaoModel> listaOcupacoes;
    private PcModel pc = new PcModel();
    private boolean alteradoPorCodigo = true, novo = false;

    private int registoAtual = 0;
//    private boolean novo = false, alteradoPorCodigo = true, alteradoPeloUtilizador = false;


    public PcDAO getPcDAO() {
        return pcDAO;
    }

    public void setPcDAO(PcDAO pcDAO) {
        this.pcDAO = pcDAO;
    }

    @FXML
    private TableView<OcupacaoModel> ocupTableView;

    @FXML
    private ComboBox<String> lstSala;

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
    private TextField txtNrSerie;


    @FXML
    private TextField txtMarca;
    @FXML
    private CheckBox ckEstado;

    @FXML
    private Spinner<Integer> spNumero;
    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 40, 1); // valores mínimo, máximo e passo

    @FXML
    private TextField txtSala;

    public void setAlunosList(ObservableList<AlunoModel> alunosList) {
        this.alunosList = alunosList;
        // Use the 'alunosList' as needed in your PcController
    }

    @FXML
    void inserirPc(ActionEvent event) {
        pc.setPc(new PcModel());
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
//        pcsTableView.setDisable(!ativa);

    }


    @FXML
    void confirmaInserir(ActionEvent event) {
        if (novo) {
            System.out.println("sala: " + pc.getSala() + " Marca: " + pc.getMarca() + " nrserie: " + pc.getNrSerie());
            pcDAO.insereNaBD(pc);
            pcs.add(pc);
            novo = false;
        }
        else{
            pcDAO.atualizaNaBD(pc);
            pcs.set(registoAtual,pc);
        }
        permiteNavegar(true);
        mostraPc();
    }

    @FXML
    void cancelaInserir(ActionEvent event) {
        permiteNavegar(true);
        mostraPc();
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
    void eliminarPc(ActionEvent event) {
        pcDAO.apagaNaBD(pcs.get(registoAtual));
        // elimina na lista em memória
        pcs.remove(registoAtual);
        if (registoAtual > pcs.size() - 1) {
            registoAtual = 0;
            if (!pcs.isEmpty())
                mostraPc();
        }
        else
            mostraPc();
    }


    @FXML
    void seguinte(ActionEvent event) {
        if (registoAtual < pcs.size() - 1) {
            registoAtual++;
            mostraPc();
        }
    }

    @FXML
    void anterior(ActionEvent event) {
        if (registoAtual > 0) {
            registoAtual--;
            mostraPc();
        }
    }

    private void mostraPc() {
        alteradoPorCodigo = true;
        pc.setPc(pcs.get(registoAtual));
        listaOcupacoes = FXCollections.observableArrayList(pc.getOcupacaoPc());
        listaOcupacoes.stream().forEach(o -> {
                        System.out.println(o.getInicio());
        });

        ocupTableView.setItems(listaOcupacoes);
        ocupTableView.refresh();

        alteradoPorCodigo = false;
    }

    @FXML
    void escolheSala(ActionEvent event) {
        String sala = lstSala.getValue();
        if (sala.equals("Todas elas")){
            pcs.setAll(FXCollections.observableArrayList(pcDAO.getPcs()));
            System.out.println(sala);
        }
        else
            pcs.setAll(FXCollections.observableArrayList(pcDAO.getPcs().stream().filter(a -> a.getSala().equals(sala)).collect(Collectors.toList())));
    }

    public  void inicia(PcDAO pcDAO){
        this.pcDAO = pcDAO;
        pcs = FXCollections.observableArrayList(pcDAO.getPcs());
        System.out.println(pcs.stream().count());
        spNumero.getValueFactory().valueProperty().bindBidirectional(pc.numeroProperty().asObject());
        txtMarca.textProperty().bindBidirectional(pc.marcaProperty());
        txtSala.textProperty().bindBidirectional(pc.salaProperty());
        txtNrSerie.textProperty().bindBidirectional(pc.nrSerieProperty());
        ObservableList<String> listaSalas = FXCollections.observableArrayList(pcs.stream()  // Percorre a lista de pcs
                .map(PcModel::getSala) // obtem a sala de cada pc
                .distinct() // Remove duplicados, se houver
                .sorted()   // ordena a sequência por ordem alfabética natural
                .collect(Collectors.toList())); // converte o resultado numa lista

        listaSalas.add("Todas elas");

        lstSala.setItems(listaSalas);


        TableColumn<OcupacaoModel, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idOcupacao"));
        TableColumn<OcupacaoModel, String> dataColumn = new TableColumn<>("Data");
        dataColumn.setPrefWidth(140);
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        lastSortedColumn = dataColumn;

        // Add columns to the TableView
        ocupTableView.getColumns().setAll(idColumn, dataColumn);

        ocupTableView.setItems(listaOcupacoes);

        ocupTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Update the text fields with the selected PcModel's data
                registoAtual = pcs.indexOf(newSelection);
                mostraPc();
            }
        });

        if (pcs.isEmpty()) {
            pc.setPc(new PcModel());
            permiteNavegar(false);
            novo = true;
        }
        //mostraPc();



    }
    @FXML
    private void initialize() {
        spNumero.setValueFactory(valueFactory);

        addChangeListener(txtMarca);
        addChangeListener(txtNrSerie);
        addChangeListener(txtSala);


    }
    private void addChangeListener(TextField textField) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!alteradoPorCodigo)
                permiteNavegar(false);
        });
    }


}