package org.example.Exceptions;

public class ErroExportacaoJsonException extends PdfException {
    public ErroExportacaoJsonException(String msg, Throwable causa) {
        super(msg, causa);
    }
}
