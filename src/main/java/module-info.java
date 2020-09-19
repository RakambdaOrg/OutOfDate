/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/01/2018.
 *
 * @since 2018-01-23
 */
open module fr.raksrinana.outofdate {
	requires org.apache.commons.lang3;
	requires info.picocli;
	
	requires org.slf4j;
	requires ch.qos.logback.classic;
	
	requires fr.raksrinana.utils.http;
	requires fr.raksrinana.utils.javafx;
	
	requires unirest.java;
	
	requires java.xml;
	requires java.xml.bind;
	requires com.sun.xml.bind;
	requires java.activation;
	requires java.persistence;
	requires java.sql;
	requires org.hibernate.orm.core;
	requires org.hibernate.commons.annotations;
	requires net.bytebuddy;
	
	requires javafx.base;
	requires javafx.controls;
	
	requires static lombok;
	
	exports fr.raksrinana.outofdate.jfx to javafx.graphics;
	exports fr.raksrinana.outofdate.model;
	exports fr.raksrinana.outofdate.utils;
}