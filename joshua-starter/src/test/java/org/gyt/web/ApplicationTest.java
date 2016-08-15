package org.gyt.web;

import org.gyt.web.model.project.Project;
import org.gyt.web.model.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.assertEquals;

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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        User user1 = new User("user1", "nick1");
        User user2 = new User("user2", "nick2");
        User user3 = new User("user3", "nick3");
        User user4 = new User("user4", "nick4");
        User user5 = new User("user5", "nick5");
        User user6 = new User("user6", "nick6");

        session.save(user1);
        session.save(user2);
        session.save(user3);
        session.save(user4);
        session.save(user5);
        session.save(user6);

        Project project1 = new Project("project1");
        Project project2 = new Project("project2");

        project1.setOwner(user1);
        project2.setOwner(user2);

        project1.getUsers().add(user3);
        project1.getUsers().add(user4);
        project2.getUsers().add(user5);
        project2.getUsers().add(user6);

        session.save(project1);
        session.save(project2);
        user1.getHostProjects().add(project1);
        user2.getHostProjects().add(project2);
        session.save(user1);
        session.save(user2);

        Project p1 = (Project) session.get(Project.class, "project1");
        Project p2 = (Project) session.get(Project.class, "project2");
        assertEquals("nick1", p1.getOwner().getNickname());
        assertEquals("nick2", p2.getOwner().getNickname());

        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        User u1 = (User) session.get(User.class, "user1");
        User u2 = (User) session.get(User.class, "user2");
        transaction.commit();
        session.close();
    }
}