package org.example.View;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.example.Exceptions.PdfException;
import org.example.Model.ArquivoSalvo;
import org.example.Services.ExportadorJsonService;
import org.example.Services.GeradorPdfServices;
import org.example.Services.LeitorPdfServices;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MenuPrincipal {
    private final LeitorPdfServices leitorPdfServices = new LeitorPdfServices();
    private final ExportadorJsonService exportadorJsonService = new ExportadorJsonService();
    private final Scanner scanner = new Scanner(System.in);


    public void iniciar(){
        int opcao = -1;

        System.out.println("Bem vindo ao menu principal");

        while(opcao != 0){
            System.out.println("\n------------------------------");
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Ler um PDF Existente");
            System.out.println("2 - Gerar um Novo PDF (Com opção de JSON)");
            System.out.println("3 - Converter PDF Existente para JSON");
            System.out.println("0 - Sair");
            System.out.print("Sua escolha: ");


            try{
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch(Exception e){
                System.out.println("Por favor, digite apenas numeros");
                scanner.nextLine();
                continue;
            }


            switch(opcao){
                case 1:
                    fluxoLerPdf();
                    break;
                case 2:
                    fluxoGerarPdf();
                    break;
                case 3:
                    fluxoConverterParaJson();
                    break;
                case 0:
                    System.out.println("Encerrando sistema");
                    break;
                default:
                    System.out.println("opçao inválida!");
            }
        }
    }


    private void fluxoLerPdf(){
        System.out.println("digite o nome do arquivo (ex: documento.pdf): ");
        String nomeArquivo = scanner.nextLine();

        if(!nomeArquivo.endsWith(".pdf")) nomeArquivo += ".pdf";

        File arquivo = new File(nomeArquivo);
        String texto = leitorPdfServices.executar(arquivo);

        System.out.println("CONTEUDO DO PDF");

        if(texto.length()>500){
            System.out.println(texto.substring(0, 500) + "...[TEXTO RESTANTE OMITIDO]");

        } else {
            System.out.println(texto);
        }
    }


    private void fluxoGerarPdf(){
        System.out.print("Digite o nome para o NOVO arquivo (sem .pdf): ");
        String nomeArquivo = scanner.nextLine();
        String nomePdfFinal = nomeArquivo + ".pdf";


        System.out.println("\n--- MODO DE TEXTO LONGO ---");
        System.out.println("Cole seu texto abaixo. Quando terminar, pressione ENTER");
        System.out.println("em uma linha vazia e digite 'FIM' para processar.");
        System.out.println();

        StringBuilder textoCompleto = new StringBuilder();

        while(true){
            String linha = scanner.nextLine();
            if(linha.equalsIgnoreCase("FIM")){
                break;
            }
            textoCompleto.append(linha).append("\n");
        }

        String textoFinal = textoCompleto.toString();

        if(textoFinal.trim().isEmpty()){
            System.out.println("Você não digitou nenhum texto. Operação cancelada.");
            return;
        }

        try(PDDocument documento = new PDDocument()) {
            GeradorPdfServices gerador = new GeradorPdfServices(documento);
            gerador.gerarPdf(textoFinal.trim());

            gerador.fechar();
            documento.save(nomePdfFinal);
            System.out.println("PDF salvo com sucesso! " + nomePdfFinal);

            System.out.println("Deseja salvar também uma cópia em JSON? (S/N): ");
            String resposta = scanner.nextLine();
            if(resposta.equalsIgnoreCase("S")){

                ArquivoSalvo modelo = new ArquivoSalvo(nomePdfFinal,1,textoFinal.trim());
                exportadorJsonService.salvarEmJson(modelo, nomeArquivo);
            }

        } catch (PdfException | IOException e) {
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
        }

    }

    private void fluxoConverterParaJson(){
        System.out.print("Digite o nome do arquivo PDF para converter: ");
        String nomeArquivo = scanner.nextLine();

        if(!nomeArquivo.endsWith(".pdf")) nomeArquivo += ".pdf";
        File arquivo = new File(nomeArquivo);

        String texto = leitorPdfServices.executar(arquivo);

        if(texto.startsWith("Erro")) {
            System.out.println(texto);
            return;
        }

        ArquivoSalvo modelo = new ArquivoSalvo(nomeArquivo, 0, texto);
        String nomeJson = nomeArquivo.replace(".pdf", "");
        exportadorJsonService.salvarEmJson(modelo, nomeJson);
    }

}
