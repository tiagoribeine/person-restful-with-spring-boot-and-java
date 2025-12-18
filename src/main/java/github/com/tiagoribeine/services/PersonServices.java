package github.com.tiagoribeine.services.services;
import static github.com.tiagoribeine.mapper.ObjectMapper.parseListObjects;
import static github.com.tiagoribeine.mapper.ObjectMapper.parseObject;

import github.com.tiagoribeine.controllers.PersonController;
import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.exception.RequiredObjectIsNullException;
import github.com.tiagoribeine.exception.ResourceNotFoundException;
import github.com.tiagoribeine.model.Person;
import github.com.tiagoribeine.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

        //Implementando o HATEOAS - find All
        var persons = parseListObjects(repository.findAll(), PersonDTO.class); //Utilizando o metodo do ObjectMapper para converter em uma DTO
        persons.forEach(this::addHateoasLinks);
        return persons;
    }

    //Busca uma pessoa por ID
    public PersonDTO findById(Long id) {
        logger.info("Finding one Person");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String

        //Implementando HATEOAS - findById
        var dto = parseObject(entity, PersonDTO.class); //Utilizando o metodo do ObjectMapper para converter em uma DTO
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO create(PersonDTO person){

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person!"); //Registra uma operação normal do sistema a nivel informativo.
        var entity = parseObject(person, Person.class);

        // Implementando o HATEOAS - create
        var dto =  parseObject(repository.save(entity), PersonDTO.class); //Salva e ja retorna ao controller
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO update(PersonDTO person){

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating One Person!"); //Registra uma operação normal do sistema a nivel informativo.
        Person entity = repository.findById(person.getId()) //Recuperamos a entidade pelo id fornecido pelo client. São dados que ja estão no banco
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String

        //Usando setters para alterar os parâmetros de entity
        entity.setFirstName(person.getFirstName()); //person foi fornecida pelo Client
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        // Implementando o HATEOAS - update
        var dto =  parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!"); //Registra uma operação normal do sistema a nivel informativo.
        Person entity = repository.findById(id) //Recuperamos a entidade pelo id fornecido pelo client. São dados que ja estão no banco
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String
        repository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {

        // find by Id
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        // find All
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        // Create
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        // UPDATE
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("update").withType("PUT"));
        // DELETE
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}