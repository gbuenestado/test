# ESTRUCTURA DEL PROYECTO
- Paquetes del proyecto:
  - Controladores: es.bnext.controller
  - Interfaz servicio l�gica negocio: es.bnext.service
  - Implementaci�n servicio l�gica negocio: es.bnext.service.impl
  - Interfaz repositorio: es.bnext.repository
  - Implementaci�n repositorio: es.bnext.repository.impl
  - Entidades: es.bnext.entity
  - Excepeciones: es.bnext.exception
  - Dto para peticiones controlador: es.bnext.dto.in
  - Dto para respuesta controlador: es.bnext.dto.out
  - Configuracion de propiedades para uso en c�digo: es.bnext.config
  - Validaci�n de tel�fono con neutrinoapi.net/phone-validate: es.bnext.facade

## TEST
- He realizdo test de integraci�n para probar la funcionalidad en su conjunto
- Prueban tanto los casos ok como los casos de error
- Seguido bajo la pr�ctica TDD, primero crear el test y despues desarrollar la funcionalidad
- Pensado para ejecutar bajo BBDD en memoria H2, para que funcione habr�a que hacer un restart al servidor para su correcta ejecuci�n

# SWAGGER
- Disponible la url de swagger con interfaz gr�fica: http://localhost:8080/swagger/views/swagger-ui/index.html
- Disponible la url de swagger con la definici�n de los servicios: http://localhost:8080/swagger/test-users-bnex-0.1.yml

# EJECUCI�N EN DOCKER
- test>gradlew build -x test
- test>docker-compose up -d