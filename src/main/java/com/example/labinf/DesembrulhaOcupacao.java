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

    public String getInicio() {
        return inicio.get();
    }

    public StringProperty inicioProperty() {
        return inicio;
    }

    public String getNomeAluno() {
        return nomeAluno.get();
    }

    public StringProperty nomeAlunoProperty() {
        return nomeAluno;
    }
}
