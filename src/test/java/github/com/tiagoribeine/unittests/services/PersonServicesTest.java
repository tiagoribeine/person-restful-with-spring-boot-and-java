package github.com.tiagoribeine.unittests.services;

import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.exception.RequiredObjectIsNullException;
import github.com.tiagoribeine.model.Person;
import github.com.tiagoribeine.repository.PersonRepository;
import github.com.tiagoribeine.services.services.PersonServices;
import github.com.tiagoribeine.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) //Os objetos instanciados aqui vão durar apenas para essa classe
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    //Mockando os inputs
    MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    // Implementação dos testes
    @Test
    void findById() {
        //Mocks
        Person person = input.mockEntity(1); //Uma instância
        person.setId(1L);

        //Deve retornar uma instancia de person
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        //Assertions: Verificar se o que passamos como parâmetro está realmente correto - Se o processamento do findById no service está correto
        var result = service.findById(1L); //Conferindo se o processamento feito no findById do Service realmente está correto

        //Confrindo se o que tem dentro dela realmente é o que deveria ter:

        assertNotNull(result); //Não pode vir nulo ou o teste está com problema
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                    && link.getHref().endsWith("/api/person/v1/1")
                    && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
            );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                    && link.getHref().endsWith("/api/person/v1")
                    && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
            );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                    && link.getHref().endsWith("/api/person/v1")
                    && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
            );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                    && link.getHref().endsWith("/api/person/v1")
                    && link.getType().equals("POST")
                ) //Link mudará de acordo com o ambiente
            );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                    && link.getHref().endsWith("/api/person/v1")
                    && link.getType().equals("PUT")
                ) //Link mudará de acordo com o ambiente
            );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                    && link.getHref().endsWith("/api/person/v1/1")
                    && link.getType().equals("DELETE")
                ) //Link mudará de acordo com o ambiente
            );

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void create() {
        //Mocks
        Person person = input.mockEntity(1); //Uma instância
        Person persisted = person;
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        //Deve retornar uma instancia de person
        when(repository.save(person)).thenReturn(persisted);

        //Assertions: Verificar se o que passamos como parâmetro está realmente correto - Se o processamento do findById no service está correto
        var result = service.create(dto); //Conferindo se o processamento feito no findById do Service realmente está correto

        //Confrindo se o que tem dentro dela realmente é o que deveria ter:

        assertNotNull(result); //Não pode vir nulo ou o teste está com problema
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE")
                ) //Link mudará de acordo com o ambiente
        );

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }


    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
        () -> {
           service.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        //Mocks
        Person person = input.mockEntity(1); //Uma instância
        Person persisted = person;
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        //Deve retornar uma instancia de person
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(persisted);


        //Assertions: Verificar se o que passamos como parâmetro está realmente correto - Se o processamento do findById no service está correto
        var result = service.update(dto); //Conferindo se o processamento feito no findById do Service realmente está correto

        //Confrindo se o que tem dentro dela realmente é o que deveria ter:

        assertNotNull(result); //Não pode vir nulo ou o teste está com problema
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE")
                ) //Link mudará de acordo com o ambiente
        );

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        //Mocks
        Person person = input.mockEntity(1); //Uma instância
        person.setId(1L);

        //Deve retornar uma instancia de person
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        //Assertions: Verificar se o que passamos como parâmetro está realmente correto - Se o processamento do findById no service está correto
        service.delete(1L); //Conferindo se o processamento feito no findById do Service realmente está correto

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<PersonDTO> people = service.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.get(1);

        assertNotNull(personOne); //Não pode vir nulo ou o teste está com problema
        assertNotNull(personOne.getId());
        assertNotNull(personOne.getLinks());
        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE")
                ) //Link mudará de acordo com o ambiente
        );

        assertEquals("Address Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());


        var personFour = people.get(4);

        assertNotNull(personFour); //Não pode vir nulo ou o teste está com problema
        assertNotNull(personFour.getId());
        assertNotNull(personFour.getLinks());
        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE")
                ) //Link mudará de acordo com o ambiente
        );

        assertEquals("Address Test4", personFour.getAddress());
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Last Name Test4", personFour.getLastName());
        assertEquals("Male", personFour.getGender());

        var personSeven = people.get(7);

        assertNotNull(personSeven); //Não pode vir nulo ou o teste está com problema
        assertNotNull(personSeven.getId());
        assertNotNull(personSeven.getLinks());
        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT")
                ) //Link mudará de acordo com o ambiente
        );

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE")
                ) //Link mudará de acordo com o ambiente
        );

        assertEquals("Address Test7", personSeven.getAddress());
        assertEquals("First Name Test7", personSeven.getFirstName());
        assertEquals("Last Name Test7", personSeven.getLastName());
        assertEquals("Female", personSeven.getGender());

    }
}