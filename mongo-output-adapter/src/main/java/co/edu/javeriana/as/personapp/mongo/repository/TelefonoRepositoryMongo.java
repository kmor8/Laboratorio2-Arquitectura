package co.edu.javeriana.as.personapp.mongo.repository;

import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.security.SecureRandom;
import java.util.List;

public interface TelefonoRepositoryMongo extends MongoRepository<TelefonoDocument,String> {
    void deleteByNum(String num);
}
