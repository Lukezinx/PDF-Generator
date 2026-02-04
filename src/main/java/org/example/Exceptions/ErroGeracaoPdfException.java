package org.example.Exceptions;

public class ErroGeracaoPdfException extends PdfException {
    public ErroGeracaoPdfException(String msg, Throwable causa) {
        super(msg, causa);
    }
}
