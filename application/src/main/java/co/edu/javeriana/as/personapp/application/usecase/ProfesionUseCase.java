package co.edu.javeriana.as.personapp.application.usecase;
import java.util.List;

import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.domain.Profesion;
import org.springframework.beans.factory.annotation.Qualifier;
import co.edu.javeriana.as.personapp.application.port.in.ProfesionInputPort;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
public class ProfesionUseCase implements ProfesionInputPort
{
    private ProfesionOutputPort profesionPersistence;

    public ProfesionUseCase(@Qualifier("professionOutputAdapterMaria") ProfesionOutputPort profesionPersistence) {
        this.profesionPersistence=profesionPersistence;
    }

    @Override
    public void setPersintence(ProfesionOutputPort professionPersintence)
    {
        this.profesionPersistence=professionPersintence;
    }

    @Override
    public Profesion create(Profesion profession)
    {
        log.debug("Into create on Application Domain");
        return profesionPersistence.save(profession);
    }

    @Override
    public Profesion edit(Integer id, Profesion profession) throws NoExistException {
        Profesion oldProfession = profesionPersistence.findById(id);
        if (oldProfession != null)
            return profesionPersistence.save(profession);
        throw new NoExistException(
                "The person with id " + id + " does not exist into db, cannot be edited");
    }

    @Override
    public Boolean drop(Integer id) throws NoExistException {
        Profesion oldProfession = profesionPersistence.findById(id);
        if (oldProfession != null)
            return profesionPersistence.delete(id);
        throw new NoExistException(
                "The person with id " + id + " does not exist into db, cannot be dropped");
    }

    @Override
    public List<Profesion> findAll() {
        log.info("Output: " + profesionPersistence.getClass());
        return profesionPersistence.find();
    }

    @Override
    public Profesion findOne(Integer id) throws NoExistException {
        Profesion oldProfesion = profesionPersistence.findById(id);
        if (oldProfesion != null)
            return oldProfesion;
        throw new NoExistException("The person with id " + id + " does not exist into db, cannot be found");
    }

    @Override
    public Integer count() {
        return findAll().size();
    }


}
