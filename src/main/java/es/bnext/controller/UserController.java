package es.bnext.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bnext.dto.in.ContactRequest;
import es.bnext.dto.in.UserRequest;
import es.bnext.dto.out.ContactResponse;
import es.bnext.dto.out.UserResponse;
import es.bnext.exception.ContactNotExist;
import es.bnext.exception.UserNotExist;
import es.bnext.facade.PhoneValidatorFacade;
import es.bnext.service.UserService;
import es.bnext.service.impl.UserServiceImpl;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.validation.validator.Validator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@ExecuteOn(TaskExecutors.IO) 
@Controller("/user")
public class UserController {

	private final static Logger LOG = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;
	private final PhoneValidatorFacade phoneValidatorFacade;

	@Inject
	Validator validator;

	public UserController(UserServiceImpl userService, PhoneValidatorFacade phoneValidatorFacade) { 
		this.userService = userService;
		this.phoneValidatorFacade = phoneValidatorFacade;
	}

	@Operation(
			method = "POST",
			summary = "Create user",
			description = "Create user"
	)
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Invalid Phone. Parameters null or empty")
	@Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public HttpResponse<?> createUser(@Body UserRequest userRequest) {
		LOG.info("INIT - create: " + userRequest);
		HttpResponse<?> result = null;

		try {
			Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
			if(violations.isEmpty()) {

				if(phoneValidatorFacade.validate(userRequest.getPhone())) {
					UserResponse userResponse = userService.createUser(userRequest);
					result = HttpResponse.created(userResponse);

				} else {
					LOG.info("badRequest El telefono " + userRequest.getPhone() + " no es valido");
					result = HttpResponse.badRequest("El telefono " + userRequest.getPhone() + " no es valido");
				}

			} else {
				StringBuilder sb = new StringBuilder();

				Iterator<ConstraintViolation<UserRequest>> it = violations.iterator();
				while(it.hasNext()) {
					ConstraintViolation<UserRequest> violation = it.next();
					sb.append(violation.getMessage());
				}
				
				LOG.info("badRequest " + sb.toString());
				result = HttpResponse.badRequest(sb.toString());
			}

		} catch (Exception e) {
			LOG.error("ERROR: ", e);
			result = HttpResponse.serverError();
		}

		LOG.info("END - createUser: " + result);
		return result;
	}

	@Operation(
			method = "POST",
			summary = "Create contact user",
			description = "Create contact user"
	)
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Invalid Phone. Parameters null or empty. User not exist. Contact exist")
	@Post(uri = "/contacts/{userId}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public HttpResponse<?> createContact(Long userId, @Body List<ContactRequest> contactRequestList) {
		LOG.info("INIT - createContact: " + contactRequestList);
		HttpResponse<?> result = null;

		try {
			Set<ConstraintViolation<ContactRequest>> violations = new HashSet<>();
			for(ContactRequest contactRequest : contactRequestList) {
				Set<ConstraintViolation<ContactRequest>> v = validator.validate(contactRequest);
				violations.addAll(v);

				if(!phoneValidatorFacade.validate(contactRequest.getPhone())) {
					LOG.info("badRequest El telefono " + contactRequest.getPhone() + " no es valido");
					result = HttpResponse.badRequest("El telefono " + contactRequest.getPhone() + " no es valido");
					break;
				}
			}

			if(result == null) {
				if(violations.isEmpty()) {
					
					List<ContactResponse> contactResponseList = userService.createUserContacts(userId, contactRequestList);
					result = HttpResponse.created(contactResponseList);

				} else {
					StringBuilder sb = new StringBuilder();

					Iterator<ConstraintViolation<ContactRequest>> it = violations.iterator();
					while(it.hasNext()) {
						ConstraintViolation<ContactRequest> violation = it.next();
						sb.append(violation.getMessage());
					}

					LOG.info("badRequest " + sb.toString());
					result = HttpResponse.badRequest(sb.toString());
				}
			}

		} catch (UserNotExist e) {
			LOG.error("ERROR: ", e);
			result = HttpResponse.badRequest(e.getMessage());

		} catch (Exception e) {
			LOG.error("ERROR: ", e);
			result = HttpResponse.serverError();
		}

		LOG.info("END - createContact: " + result);
		return result;
	}

