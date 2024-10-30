package com.example.ventz.model;

public class Card {

    private int idCard;
    private String pergunta;
    private String resposta;
    private int idDeckFk;
    private String acertou;

    // Construtor padrão que inicializa com valores padrão
    public Card() {
        this.idCard = 0; // ou você pode usar um valor padrão apropriado
        this.pergunta = ""; // inicializa como string vazia
        this.resposta = ""; // inicializa como string vazia
        this.idDeckFk = 0; // ou outro valor padrão
        this.acertou = ""; // inicializa como string vazia
    }

    // Construtor que recebe valores
    public Card(int idCard, String pergunta, String resposta, int idDeckFk, String acertou) {
        this.idCard = idCard;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.idDeckFk = idDeckFk;
        this.acertou = acertou;
    }

    // Construtor para convers�o para o banco de dados local
    public Card(int idCard, String pergunta, String resposta, int idDeckFk) {
            this.idCard = idCard;
            this.pergunta = pergunta;
            this.resposta = resposta;
            this.idDeckFk = idDeckFk;
            this.acertou = "";
        }

    // Getters e Setters
    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public int getIdDeckFk() {
        return idDeckFk;
    }

    public void setIdDeckFk(int idDeckFk) {
        this.idDeckFk = idDeckFk;
    }

    public String getAcertou() {
        return acertou;
    }

    public void setAcertou(String acertou) {
        this.acertou = acertou;
    }

    @Override
    public String toString() {
        return "Card{" +
                "idCard=" + idCard +
                ", pergunta='" + pergunta + '\'' +
                ", resposta='" + resposta + '\'' +
                ", idDeckFk=" + idDeckFk +
                ", acertou='" + acertou + '\'' +
                '}';
    }
}
