package com.example.labinf;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DesembrulhaOcupacao {
    private final StringProperty inicio;
    private final StringProperty nomeAluno;

    public DesembrulhaOcupacao(OcupacaoModel ocupacao) {
        this.inicio = new SimpleStringProperty(ocupacao.getInicio());
        this.nomeAluno = new SimpleStringProperty(ocupacao.getAluno().getNome());
    }

}