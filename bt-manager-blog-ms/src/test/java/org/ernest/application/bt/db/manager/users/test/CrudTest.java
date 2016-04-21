package org.ernest.application.bt.db.manager.users.test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.ernest.applications.bt.db.manager.blog.ct.entities.CreatePostInput;
import org.ernest.applications.bt.db.manager.blog.ct.entities.PostInformation;
import org.ernest.applications.bt.db.manager.blog.ms.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest({"server.port=0"})
public class CrudTest {
	
	@Value("${local.server.port}")
	String port;
	
	@Test
	public void crd() throws InterruptedException{
		CreatePostInput input = new CreatePostInput();
		input.setTitle("title-to-test-will-be-deleted");
		input.setDescription("description");
		input.setContent("content");
		input.setCreationDate(new Date());
		
		new RestTemplate().postForObject("http://localhost:"+port+"/create", input, String.class);
		
		List<PostInformation> postInformationList = Arrays.asList(new RestTemplate().getForObject("http://localhost:"+port+"/retrieveall", PostInformation[].class));
		Assert.assertTrue(postInformationList.size() > 0);
		
		PostInformation postInformation = postInformationList.stream().filter( info -> info.getTitle().equals(input.getTitle())).findAny().get();
		new RestTemplate().getForObject("http://localhost:"+port+"/delete/"+postInformation.get_id(), String.class);
		
		Thread.sleep(1000L);
		postInformationList = Arrays.asList(new RestTemplate().getForObject("http://localhost:"+port+"/retrieveall", PostInformation[].class));
		postInformation = postInformationList.stream().filter( info -> info.getTitle().equals(input.getTitle())).findAny().orElse(null);
		Assert.assertTrue(postInformation == null);		
	}
}