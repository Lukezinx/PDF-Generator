package org.example.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArquivoSalvo {

    private String nomeOriginal;
    private String dataLeitura;
    private int totalPaginas;
    private String conteudoTexto;


    public ArquivoSalvo(String nomeOriginal, int totalPaginas, String conteudoTexto) {
        this.nomeOriginal = nomeOriginal;
        this.totalPaginas = totalPaginas;
        this.conteudoTexto = conteudoTexto;
        this.dataLeitura = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public String getNomeOriginal() {
        return nomeOriginal;
    }

    public void setNomeOriginal(String nomeOriginal) {
        this.nomeOriginal = nomeOriginal;
    }

    public String getConteudoTexto() {
        return conteudoTexto;
    }

    public void setConteudoTexto(String conteudoTexto) {
        this.conteudoTexto = conteudoTexto;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public String getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(String dataLeitura) {
        this.dataLeitura = dataLeitura;
    }
}
