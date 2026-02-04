package github.com.tiagoribeine.controllers;

import github.com.tiagoribeine.controllers.docs.PersonControllerDocs;
import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.file.exporter.MediaTypes;
import github.com.tiagoribeine.services.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

// @CrossOrigin(origins = "http://localhost:8080") //Dominio da API - Se não for específicado, será permitido o acesso de todos os locais desde que esteja autenticado
@RestController
@RequestMapping("/api/person/v1") //Define a URL Base
@Tag(name = "People", description = "Endpoints for Managing People") //Organiza e documenta os endpoints no Swagger UI - Nome da tag (aparece no menu lateral do Swagger) -
public class PersonController implements PersonControllerDocs {


    @Autowired //Faz com que o Spring Boot injete a instância dessa classe quando for necessário
    private PersonService service; //Indica que o service desse controlador é o PersonService. Esta variável receberá um PersonServices
    //private PersonServices service = new PersonServices(); Quando não há injeção de dependências

    //Find all
    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}//Produz um Json/Lista de Json
    )
    @Override
    public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC: Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    //Export
    @GetMapping(value = "/exportPage",
            produces = {
                    MediaTypes.APPLICATION_CSV_VALUE,
                    MediaTypes.APPLICATION_XLSX_VALUE,
                    MediaTypes.APPLICATION_PDF_VALUE
                    })
    @Override
    public ResponseEntity<Resource> exportPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            HttpServletRequest request
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC: Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);

        //determinando o ContentType
        Resource file = service.exportPage(pageable, acceptHeader);

        Map<String, String> extensionMap = Map.of(
          MediaTypes.APPLICATION_XLSX_VALUE, ".xlsx",
          MediaTypes.APPLICATION_CSV_VALUE, ".csv",
          MediaTypes.APPLICATION_PDF_VALUE, ".pdf"
        );

        var fileExtension = extensionMap.getOrDefault(acceptHeader, "");
        var contentType = acceptHeader !=null ?acceptHeader : "application/octet-stream";
        var fileName = "people_exported" + fileExtension;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; fileName=\"" + fileName + "\"")
                .body(file);
    }

    //Find by Name
    @GetMapping(value = "/findPeopleByName/{firstName}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}//Produz um Json/Lista de Json
    )
    @Override
    public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findByName(
            @PathVariable("firstName") String firstName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC: Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return ResponseEntity.ok(service.findByName(firstName, pageable));
    }


    //Find by Id
    //@CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(
            value = "/{id}", //URL
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE} //Produz um Json
    )
    @Override
    public PersonDTO findById( //Retorna uma pessoa com Id específico do Service
                               @PathVariable("id") Long id
    ){
        return service.findById(id);
    }

    @GetMapping(
            value = "/export/{id}", //URL
            produces = {
                MediaType.APPLICATION_PDF_VALUE}
    )
    @Override
    public ResponseEntity<Resource> export(Long id, HttpServletRequest request) {
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);

        //determinando o ContentType
        Resource file = service.exportPerson(id, acceptHeader);

        Map<String, String> extensionMap = Map.of(
                MediaTypes.APPLICATION_PDF_VALUE, ".pdf"
        );

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(acceptHeader))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; fileName=person.pdf")
                .body(file);
    }

    //Create
//@CrossOrigin(origins = {"http://localhost:8080", "https://www.erudio.com.br"})
    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}, //Consome Application Json
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE} // Produz Application Json Value
    )

    @Override
    public PersonDTO create(@RequestBody PersonDTO person){ //Cria pessoa(instancia de Person) no Service
        return service.create(person);
    }

    //@CrossOrigin(origins = {"http://localhost:8080", "https://www.erudio.com.br"})
    @PostMapping(value = "/massCreation",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE} // Produz Application Json Value
    )
    @Override
    public List<PersonDTO> massCreation(@RequestParam("file") MultipartFile file){ //Cria pessoa(instancia de Person) no Service
        return service.massCreation(file);
    }

    //Update
    @PutMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}, //Consome Application Json
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE} // Produz Application Json Value
    )
    @Override
    public PersonDTO update(@RequestBody PersonDTO person) {
        return service.update(person);
    }

    @PatchMapping(
            value = "/{id}", //URL
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE} //Produz um Json
    )
    @Override
    public PersonDTO disablePerson(@PathVariable("id") Long id) {
        return service.disablePerson(id);
    }

    //Delete
    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build(); //Retorna o status Code 204
    }
}


