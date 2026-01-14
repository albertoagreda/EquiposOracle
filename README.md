
PROYECTO: Gestión de Equipos de Fútbol con Oracle y Java
Autor: Alberto Agredano

====================================
DESCRIPCIÓN GENERAL
====================================
Este proyecto consiste en una aplicación Java que se conecta a una base de datos Oracle
y gestiona información de equipos de fútbol a partir de archivos XML.

Cada archivo XML representa un equipo y cada jugador se almacena en la base de datos
utilizando un tipo objeto de Oracle. El programa se maneja mediante un menú por consola.

====================================
TECNOLOGÍAS UTILIZADAS
====================================
- Java (IntelliJ IDEA)
- Oracle Database (XE)
- Oracle SQL Developer
- JDBC (ojdbc)
- XML (DOM)

====================================
ESTRUCTURA DEL PROYECTO
====================================
- src/
  - ConexionOracle.java  -> Lógica de conexión, base de datos y XML
  - Main.java            -> Menú principal y ejecución del programa

====================================
BASE DE DATOS
====================================
Se utiliza un TYPE de Oracle llamado JUGADOR_T con los campos:
- nombre
- dorsal
- demarcacion
- nacimiento

Cada equipo se guarda en una tabla distinta creada a partir de este TYPE.
El nombre de la tabla coincide con el nombre del archivo XML.

====================================
FUNCIONAMIENTO DEL PROGRAMA
====================================
Al ejecutar el programa se muestra un menú con las siguientes opciones:

1. Recorrer directorio y crear tablas
   - Lee una carpeta con archivos XML
   - Crea una tabla por cada equipo

2. Rellenar equipo desde XML
   - El usuario elige un equipo
   - Se leen los jugadores del XML y se insertan en Oracle

3. Mostrar equipo
   - Muestra por pantalla los jugadores del equipo seleccionado

4. Eliminar todas las tablas
   - Borra las tablas creadas por el programa

5. Salir
   - Cierra la conexión y termina la ejecución

====================================
CÓMO USAR EL PROYECTO
====================================
1. Abrir el proyecto en IntelliJ IDEA
2. Asegurarse de tener el driver ojdbc añadido al proyecto
3. Configurar correctamente el usuario y contraseña de Oracle
4. Ejecutar la clase Main
5. Seguir las opciones del menú

====================================
COMPROBACIÓN DE DATOS
====================================
Los datos pueden comprobarse desde Oracle SQL Developer utilizando sentencias SELECT
sobre las tablas creadas (por ejemplo: SELECT * FROM BARCELONA;).

====================================
OBJETIVO DE LA PRÁCTICA
====================================
- Trabajar con tipos objeto en Oracle
- Conectar Java con Oracle mediante JDBC
- Leer y procesar archivos XML
- Implementar un menú interactivo
- Aplicar una estructura de proyecto clara y organizada
