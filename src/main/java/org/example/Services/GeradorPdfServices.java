package org.example.Services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.example.Exceptions.ErroGeracaoPdfException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GeradorPdfServices {

    private static final Logger logger = LoggerFactory.getLogger(GeradorPdfServices.class);

    private final PDDocument documento;
    private PDPageContentStream streamAtual;
    private PDPage pageAtual;
    private final PDFont fonte = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    private final float tamanhoFonte = 12;
    private final float margem = 50;
    private final float alturaPagina = 792;
    private float yAtual;



    public GeradorPdfServices(PDDocument documento) {
        this.documento = documento;
        criarNovaPagina();
    }

    private void criarNovaPagina() {

        try {
            if (streamAtual !=  null) {
                    streamAtual.endText();
                    streamAtual.close();
            }

            this.pageAtual = new PDPage();
            this.documento.addPage(pageAtual);
            this.streamAtual = new PDPageContentStream(documento, pageAtual);

            this.yAtual = 750;

            this.streamAtual.beginText();
            this.streamAtual.setFont(fonte, tamanhoFonte);
            this.streamAtual.newLineAtOffset(margem, yAtual);
            logger.debug("Criando nova página no PDF...");


        } catch (Exception e) {
            throw new ErroGeracaoPdfException("Erro ao criar nova página", e);
        }

    }

    public void gerarPdf(String texto){
        logger.info("Iniciando geração de PDF com texto de tamanho: {}", texto.length());

        float larguraUtil = pageAtual.getMediaBox().getWidth() - (2 * margem);

        texto = texto.replace("\t"," ");

        texto = texto.replaceAll("\\r\\n","\n").replace("\r", "\n");

        String[] paragrafos = texto.split("\\n");

        for(String paragrafo : paragrafos){

            if(paragrafo.trim().isEmpty()){
                imprimirLinha(" ");
                continue;
            }

            String[] palavras = texto.split(" ");
            String linhaAtual = "";


            for(String palavra : palavras){
                if (palavra.isEmpty()) continue;

                String testeLinha = linhaAtual + (linhaAtual.isEmpty() ? "" : " ") + palavra;

                try {
                    float larguraLinha = (fonte.getStringWidth(testeLinha) / 1000) * tamanhoFonte;

                    if (larguraLinha < larguraUtil) {
                        linhaAtual = testeLinha;
                    } else {
                        imprimirLinha(linhaAtual);
                        linhaAtual = palavra;
                    }
                } catch (Exception e) {
                    logger.warn("Caractere ignorado na palavra '{}'. Motivo: {}", palavra, e.getMessage());
                }
            }

            if(linhaAtual.isEmpty()){
                imprimirLinha(linhaAtual);
            }

        }
    }


    private void imprimirLinha(String linha){
         float alturaLinha = tamanhoFonte * 1.5f;

         if (yAtual < margem) {
             criarNovaPagina();
         }
         try {
             streamAtual.showText(linha);
             streamAtual.newLineAtOffset(0, -alturaLinha);
             yAtual -= alturaLinha;
         } catch (Exception e ){
             e.printStackTrace();
         }


    }


    public void fechar() {
        try {
            if (streamAtual != null) {
                streamAtual.endText();
                streamAtual.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


