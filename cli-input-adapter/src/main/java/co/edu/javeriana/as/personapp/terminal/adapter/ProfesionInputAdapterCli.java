package co.edu.javeriana.as.personapp.terminal.adapter;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.javeriana.as.personapp.mariadb.entity.ProfesionEntity;
import co.edu.javeriana.as.personapp.mariadb.mapper.ProfesionMapperMaria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import co.edu.javeriana.as.personapp.domain.Profesion;
import co.edu.javeriana.as.personapp.application.port.in.ProfesionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.ProfesionUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.terminal.mapper.ProfesionMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Adapter
public class ProfesionInputAdapterCli
{
    @Autowired
    @Qualifier("profesionOutputAdapterMaria")
    private ProfesionOutputPort profesionOutputPortMaria;

    @Autowired
    @Qualifier("profesionOutputAdapterMongo")
    private ProfesionOutputPort profesionOutputPortMongo;

    @Autowired
    private ProfesionMapperCli profesionMapperCli;

    ProfesionInputPort profesionInputPort;

    public void setProfesionOutputPortInjection(String dbOption) throws InvalidOptionException
    {
        if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
            profesionInputPort = new ProfesionUseCase(profesionOutputPortMaria);
        } else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
            profesionInputPort = new ProfesionUseCase(profesionOutputPortMongo);
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public void historial1()
    {
        log.info("Info historial ProfesionEntity in Input Adapter");
        List<ProfesionModelCli> profesion  = profesionInputPort.findAll().stream().map(profesionMapperCli::fromDomainToAdapterCli)
                .collect(Collectors.toList());
        profesion.forEach(p -> System.out.println(p.toString()));
    }
    public void historial() {
        log.info("Info historial ProfesionEntity in Input Adapter");
        profesionInputPort.findAll().stream()
                .map(profesionMapperCli::fromDomainToAdapterCli)
                .forEach(System.out::println);
    }

    public void crearP(int idn,String nombre,String des)
    {
        log.info("ProfesionEntity Create in Input Adapter");
        ProfesionEntity nueva = new ProfesionEntity(idn,nombre,des);
        ProfesionMapperMaria map = new ProfesionMapperMaria();
        profesionInputPort.create(map.fromAdapterToDomain(nueva));

    }

    public void borrarP(int cc)
    {
        log.info("ProfesionEntity Delete in Input Adapter");
        try{
            profesionInputPort.drop(cc);
        }catch (Exception e){
            System.out.println("No existe cc en la base de datos");
        }
    }

    public Profesion find(int cc)
    {
        Profesion p= new Profesion();
        try
        {
            return profesionInputPort.findOne(cc);
        }catch (Exception e)
        {
            System.out.println("No existe el idn en la base de datos");
        }
        return p;
    }

    public void updateP(int idn, Profesion profesion)
    {
        try
        {
            profesionInputPort.edit(idn,profesion);
        }catch (Exception e)
        {
            System.out.println("");
        }

    }
}



