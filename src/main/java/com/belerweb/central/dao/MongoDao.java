package com.belerweb.central.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import com.googlecode.mjorm.MapReduce;
import com.googlecode.mjorm.MapReduceResult;
import com.googlecode.mjorm.MongoDaoImpl;
import com.googlecode.mjorm.ObjectIterator;
import com.googlecode.mjorm.XmlDescriptorObjectMapper;
import com.googlecode.mjorm.mql.Statement;
import com.googlecode.mjorm.query.DaoQuery;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBEncoder;
import com.mongodb.DBObject;
import com.mongodb.DBPort;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

@Repository
public class MongoDao implements com.googlecode.mjorm.MongoDao, InitializingBean {

  private static final String PORT = "central.db.port";
  private static final String HOST = "central.db.host";
  private static final String DBNAME = "central.db.name";
  private static final String USERNAME = "central.db.username";
  private static final String PASSWORD = "central.db.password";

  private Mongo mongo;
  private DB db;
  private XmlDescriptorObjectMapper mapper;
  private MongoDaoImpl daoImpl;

  @Override
  public void afterPropertiesSet() throws Exception {
    String host = System.getProperty(HOST, System.getenv(HOST));
    String port = System.getProperty(PORT, System.getenv(PORT));
    String dbName = System.getProperty(DBNAME, System.getenv(DBNAME));
    mongo = new Mongo(host, port != null ? Integer.parseInt(port) : DBPort.PORT);
    db = mongo.getDB(dbName);

    // authenticate if needed
    String username = System.getProperty(USERNAME, System.getenv(USERNAME));
    String password = System.getProperty(PASSWORD, System.getenv(PASSWORD));
    if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
      if (!db.authenticate(username, password.toCharArray())) {
        throw new Exception("Database authenticate failed!");
      }
    }

