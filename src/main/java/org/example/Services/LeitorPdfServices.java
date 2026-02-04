package org.example.Services;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.Exceptions.ErroLeituraPdfException;
import org.example.Model.ArquivoSalvo;
import org.example.Services.Interfaces.IPdfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class LeitorPdfServices implements IPdfServices {

    private static final Logger logger =LoggerFactory.getLogger(LeitorPdfServices.class);
    private static final  int LIMITE_PAGINAS = 100;



    @Override
    public ArquivoSalvo executar(File arquivoPdf) {

        logger.info("Iniciando leitura do arquivo: {}", arquivoPdf.getName());

        if(!arquivoPdf.exists()){
            logger.error("Arquivo não encontrado: {}", arquivoPdf.getAbsolutePath());
            throw new ErroLeituraPdfException("Arquivo não encontrado: " + arquivoPdf.getName(), null);
        }

        try(PDDocument document = Loader.loadPDF(arquivoPdf)){

            int totalPaginas = document.getNumberOfPages();

            if(totalPaginas > LIMITE_PAGINAS){
                logger.warn("Tentativa de ler PDF muito grande: {} paginas", totalPaginas);
                throw new ErroLeituraPdfException("PDF excede o limite de " + LIMITE_PAGINAS + " paginas.", null);
            }

            logger.info("Arquivo aberto com sucesso, Lendo {} paginas...", totalPaginas);

            PDFTextStripper extrair = new PDFTextStripper();

            extrair.setSortByPosition(true);
            String textoExtraido = extrair.getText(document);

            return new ArquivoSalvo(arquivoPdf.getName(),totalPaginas,textoExtraido);

        } catch (IOException e) {
            logger.error("Falha ao ler o PDF", e);
            throw new ErroLeituraPdfException("Falha tecnica ao ler o PDF", e);
        }


    }

}
