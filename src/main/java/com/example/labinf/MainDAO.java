package com.example.labinf;

public class MainDAO {
    private String url;

    private AlunoDAO alunoDAO;
    private PcDAO pcDAO;

    public AlunoDAO getAlunoDAO() {
        return alunoDAO;
    }

    public PcDAO getPcDAO() {
        return pcDAO;
    }

    public MainDAO() {
        url = "jdbc:sqlite:C:/Users/afoliveira/labinf.db";
        alunoDAO = new AlunoDAO(url);
        alunoDAO.sacaTodosAlunos();
        this.pcDAO = new PcDAO(url);
        pcDAO.sacaTodosPcs(alunoDAO.getTodosAlunos());
    }
}
