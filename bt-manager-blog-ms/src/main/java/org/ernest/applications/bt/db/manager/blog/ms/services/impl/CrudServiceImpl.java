package org.ernest.applications.bt.db.manager.blog.ms.services.impl;

import java.util.List;
import java.util.UUID;

import org.ernest.applications.bt.db.manager.blog.ct.entities.CreatePostInput;
import org.ernest.applications.bt.db.manager.blog.ct.entities.PostContent;
import org.ernest.applications.bt.db.manager.blog.ct.entities.PostInformation;
import org.ernest.applications.bt.db.manager.blog.ct.exceptions.CreatePostException;
import org.ernest.applications.bt.db.manager.blog.ct.exceptions.DeletePostException;
import org.ernest.applications.bt.db.manager.blog.ms.services.CrudService;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CrudServiceImpl implements CrudService{
	
	@Value("${db.info.name}")
	private String dbNameInfo;
	
	@Value("${db.content.name}")
	private String dbNameContent;
	
	@Value("${db.host}")
	private String dbHost;

	@Override
	public void create(CreatePostInput createPostInput) throws CreatePostException {
		PostInformation postInformation = new PostInformation();
		postInformation.set_id(UUID.randomUUID().toString());
		postInformation.setTitle(createPostInput.getTitle());
		postInformation.setDescription(createPostInput.getDescription());
		postInformation.setCreationDate(createPostInput.getCreationDate());
		
		try{
			CouchDbClient dbClient = new CouchDbClient(buildCouchDbPropertiesInfo());
			dbClient.save(postInformation);
			dbClient.shutdown();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new CreatePostException(e.getMessage());
		}
		
		PostContent postContent = new PostContent();
		postContent.set_id(postInformation.get_id());
		postContent.setContent(createPostInput.getContent());
		
		try{
			CouchDbClient dbClient = new CouchDbClient(buildCouchDbPropertiesContent());
			dbClient.save(postContent);
			dbClient.shutdown();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new CreatePostException(e.getMessage());
		}
	}
	
	@Override
	public PostInformation retrieveInfo(String postId) throws CreatePostException {
		try{
			CouchDbClient dbClient = new CouchDbClient(buildCouchDbPropertiesInfo());
			PostInformation postInformation = dbClient.find(PostInformation.class, postId);
			dbClient.shutdown();
			return postInformation;
		}catch(Exception e){
			e.printStackTrace();
			throw new CreatePostException(e.getMessage());
		}
	}

	@Override
	public PostContent retrieveContent(String postId) throws CreatePostException {
		try{
			CouchDbClient dbClient = new CouchDbClient(buildCouchDbPropertiesContent());
			PostContent postContent = dbClient.find(PostContent.class, postId);
			dbClient.shutdown();
			return postContent;
		}catch(Exception e){
			e.printStackTrace();
			throw new CreatePostException(e.getMessage());
		}
	}

	@Override
	public List<PostInformation> retrieveInfoAllPosts() throws CreatePostException {
		try{
			CouchDbClient dbClient = new CouchDbClient(buildCouchDbPropertiesInfo());
			return dbClient.view("_all_docs").includeDocs(true).query(PostInformation.class);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new CreatePostException(e.getMessage());
		}
	}
	
	@Override
	public void delete(String postId) throws DeletePostException {
		try{
			CouchDbClient dbClient = new CouchDbClient(buildCouchDbPropertiesInfo());
			PostInformation postInformation = dbClient.find(PostInformation.class, postId);
			dbClient.remove(postInformation);
			dbClient.shutdown();
		}catch(Exception e){
			e.printStackTrace();
			throw new DeletePostException(e.getMessage());
		}
		
		try{
			CouchDbClient dbClient = new CouchDbClient(buildCouchDbPropertiesContent());
			PostContent postContent = dbClient.find(PostContent.class, postId);
			dbClient.remove(postContent);
			dbClient.shutdown();
		}catch(Exception e){
			e.printStackTrace();
			throw new DeletePostException(e.getMessage());
		}
	}
	
	private CouchDbProperties buildCouchDbPropertiesInfo() {
		CouchDbProperties properties = new CouchDbProperties();
		properties.setDbName(dbNameInfo);
		properties.setHost(dbHost);
		properties.setPort(5984);
		properties.setCreateDbIfNotExist(true);
		properties.setProtocol("http");
		return properties;
	}
	
	private CouchDbProperties buildCouchDbPropertiesContent() {
		CouchDbProperties properties = new CouchDbProperties();
		properties.setDbName(dbNameContent);
		properties.setHost(dbHost);
		properties.setPort(5984);
		properties.setCreateDbIfNotExist(true);
		properties.setProtocol("http");
		return properties;
	}
}