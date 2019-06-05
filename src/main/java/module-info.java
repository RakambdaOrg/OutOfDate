/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/01/2018.
 *
 * @since 2018-01-23
 */
open module fr.mrcraftcod.outofdate {
	requires org.json;
	requires org.apache.commons.lang3;
	requires jcommander;
	
	requires org.slf4j;
	requires ch.qos.logback.classic;
	
	requires fr.mrcraftcod.utils.http;
	requires fr.mrcraftcod.utils.javafx;
	
	requires java.xml;
	requires java.xml.bind;
	requires com.sun.xml.bind;
	requires java.activation;
	requires java.persistence;
	requires java.sql;
	requires org.hibernate.orm.core;
	requires org.hibernate.commons.annotations;
	requires net.bytebuddy;
	
	exports fr.mrcraftcod.outofdate.jfx to javafx.graphics;
	exports fr.mrcraftcod.outofdate.model;
	exports fr.mrcraftcod.outofdate.utils;
}