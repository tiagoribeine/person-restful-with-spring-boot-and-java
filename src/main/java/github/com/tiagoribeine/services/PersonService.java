package github.com.tiagoribeine.services;

import github.com.tiagoribeine.controllers.PersonController;
import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.exception.BadRequestException;
import github.com.tiagoribeine.exception.FileStorageException;
import github.com.tiagoribeine.exception.RequiredObjectIsNullException;
import github.com.tiagoribeine.exception.ResourceNotFoundException;
import github.com.tiagoribeine.file.exporter.contract.PersonExporter;
import github.com.tiagoribeine.file.exporter.factory.FileExporterFactory;
import github.com.tiagoribeine.file.importer.contract.FileImporter;
import github.com.tiagoribeine.file.importer.factory.FileImporterFactory;
import github.com.tiagoribeine.model.Person;
import github.com.tiagoribeine.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static github.com.tiagoribeine.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// Aqui teremos Operações para cadastrar uma pessoa

@Service //Marca a classe como Serviço gerenciado pelo Spring. Faz com que fique disponível e seja injetado onde necessário
public class PersonService {

    private final AtomicLong counter = new AtomicLong(); // Gera IDs únicos thread-safe
    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName()); //Cria um logger(gravador) específico para a classe PersonServices;


    @Autowired
    PersonRepository repository; //Spring insere o repository no Service, permitindo conexão com o banco de dados

    @Autowired
    FileImporterFactory importer;
    @Autowired
    FileExporterFactory exporter;


    @Autowired
    PagedResourcesAssembler<PersonDTO> assembler;

    // Busca TODAS as pessoas
    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable){

        logger.info("Finding all people!"); //Registra uma operação normal do sistema a nivel informativo. Exibe a info no console/terminal
        var people = repository.findAll(pageable);

        return buildPagedModel(pageable, people);
    }

    public PagedModel<EntityModel<PersonDTO>> findByName(String firstName, Pageable pageable){

        logger.info("Finding people by Name!"); //Registra uma operação normal do sistema a nivel informativo. Exibe a info no console/terminal
        var people = repository.findPeopleByName(firstName, pageable);

        return buildPagedModel(pageable, people);
    }

    // Busca TODAS as pessoas
    public Resource exportPage(Pageable pageable, String acceptHeader){

        logger.info("Exporting a People page!"); //Registra uma operação normal do sistema a nivel informativo. Exibe a info no console/terminal

        //Criando a lista de pessoas
        var people = repository.findAll(pageable)
                .map(person -> parseObject(person, PersonDTO.class))
                .getContent();

        //Chamando o exportador e fornecendo a lista como parâmetro
        try {
            PersonExporter exporter = this.exporter.getExporter(acceptHeader);
            return exporter.exportPeople(people);
        } catch (Exception e) {
            throw new RuntimeException("Error during file export", e);
        }
    }

    public Resource exportPerson(Long id, String acceptHeader) {
        logger.info("Exporting data of one Person!");
        var person = repository.findById(id)
                .map( entity -> parseObject(entity, PersonDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String

        //Implementando HATEOAS - findById

        PersonExporter exporter = null;
        try {
            exporter = this.exporter.getExporter(acceptHeader);
            return exporter.exportPerson(person);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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



    // Cria a person de acordo com um arquivo importado
    public List<PersonDTO> massCreation(MultipartFile file) {

        logger.info("Importing People from file!"); //Registra uma operação normal do sistema a nivel informativo. Exibe a info no console/terminal
        if (file.isEmpty()) throw new BadRequestException("Please set a Valid File!"); //Verifica se o multipart file está preenchido

        try(InputStream inputStream = file.getInputStream()){ //Cria um InputStream e
            String filename = Optional.ofNullable(file.getOriginalFilename()) //Obtem o nome do Arquivo para saber qual instancia utilizar na factory
                    .orElseThrow(() -> new BadRequestException("File name cannot be null"));
            FileImporter importer = this.importer.getImporter(filename); //Definindo qual instância será utilizada

            List<Person> entities = importer.importFile(inputStream).stream() //Importer importa um inputStream através do CSV importer ou do XLSX importer
                    .map(dto -> repository.save(parseObject(dto, Person.class))) //retorna uma lista de DTOs - Lista de DTOs não pode ser persistida no banco - Deve ser convertido para Person antes iterando sobre os itens
                    .toList(); //Retorna tudo o que foi persistido no banco adicionando em uma lista de entidades

            return entities.stream() //Itera na lista de Person passando de entidade para DTO e adiciona os links HATEOAS, ao final adiciona uma lista e retorna
                    .map(entity -> {
                        var dto = parseObject(entity, PersonDTO.class);
                        addHateoasLinks(dto);
                        return dto;
                    }).toList();
        } catch (Exception e) {
            throw new FileStorageException("Error processing the file");
        }
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

    @Transactional
    public PersonDTO disablePerson(Long id) {

        logger.info("Disabling one person!"); //Registra uma operação normal do sistema a nivel informativo.

        repository.findById(id) //Recuperamos a entidade pelo id fornecido pelo client. São dados que ja estão no banco
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String
        repository.disablePerson(id);

        var entity = repository.findById(id).get();
        var dto =  parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one person!"); //Registra uma operação normal do sistema a nivel informativo.
        Person entity = repository.findById(id) //Recuperamos a entidade pelo id fornecido pelo client. São dados que ja estão no banco
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String
        repository.delete(entity);
    }

    private PagedModel<EntityModel<PersonDTO>> buildPagedModel(Pageable pageable, Page<Person> people) {
        var peopleWithLinks = people.map(person -> {
            //Pegando cada person e convertendo para DTO
            var dto = parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PersonController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                String.valueOf(pageable.getSort()))).withSelfRel();
        return assembler.toModel(peopleWithLinks, findAllLink);
    }

    private void addHateoasLinks(PersonDTO dto) {

        // find by Id
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        // find All
        dto.add(linkTo(methodOn(PersonController.class).findAll(1, 12,"asc" )).withRel("findAll").withType("GET"));
        // find by Name
        dto.add(linkTo(methodOn(PersonController.class).findByName("", 1, 12,"asc" )).withRel("findByName").withType("GET"));
        // Create
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        // massCreation
        dto.add(linkTo(methodOn(PersonController.class)).slash("massCreation").withRel("massCreation").withType("POST"));
        // UPDATE
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        // PATCH
        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("update").withType("PATCH"));
        // DELETE
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        // exportFiles
        dto.add(linkTo(methodOn(PersonController.class).exportPage(1, 12,"asc", null ))
                .withRel("exportPage")
                .withType("GET")
                .withTitle("Export People"));
    }
}