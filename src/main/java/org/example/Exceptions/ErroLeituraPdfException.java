package org.example.Exceptions;

public class ErroLeituraPdfException extends PdfException {
    public ErroLeituraPdfException(String msg, Throwable causa) {
        super(msg, causa);
    }
}
