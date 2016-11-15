package org.gyt.web;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManagerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class ApplicationTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws Exception {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Test
    @Ignore
    public void testUserCreate() throws Exception {
    }
}