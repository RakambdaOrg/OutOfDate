package fr.raksrinana.outofdate.jpa;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.engine.jdbc.dialect.internal.DialectResolverSet;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLiteMetadataBuilderInitializer implements MetadataBuilderInitializer{
	private static final Logger log = LoggerFactory.getLogger(SQLiteMetadataBuilderInitializer.class);
	@Override
	public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry serviceRegistry){
		DialectResolver dialectResolver = serviceRegistry.getService(DialectResolver.class);
		if(!(dialectResolver instanceof DialectResolverSet)){
			log.warn("DialectResolver '{}' is not an instance of DialectResolverSet, not registering SQLiteDialect", dialectResolver);
			return;
		}
		((DialectResolverSet) dialectResolver).addResolver(resolver);
	}
	
	static private final SQLiteDialect dialect = new SQLiteDialect();
	static private final DialectResolver resolver = (DialectResolver) info -> {
		if(info.getDatabaseName().equals("SQLite")){
			return dialect;
		}
		return null;
	};
}
