package co.edu.javeriana.as.personapp.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Profesion;
import co.edu.javeriana.as.personapp.model.request.ProfesionRequest;
import co.edu.javeriana.as.personapp.model.response.ProfesionResponse;

@Mapper
public class ProfesionMapperRest
{
    public ProfesionResponse fromDomainToAdapterRestMaria(Profesion profesion) {
        return fromDomainToAdapterRest(profesion, "MariaDB");
    }
    public ProfesionResponse fromDomainToAdapterRestMongo(Profesion profesion) {
        return fromDomainToAdapterRest(profesion, "MongoDB");
    }

    public ProfesionResponse fromDomainToAdapterRest(Profesion profesion, String database) {
        return new ProfesionResponse(
                profesion.getIdentification()+"",
                profesion.getName(),
                profesion.getDescription(),
                database,
                "OK");
    }

    public Profesion fromAdapterToDomain(ProfesionRequest request) {
        // TODO Auto-generated method stub
        return new Profesion();
    }
}
