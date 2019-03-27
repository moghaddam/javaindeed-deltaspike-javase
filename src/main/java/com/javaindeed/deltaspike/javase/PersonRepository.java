package com.javaindeed.deltaspike.javase;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

/**
 * @author Ehsan Zaery Moghaddam (zaerymoghaddam@gmail.com)
 */
@Repository(forEntity = Person.class)
public abstract class PersonRepository extends AbstractEntityRepository<Person, Long>
{
    public abstract List<Person> findByFirstNameOrderByCreateDateDesc(String name);
}
