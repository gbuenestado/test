package es.bnext.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bnext.dto.in.ContactRequest;
import es.bnext.dto.in.UserRequest;
import es.bnext.dto.out.UserResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

@MicronautTest
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTest {
	
	private final static Logger LOG = LoggerFactory.getLogger(UserControllerTest.class);

	@Inject
	@Client("http://localhost:8080")
	HttpClient client;

	@Test
	@Order(1)
	public void testCreateUser1() {
		LOG.info("INIT 1 - testCreateUser1");
		
		UserRequest userRequest = new UserRequest();
		userRequest.setLastName("Buenestado");
		userRequest.setName("Gabriel");
		userRequest.setPhone("+34680426440");

		HttpRequest<UserRequest> request = HttpRequest.POST("/user", userRequest); 
		HttpResponse<UserResponse> response = client.toBlocking().exchange(request, UserResponse.class);

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
		Assertions.assertEquals(response.body().getId(), 1L);
		
		LOG.info("END 1 - testCreateUser1");
	}

	@Test
	@Order(2)
	public void testCreateUser2() {
		LOG.info("INIT 2 - testCreateUser2");
		
		UserRequest userRequest = new UserRequest();
		userRequest.setLastName("Lopez");
		userRequest.setName("Sara");
		userRequest.setPhone("+34666777111");

		HttpRequest<UserRequest> request = HttpRequest.POST("/user", userRequest); 
		HttpResponse<UserResponse> response = client.toBlocking().exchange(request, UserResponse.class);

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
		Assertions.assertEquals(response.body().getId(), 2L);
		
		LOG.info("END 2 - testCreateUser2");
	}

	@Test
	@Order(3)
	public void testCreateNoLastName() {
		LOG.info("INIT 3 - testCreateNoLastName");
		
		UserRequest userRequest = new UserRequest();
		userRequest.setName("Gabriel");
		userRequest.setPhone("+34680426440");

		HttpRequest<UserRequest> request = HttpRequest.POST("/user", userRequest);
		try {
			client.toBlocking().exchange(request, UserResponse.class);

		} catch(HttpClientResponseException e) {
			Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		}
		
		LOG.info("END 3 - testCreateNoLastName");
	}

	@Test
	@Order(4)
	public void testCreateBadPhone() {
		LOG.info("INIT 4 - testCreateBadPhone");
		
		UserRequest userRequest = new UserRequest();
		userRequest.setLastName("Santiago");
		userRequest.setName("Gabriel");
		userRequest.setPhone("680426440");

		HttpRequest<UserRequest> request = HttpRequest.POST("/user", userRequest);
		try {
			client.toBlocking().exchange(request, UserResponse.class);
		} catch(HttpClientResponseException e) {
			Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		}
		
		LOG.info("END 4 - testCreateBadPhone");
	}

	@Test
	@Order(5)
	public void testCreateContatsUser1() {
		LOG.info("INIT 5 - testCreateContatsUser1");
		
		List<ContactRequest> contactRequestList = new ArrayList<>();
		ContactRequest contactRequestSara = new ContactRequest();
		contactRequestSara.setContactName("Sara");
		contactRequestSara.setPhone("+34666777111");
		contactRequestList.add(contactRequestSara);

		ContactRequest contactRequestFrancisco = new ContactRequest();
		contactRequestFrancisco.setContactName("Francisco");
		contactRequestFrancisco.setPhone("+34666777222");
		contactRequestList.add(contactRequestFrancisco);

		HttpRequest<List<ContactRequest>> request = HttpRequest.POST("/user/contacts/1", contactRequestList);
		HttpResponse<ArrayList> response = client.toBlocking().exchange(request, ArrayList.class);

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
		Assertions.assertEquals(response.getBody().isPresent(), true);
		Assertions.assertEquals(response.getBody().get().size(), 2);
		
		LOG.info("END 5 - testCreateContatsUser1");
	}

	@Test
	@Order(6)
	public void testCreateContatsUser2() {
		LOG.info("INIT 6 - testCreateContatsUser2");
		
		List<ContactRequest> contactRequestList = new ArrayList<>();
		ContactRequest contactRequest = new ContactRequest();
		contactRequest.setContactName("Sara1");
		contactRequest.setPhone("+34666777111");
		contactRequestList.add(contactRequest);

		HttpRequest<List<ContactRequest>> request = HttpRequest.POST("/user/contacts/2", contactRequestList);
		HttpResponse<ArrayList> response = client.toBlocking().exchange(request, ArrayList.class);

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
		Assertions.assertEquals(response.getBody().isPresent(), true);
		Assertions.assertEquals(response.getBody().get().size(), 1);
		
		LOG.info("END 6 - testCreateContatsUser2");
	}

	@Test
	@Order(7)
	public void testUpdateContatsUser1() {
		LOG.info("INIT 7 - testUpdateContatsUser1");
		
		List<ContactRequest> contactRequestList = new ArrayList<>();
		ContactRequest contactRequestSara = new ContactRequest();
		contactRequestSara.setContactName("Sara1");
		contactRequestSara.setPhone("+34666777111");
		contactRequestList.add(contactRequestSara);

		ContactRequest contactRequestFrancisco = new ContactRequest();
		contactRequestFrancisco.setContactName("Francisco");
		contactRequestFrancisco.setPhone("+34666777222");
		contactRequestList.add(contactRequestFrancisco);

		HttpRequest<List<ContactRequest>> request = HttpRequest.PUT("/user/contacts/1", contactRequestList);
		HttpResponse<ArrayList> response = client.toBlocking().exchange(request, ArrayList.class);

		Assertions.assertEquals(HttpStatus.OK, response.getStatus());
		Assertions.assertEquals(response.getBody().isPresent(), true);
		Assertions.assertEquals(response.getBody().get().size(), 2);
		
		LOG.info("END 7 - testUpdateContatsUser1");
	}
	
	@Test
	@Order(8)
	public void testCreateContatsUserBadUserNotExist() {
		LOG.info("INIT 8 - testCreateContatsUserBadUserNotExist");
		
		List<ContactRequest> contactRequestList = new ArrayList<>();
		ContactRequest contactRequest = new ContactRequest();
		contactRequest.setContactName("Sara1");
		contactRequest.setPhone("+34666777111");
		contactRequestList.add(contactRequest);

		HttpRequest<List<ContactRequest>> request = HttpRequest.POST("/user/contacts/3", contactRequestList);
		
		try {
			HttpResponse<ArrayList> response = client.toBlocking().exchange(request, ArrayList.class);
		} catch(HttpClientResponseException e) {
			Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		}
		
		LOG.info("END 8 - testCreateContatsUserBadUserNotExist");
	}

	@Test
	@Order(9)
	public void testCreateContatsUserBad1Rep() {
		LOG.info("INIT 9 - testCreateContatsUserBad1Rep");
		
		List<ContactRequest> contactRequestList = new ArrayList<>();
		ContactRequest contactRequest = new ContactRequest();
		contactRequest.setContactName("Sara1");
		contactRequest.setPhone("+34666777111");
		contactRequestList.add(contactRequest);

		HttpRequest<List<ContactRequest>> request = HttpRequest.POST("/user/contacts/1", contactRequestList);

		try {
			client.toBlocking().exchange(request, ArrayList.class);
		} catch(HttpClientResponseException e) {
			Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		}
		
		LOG.info("END 9 - testCreateContatsUserBad1Rep");
	}

	@Test
	@Order(10)
	public void testCreateContatsUserBad1Phone() {
		LOG.info("INIT 10 - testCreateContatsUserBad1Phone");
		
		List<ContactRequest> contactRequestList = new ArrayList<>();
		ContactRequest contactRequest = new ContactRequest();
		contactRequest.setContactName("Sara1");
		contactRequest.setPhone("666777111");
		contactRequestList.add(contactRequest);

		HttpRequest<List<ContactRequest>> request = HttpRequest.POST("/user/contacts/1", contactRequestList);

		try {
			client.toBlocking().exchange(request, ArrayList.class);
		} catch(HttpClientResponseException e) {
			Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		}
		
		LOG.info("END 10 - testCreateContatsUserBad1Phone");
	}
	
	@Test
	@Order(11)
	public void testUpdateContatsUserBadUserNotExist() {
		LOG.info("INIT 11 - testUpdateContatsUserBadUserNotExist");
		
		List<ContactRequest> contactRequestList = new ArrayList<>();
		ContactRequest contactRequest = new ContactRequest();
		contactRequest.setContactName("Sara1");
		contactRequest.setPhone("+34666777111");
		contactRequestList.add(contactRequest);

		HttpRequest<List<ContactRequest>> request = HttpRequest.POST("/user/contacts/3", contactRequestList);
		
		try {
			HttpResponse<ArrayList> response = client.toBlocking().exchange(request, ArrayList.class);
		} catch(HttpClientResponseException e) {
			Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		}
		
		LOG.info("END 11 - testUpdateContatsUserBadUserNotExist");
	}

	@Test
	@Order(12)
	public void testUpdateContatsUserBadNotExistContact() {
		LOG.info("INIT 12 - testUpdateContatsUserBadNotExistContact");
		
		List<ContactRequest> contactRequestList = new ArrayList<>();
		ContactRequest contactRequest = new ContactRequest();
		contactRequest.setContactName("Sara");
		contactRequest.setPhone("+34666777333");
		contactRequestList.add(contactRequest);

		HttpRequest<List<ContactRequest>> request = HttpRequest.PUT("/user/contacts/1", contactRequestList);

		try {
			client.toBlocking().exchange(request, ArrayList.class);
		} catch(HttpClientResponseException e) {
			Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		}
		
		LOG.info("END 12 - testUpdateContatsUserBadNotExistContact");
	}

	@Test
	@Order(13)
	public void testUpdateContatsUserBadPhone() {
		LOG.info("INIT 13 - testUpdateContatsUserBadPhone");
		
		List<ContactRequest> contactRequestList = new ArrayList<>();
		ContactRequest contactRequest = new ContactRequest();
		contactRequest.setContactName("Sara");
		contactRequest.setPhone("666777111");
		contactRequestList.add(contactRequest);

		HttpRequest<List<ContactRequest>> request = HttpRequest.PUT("/user/contacts/1", contactRequestList);

		try {
			client.toBlocking().exchange(request, ArrayList.class);
		} catch(HttpClientResponseException e) {
			Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		}
		
		LOG.info("END 13 - testUpdateContatsUserBadPhone");
	}

	@Test
	@Order(14)
	public void testCreateContatsUserBad1ContactName() {
		LOG.info("INIT 14 - testCreateContatsUserBad1ContactName");
		
		List<ContactRequest> contactRequestList = new ArrayList<>();
		ContactRequest contactRequest = new ContactRequest();
		contactRequest.setContactName("");
		contactRequest.setPhone("34666777111");
		contactRequestList.add(contactRequest);

		HttpRequest<List<ContactRequest>> request = HttpRequest.POST("/user/contacts/1", contactRequestList);

		try {
			client.toBlocking().exchange(request, ArrayList.class);
		} catch(HttpClientResponseException e) {
			Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		}
		
		LOG.info("END 14 - testCreateContatsUserBad1ContactName");
	}

	@Test
	@Order(15)
	public void testGetContactsUser1() {
		LOG.info("INIT 15 - testGetContactsUser1");
		
		HttpRequest request = HttpRequest.GET("/user/contacts/1");
		HttpResponse<ArrayList> response = client.toBlocking().exchange(request, ArrayList.class);

		Assertions.assertEquals(HttpStatus.OK, response.getStatus());
		Assertions.assertEquals(response.getBody().isPresent(), true);
		Assertions.assertEquals(response.getBody().get().size(), 2);
		
		LOG.info("END 15 - testGetContactsUser1");
	}

	@Test
	@Order(16)
	public void testGetContactsUser2() {
		LOG.info("INIT 16 - testGetContactsUser2");
		
		HttpRequest request = HttpRequest.GET("/user/contacts/2");
		HttpResponse<ArrayList> response = client.toBlocking().exchange(request, ArrayList.class);

		Assertions.assertEquals(HttpStatus.OK, response.getStatus());
		Assertions.assertEquals(response.getBody().isPresent(), true);
		Assertions.assertEquals(response.getBody().get().size(), 1);
		
		LOG.info("END 16 - testGetContactsUser2");
	}

	@Test
	@Order(17)
	public void testGetContactsUserBadUserNotExist() {
		LOG.info("INIT 17 - testGetContactsUserBadUserNotExist");
		
		HttpRequest request = HttpRequest.GET("/user/contacts/3");

		try {
			client.toBlocking().exchange(request, ArrayList.class);
		} catch(HttpClientResponseException e) {
			Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		}
		
		LOG.info("END 17 - testGetContactsUserBadUserNotExist");
	}

	@Test
	@Order(18)
	public void testGetCommonContactsUser1AndUser2() {
		LOG.info("INIT 18 - testGetCommonContactsUser1AndUser2");
		
		HttpRequest request = HttpRequest.GET("/user/contacts/1/2");
		HttpResponse<ArrayList> response = client.toBlocking().exchange(request, ArrayList.class);

		Assertions.assertEquals(HttpStatus.OK, response.getStatus());
		Assertions.assertEquals(response.getBody().isPresent(), true);
		Assertions.assertEquals(response.getBody().get().size(), 1);
		
		LOG.info("END 18 - testGetCommonContactsUser1AndUser2");
	}

}
