package no.regnskap.dao.impl;

import no.regnskap.dao.TransactionDao;
import no.regnskap.domain.CategoryType;
import no.regnskap.domain.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TransactionDaoImpl implements TransactionDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public TransactionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void save(Transaction transaction) {
        getSession().save(transaction);
    }

    @Override
    @Transactional
    public void delete(Transaction transaction) {
        getSession().delete(transaction);
    }

    @Override
    public Transaction findById(long id) {
        return getSession().get(Transaction.class, id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return getSession().createQuery("from Transaction").getResultList();
    }

    @Override
    public List<Transaction> getTransactionsByCategory(long id) {
        Query query = getSession().createQuery("from Transaction t where t.category.id = :id");
        query.setParameter("id", id);
        return query.list();
    }

    @Override
    public List<Transaction> getTransactionsByUser(long id) {
        Query query = getSession().createQuery("from Transaction t where t.user.id = :id");
        query.setParameter("id", id);

        return query.list();
    }

    @Override
    public List<Transaction> getTransactionsByUserAndCategoryType(long userId, CategoryType categoryType) {
        Query query = getSession().createQuery("from Transaction t where t.user.id = :userId and t.category.categoryType = :categoryType");
        query.setParameter("userId", userId);
        query.setParameter("categoryType", categoryType);

        return query.list();
    }

    @Override
    public List<Transaction> getTransactionsByDate() {
        return null;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
