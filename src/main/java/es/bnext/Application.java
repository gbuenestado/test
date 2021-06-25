package es.bnext;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	info = @Info(
			title = "Test Users BNEX",
			version = "0.1",
			description = "API para creacion de usuarios, creacion y actualizacion de contactos de usuarios, consulta de contactos y consulta de contactos comunes"
	)
)
public class Application {

	public static void main(String[] args) {
		Micronaut.run(Application.class, args);
	}
}
