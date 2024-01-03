package com.example.labinf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class MainController {
    @FXML
    private Button btnAlunos1;
    @FXML
    private Button btnEquipamentos;
    @FXML
    private VBox vista;
    @FXML
    void vistaAlunos(ActionEvent event) {
        loadView("aluno-view.fxml");
    }
    @FXML
    void vistaEquipamentos(ActionEvent event) {
        loadView("pc-view.fxml");
    }
    private void loadView(String fxmlFileName) {
        try {
            URL resourceUrl = getClass().getResource(fxmlFileName);
            System.out.println("Loading FXML from path: " + resourceUrl);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Node view = loader.load();
            vista.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();            // Lidar com exceção
        }
    }
    @FXML
    private void initialize() {
        loadView("abre-view.fxml");
    }
}
