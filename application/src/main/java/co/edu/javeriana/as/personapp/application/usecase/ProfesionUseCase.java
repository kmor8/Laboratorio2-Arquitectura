package co.edu.javeriana.as.personapp.application.usecase;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.ProfesionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Profesion;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
public class ProfesionUseCase implements ProfesionInputPort
{
    private ProfesionOutputPort profesionPersistence;

    public ProfesionUseCase(@Qualifier("profesionOutputAdapterMaria") ProfesionOutputPort profesionPersistence) {
        this.profesionPersistence=profesionPersistence;
    }

    @Override
    public void setPersintence(ProfesionOutputPort profesionPersintence)
    {
        this.profesionPersistence=profesionPersintence;
    }

    @Override
    public Profesion create(Profesion profesion)
    {
        log.debug("Into create on Application Domain");
        return profesionPersistence.save(profesion);
    }

    @Override
    public Profesion edit(Integer identification, Profesion profesion) throws NoExistException {
        Profesion oldProfesion = profesionPersistence.findById(identification);
        if (oldProfesion != null)
            return profesionPersistence.save(profesion);
        throw new NoExistException(
                "The profesion with id " + identification + " does not exist into db, cannot be edited");
    }

    @Override
    public Boolean drop(Integer identification) throws NoExistException {
        Profesion oldProfesion = profesionPersistence.findById(identification);
        if (oldProfesion != null)
            return profesionPersistence.delete(identification);
        throw new NoExistException(
                "The profesion with id " + identification + " does not exist into db, cannot be dropped");
    }

    @Override
    public List<Profesion> findAll() {
        log.info("Output: " + profesionPersistence.getClass());
        return profesionPersistence.find();
    }

    @Override
    public Profesion findOne(Integer identification) throws NoExistException {
        Profesion oldProfesion = profesionPersistence.findById(identification);
        if (oldProfesion != null)
            return oldProfesion;
        throw new NoExistException("The profesion with id " + identification + " does not exist into db, cannot be found");
    }

    @Override
    public Integer count() {
        return findAll().size();
    }


}
