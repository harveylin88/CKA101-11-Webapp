package com.activity.model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.activity.util.HibernateUtil;

public class ActivityHibernateDAO implements ActivityDAO {

    @Override
    public void insert(ActivityVO activityVo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(activityVo); // 一行取代 INSERT 語法！
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(ActivityVO activityVo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(activityVo); // 一行取代 UPDATE 語法！
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer activityId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            ActivityVO activityVo = session.get(ActivityVO.class, activityId);
            if (activityVo != null) {
                session.remove(activityVo); // 一行取代 DELETE 語法！
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public ActivityVO findByPrimaryKey(Integer activityId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // 一行取代 SELECT * FROM WHERE ID = ?
            return session.get(ActivityVO.class, activityId); 
        }
    }

    @Override
    public List<ActivityVO> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // 使用 HQL (Hibernate Query Language)，注意這裡是類別名稱 ActivityVo，不是表格名稱
            return session.createQuery("FROM ActivityVO ORDER BY activityId DESC", ActivityVO.class).list();
        }
    }
}