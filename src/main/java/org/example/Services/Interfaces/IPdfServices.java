package org.example.Services.Interfaces;

import org.example.Model.ArquivoSalvo;

import java.io.File;
import java.io.IOException;

public interface IPdfServices {

    ArquivoSalvo executar(File arquivo);

}
