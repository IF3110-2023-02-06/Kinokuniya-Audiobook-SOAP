package soapserver.repositories;

import soapserver.models.*;
import soapserver.enums.Stat;
import soapserver.utils.HibernateUtil;

import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SubscriptionRepository {
    public String createSubscribe(int creator_id, int subscriber_id, String creator_name, String subscriber_name) {
        try {
            Subscription subscription = new Subscription();
            subscription.setCreatorID(creator_id);
            subscription.setSubscriberID(subscriber_id);
            subscription.setCreatorName(creator_name);
            subscription.setSubscriberName(subscriber_name);

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            session.save(subscription);
            session.getTransaction().commit();

            return "Subscription created, wait for approval";

        } catch (Exception e) {
            return "Error creating subscription";
        }
    }

    public String approveSubscribe(int creator_id, int subscriber_id) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            Subscription subscription = session.get(Subscription.class, new Subscription(creator_id, subscriber_id));

            if (subscription == null) {
                session.getTransaction().commit();
                return "Subscription not found";
            }

            Stat currentStatus = subscription.getStatus();

            if (currentStatus == Stat.PENDING) {
                subscription.setStatus(Stat.ACCEPTED);
                session.save(subscription);
                session.getTransaction().commit();

                return "Subscription accepted";
            } else if (currentStatus == Stat.ACCEPTED) {
                session.getTransaction().commit();
                return "Subscription already accepted";
            } else {
                session.getTransaction().commit();
                return "Subscription already rejected";
            }

        } catch (Exception e) {
            return "Error approving subscription";
        }
    }

    public String rejectSubscribe(int creator_id, int subscriber_id) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            Subscription subscription = session.get(Subscription.class, new Subscription(creator_id, subscriber_id));

            if (subscription == null) {
                session.getTransaction().commit();
                return "Subscription not found";
            }

            Stat currentStatus = subscription.getStatus();

            if (currentStatus == Stat.PENDING) {
                subscription.setStatus(Stat.REJECTED);
                session.save(subscription);
                session.getTransaction().commit();

                return "Subscription rejected";

            } else if (currentStatus == Stat.ACCEPTED) {
                session.getTransaction().commit();
                return "Subscription already accepted";
            } else {
                session.getTransaction().commit();
                return "Subscription already rejected";
            }
        } catch (Exception e) {
            return "Error rejecting subscription";
        } 
    }

    public DataSubs getAllReqSubscribe() {
        try {
            DataSubs data = new DataSubs();
    
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();
    
            session.beginTransaction();
    
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Subscription> criteria = builder.createQuery(Subscription.class);
            Root<Subscription> root = criteria.from(Subscription.class);
            Predicate predicate = builder.equal(root.get("status"), Stat.PENDING);
            criteria.select(root).where(predicate);
            TypedQuery<Subscription> query = session.createQuery(criteria);
    
            List<Subscription> subscriptions = query.getResultList();
            data.setData(subscriptions);
    
            session.getTransaction().commit();
    
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public DataSubs getAllAuthorBySubID(Integer subscriberId) {
        try {
            DataSubs data = new DataSubs();
    
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();
    
            session.beginTransaction();
    
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Subscription> criteria = builder.createQuery(Subscription.class);
            Root<Subscription> root = criteria.from(Subscription.class);
    
            Predicate subscriberPredicate = builder.equal(root.get("subscriberID"), subscriberId);
            Predicate statusPredicate = builder.equal(root.get("status"), Stat.ACCEPTED);
    
            criteria.select(root).where(subscriberPredicate, statusPredicate);
            TypedQuery<Subscription> query = session.createQuery(criteria);
    
            List<Subscription> subscriptions = query.getResultList();
            data.setData(subscriptions);
    
            session.getTransaction().commit();
    
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public Stat checkStatus(int creator_id, int subscriber_id) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            Subscription subscription = session.get(Subscription.class, new Subscription(creator_id, subscriber_id));
            session.getTransaction().commit();

            if (subscription == null) {
                return Stat.NODATA;
            }
            return subscription.getStatus();

        } catch (Exception e) {
            return Stat.NODATA;
        }
    } 
}