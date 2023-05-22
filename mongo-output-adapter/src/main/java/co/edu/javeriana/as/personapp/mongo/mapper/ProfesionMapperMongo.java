package co.edu.javeriana.as.personapp.mongo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Profesion;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument;
import co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument;

@Mapper
public class ProfesionMapperMongo {
	
	@Autowired
	private EstudiosMapperMongo estudiosMapperMongo;

	public ProfesionDocument fromDomainToAdapter(Profesion profesion) {
		ProfesionDocument profesionDocument = new ProfesionDocument();
		profesionDocument.setId(profesion.getIdentification());
		profesionDocument.setNom(profesion.getName());
		profesionDocument.setDes(validateDes(profesion.getDescription()));
		profesionDocument.setEstudios(validateEstudios(profesion.getStudies()));
		return profesionDocument;
	}

	private String validateDes(String description) {
		return description != null ? description : "";
	}

	private List<EstudiosDocument> validateEstudios(List<Study> studies) {
		return studies != null && !studies.isEmpty() ? studies.stream()
				.map(study -> estudiosMapperMongo.fromDomainToAdapter(study)).collect(Collectors.toList())
				: new ArrayList<EstudiosDocument>();
	}

	public Profesion fromAdapterToDomain(ProfesionDocument profesionDocument) {
		Profesion profesion = new Profesion();
		profesion.setIdentification(profesionDocument.getId());
		profesion.setName(profesionDocument.getNom());
		profesion.setDescription(validateDescription(profesionDocument.getDes()));
		profesion.setStudies(validateStudies(profesionDocument.getEstudios()));
		return profesion;
	}

	private String validateDescription(String des) {
		return des != null ? des : "";
	}

	private List<Study> validateStudies(List<EstudiosDocument> estudiosDocument) {
		return estudiosDocument != null && !estudiosDocument.isEmpty() ? estudiosDocument.stream()
				.map(estudio -> estudiosMapperMongo.fromAdapterToDomain(estudio)).collect(Collectors.toList())
				: new ArrayList<Study>();
	}
}
