// File: /mi-pagina-web/public/js/products.js

// Function to fetch the list of products from the API
async function fetchProducts() {
  try {
    const response = await fetch(`http://localhost:8081/productos?page=${getRandomInteger(100)}&size=${getRandomInteger(100)}&categoria=${getRandomAlphanumeric(8)}`);
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching products:', error);
    return [];
  }
}

// Function to display the list of products on the page
async function displayProducts() {
  const productsContainer = document.getElementById('products-container');
  const products = await fetchProducts();

  // Clear the products container
  productsContainer.innerHTML = '';

  // Loop through the products and create HTML elements to display them
  products.forEach(product => {
    const productElement = document.createElement('div');
    productElement.classList.add('product');
    productElement.innerHTML = `
      <h3>${product.nombre}</h3>
      <p>Precio: ${product.precio}</p>
      <p>Categoría: ${product.categoria}</p>
      <button onclick="addToCart(${product.id})">Agregar al carrito</button>
    `;
    productsContainer.appendChild(productElement);
  });
}

// Function to add a product to the cart
function addToCart(productId) {
  // Implement logic to add the product to the cart
  // You can use the cart.js file for this functionality
}

// Function to generate a random integer
function getRandomInteger(max) {
  return Math.floor(Math.random() * max) + 1;
}

// Function to generate a random alphanumeric string
function getRandomAlphanumeric(length) {
  let result = '';
  const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  const charactersLength = characters.length;
  for (let i = 0; i < length; i++) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }
  return result;
}

// Call the displayProducts function when the page loads
window.addEventListener('load', displayProducts);