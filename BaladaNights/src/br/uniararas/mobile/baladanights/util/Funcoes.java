package br.uniararas.mobile.baladanights.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Funcoes {
	
	/**
	 * Função para convertar straem em uma string de sistema
	 * @param stream
	 * @return 
	 * @throws IOException
	 */
	public static String lerResposta(InputStream stream) throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int tamanhoido = 0;

        while ((tamanhoido = stream.read(buffer) ) > 0) {
            //Passar o conteudo lido para o outiput stream
            outputStream.write(buffer, 0, tamanhoido);
        }

        String valorTextual = new String(outputStream.toByteArray());
        return  valorTextual;

    }

}
