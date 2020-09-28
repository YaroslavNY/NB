package NB;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class NotesDAO {
    public NotesEntity findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(NotesEntity.class, id);
    }

    public void save(NotesEntity notes) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(notes);
        tx1.commit();
        session.close();
    }

    public void update(NotesEntity notes) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(notes);
        tx1.commit();
        session.close();
    }

    public void delete(NotesEntity notes) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(notes);
        tx1.commit();
        session.close();
    }


    public List<NotesEntity> getNotesList() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        String hql = "from NotesEntity ";
        List <NotesEntity>  notes = session.createQuery(hql).list();
        session.close();
        return notes;
    }

}
