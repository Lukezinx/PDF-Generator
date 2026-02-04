package org.example.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Exceptions.ErroExportacaoJsonException;
import org.example.Model.ArquivoSalvo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;

public class ExportadorJsonService {

    private static final Logger logger = LoggerFactory.getLogger(ExportadorJsonService.class);

    public void salvarEmJson(ArquivoSalvo dados, String nomeArquivoSaida){

        logger.info("Preparando para exportar JSON: {}", nomeArquivoSaida);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(dados);

        try(FileWriter fw = new FileWriter(nomeArquivoSaida+".json");) {

            fw.write(json);
            logger.info("Arquivo JSON exportado com sucesso!");
        } catch (IOException e) {
            logger.error("Erro de I/O ao salvar JSON", e);
            throw new ErroExportacaoJsonException("Nao foi possivel salvar o arquivo JSON no disco.", e);
        }
    }

}
