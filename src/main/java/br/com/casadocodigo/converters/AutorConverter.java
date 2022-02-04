package br.com.casadocodigo.converters;


import br.com.casadocodigo.models.Autor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

//Pegar o tipo do autor como objto não só como id
@FacesConverter("autorConverter")
public class AutorConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String id) {
        if(id == null || id.trim().isEmpty())
            return null; //Tratamento de erro para id nulo ou vazio
        System.out.println("Convertendo parar Object" +id);
        Autor autor = new Autor();
        autor.setId(Integer.valueOf(id));
        return autor;
    }//String->Objeto

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object autorObject) {
        if(autorObject == null)
            return null; //Tratamento de erro para id nulo ou vazio
        System.out.println("Convertendo parar String" +autorObject);
        Autor autor = (Autor) autorObject;
        return autor.getId().toString();//a string desejada é só o id e convertelo para string
    }//Objeto->String(Para a view)
}
