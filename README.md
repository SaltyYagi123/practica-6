# Practica 6 - PAT: Testing Aplicaciones Spring Boot

## Gitpod:

(https://gitpod.io/#https://github.com/SaltyYagi123/practica-6)

## Objetivos: 
Dado un desarollo de SpringBoot, es necesario añadir tests a las siguientes clases: 
- DNI y Telefono en formato Unit Tests. Cada clase con su metodo y casuisticas a probar
	
- ProcessController (End to End Tests) (2 endpoints, incluyendo Legacy)

## Pruebas jUnit
En la carpeta "model" podrá encontrar 2 ficheros "DNITest.java" y "TelefonoTest.java", en los cuales se han desarrollado los tests correspondientes. 

- DNI -> Se ha analizado los patterns y secuencias numericas declaradas en el metodo de validación incorporada en la clase de Java del DNI 
- Teléfono -> Se han realizado las correspondientes pruebas para verificar si se pueden usar con o sin prefijo internacional. Todo se verifica mediante el metodo de valiación. 

## Pruebas E2E (End to End)

Se va a validar que la clase ProcessControllerTest.java, ubicada en la carpeta "controller" del directorio corresponda a los Tests y funcione adecuadamente. Para ello: 
- Se debe de verificar el status
- Se deben de realizar dos tipos de pruebas (OK y KO), teniendo un total de 4 anotaciones @Test
- Se comprueba que al enviar los datos correctos se obtiene en el BODY HTML "OK", y su HTTPStatus correspondiente. 

- Igualmente, se introducen los DNI's, Nombres y Numeros de telefonos para efectuar a su vez los unit tests hechos previamente 
	- Es importante anotar que en caso de que estos valores sean Nulos, se debería recibir un BAD REQUEST ya que no se ha recibido lo apropiado y los datos en su totalidad. 
