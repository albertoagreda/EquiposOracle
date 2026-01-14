
⚽ Gestión de Equipos de Fútbol con Oracle y Java  
### Práctica de Base de Datos – Orientación a Objetos  
**Autor:** Alberto Agredano

Aplicación desarrollada en Java que permite gestionar equipos de fútbol a partir de
archivos XML, almacenando la información en una base de datos Oracle mediante el uso
de tipos objeto y tablas orientadas a objetos.

---

## 📌 Descripción General

El proyecto permite leer archivos XML que representan equipos de fútbol y almacenar
sus jugadores en Oracle. Cada jugador se guarda como un objeto y cada equipo dispone
de su propia tabla en la base de datos.

La aplicación se ejecuta desde consola y se controla mediante un menú interactivo,
facilitando la creación de tablas, la carga de datos y la visualización de la información.

---

## ✥ Funcionalidades

- Creación de un tipo objeto `JUGADOR_T` en Oracle  
- Creación automática de una tabla por cada equipo  
- Lectura de archivos XML mediante DOM  
- Inserción de jugadores en la base de datos  
- Visualización de los jugadores de un equipo  
- Eliminación de todas las tablas creadas  
- Menú interactivo por consola  

---

## 🧱 Diseño de la Base de Datos

### Tipo Objeto: JUGADOR_T
El tipo `JUGADOR_T` representa a un jugador de fútbol y contiene los siguientes atributos:

- nombre → Nombre del jugador  
- dorsal → Número del jugador  
- demarcacion → Posición en el campo  
- nacimiento → Fecha de nacimiento  

Cada tabla de equipo se crea utilizando este tipo objeto, lo que permite trabajar con
orientación a objetos dentro de Oracle.

---

## 🧠 Funcionamiento del Programa

Al iniciar la aplicación se muestra un menú con las siguientes opciones:

1️⃣ Recorrer directorio y crear tablas  
- Lee una carpeta con archivos XML  
- Crea una tabla por cada equipo  

2️⃣ Rellenar equipo desde XML  
- El usuario selecciona un equipo  
- Se cargan automáticamente todos sus jugadores  

3️⃣ Mostrar equipo  
- Muestra por consola los jugadores almacenados  

4️⃣ Eliminar todas las tablas  
- Borra todas las tablas creadas por el programa  

5️⃣ Salir  
- Cierra la conexión y finaliza la aplicación  

---

## 🔗 Lectura de Archivos XML

Los archivos XML se procesan utilizando la API DOM de Java. El programa recorre cada
nodo `<jugador>` y extrae sus datos para crear los objetos correspondientes en Oracle.

Esta técnica permite una lectura clara y estructurada de la información.

---

## 🔧 Conexión con Oracle

La conexión con la base de datos se realiza mediante JDBC, utilizando el driver `ojdbc`.
Todas las operaciones de base de datos están centralizadas en la clase `ConexionOracle`.

---

## 📁 Estructura del Proyecto

EquiposOracle/
 ├── src/
 │   ├── ConexionOracle.java   → Conexión y lógica de base de datos  
 │   └── Main.java             → Menú principal del programa  
 └── README.txt

---

## 🧪 Pruebas Realizadas

- Creación de tablas desde una carpeta de XML  
- Inserción correcta de jugadores  
- Visualización de equipos completos  
- Comprobación de datos desde SQL Developer  

---

## ✔️ Buenas Prácticas Aplicadas

- Uso de tipos objeto en Oracle  
- Código organizado por responsabilidades  
- Uso de PreparedStatement  
- Control de errores básicos  
- Nombres claros y descriptivos  

---

## 📄 Autor
**Alberto Agredano**
