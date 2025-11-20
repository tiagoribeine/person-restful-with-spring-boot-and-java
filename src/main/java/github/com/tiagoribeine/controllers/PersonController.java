package github.com.tiagoribeine.controllers;
import github.com.tiagoribeine.PersonServices;
import github.com.tiagoribeine.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {


    @Autowired //Faz com que o Spring Boot injete a instância dessa classe quando for necessário
    private PersonServices service;
    //private PersonServices service = new PersonServices(); Quando não há injeção de dependências

    //Find all
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Person> findAll(){
        return service.findAll();
    }

    //Find by Id
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person findById(
            @PathVariable("id") String id
    ){
        return service.findById(id);
    }

    //Create
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, //Consome Application Json
            produces = MediaType.APPLICATION_JSON_VALUE // Produz Application Json Value
    )
    public Person create(@RequestBody Person person){
        return service.create(person);
    }

    //Update
    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, //Consome Application Json
            produces = MediaType.APPLICATION_JSON_VALUE // Produz Application Json Value
    )
    public Person update(@RequestBody Person person){
        return service.update(person);
    }

    //Delete
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE
    )
    public void delete(@PathVariable("id") String id)
    {
        service.delete(id);
    }



}