	@Operation(
			method = "PUT",
			summary = "Update contact user",
			description = "Update contact user. Only update the contactName"
	)
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Invalid Phone. Parameters null or empty. User not exist. Contact not exist")
	@Put(uri = "/contacts/{userId}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public HttpResponse<?> updateContact(Long userId, @Body List<ContactRequest> contactRequestList) {
		LOG.info("INIT - updateContact: " + contactRequestList);
		HttpResponse<?> result = null;

		try {
			Set<ConstraintViolation<ContactRequest>> violations = new HashSet<>();
			for(ContactRequest contactRequest : contactRequestList) {
				violations.addAll(validator.validate(contactRequest));

				if(!phoneValidatorFacade.validate(contactRequest.getPhone())) {
					LOG.info("badRequest El telefono " + contactRequest.getPhone() + " no es valido");
					result = HttpResponse.badRequest("El telefono " + contactRequest.getPhone() + " no es valido");
					break;
				}
			}

			if(result == null) {
				if(violations.isEmpty()) {
					List<ContactResponse> contactResponseList = userService.updateUserContacts(userId, contactRequestList);
					result = HttpResponse.ok(contactResponseList);

				} else {
					StringBuilder sb = new StringBuilder();

					Iterator<ConstraintViolation<ContactRequest>> it = violations.iterator();
					while(it.hasNext()) {
						ConstraintViolation<ContactRequest> violation = it.next();
						sb.append(violation.getMessage());
					}

					LOG.info("badRequest " + sb.toString());
					result = HttpResponse.badRequest(sb.toString());
				}
			}

		} catch (UserNotExist | ContactNotExist e) {
			LOG.error("ERROR: ", e);
			result = HttpResponse.badRequest(e.getMessage());

		} catch (Exception e) {
			LOG.error("ERROR: ", e);
			result = HttpResponse.serverError();
		}

		LOG.info("END - updateContact: " + result);
		return result;
	}
	
	@Operation(
			method = "GET",
			summary = "Get common contacts",
			description = "Get common contacts"
	)
    @ApiResponse(responseCode = "200", description = "OK")
	@Get(uri = "/contacts/{userIdA}/{userIdB}", produces = MediaType.APPLICATION_JSON)
	public HttpResponse<?> getCommonContacts(Long userIdA, Long userIdB) {
		LOG.info("INIT - getCommonContacts: " + userIdA + " " + userIdB);
		HttpResponse<?> result = null;

		try {
			List<ContactResponse> contactResponseList = userService.getCommonContacts(userIdA, userIdB);
			result = HttpResponse.ok(contactResponseList);

		} catch (Exception e) {
			LOG.error("ERROR: ", e);
			result = HttpResponse.serverError();
		}

		LOG.info("END - getCommonContacts: " + result);
		return result;
	}

	@Operation(
			method = "GET",
			summary = "Get all contacts from user",
			description = "Get all contacts from user"
	)
    @ApiResponse(responseCode = "200", description = "OK")
	@Get(uri = "/contacts/{userId}", produces = MediaType.APPLICATION_JSON)
	public HttpResponse<?> getAllContacts(Long userId) {
		LOG.info("INIT - getAllContacts: " + userId);
		HttpResponse<?> result = null;

		try {
			List<ContactResponse> contactResponseList = userService.getAllContacts(userId);
			result = HttpResponse.ok(contactResponseList);

		} catch (UserNotExist e) {
			LOG.error("ERROR: ", e);
			result = HttpResponse.badRequest(e.getMessage());

		} catch (Exception e) {
			LOG.error("ERROR: ", e);
			result = HttpResponse.serverError();
		}

		LOG.info("END - getAllContacts: " + result);
		return result;
	}

}
