package github.com.tiagoribeine.data.dto;

import java.io.Serializable;
import java.util.Objects;


public class PersonDTO implements Serializable {


    private static final long serialVersionUID = 1L; //Número da versão para controle durante a serialização

    //Atributos
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;

    public PersonDTO() {} //Construtor - Spring Data JPA e frameworks de serialização exigem construtor vazio

    //Getters e Setters - Spring, Jackson e JPA usam getters/setters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    //Gerando o Equals and HashCode: Botão Direito >> Generate >> Equals and HashCode
    @Override
    public boolean equals(Object o) { //Compara se dois objetos são iguais baseado nos valores de seus atributos
        if (!(o instanceof PersonDTO person)) return false;
        return Objects.equals(getId(), person.getId()) && Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName()) && Objects.equals(getAddress(), person.getAddress()) && Objects.equals(getGender(), person.getGender());
    }

    @Override
    public int hashCode() { //Gera um código único baseado nos atributos
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getGender());
    }
}
