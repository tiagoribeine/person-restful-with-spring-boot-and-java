package github.com.tiagoribeine.controllers;
import github.com.tiagoribeine.controllers.docs.PersonControllerDocs;
import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.services.services.PersonServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @CrossOrigin(origins = "http://localhost:8080") //Dominio da API - Se não for específicado, será permitido o acesso de todos os locais desde que esteja autenticado
@RestController
@RequestMapping("/api/person/v1") //Define a URL Base
@Tag(name = "People", description = "Endpoints for Managing People") //Organiza e documenta os endpoints no Swagger UI - Nome da tag (aparece no menu lateral do Swagger) -
public class PersonController implements PersonControllerDocs {


    @Autowired //Faz com que o Spring Boot injete a instância dessa classe quando for necessário
    private PersonServices service; //Indica que o service desse controlador é o PersonService. Esta variável receberá um PersonServices
    //private PersonServices service = new PersonServices(); Quando não há injeção de dependências

    //Find all
    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}//Produz um Json/Lista de Json
    )
    @Override
    public List<PersonDTO> findAll(){ //Retorna todas as pessoas do Service
        return service.findAll();
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

    //Delete
    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build(); //Retorna o status Code 204
    }
}


