package com.javaindeed.deltaspike.javase;

import org.apache.deltaspike.jpa.api.entitymanager.PersistenceUnitName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author Ehsan Zaery Moghaddam (zaerymoghaddam@gmail.com)
 */
@ApplicationScoped
public class EntityManagerProducer
{

    private static final Logger logger = LogManager.getLogger(EntityManagerProducer.class);

    @Inject
    @PersistenceUnitName("sample-unit")
    private EntityManagerFactory entityManagerFactory;

    @Produces
    public EntityManager getEntityManager()
    {
        logger.info("Creating entity manager");
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Closes the entity manager produced earlier. It's called automatically by CDI container
     *
     * @param entityManager the entity manager produced earlier in the {@link #getEntityManager()} method
     */
    public void closeEntityManager(@Disposes EntityManager entityManager)
    {
        logger.info("Closing entity manager " + entityManager.isOpen());

        if (entityManager.isOpen())
        {
            entityManager.close();
        }
    }

    /**
     * Closes the entity manager factory instance so that the CDI container can be gracefully shutdown
     */
    @PreDestroy
    public void closeFactory()
    {
        logger.info("Closing the Factory");
        if (entityManagerFactory.isOpen())
        {
            entityManagerFactory.close();
        }
    }
}
