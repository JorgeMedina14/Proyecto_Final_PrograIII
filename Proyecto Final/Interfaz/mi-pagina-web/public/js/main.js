// File: /mi-pagina-web/public/js/main.js

// Aquí puedes agregar tu código JavaScript principal para interactuar con la API y manipular los elementos de la página.

// Ejemplo de función para obtener la lista de productos mediante una petición AJAX
function getProducts() {
  const url = 'http://localhost:8081/productos?page=1&size=10&categoria=alphanumeric';
  
  // Realizar petición AJAX
  fetch(url)
    .then(response => response.json())
    .then(data => {
      // Manipular los datos de la respuesta
      // Aquí puedes mostrar la lista de productos en la página principal
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

// Ejemplo de función para agregar un producto al carrito
function addToCart(productId) {
  const url = 'http://localhost:8081/carrito';
  
  // Realizar petición AJAX para agregar el producto al carrito
  fetch(url, {
    method: 'POST',
    body: JSON.stringify({ productId }),
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(response => response.json())
    .then(data => {
      // Manipular los datos de la respuesta
      // Aquí puedes actualizar el carrito de compras en la página
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

// Ejemplo de función para realizar el pedido
function placeOrder() {
  const url = 'http://localhost:8081/carrito/pedido';
  
  // Realizar petición AJAX para realizar el pedido
  fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(response => response.json())
    .then(data => {
      // Manipular los datos de la respuesta
      // Aquí puedes mostrar el resumen del pedido y actualizar el estado en la página
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

// Ejemplo de función para mostrar la lista de productos en la página principal
function showProducts(products) {
  // Aquí puedes generar el HTML para mostrar la lista de productos en la página
}

// Ejemplo de función para actualizar el carrito de compras en la página
function updateCart(cart) {
  // Aquí puedes generar el HTML para mostrar el carrito de compras en la página
}

// Ejemplo de función para mostrar el resumen del pedido y actualizar el estado en la página
function showOrderSummary(order) {
  // Aquí puedes generar el HTML para mostrar el resumen del pedido y el estado en la página
}

// Ejemplo de función para inicializar la página
function init() {
  // Obtener la lista de productos al cargar la página
  getProducts();
  
  // Agregar eventos a los botones de agregar al carrito y realizar el pedido
  const addToCartButtons = document.querySelectorAll('.add-to-cart-button');
  addToCartButtons.forEach(button => {
    button.addEventListener('click', () => {
      const productId = button.dataset.productId;
      addToCart(productId);
    });
  });
  
  const placeOrderButton = document.querySelector('#place-order-button');
  placeOrderButton.addEventListener('click', placeOrder);
}

// Inicializar la página al cargar el DOM
document.addEventListener('DOMContentLoaded', init);