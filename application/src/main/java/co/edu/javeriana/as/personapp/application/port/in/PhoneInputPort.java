package co.edu.javeriana.as.personapp.application.port.in;

import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Phone;

import java.util.List;

@Port
public interface PhoneInputPort {
    public void setPersintence(PhoneOutputPort phonePersintence);

    public Phone create(Phone person);

    public Phone edit(String num, Phone phone) throws NoExistException;

    public Boolean drop(String num) throws NoExistException;

    public List<Phone> findAll();

    public Phone findOne(String num) throws NoExistException;

    public Integer count();


}
