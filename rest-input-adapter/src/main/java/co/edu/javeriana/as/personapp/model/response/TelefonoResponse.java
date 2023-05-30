package co.edu.javeriana.as.personapp.model.response;


import co.edu.javeriana.as.personapp.model.request.TelefonoRequest;
import lombok.NonNull;

public class TelefonoResponse extends TelefonoRequest {

    private String status;

    public TelefonoResponse(String num, String operador, Integer duenio, String database, String status) {
        super(num,operador,duenio,database);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
