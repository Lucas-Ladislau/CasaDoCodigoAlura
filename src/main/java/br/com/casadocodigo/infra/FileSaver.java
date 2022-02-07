package br.com.casadocodigo.infra;

import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;

//Classe para salvar arquivos e obter o caminho relativo do Bd
public class FileSaver {
    public static final String SERVER_PATH = "/casadocodigo";

    public static void transfer(Path source, OutputStream outputStream) {//manipulando o arquivo
        try {//necessário inserir o arquivo no sistema para depois utilizar
            FileInputStream input = new FileInputStream(source.toFile());
            try (ReadableByteChannel inputChannel = Channels.newChannel(input);
                    WritableByteChannel outputChannel = Channels.newChannel(outputStream)){
                ByteBuffer buffer = ByteBuffer.allocateDirect(1024 *10);

                while(inputChannel.read(buffer) != -1){
                    buffer.flip();
                    outputChannel.write(buffer);
                    buffer.clear();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String write(Part arquivo,String path) {
        String relativePath =  path + "/" + arquivo.getSubmittedFileName();//caminho que vai para o BD
        try {
            //        nomedoobjeto.write é uma método do tipo part que salva o arquivo em um diretório
            arquivo.write(SERVER_PATH + "/" + relativePath);
            return relativePath;
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
