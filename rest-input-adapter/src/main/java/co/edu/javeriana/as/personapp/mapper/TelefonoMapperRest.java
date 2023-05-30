package co.edu.javeriana.as.personapp.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.model.request.TelefonoRequest;
import co.edu.javeriana.as.personapp.model.response.TelefonoResponse;

@Mapper
public class TelefonoMapperRest
{
    public TelefonoResponse fromDomainToAdapterRestMaria(Phone phone) {
        return fromDomainToAdapterRest(phone, "MariaDB");
    }
    public TelefonoResponse fromDomainToAdapterRestMongo(Phone phone) {
        return fromDomainToAdapterRest(phone, "MongoDB");
    }

    public TelefonoResponse fromDomainToAdapterRest(Phone phone, String database) {
        return new TelefonoResponse(
                phone.getNumber()+"",
                phone.getCompany()+"",
                phone.getOwner().getIdentification(),
                database,
                "OK");
    }

    public Phone fromAdapterToDomain(TelefonoRequest request) {
        // TODO Auto-generated method stub
        return new Phone();
    }
}
