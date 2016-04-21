package org.ernest.applications.bt.db.manager.blog.ms.services;

import java.util.List;

import org.ernest.applications.bt.db.manager.blog.ct.entities.CreatePostInput;
import org.ernest.applications.bt.db.manager.blog.ct.entities.PostContent;
import org.ernest.applications.bt.db.manager.blog.ct.entities.PostInformation;
import org.ernest.applications.bt.db.manager.blog.ct.exceptions.CreatePostException;
import org.ernest.applications.bt.db.manager.blog.ct.exceptions.DeletePostException;

public interface CrudService {

	void create(CreatePostInput createPostInput) throws CreatePostException;

	List<PostInformation> retrieveInfoAllPosts() throws CreatePostException;

	void delete(String postId) throws DeletePostException;

	PostInformation retrieveInfo(String postId) throws CreatePostException;

	PostContent retrieveContent(String postId) throws CreatePostException;
}