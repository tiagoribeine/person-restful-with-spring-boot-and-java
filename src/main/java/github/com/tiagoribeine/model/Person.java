package github.com.tiagoribeine.model;

import java.io.Serializable;
import java.util.Objects;

//Definindo os atributos de Person

public class Person implements Serializable { //Indica que a classe pode ser serializada

    private static final long serialVersionUID = 1L; //Número da versão para controle durante a serialização

    //Atributos
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;

    public Person() {} //Construtor

    //Getters and Setters
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
    public boolean equals(Object o) { //Compara se dois objetos são iguais baseado nos valores
        if (!(o instanceof Person person)) return false;
        return Objects.equals(getId(), person.getId()) && Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName()) && Objects.equals(getAddress(), person.getAddress()) && Objects.equals(getGender(), person.getGender());
    }

    @Override
    public int hashCode() { //Gera um código único baseado nos atributos
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getGender());
    }
}
