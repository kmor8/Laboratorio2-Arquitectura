package co.edu.javeriana.as.personapp.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import co.edu.javeriana.as.personapp.application.port.in.ProfesionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.ProfesionUseCase;
import co.edu.javeriana.as.personapp.domain.Profesion;
import co.edu.javeriana.as.personapp.mapper.ProfesionMapperRest;
import co.edu.javeriana.as.personapp.model.request.ProfesionRequest;
import co.edu.javeriana.as.personapp.model.response.ProfesionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class ProfesionInputAdapterRest
{
    @Autowired
    @Qualifier("profesionOutputAdapterMaria")
    private ProfesionOutputPort profesionOutputPortMaria;

    @Autowired
    @Qualifier("profesionOutputAdapterMongo")
    private ProfesionOutputPort profesionOutputPortMongo;

    @Autowired
    private ProfesionMapperRest profesionMapperRest;

    ProfesionInputPort profesionInputPort;

    private String setProfesionOutputPortInjection(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
            profesionInputPort = new ProfesionUseCase(profesionOutputPortMaria);
            return DatabaseOption.MARIA.toString();
        } else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
            profesionInputPort = new ProfesionUseCase(profesionOutputPortMongo);
            return  DatabaseOption.MONGO.toString();
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public List<ProfesionResponse> historial(String database) {
        log.info("Info historial ProfesionEntity in Input Adapter");
        try {
            if(setProfesionOutputPortInjection(database).equalsIgnoreCase(DatabaseOption.MARIA.toString())){
                return profesionInputPort.findAll().stream().map(profesionMapperRest::fromDomainToAdapterRestMaria)
                        .collect(Collectors.toList());
            }else {
                return profesionInputPort.findAll().stream().map(profesionMapperRest::fromDomainToAdapterRestMongo)
                        .collect(Collectors.toList());
            }

        } catch (InvalidOptionException e) {
            log.warn(e.getMessage());
            return new ArrayList<ProfesionResponse>();
        }
    }

    public ProfesionResponse crearProfesion(ProfesionRequest request) {
        try {
            setProfesionOutputPortInjection(request.getDatabase());
            Profesion profesion = profesionInputPort.create(profesionMapperRest.fromAdapterToDomain(request));
            return profesionMapperRest.fromDomainToAdapterRestMaria(profesion);
        } catch (InvalidOptionException e) {
            log.warn(e.getMessage());
            //return new PersonaResponse("", "", "", "", "", "", "");
        }
        return null;
    }
}
