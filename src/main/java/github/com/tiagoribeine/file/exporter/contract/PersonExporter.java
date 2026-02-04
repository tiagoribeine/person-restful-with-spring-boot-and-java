package github.com.tiagoribeine.file.exporter.contract;

import github.com.tiagoribeine.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface PersonExporter {

    Resource exportPeople(List<PersonDTO> people) throws Exception;

    Resource exportPerson(PersonDTO person) throws Exception;


}
