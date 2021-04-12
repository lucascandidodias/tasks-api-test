package br.ce.waquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {

	@BeforeClass
	public static void setUp() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")	
		.then()
			.statusCode(200);
		;
	}
	
	@Test
	public void deveAdicionarTarefasComSucesso() {
		RestAssured.given()
			.body("{\"task\": \"teste via api\",\"dueDate\": \"2022-12-12\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")	
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void NaoDeveAdicionarTarefasInvalida() {
		RestAssured.given()
			.body("{\"task\": \"teste via api\",\"dueDate\": \"2010-12-12\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")	
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
			
		;
	}
}


