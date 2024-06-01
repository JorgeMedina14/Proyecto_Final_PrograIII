# Mi Página Web

Este proyecto es una página web que utiliza Tailwind CSS para los estilos y tiene funcionalidades de lista de productos y carrito de compras.

## Estructura del proyecto

El proyecto tiene la siguiente estructura de archivos:

```
mi-pagina-web
├── src
│   ├── js
│   │   ├── main.js
│   │   ├── products.js
│   │   └── cart.js
│   ├── css
│   │   └── styles.css
│   └── index.html
├── public
│   ├── js
│   │   ├── main.js
│   │   ├── products.js
│   │   └── cart.js
│   ├── css
│   │   └── styles.css
│   └── index.html
├── package.json
├── tailwind.config.js
└── README.md
```

## Archivos principales

- `src/js/main.js`: Este archivo contiene el código JavaScript principal de la página web. Aquí se pueden agregar funciones y lógica adicional para interactuar con la API y manipular los elementos de la página.

- `src/js/products.js`: Este archivo contiene el código JavaScript para obtener la lista de productos mediante una petición AJAX a la ruta `http://localhost:8081/productos`. Aquí se pueden implementar las funciones necesarias para mostrar la lista de productos en la página principal, incluyendo la paginación y el filtrado por categorías.

- `src/js/cart.js`: Este archivo contiene el código JavaScript para manejar el carrito de compras. Aquí se pueden implementar las funciones necesarias para agregar productos al carrito, realizar el pedido y persistir los datos en la API utilizando la ruta `http://localhost:8081/carrito`.

- `src/css/styles.css`: Este archivo contiene los estilos CSS básicos para la página web. Puedes reemplazar estos estilos por los estilos proporcionados por Tailwind CSS.

- `src/index.html`: Este archivo es la página principal de la aplicación. Aquí se puede agregar el marcado HTML necesario para mostrar la lista de productos y el carrito de compras. También se deben incluir los enlaces a los archivos CSS y JavaScript correspondientes.

## Archivos de producción

- `public/js/main.js`: Este archivo es una copia del archivo `src/js/main.js`. Puedes utilizarlo si deseas tener una versión compilada y optimizada del código JavaScript para producción.

- `public/js/products.js`: Este archivo es una copia del archivo `src/js/products.js`. Puedes utilizarlo si deseas tener una versión compilada y optimizada del código JavaScript para producción.

- `public/js/cart.js`: Este archivo es una copia del archivo `src/js/cart.js`. Puedes utilizarlo si deseas tener una versión compilada y optimizada del código JavaScript para producción.

- `public/css/styles.css`: Este archivo es una copia del archivo `src/css/styles.css`. Puedes utilizarlo si deseas tener una versión compilada y optimizada de los estilos CSS para producción.

- `public/index.html`: Este archivo es una copia del archivo `src/index.html`. Puedes utilizarlo si deseas tener una versión compilada y optimizada de la página principal para producción.

## Otros archivos

- `package.json`: Este archivo es el archivo de configuración de npm. Aquí se especifican las dependencias del proyecto y los scripts para ejecutar tareas como la compilación de los archivos JavaScript y CSS.

- `tailwind.config.js`: Este archivo es el archivo de configuración de Tailwind CSS. Aquí se pueden personalizar los estilos y configuraciones de Tailwind CSS según las necesidades del proyecto.

## Instrucciones de instalación

1. Clona este repositorio en tu máquina local.
2. Navega hasta la carpeta `mi-pagina-web`.
3. Ejecuta el comando `npm install` para instalar las dependencias del proyecto.
4. Ejecuta el comando `npm run build` para compilar los archivos JavaScript y CSS para producción.
5. Abre el archivo `public/index.html` en tu navegador para ver la página web.

¡Disfruta de tu página web con lista de productos y carrito de compras utilizando Tailwind CSS!