package no.regnskap.dao.impl;

import no.regnskap.dao.CategoryDao;
import no.regnskap.domain.Category;
import no.regnskap.domain.CategoryType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public CategoryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void save(Category category) {
        getSession().save(category);
    }

    @Override
    public Category findById(long id) {
        return getSession().get(Category.class, id);
    }

    @Override
    public List<Category> getAllCategories() {
        return getSession().createQuery("from Category").getResultList();
    }

    @Override
    public CategoryType findCategoryTypeById(String id) {
        Query query = getSession().createQuery("from CategoryType ct where ct.categoryType = :id");
        query.setParameter("id", id);

        return (CategoryType) query.uniqueResult();
    }

    @Override
    public List<CategoryType> getAllCategoryTypes() {
        return getSession().createQuery("from CategoryType").getResultList();
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
