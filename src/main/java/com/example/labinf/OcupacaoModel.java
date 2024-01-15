package com.example.labinf;

import java.sql.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;


public class OcupacaoModel {
    private final SimpleStringProperty inicio = new SimpleStringProperty();
    private final SimpleIntegerProperty idOcupacao = new SimpleIntegerProperty();
    private PcModel pc;
    private AlunoModel aluno;

    // Getter and Setter methods for properties
    public String getInicio() {
        return inicio.get();
    }

    public SimpleStringProperty inicioProperty() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio.set(inicio);
    }


    public int getIdOcupacao() {
        return idOcupacao.get();
    }

    public SimpleIntegerProperty idOcupacaoProperty() {
        return idOcupacao;
    }

    public void setIdOcupacao(int idOcupacao) {
        this.idOcupacao.set(idOcupacao);
    }

    public PcModel getPC() {
        return pc;
    }

    public void setPC(PcModel pc) {
        this.pc = pc;
    }

    public AlunoModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunoModel aluno) {
        this.aluno = aluno;
    }
}
