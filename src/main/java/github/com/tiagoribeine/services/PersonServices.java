package github.com.tiagoribeine.services;
import static github.com.tiagoribeine.mapper.ObjectMapper.parseListObjects;
import static github.com.tiagoribeine.mapper.ObjectMapper.parseObject;
import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.exception.ResourceNotFoundException;
import github.com.tiagoribeine.model.Person;
import github.com.tiagoribeine.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


// Aqui teremos Operações para cadastrar uma pessoa

@Service //Marca a classe como Serviço gerenciado pelo Spring. Faz com que fique disponível e seja injetado onde necessário
public class PersonServices {

    private final AtomicLong counter = new AtomicLong(); // Gera IDs únicos thread-safe
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName()); //Cria um logger(gravador) específico para a classe PersonServices;


    @Autowired
    PersonRepository repository; //Spring insere o repository no Service, permitindo conexão com o banco de dados

    // Busca TODAS as pessoas
    public List<PersonDTO> findAll(){
        logger.info("Finding all people!"); //Registra uma operação normal do sistema a nivel informativo. Exibe a info no console/terminal
        return parseListObjects(repository.findAll(), PersonDTO.class); //Utilizando o metodo do ObjectMapper para converter em uma DTO
    }

    //Busca uma pessoa por ID
    public PersonDTO findById(Long id) {
        logger.info("Finding one Person");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String
        return parseObject(entity, PersonDTO.class); //Utilizando o metodo do ObjectMapper para converter em uma DTO
    }

    public PersonDTO create(PersonDTO person){ //

        logger.info("Creating one Person!"); //Registra uma operação normal do sistema a nivel informativo.
        var entity = parseObject(person, Person.class);

        return parseObject(repository.save(entity), PersonDTO.class); //Salva e ja retorna ao controller
    }

    public PersonDTO update(PersonDTO person){

        logger.info("Updating One Person!"); //Registra uma operação normal do sistema a nivel informativo.

        Person entity = repository.findById(person.getId()) //Recuperamos a entidade pelo id fornecido pelo client. São dados que ja estão no banco
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String

        //Usando setters para alterar os parâmetros de entity
        entity.setFirstName(person.getFirstName()); //person foi fornecida pelo Client
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!"); //Registra uma operação normal do sistema a nivel informativo.
        Person entity = repository.findById(id) //Recuperamos a entidade pelo id fornecido pelo client. São dados que ja estão no banco
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String
        repository.delete(entity);
    }
}