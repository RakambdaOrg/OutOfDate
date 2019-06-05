package fr.mrcraftcod.outofdate.utils;

import fr.mrcraftcod.outofdate.Main;
import fr.mrcraftcod.outofdate.model.OwnedProduct;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HBDatabase implements AutoCloseable{
	private final SessionFactory sessionFactory;
	
	public HBDatabase(){
		this.sessionFactory = new Configuration().configure(Main.class.getResource("/hibernate.cfg.xml")).buildSessionFactory();
	}
	
	@Override
	public void close() throws Exception{
		this.sessionFactory.close();
	}
	
	public List<OwnedProduct> getOwnedProducts(){
		List<OwnedProduct> ownedProducts = new ArrayList<>();
		Transaction tx = null;
		try(Session session = this.sessionFactory.openSession()){
			tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<OwnedProduct> criteria = builder.createQuery(OwnedProduct.class);
			criteria.from(OwnedProduct.class);
			ownedProducts.addAll(session.createQuery(criteria).getResultList());
			tx.commit();
		}
		catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			log.error("Failed to list OwnedProducts", e);
		}
		return ownedProducts;
	}
}
