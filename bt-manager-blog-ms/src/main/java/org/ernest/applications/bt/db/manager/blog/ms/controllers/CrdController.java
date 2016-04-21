package org.ernest.applications.bt.db.manager.blog.ms.controllers;

import java.util.List;

import org.ernest.applications.bt.db.manager.blog.ct.entities.CreatePostInput;
import org.ernest.applications.bt.db.manager.blog.ct.entities.PostContent;
import org.ernest.applications.bt.db.manager.blog.ct.entities.PostInformation;
import org.ernest.applications.bt.db.manager.blog.ct.exceptions.CreatePostException;
import org.ernest.applications.bt.db.manager.blog.ct.exceptions.DeletePostException;
import org.ernest.applications.bt.db.manager.blog.ct.exceptions.RetrievePostException;
import org.ernest.applications.bt.db.manager.blog.ms.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrdController {

	@Autowired
	CrudService crudService;
	
	@RequestMapping("/create")
	public void createPost(@RequestBody CreatePostInput createPostInput) throws CreatePostException {
		crudService.create(createPostInput);
	}
	
	@RequestMapping(path = "/retrieveall", method = RequestMethod.GET)
	public List<PostInformation> retrieveInfoAllPosts() throws RetrievePostException, CreatePostException {
		return crudService.retrieveInfoAllPosts();
	}
	
	@RequestMapping(path = "/retrieveinfo/{postId}", method = RequestMethod.GET)
	public PostInformation retrieveInfo(@PathVariable("postId") String postId) throws RetrievePostException, CreatePostException {
		return crudService.retrieveInfo(postId);
	}
	
	@RequestMapping(path = "/retrievecontent/{postId}", method = RequestMethod.GET)
	public PostContent retrieveContent(@PathVariable("postId") String postId) throws RetrievePostException, CreatePostException {
		return crudService.retrieveContent(postId);
	}
	
	@RequestMapping(path = "/delete/{postId}", method = RequestMethod.GET)
	public void deletePost(@PathVariable("postId") String postId) throws DeletePostException {
		crudService.delete(postId);
	}
}