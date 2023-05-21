package co.edu.javeriana.as.personapp.terminal.adapter;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity;
import co.edu.javeriana.as.personapp.mariadb.mapper.PersonaMapperMaria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.terminal.mapper.PersonaMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class PersonaInputAdapterCli {

	@Autowired
	@Qualifier("personOutputAdapterMaria")
	private PersonOutputPort personOutputPortMaria;

	@Autowired
	@Qualifier("personOutputAdapterMongo")
	private PersonOutputPort personOutputPortMongo;

	@Autowired
	private PersonaMapperCli personaMapperCli;

	PersonInputPort personInputPort;

	public void setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMaria);
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMongo);
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public void historial1() {
		log.info("Info historial PersonaEntity in Input Adapter");
		List<PersonaModelCli> persona = personInputPort.findAll().stream().map(personaMapperCli::fromDomainToAdapterCli)
					.collect(Collectors.toList());
		persona.forEach(p -> System.out.println(p.toString()));
	}
	public void historial() {
	    log.info("Info historial PersonaEntity in Input Adapter");
	    personInputPort.findAll().stream()
	        .map(personaMapperCli::fromDomainToAdapterCli)
	        .forEach(System.out::println);
	}

	public void crearP(int cc,String nombre,String apellido,Character genero, int edad)
	{
		log.info("PersonaEntity Create in Input Adapter");
		PersonaEntity nueva = new PersonaEntity(cc,nombre,apellido,genero,edad);
		PersonaMapperMaria map = new PersonaMapperMaria();
		personInputPort.create(map.fromAdapterToDomain(nueva));

	}

	public void borrarP(int cc)
	{
		log.info("PersonaEntity Delete in Input Adapter");
		try{
			personInputPort.drop(cc);
		}catch (Exception e){
			System.out.println("No existe cc en la base de datos");
		}
	}

	public Person find(int cc)
	{
		Person p= new Person();
		try
		{
			return personInputPort.findOne(cc);
		}catch (Exception e)
		{
			System.out.println("No existe el cc en la base de datos");
		}
		return p;
	}

	public void updateP(int cc, Person persona)
	{
		try
		{
			personInputPort.edit(cc,persona);
		}catch (Exception e)
		{

		}

	}

}
