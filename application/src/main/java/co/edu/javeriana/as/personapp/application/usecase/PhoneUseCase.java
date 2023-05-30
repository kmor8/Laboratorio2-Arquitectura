package co.edu.javeriana.as.personapp.application.usecase;

import co.edu.javeriana.as.personapp.application.port.in.PhoneInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Slf4j
@UseCase
public class PhoneUseCase implements PhoneInputPort {
    private PhoneOutputPort phonePersintence;

    public PhoneUseCase(@Qualifier("phoneOutputAdapterMaria") PhoneOutputPort phonePersintence) {
        this.phonePersintence=phonePersintence;
    }

    @Override
    public void setPersintence(PhoneOutputPort phonePersintence) {
        this.phonePersintence=phonePersintence;
    }

    @Override
    public Phone create(Phone phone) {
        log.debug("Into create on Application Domain");
        return phonePersintence.save(phone);
    }

    @Override
    public Phone edit(String num, Phone phone) throws NoExistException {
        Phone oldPhone = phonePersintence.findByNum(num);
        if (oldPhone != null)
            return phonePersintence.save(phone);
        throw new NoExistException(
                "The phone with number " + num + " does not exist into db, cannot be edited");
    }

    @Override
    public Boolean drop(String num) throws NoExistException {
        Phone oldPerson = phonePersintence.findByNum(num);
        if (oldPerson != null)
            return phonePersintence.delete(num);
        throw new NoExistException(
                "The phone with number " + num + " does not exist into db, cannot be dropped");
    }

    @Override
    public List<Phone> findAll() {
        log.info("Output: " + phonePersintence.getClass());
        return phonePersintence.find();
    }

    @Override
    public Phone findOne(String num) throws NoExistException {
        Phone oldPhone = phonePersintence.findByNum(num);
        if (oldPhone != null)
            return oldPhone;
        throw new NoExistException("The phone with number " + num + " does not exist into db, cannot be found");
    }

    @Override
    public Integer count() {
        return findAll().size();
    }


}
