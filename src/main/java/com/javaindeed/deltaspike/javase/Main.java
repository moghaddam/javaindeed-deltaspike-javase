package com.javaindeed.deltaspike.javase;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import java.util.List;
import java.util.Set;

/**
 * @author Ehsan Zaery Moghaddam (zaerymoghaddam@gmail.com)
 */
public class Main
{
    private static final Logger logger = LogManager.getLogger(Main.class);

    private CdiContainer cdiContainer;

    public static void main(String[] args)
    {
        Main main = new Main();
        main.run();
    }

    private void run()
    {
        initCdiContainer();

        try
        {
            persistPersons();
            fetchAllPersons();
            findByName("John");
        }
        finally
        {
            shutdownCdiContainer();
        }
    }

    private void findByName(String firstName)
    {
        log("Find person by first name = '" + firstName + "'");

        PersonService personService = (PersonService) getBean(PersonService.class);

        List<Person> people = personService.findByName(firstName);
        people.forEach(logger::info);
    }

    private void fetchAllPersons()
    {
        log("Finding all persons");
        PersonService personService = (PersonService) getBean(PersonService.class);
        List<Person> people = personService.findAll();

        for(int i=0; i<people.size(); i++) {
            logger.info("Person [" + i + "]: " + people.get(i));
        }
    }


    private void persistPersons()
    {
        log("Persisting sample persons");
        PersonService personService = (PersonService) getBean(PersonService.class);

        personService.save("John", "Smith");
        personService.save("Will", "Smith");
        personService.save("Black", "Smith");
    }

    private void initCdiContainer()
    {
        log("Initializing CDI container");

        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
    }

    private void shutdownCdiContainer()
    {
        log("Shutting down CDI container");
        cdiContainer.shutdown();
    }

    /**
     * Encapsulates the necessary steps to get a reference to an instance of a Bean via CDI container
     *
     * @param type Type of the bean requested
     * @return An instance of the requested bean (either newly created or an already existing instance based on the
     * scope of the bean)
     */
    private Object getBean(Class type)
    {
        BeanManager beanManager = cdiContainer.getBeanManager();
        Set<Bean<?>> personServiceBean = beanManager.getBeans(type);
        Bean<?> bean = beanManager.resolve(personServiceBean);
        CreationalContext<?> context = beanManager.createCreationalContext(bean);
        return beanManager.getReference(bean, type, context);
    }

    private void log(String message) {
        logger.info("/***********************************************************");
        logger.info(" *\t\t\t" + message);
        logger.info(" ***********************************************************/");
    }
}
