package co.edu.javeriana.as.personapp.application.port.in;

import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Profesion;

import java.util.List;


@Port
public interface ProfesionInputPort
{
    public void setPersintence(ProfesionOutputPort personPersintence);

    public Profesion create(Profesion profession);

    public Profesion edit(Integer id, Profesion profession) throws NoExistException;

    public Boolean drop(Integer identification) throws NoExistException;

    public List<Profesion> findAll();

    public Profesion findOne(Integer identification) throws NoExistException;

    public Integer count();

    // este para la lista de estudios cuando la haga
    //public List<Phone> getPhones(Integer identification) throws NoExistException;

}