    initObjectMapper();
    daoImpl = new MongoDaoImpl(db, mapper);
  }

  private void initObjectMapper() throws IOException, XPathExpressionException,
      ClassNotFoundException, ParserConfigurationException, SAXException {
    mapper = new XmlDescriptorObjectMapper();
    mapper.registerTypeConverter(new ByteArrayToBinary());
    mapper.registerTypeConverter(new BinaryToByteArray());
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    Resource[] xmlResources = resolver.getResources("classpath:/mongo/**/*.xml");
    for (Resource resource : xmlResources) {
      InputStream ips = resource.getInputStream();
      mapper.addXmlObjectDescriptor(ips);
      ips.close();
    }
  }

  public DaoQuery createQuery(String collection) {
    return daoImpl.createQuery().setCollection(collection);
  }

  public <T> T findById(String collection, Class<T> cls, String id) {
    return createQuery(collection).eq("_id", id).findObject(cls);
  }

  public <T> T findBy(String collection, Class<T> cls, String property, Object value) {
    return createQuery(collection).eq(property, value).findObject(cls);
  }

  public <T> List<T> findAllBy(String collection, Class<T> cls, String property, Object value) {
    return createQuery(collection).eq(property, value).findObjects(cls).readAll();
  }

  public <T> DBObject unmap(T object) {
    return mapper.unmap(object);
  }

  public <T> T map(DBObject dbObject, Class<T> cls) {
    return mapper.map(dbObject, cls);
  }

  public <T> List<T> map(DBCursor dbCursor, Class<T> cls) {
    List<T> result = new ArrayList<T>();
    while (dbCursor.hasNext()) {
      result.add(mapper.map(dbCursor.next(), cls));
    }
    return result;
  }

  @Override
  public Statement createStatement(String mql) {
    return daoImpl.createStatement(mql);
  }

  @Override
  public DaoQuery createQuery() {
    return daoImpl.createQuery();
  }

  @Override
  public <T> T createObject(String collection, T object) {
    return daoImpl.createObject(collection, object);
  }

  @Override
  public <T> T createObject(String collection, T object, WriteConcern concern) {
    return daoImpl.createObject(collection, object, concern);
  }

  @Override
  public <T> T[] createObjects(String collection, T[] objects) {
    return daoImpl.createObjects(collection, objects);
  }

  @Override
  public <T> T[] createObjects(String collection, T[] objects, WriteConcern concern) {
    return daoImpl.createObjects(collection, objects, concern);
  }

  @Override
  public <T> T readObject(String collection, Object id, Class<T> clazz) {
    return daoImpl.readObject(collection, id, clazz);
  }

  @Override
  public <T> T[] readObjects(String collection, Object[] ids, Class<T> clazz) {
    return daoImpl.readObjects(collection, ids, clazz);
  }

  @Override
  public void updateObject(String collection, Object id, Object o) {
    daoImpl.updateObject(collection, id, o);
  }

  @Override
  public void updateObject(String collection, Object id, Object o, WriteConcern concern) {
    daoImpl.updateObject(collection, id, o, concern);
  }

  @Override
  public void deleteObject(String collection, Object id) {
    daoImpl.deleteObject(collection, id);
  }

  @Override
  public void deleteObject(String collection, Object id, WriteConcern concern) {
    daoImpl.deleteObject(collection, id, concern);
  }

  @Override
  public void deleteObjects(String collection, DBObject query) {
    daoImpl.deleteObjects(collection, query);
  }

  @Override
  public void deleteObjects(String collection, DBObject query, WriteConcern concern) {
    daoImpl.deleteObjects(collection, query, concern);
  }

  @Override
  public void deleteObjects(String collection, Object[] ids) {
    daoImpl.deleteObjects(collection, ids);
  }

  @Override
  public void deleteObjects(String collection, Object[] ids, WriteConcern concern) {
    daoImpl.deleteObjects(collection, ids, concern);
  }

  @Override
  public <T> T getPartialObject(String collection, Object id, String name, Class<T> clazz) {
    return daoImpl.getPartialObject(collection, id, name, clazz);
  }

  @Override
  public <T> T getPartialObject(String collection, DBObject query, String name, Class<T> clazz) {
    return daoImpl.getPartialObject(collection, query, name, clazz);
  }

  @Override
  public <T> void savePartialObject(String collection, Object id, String name, T data,
      boolean upsert) {
    daoImpl.savePartialObject(collection, id, name, data, upsert);
  }

  @Override
  public <T> void savePartialObject(String collection, Object id, String name, T data,
      boolean upsert, WriteConcern concern) {
    daoImpl.savePartialObject(collection, id, name, data, upsert, concern);
  }

  @Override
  public <T> void savePartialObject(String collection, DBObject query, String name, T data,
      boolean upsert, WriteConcern concern) {
    daoImpl.savePartialObject(collection, query, name, data, upsert);

  }

  @Override
  public void deletePartialObject(String collection, Object id, String name) {
    daoImpl.deletePartialObject(collection, id, name);
  }

  @Override
  public void deletePartialObject(String collection, Object id, String name, WriteConcern concern) {
    daoImpl.deletePartialObject(collection, id, name, concern);
  }

  @Override
  public void deletePartialObject(String collection, DBObject query, String name) {
    daoImpl.deletePartialObject(collection, query, name);
  }

  @Override
  public void deletePartialObject(String collection, DBObject query, String name,
      WriteConcern concern) {
    daoImpl.deletePartialObject(collection, query, name, concern);
  }

  @Override
  public <T> ObjectIterator<T> findByExample(String collection, T example, Class<T> clazz) {
    return daoImpl.findByExample(collection, example, clazz);
  }

  @Override
  public <T> ObjectIterator<T> findObjects(String collection, DBObject query, Class<T> clazz) {
    return daoImpl.findObjects(collection, query, clazz);
  }

  @Override
  public long countObjects(String collection, DBObject query) {
    return daoImpl.countObjects(collection, query);
  }

  @Override
  public <T> T findObject(String collection, DBObject query, Class<T> clazz) {
    return daoImpl.findObject(collection, query, clazz);
  }

  @Override
  public CommandResult executeCommand(DBObject cmd) {
    return daoImpl.executeCommand(cmd);
  }

  @Override
  public CommandResult executeCommand(String cmd) {
    return daoImpl.executeCommand(cmd);
  }

  @Override
  public MapReduceResult mapReduce(String collection, MapReduce mapReduce) {
    return daoImpl.mapReduce(collection, mapReduce);
  }

  @Override
  public void ensureIndex(String collection, String key, boolean background, boolean unique,
      boolean dropDupes) {
    daoImpl.ensureIndex(collection, key, background, unique, dropDupes);
  }

  @Override
  public void ensureIndex(String collection, DBObject keys, boolean background, boolean unique,
      boolean dropDupes) {
    daoImpl.ensureIndex(collection, keys, background, unique, dropDupes);
  }

  @Override
  public <T> T findAndDelete(String collection, DBObject query, DBObject sort, Class<T> clazz,
      String[] fields) {
    return daoImpl.findAndDelete(collection, query, sort, clazz, fields);
  }

  @Override
  public <T> T findAndDelete(String collection, DBObject query, DBObject sort, Class<T> clazz) {
    return daoImpl.findAndDelete(collection, query, sort, clazz);
  }

  @Override
  public <T> T findAndModify(String collection, DBObject query, DBObject sort, DBObject update,
      boolean returnNew, boolean upsert, Class<T> clazz) {
    return daoImpl.findAndModify(collection, query, sort, update, returnNew, upsert, clazz);
  }

  @Override
  public <T> T findAndModify(String collection, DBObject query, DBObject sort, DBObject update,
      boolean returnNew, boolean upsert, Class<T> clazz, String[] fields) {
    return null;
  }

  @Override
  public void update(String collection, DBObject query, DBObject update, boolean upsert,
      boolean multi, WriteConcern concern, DBEncoder encoder) {
    daoImpl.update(collection, query, update, upsert, multi, concern, encoder);
  }

  @Override
  public void update(String collection, DBObject query, DBObject update, boolean upsert,
      boolean multi, WriteConcern concern) {
    daoImpl.update(collection, query, update, upsert, multi, concern);
  }

  @Override
  public void update(String collection, DBObject query, DBObject update, boolean upsert,
      boolean multi) {
    daoImpl.update(collection, query, update, upsert, multi);
  }

  @Override
  public DB getDB() {
    return daoImpl.getDB();
  }

  @Override
  public DBCollection getCollection(String name) {
    return daoImpl.getCollection(name);
  }

}
