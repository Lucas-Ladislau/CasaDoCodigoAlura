package br.com.casadocodigo.servlets;

import br.com.casadocodigo.infra.FileSaver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//Classe para poder exibir a informação da imagem[
//para o server poder reconhecer o caminho
@WebServlet("/file/*")
public class FileServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String path = req.getRequestURI().split("/file")[1];

        Path source = Paths.get(FileSaver.SERVER_PATH + "/" + path);
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contextType = fileNameMap.getContentTypeFor("File:"+ source);

        res.reset();
        res.setContentType(contextType);
        res.setHeader("Content-Length", String.valueOf(Files.size(source)));
        res.setHeader("Content-Disposition", "filename=\""+ source.getFileName().toString() +"\"");
        FileSaver.transfer(source, res.getOutputStream());
// transferir o arquivo para dentro do response (res)
    }
}
