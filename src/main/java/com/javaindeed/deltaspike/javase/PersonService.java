package com.javaindeed.deltaspike.javase;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author Ehsan Zaery Moghaddam (zaerymoghaddam@gmail.com)
 */
public class PersonService
{
    @Inject
    private PersonRepository personRepository;

    public Person save(String firstName, String lastName)
    {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setCreateDate(new Date());

        return personRepository.save(person);
    }

    public List<Person> findAll()
    {
        return personRepository.findAll();
    }

    public List<Person> findByName(String name) {
        return personRepository.findByFirstNameOrderByCreateDateDesc(name);
    }
}