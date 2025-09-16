# 4Sabores_NAELApp - Proyecto (Java Swing)

**Descripción**
Aplicación de tienda de comida rápida (interfaz gráfica con Swing) con CRUD para varias entidades y estructura de proyecto. 
Colores de la UI: azul, blanco y negro.

**Lo que incluye**
- Inicio de sesión con 3 usuarios precreados:
  - Usuario: `Nehemias`  | Contraseña: `Nehemias`
  - Usuario: `Anderson`  | Contraseña: `Anderson`
  - Usuario: `Erick`     | Contraseña: `Erick`
- CRUD funcional para: `Usuario`, `Producto`.
- Pestañas/skeletons para: `Cliente`, `Categoria`, `Venta`, `VentaDetalle`, `CarritoVenta`, `Delivery`.
- Persistencia simple con serialización (archivos `.dat` en la carpeta `data/`).

**Cómo ejecutar**
1. Tener JDK instalado (Java 8+).
2. Compilar:
   ```bash
   javac -d out src/app/*.java src/app/models/*.java src/app/dao/*.java src/app/ui/*.java
   ```
3. Ejecutar:
   ```bash
   java -cp out app.Main
   ```
4. Los archivos de datos se crearán en la carpeta `data/` automáticamente.

**Subir a GitHub**
- Crea un repositorio (público o privado).
- Sube todo el contenido del proyecto.
- Si el repositorio es privado, agrega como colaborador a `dmamanipar`.

**Capturas de pantalla**
- Para las capturas que debes entregar (requisito 3), abre la aplicación y toma pantallazos de las pestañas `Usuario` y `Producto` mostrando la tabla y los formularios al crear/editar.

**Notas**
- Implementación hecha con Swing para evitar dependencias externas; está lista para compilar directamente con `javac`.
- Si quieres que implemente el CRUD completo para todas las tablas (Venta, Carrito, etc.) con lógica de negocio más avanzada, lo hago en la siguiente iteración.



## Estructura nueva añadida
Se añadieron carpetas `service/` y `util/` y se integraron constantes de UI para homogeneizar colores.
