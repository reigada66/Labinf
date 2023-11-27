package com.example.labinf;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AlunoModel {

    private int id;


    private SimpleIntegerProperty numero = new SimpleIntegerProperty();
    private SimpleStringProperty nome = new SimpleStringProperty();
    private SimpleStringProperty turma = new SimpleStringProperty();
    private SimpleStringProperty contacto = new SimpleStringProperty();

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

    public SimpleIntegerProperty numeroProperty() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero.set(numero);
    }

    public String getNome() {
        return nome.get();
    }

    public SimpleStringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getTurma() {
        return turma.get();
    }

    public SimpleStringProperty turmaProperty() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma.set(turma);
    }

    public String getContacto() {
        return contacto.get();
    }

    public SimpleStringProperty contactoProperty() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto.set(contacto);
    }

    public AlunoModel(){
        id = 99999;
        nome.set("Malaquias");
        numero.set(1);
    }

    public void setAluno(AlunoModel original){
        setId(original.getId());
        setNome(original.getNome());
        setContacto(original.getContacto());
        setNumero(original.getNumero());
        setTurma(original.getTurma());
    }
}
