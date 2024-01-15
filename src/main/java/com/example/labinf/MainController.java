package com.example.labinf;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class MainController {
    private MainDAO daoPrincipal;
    @FXML
    private Button btnAlunos1;
    @FXML
    private Button btnEquipamentos;
    @FXML
    private VBox vista;
    private Node alunoView;
    private Node pcView;

    @FXML
    void vistaAlunos(ActionEvent event) {
        vista.getChildren().setAll(alunoView);
    }

    @FXML
    void vistaEquipamentos(ActionEvent event) {
        vista.getChildren().setAll(pcView);
    }

    private void loadViews() {
        try {
            FXMLLoader alunoLoader = new FXMLLoader(getClass().getResource("aluno-view.fxml"));
            alunoView = alunoLoader.load();

            // Get the controller for alunoView
            AlunoController alunoController = alunoLoader.getController();
            alunoController.inicia(daoPrincipal.getAlunoDAO());

            // Retrieve the 'alunos' list from AlunoController

            FXMLLoader pcLoader = new FXMLLoader(getClass().getResource("pc-view.fxml"));
            pcView = pcLoader.load();

            // Get the controller for pcView
            PcController pcController = pcLoader.getController();
            pcController.inicia(daoPrincipal.getPcDAO());

            // Pass the 'alunosList' to PcController
//            pcController.setAlunosList(alunoController.getAlunosList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        daoPrincipal = new MainDAO();
        loadViews();
        vista.getChildren().setAll(alunoView);
    }
}
