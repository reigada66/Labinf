package com.example.labinf;

import java.util.Date;

public class PcModel {

    private int id;
    private String sala;
    private String nrSerie;
    private String marca;
    private String estado;
    public Date dataAquisicao;

    public PcModel(){
        id = 1;
        sala="B2P1S17";
        marca = "HP";
    }

    public PcModel(String sala, String m){
        this.sala = sala;
        marca = m;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSala() {
        return sala;
    }

    public String getNrSerie() {
        return nrSerie;
    }

    public void setNrSerie(String nrSerie) {
        this.nrSerie = nrSerie;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
