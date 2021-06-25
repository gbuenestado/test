# ESTRUCTURA DEL PROYECTO
- Paquetes del proyecto:
  - Controladores: es.bnext.controller
  - Interfaz servicio lógica negocio: es.bnext.service
  - Implementación servicio lógica negocio: es.bnext.service.impl
  - Interfaz repositorio: es.bnext.repository
  - Implementación repositorio: es.bnext.repository.impl
  - Entidades: es.bnext.entity
  - Excepeciones: es.bnext.exception
  - Dto para peticiones controlador: es.bnext.dto.in
  - Dto para respuesta controlador: es.bnext.dto.out
  - Configuracion de propiedades para uso en código: es.bnext.config
  - Validación de teléfono con neutrinoapi.net/phone-validate: es.bnext.facade

## TEST
- He realizdo test de integración para probar la funcionalidad en su conjunto
- Prueban tanto los casos ok como los casos de error
- Seguido bajo la práctica TDD, primero crear el test y despues desarrollar la funcionalidad
- Pensado para ejecutar bajo BBDD en memoria H2, para que funcione habría que hacer un restart al servidor para su correcta ejecución

# SWAGGER
- Disponible la url de swagger con interfaz gráfica: http://localhost:8080/swagger/views/swagger-ui/index.html
- Disponible la url de swagger con la definición de los servicios: http://localhost:8080/swagger/test-users-bnex-0.1.yml

# EJECUCIÓN EN DOCKER
- test>gradlew build -x test
- test>docker-compose up -d