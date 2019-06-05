package fr.mrcraftcod.outofdate.utils;

import fr.mrcraftcod.outofdate.Main;
import fr.mrcraftcod.outofdate.model.OwnedProduct;
import fr.mrcraftcod.outofdate.model.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HBDatabase implements AutoCloseable{
	private static final Logger log = LoggerFactory.getLogger(HBDatabase.class);
	private final SessionFactory sessionFactory;
	
	public HBDatabase(){
		this.sessionFactory = new Configuration().configure(Main.class.getResource("/hibernate.cfg.xml")).buildSessionFactory();
	}
	
	@Override
	public void close() throws Exception{
		this.sessionFactory.close();
	}
	
	public Optional<Product> getProduct(String id){
		Product product = null;
		Transaction tx = null;
		try(Session session = this.sessionFactory.openSession()){
			tx = session.beginTransaction();
			product = session.get(Product.class, id);
			tx.commit();
		}
		catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			log.error("Failed to get product with id {}", id, e);
		}
		return Optional.ofNullable(product);
	}
	
	public boolean persistProduct(Product product){
		try{
			this.persistObject(product);
			return true;
		}
		catch(HibernateException e){
			log.error("Failed to persist product {}", product, e);
		}
		return false;
	}
	
	public boolean persistOwnedProduct(OwnedProduct ownedProduct){
		try{
			persistObject(ownedProduct);
			return true;
		}
		catch(HibernateException e){
			log.error("Failed to persist owned product {}", ownedProduct, e);
		}
		return false;
	}
	
	private void persistObject(Object o) throws HibernateException{
		Transaction tx = null;
		try(Session session = this.sessionFactory.openSession()){
			tx = session.beginTransaction();
			session.persist(o);
			tx.commit();
		}
		catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			throw e;
		}
	}
	
	public boolean updateProduct(Product product){
		try{
			updateEntity(product);
			return true;
		}
		catch(HibernateException e){
			log.error("Failed to update product {}", product, e);
		}
		return false;
	}
	
	private void updateEntity(Object o){
		Transaction tx = null;
		try(Session session = this.sessionFactory.openSession()){
			tx = session.beginTransaction();
			session.update(o);
			tx.commit();
		}
		catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			throw e;
		}
	}
	
	public boolean removeOwnedProduct(OwnedProduct ownedProduct){
		try{
			removeEntity(ownedProduct);
			return true;
		}
		catch(HibernateException e){
			log.error("Failed to remove owned product {}", ownedProduct, e);
		}
		return false;
	}
	
	private void removeEntity(Object o){
		Transaction tx = null;
		try(Session session = this.sessionFactory.openSession()){
			tx = session.beginTransaction();
			session.remove(o);
			tx.commit();
		}
		catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			throw e;
		}
	}
	
	public boolean updateOwnedProduct(OwnedProduct ownedProduct){
		try{
			updateEntity(ownedProduct);
			return true;
		}
		catch(HibernateException e){
			log.error("Failed to update owned product {}", ownedProduct, e);
		}
		return false;
	}
	
	public List<Product> getProducts(){
		List<Product> products = new ArrayList<>();
		try{
			products.addAll(listEntities(Product.class));
		}
		catch(HibernateException e){
			log.error("Failed to list products", e);
		}
		return products;
	}
	
	public List<OwnedProduct> getOwnedProducts(){
		List<OwnedProduct> ownedProducts = new ArrayList<>();
		try{
			ownedProducts.addAll(listEntities(OwnedProduct.class));
		}
		catch(HibernateException e){
			log.error("Failed to list owned products", e);
		}
		return ownedProducts;
	}
	
	private <T> List<T> listEntities(Class<T> clazz) throws HibernateException{
		Transaction tx = null;
		try(Session session = this.sessionFactory.openSession()){
			tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> criteria = builder.createQuery(clazz);
			criteria.from(clazz);
			List<T> entities = new ArrayList<>(session.createQuery(criteria).getResultList());
			tx.commit();
			return entities;
		}
		catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			throw e;
		}
	}
}
