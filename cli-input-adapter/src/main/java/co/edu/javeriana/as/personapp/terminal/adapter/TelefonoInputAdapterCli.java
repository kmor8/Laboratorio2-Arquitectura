package co.edu.javeriana.as.personapp.terminal.adapter;


import co.edu.javeriana.as.personapp.application.port.in.PhoneInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PhoneUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.TelefonoEntity;
import co.edu.javeriana.as.personapp.mariadb.mapper.TelefonoMapperMaria;
import co.edu.javeriana.as.personapp.terminal.mapper.TelefonoMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.TelefonoModelCli;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Adapter
public class TelefonoInputAdapterCli {
    @Autowired
    @Qualifier("phoneOutputAdapterMaria")
    private PhoneOutputPort phoneOutputPortMaria;

    @Autowired
    @Qualifier("phoneOutputAdapterMongo")
    private PhoneOutputPort phoneOutputPortMongo;

    @Autowired
    private TelefonoMapperCli telefonoMapperCli;

    PhoneInputPort phoneInputPort;

    public void setPhoneOutputPortInjection(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
            phoneInputPort = new PhoneUseCase(phoneOutputPortMaria);
        } else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
            phoneInputPort = new PhoneUseCase(phoneOutputPortMongo);
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public void historial1() {
        log.info("Info historial TelefonoEntity in Input Adapter");
        List<TelefonoModelCli> telefono = phoneInputPort.findAll().stream().map(telefonoMapperCli::fromDomainToAdapterCli)
                .collect(Collectors.toList());
        telefono.forEach(p -> System.out.println(p.toString()));
    }
    public void historial() {
        log.info("Info historial TelefonoEntity in Input Adapter");
        phoneInputPort.findAll().stream()
                .map(telefonoMapperCli::fromDomainToAdapterCli)
                .forEach(System.out::println);
    }


    public void crearT(String num, String oper, PersonaEntity duenio)
    {
        log.info("TelefonoEntity Create in Input Adapter");
        TelefonoEntity nueva = new TelefonoEntity(num,oper,duenio);
        TelefonoMapperMaria map = new TelefonoMapperMaria();
        phoneInputPort.create(map.fromAdapterToDomain(nueva));

    }


    public void borrarT(String cc)
    {
        log.info("TelefonoEntity Delete in Input Adapter");
        try{
            phoneInputPort.drop(cc);
        }catch (Exception e){
            System.out.println("No existe cc en la base de datos");
        }
    }

    public Phone find(String num)
    {
        Phone p= new Phone();
        try
        {
            return phoneInputPort.findOne(num);
        }catch (Exception e)
        {
            System.out.println("No existe el cc en la base de datos");
        }
        return p;
    }

    public void updateT(String num, Phone telefono)
    {
        try
        {
            phoneInputPort.edit(num,telefono);
        }catch (Exception e)
        {

        }

    }

}
