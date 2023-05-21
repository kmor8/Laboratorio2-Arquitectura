package co.edu.javeriana.as.personapp.mariadb.repository;
import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.javeriana.as.personapp.mariadb.entity.ProfesionEntity;


public interface ProfesionRepositoryMaria extends JpaRepository<ProfesionEntity,Integer> {
}
