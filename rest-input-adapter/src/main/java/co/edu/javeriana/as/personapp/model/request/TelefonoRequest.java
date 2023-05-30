package co.edu.javeriana.as.personapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.transform.sax.SAXResult;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoRequest
{
    private String num;
    private String oper;
    private Integer duenio;
    private String database;
}
