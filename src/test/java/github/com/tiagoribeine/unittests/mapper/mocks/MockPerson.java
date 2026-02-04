package github.com.tiagoribeine.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.model.Person;

public class MockPerson {


    public Person mockEntity() {
        return mockEntity(0);
    } //Mocka uma entidade
    
    public PersonDTO mockDTO() {
        return mockDTO(0);
    } //Mocka um DTO
    
    public List<Person> mockEntityList() { //Mocka uma lista de entidades
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<PersonDTO> mockDTOList() { //Mocka uma lista de DTOs
        List<PersonDTO> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockDTO(i));
        }
        return persons;
    }
    
    public Person mockEntity(Integer number) {
        Person person = new Person();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

    public PersonDTO mockDTO(Integer number) {
        PersonDTO person = new PersonDTO();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

}