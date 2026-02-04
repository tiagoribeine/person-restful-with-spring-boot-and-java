package github.com.tiagoribeine.controllers;
import github.com.tiagoribeine.controllers.docs.BookControllerDocs;
import github.com.tiagoribeine.data.dto.BookDTO;
import github.com.tiagoribeine.services.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book/v1") //Define a URL Base
@Tag(name = "Book", description = "Endpoints for Managing Book") //Organiza e documenta os endpoints no Swagger UI - Nome da tag (aparece no menu lateral do Swagger) -
public class BookController implements BookControllerDocs {


    @Autowired //Faz com que o Spring Boot injete a instância dessa classe quando for necessário
    private BookService service; //Indica que o service desse controlador é o BookService. Esta variável receberá um BookServices
    //private BookServices service = new BookServices(); Quando não há injeção de dependências

    //Find all
    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}//Produz um Json/Lista de Json
    )
    @Override
    public ResponseEntity<Page<BookDTO>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "12") Integer size
    ){ //Retorna todas as pessoas do Service
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.findAll(pageable));
    }

    //Find by Id
    @GetMapping(
            value = "/{id}", //URL
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE} //Produz um Json
    )

    @Override
    public BookDTO findById( //Retorna uma pessoa com Id específico do Service
                               @PathVariable("id") Long id
    ){
        return service.findById(id);
    }

    //Create
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
    public BookDTO create(@RequestBody BookDTO book){ //Cria pessoa(instancia de Book) no Service
        return service.create(book);
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
    public BookDTO update(@RequestBody BookDTO book) {
        return service.update(book);
    }

    //Delete
    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build(); //Retorna o status Code 204
    }
}


