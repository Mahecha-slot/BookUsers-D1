DOCUMENTACIÓN DEL PROYECTO – GESTOR DE USUARIOS

1. Integrantes del grupo

Manuel Jose Londoño Vergara
Mateo Mahecha Gutierrez

Programa académico:Ingeniería Informática – UNIPAZ.

2. Lenguaje de programación

Java: Es el lenguaje de programación utilizado para desarrollar el proyecto. Se empleó para implementar la lógica del sistema, administrar los usuarios, validar información y gestionar las diferentes funcionalidades.

3. Entorno de desarrollo (IDE)

IntelliJ IDEA: Es el entorno de desarrollo integrado utilizado para escribir, organizar, ejecutar y depurar el código fuente del proyecto.

4. Interfaz gráfica

Java Swing: Es la biblioteca utilizada para desarrollar la interfaz gráfica del programa. Permite crear ventanas, botones, formularios, listas y otros componentes con los que el usuario puede interactuar.

5. Kit de desarrollo

JDK 27: Se utilizó el Java Development Kit (JDK) versión 27, que proporciona las herramientas necesarias para compilar, ejecutar y desarrollar aplicaciones en Java.

6. Estructura del proyecto

6.1. Usuarios.java
Es la clase encargada de representar a cada usuario registrado. Contiene sus atributos, como identificación, nombres, apellidos, datos de contacto, nombre de usuario y fotografía. También permite acceder a la información mediante métodos Getters.

6.2. GestorUsuarios.java
Es la clase encargada de administrar los usuarios del sistema. Utiliza una estructura ArrayList para almacenar los registros y dispone de métodos para agregar, consultar y buscar usuarios.

6.3. VentanaPrincipal.java
Es la ventana principal del programa. Desde ella se puede acceder a las diferentes funcionalidades mediante botones, como crear usuarios, consultar registros y visualizar los créditos de los creadores.

6.4. VentanaCrearUsuario.java
Es la ventana encargada del registro de nuevos usuarios. Contiene los campos del formulario, realiza validaciones de información, permite seleccionar una fotografía y guarda los datos mediante el gestor de usuarios.

6.5. VentanaVerUsuarios.java
Es la ventana destinada a consultar los usuarios registrados. Permite visualizar y seleccionar usuarios, consultar su información y generar un documento PDF con los datos del usuario seleccionado.

6.6. PanelConFondo.java
Es una clase que permite personalizar los paneles de la interfaz gráfica mediante imágenes de fondo, mejorando la presentación visual del programa.

7. Generación de documentos PDF

OpenPDF: Es la biblioteca utilizada para generar documentos PDF. Permite crear fichas individuales con la información y fotografía del usuario seleccionado.