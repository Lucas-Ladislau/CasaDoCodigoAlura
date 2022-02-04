package br.com.casadocodigo.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@FacesConverter(forClass = Calendar.class)
public class CalendarConverter implements Converter {

    private DateTimeConverter converter = new DateTimeConverter();

    public CalendarConverter (){
        converter.setPattern("dd/MM/yyyy");
        converter.setTimeZone(TimeZone.getTimeZone("America/Boa_Vista"));//talvez seja necessário colo Sao_Paulo
    }//definir o formato da data como dd/mm/aaaa

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String dataTexto) {
        Date data = (Date) converter.getAsObject(context, component, dataTexto);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        return calendar;
    }//View-> ManagedBean(String para Objeto)

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object dataObject) {
        if (dataObject == null)
            return null;
        Calendar calendar = (Calendar) dataObject; //fazer um cast para garantir que seja do tipo calendar
        return converter.getAsString(context,component, calendar.getTime());

    }//ManagedBean-> View(Objeto para String)
}//Conversao de calendar para String
