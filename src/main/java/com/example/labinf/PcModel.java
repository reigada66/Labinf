package com.example.labinf;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PcModel {

    private int id;


    private SimpleIntegerProperty numero = new SimpleIntegerProperty();
    private SimpleStringProperty marca = new SimpleStringProperty();
    private SimpleStringProperty sala = new SimpleStringProperty();
    private SimpleStringProperty nrSerie = new SimpleStringProperty();

    private SimpleBooleanProperty estado = new SimpleBooleanProperty();

    // Getter and Setter methods for properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero.get();
    }

    public boolean getEstado() {
        return estado.get();
    }

    public SimpleBooleanProperty estadoProperty() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado.set(estado);
    }

    public SimpleIntegerProperty numeroProperty() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero.set(numero);
    }

    public String getMarca() {
        return marca.get();
    }

    public SimpleStringProperty marcaProperty() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca.set(marca);
    }

    public String getSala() {
        return sala.get();
    }

    public SimpleStringProperty salaProperty() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala.set(sala);
    }

    public String getNrSerie() {
        return nrSerie.get();
    }

    public SimpleStringProperty nrSerieProperty() {
        return nrSerie;
    }

    public void setNrSerie(String nrSerie) {
        this.nrSerie.set(nrSerie);
    }

    public PcModel(){
        marca.set("Dell");
        numero.set(1);
    }

    public void setPc(PcModel original){
        setId(original.getId());
        setMarca(original.getMarca());
        setNrSerie(original.getNrSerie());
        setNumero(original.getNumero());
        setSala(original.getSala());
        setEstado(original.getEstado());
    }
}
