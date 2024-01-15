package com.example.labinf;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class PcModel {

    private int id;


    private SimpleIntegerProperty numero = new SimpleIntegerProperty();
    private SimpleStringProperty marca = new SimpleStringProperty();
    private SimpleStringProperty sala = new SimpleStringProperty();
    private SimpleStringProperty nrSerie = new SimpleStringProperty();

    private SimpleBooleanProperty estado = new SimpleBooleanProperty();
    private List<OcupacaoModel> pcOcupacao;

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
        pcOcupacao = new ArrayList<>();
    }

    public List<OcupacaoModel> getPcOcupacao() {
        return pcOcupacao;
    }

    public void setPcOcupacao(List<OcupacaoModel> pcOcupacao) {
        this.pcOcupacao = pcOcupacao;
    }

    public void addOcupacao(OcupacaoModel ocupacao){
        pcOcupacao.add(ocupacao);
    }

    public void setPc(PcModel original){
        setId(original.getId());
        setMarca(original.getMarca());
        setNrSerie(original.getNrSerie());
        setNumero(original.getNumero());
        setSala(original.getSala());
        setEstado(original.getEstado());
    }

    public void printPcData() {
        System.out.println("PC Data:");
        System.out.println("ID: " + getId());
        System.out.println("Marca: " + getMarca());
        System.out.println("Número: " + getNumero());
        System.out.println("Sala: " + getSala());
        System.out.println("Número de Série: " + getNrSerie());
        System.out.println("Estado: " + getEstado());

        if (pcOcupacao != null && !pcOcupacao.isEmpty()) {
            System.out.println("\nOcupacao List:");

            for (OcupacaoModel ocupacao : pcOcupacao) {
                System.out.println("\nOcupacao Data:");
                System.out.println("ID: " + ocupacao.getIdOcupacao());
                System.out.println("Inicio: " + ocupacao.getInicio());

                // Check if Ocupacao has an associated Aluno
                if (ocupacao.getAluno() != null) {
                    System.out.println("\nAluno Data:");
                    System.out.println("ID: " + ocupacao.getAluno().getId());
                    System.out.println("Nome: " + ocupacao.getAluno().getNome());
                    System.out.println("Número: " + ocupacao.getAluno().getNumero());
                    System.out.println("Turma: " + ocupacao.getAluno().getTurma());
                    System.out.println("Contacto: " + ocupacao.getAluno().getContacto());
                } else {
                    System.out.println("No Aluno assigned to this Ocupacao.");
                }
            }
        } else {
            System.out.println("No Ocupacao assigned to this PC.");
        }
    }

}
