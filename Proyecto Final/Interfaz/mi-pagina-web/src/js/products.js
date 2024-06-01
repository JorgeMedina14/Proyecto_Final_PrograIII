async function fetchProducts(page = 0, size = 10, categoria) {
  try {
    const response = await fetch(
      `http://localhost:8081/productos?page=${page}&size=${size}${
        categoria ? `&categoria=${categoria}` : ""
      }`
    );
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching products:", error);
    return [];
  }
}

async function displayProducts() {
  const productsContainer = document.getElementById("product-list");
  const products = await fetchProducts();
  productsContainer.innerHTML = "";
  products.content.forEach((product) => {
    const productElement = document.createElement("article");
    productElement.classList.add(
      "rounded-xl",
      "bg-white",
      "p-3",
      "shadow-lg",
      "hover:shadow-xl",
      "hover:transform",
      "hover:scale-105",
      "duration-300"
    );
    productElement.innerHTML = `
      <a href="#">
        <div class="mt-1 p-2">
          <div class="flex items-start justify-between">
          <h2 class="text-slate-700">${product.nombre}</h2>
          <span class="inline-flex items-center rounded-md bg-green-50 px-2 py-1 text-xs font-medium text-gray-600 ring-1 ring-inset ring-gray-500/10">${
            product.stock
          } disponibles</span>
          </div>
          <p class="mt-1 text-sm text-slate-400">${product.categoria}</p>
          <div class="mt-3 flex items-end justify-between">
            <p class="text-lg font-bold text-blue-500">Q. ${product.precio}</p>
            <div class="flex items-center space-x-1.5 rounded-lg bg-blue-500 px-4 py-1.5 text-white duration-100 hover:bg-blue-600">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="h-4 w-4">
                <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 00-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 00-16.536-1.84M7.5 14.25L5.106 5.272M6 20.25a.75.75 0 11-1.5 0 .75.75 0 011.5 0zm12.75 0a.75.75 0 11-1.5 0 .75.75 0 011.5 0z" />
              </svg>
              <button class="text-sm" id="add-to-cart"
                onClick='addToCart(${JSON.stringify(
                  product
                )})'>Añadir al carrito</button>
            </div>
          </div>
        </div>
      </a>
    `;
    productsContainer.appendChild(productElement);
  });
}

function handleProducts() {
  displayProducts();
}

export default { handleProducts, fetchProducts, displayProducts };
