package github.com.tiagoribeine.controllers;
import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person") //Define a URL Base
public class PersonController {


    @Autowired //Faz com que o Spring Boot injete a instância dessa classe quando for necessário
    private PersonServices service; //Indica que o service desse controlador é o PersonService. Esta variável receberá um PersonServices
    //private PersonServices service = new PersonServices(); Quando não há injeção de dependências

    //Find all
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE //Produz um Json/Lista de Json
    )
    public List<PersonDTO> findAll(){ //Retorna todas as pessoas do Service
        return service.findAll();
    }

    //Find by Id
    @GetMapping(
            value = "/{id}", //URL
            produces = MediaType.APPLICATION_JSON_VALUE //Produz um Json
    )
    public PersonDTO findById( //Retorna uma pessoa com Id específico do Service
                               @PathVariable("id") Long id
    ){
        return service.findById(id);
    }

    //Create
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE, //Consome Application Json
            produces = MediaType.APPLICATION_JSON_VALUE // Produz Application Json Value
    )
    public PersonDTO create(@RequestBody PersonDTO person){ //Cria pessoa(instancia de Person) no Service
        return service.create(person);
    }

    //Update
    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE, //Consome Application Json
            produces = MediaType.APPLICATION_JSON_VALUE // Produz Application Json Value
    )
    public PersonDTO update(@RequestBody PersonDTO person) {
        return service.update(person);
    }

    //Delete
    @DeleteMapping(value = "/{id}") //URL
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build(); //Retorna o status Code 204
    }
}


