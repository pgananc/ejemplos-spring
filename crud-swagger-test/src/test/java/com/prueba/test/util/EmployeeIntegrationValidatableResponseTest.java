package com.prueba.test.util;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import static org.hamcrest.Matchers.*;
import com.prueba.TestsServiceApplication;
import com.prueba.modelo.Employee;

import io.restassured.response.ValidatableResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestsServiceApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles({ "integration" })
@TestMethodOrder(OrderAnnotation.class)
public class EmployeeIntegrationValidatableResponseTest {
	@Value("${local.server.port}")
	private int ports;

	@BeforeAll
	public void setUp() {
		port = ports;
		baseURI = "http://localhost/api/v1/employees"; // Will result in "http://localhost:xxxx/demo"
	}

	@Test
	@Order(1)
	public void post_newEmployee_returnsCreatedEmployee_201() {
		Employee newEmployee = Employee.builder().id(1L).firstName("Pablo").lastName("Ganan")
				.birthDate(LocalDate.of(2000, 01, 10)).build();

		ValidatableResponse response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).body(newEmployee).when().post("/create").then();

		System.out.println("'post_Employee_returnsCreatedEmployee_201()' response:\n" + response.extract().asString());

		response.assertThat().statusCode(HttpStatus.CREATED.value()).body("id", equalTo(1))
				.body("firstName", equalTo("Pablo")).body("lastName", equalTo("Ganan"))
				.body("birthDate", equalTo("2000-01-10"));
	}

	@Test
	@Order(2)
	public void post_newEmployee_Returns_BadRequest_400() {
		Employee newEmployee = Employee.builder().id(2L).firstName("Juan").lastName("")
				.birthDate(LocalDate.of(2000, 01, 10)).build();

		ValidatableResponse response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).body(newEmployee).when().post("/create").then();

		System.out.println("'post_newEmployee_Returns_BadRequest_400()' response:\n" + response.extract().asString());

		response.assertThat().statusCode(HttpStatus.BAD_REQUEST.value()).body("errorMessage",
				containsString("'lastName' field was empty"));
	}

	@Test
	@Order(3)
	public void put_updateEmployee_returnsUpdatedEmployee_202() {
		Employee updateEmployee = Employee.builder().id(1L).firstName("Pablo").lastName("Ganan")
				.birthDate(LocalDate.of(1984, 01, 10)).build();

		ValidatableResponse response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).body(updateEmployee).when().put("/update/1").then();

		System.out.println(
				"'put_updateEmployee_returnsUpdatedEmployee_202()' response:\n" + response.extract().asString());

		response.assertThat().statusCode(HttpStatus.ACCEPTED.value()).body("id", equalTo(1))
				.body("firstName", equalTo("Pablo")).body("lastName", equalTo("Ganan"))
				.body("birthDate", equalTo("1984-01-10"));
	}

	@Test
	@Order(4)
	public void get_EmployeeById_returnsNotFound_404() {

		ValidatableResponse response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).when().get("/22").then();

		System.out.println("'get_EmployeeById_returnsNotFound_404()' response:\n" + response.extract().asString());

		response.assertThat().statusCode(HttpStatus.NOT_FOUND.value()).body("errorMessage",
				containsString("404 Employee with Id (22) not found"));
	}

	@Test
	@Order(5)
	public void get_EmployeeById_returnsEmployee_200() {

		ValidatableResponse response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).when().get("/1").then();

		System.out.println("'getEmployeeById_returnsEmployee_200()' response:\n" + response.extract().asString());

		response.assertThat().statusCode(HttpStatus.OK.value()).body("id", equalTo(1))
				.body("firstName", equalTo("Pablo")).body("lastName", equalTo("Ganan"))
				.body("birthDate", equalTo("1984-01-10"));
	}

	@Test
	@Order(6)
	public void get_AllEmployees_returnsAllEmployees_200() {

		ValidatableResponse response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).when().get("").then();

		System.out.println("'getAllEmployees_returnsAllEmployees_200()' response:\n" + response.extract().asString());

		response.assertThat().statusCode(HttpStatus.OK.value()).body(containsString("Ganan"));
	}

	@Test
	@Order(7)
	public void delete_vehicle_returnsNoContent_204() {

		ValidatableResponse response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).when().delete("/1").then();

		System.out.println("'delete_employee_returnsNoContent_204()' response:\n" + response.extract().asString());

		response.assertThat().statusCode(HttpStatus.NO_CONTENT.value());
	}
}
