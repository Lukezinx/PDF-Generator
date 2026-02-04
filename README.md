# 📄 Gerador e Manipulador de PDF (CLI)

![Java](https://img.shields.io/badge/Java-17-orange)
![Apache PDFBox](https://img.shields.io/badge/Apache%20PDFBox-3.0.6-red)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![License](https://img.shields.io/badge/License-Apache%202.0-green)

Uma aplicação de linha de comando (CLI) robusta desenvolvida em **Java** para a geração, leitura e conversão de documentos PDF. O projeto utiliza **Apache PDFBox** para manipulação de arquivos e foca em conceitos sólidos de **Orientação a Objetos**.

---

## 🚀 Funcionalidades

O sistema oferece três fluxos principais de operação via terminal:

### 1. Geração de PDF 📝
Cria arquivos PDF a partir de entrada de texto manual ou colada no console.
- **Paginação Automática:** Algoritmo inteligente que detecta o limite da página e cria novas páginas automaticamente.
- **Quebra de Linha:** Calcula a largura da fonte para evitar cortes abruptos de palavras.
- **Limpeza de Texto:** Normalização de caracteres e formatação.

### 2. Leitura de PDF 🔍
Extrai o conteúdo textual de arquivos PDF existentes.
- **Metadados:** Exibe contagem real de páginas e tamanho do texto.
- **Preview:** Mostra uma amostra do conteúdo no console para evitar poluição visual com arquivos grandes.

### 3. Conversão para JSON 💾
Exporta o conteúdo do PDF para um arquivo estruturado `.json`.
- **Integridade de Dados:** Garante que o número de páginas e o texto extraído sejam salvos corretamente em um objeto JSON.
- **DTO:** Utiliza objetos de transferência de dados (`ArquivoSalvo`) para garantir consistência.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17:** Linguagem principal.
- **Apache PDFBox 3.0.6:** Motor de manipulação de documentos PDF.
- **Maven:** Gerenciamento de dependências e build.
- **SLF4J:** Sistema de logs para monitoramento e debug.
- **Gson:**  Para serialização de objetos.

---

## 🏗️ Arquitetura e Aprendizados

Este projeto foi desenvolvido com foco em boas práticas de engenharia de software:

* **Separação de Responsabilidades:** A camada de visão (`View`) é totalmente desacoplada da lógica de negócios (`Services`).
* **Contratos e Interfaces:** Uso de `IPdfServices` para padronizar as operações.
* **Tratamento de Exceções:** Exceções personalizadas (`ErroGeracaoPdfException`, `ErroLeituraPdfException`) para um fluxo de erro controlado.
* **Modelagem de Domínio:** Uso de classes como `ArquivoSalvo` para evitar o uso de tipos primitivos soltos e garantir a integridade da informação (ex: número de páginas).

---

## 📦 Como Rodar o Projeto

### Pré-requisitos
- Java JDK 17+
- Maven instalado


📄 Licença
Este projeto está licenciado sob a licença Apache 2.0 - veja o arquivo LICENSE para mais detalhes.

Desenvolvido por Lucas dos Santos https://www.linkedin.com/in/lucas-luke/
