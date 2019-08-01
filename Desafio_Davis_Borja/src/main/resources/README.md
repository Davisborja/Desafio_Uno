Este proyecto es la solución al desafio en nivel 3.

Primero debes levantar este proyecto y tambien la api de periodos (GDD).
Puedes configurar la ruta que se consumira del generador de datos en el properties de este proyecto este archivo se encuentra en:
\src\main\resources\application.properties
Por defecto es: URL_ORIGEN_DATOS = http://localhost:9090/periodos/api

Luego para poder probar el resultado del servicio se debe enviar un GET a la url:
localhost:9091/periodos/api

El header debe contener:
Accept : application/json
Content-Type : application/json

Se puede revisar mediante Swagger en:
http://localhost:9091/periodos/swagger-ui.html