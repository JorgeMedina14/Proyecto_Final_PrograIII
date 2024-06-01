async function addToCart(product) {
  let response = await fetch("http://localhost:8081/carrito/2");
  let carrito = await response.json();
  console.log("Success:", carrito);

  const index = carrito.productos.findIndex(
    (item) => item.producto.id === product.id
  );
  if (index !== -1) {
    carrito.productos[index].cantidad++;
  } else {
    carrito.productos.push({ producto: product, cantidad: 1 });
  }

  response = await fetch("http://localhost:8081/carrito", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(carrito),
  });

  let data = await response.json();
  console.log("Success:", data);
}

async function disminuirCantidad(product) {
  let response = await fetch("http://localhost:8081/carrito/2");
  let carrito = await response.json();
  console.log("Success:", carrito);

  const index = carrito.productos.findIndex(
    (item) => item.producto.id === product.id
  );
  if (index !== -1) {
    carrito.productos[index].cantidad--;
  }

  response = await fetch("http://localhost:8081/carrito", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(carrito),
  });

  let data = await response.json();
  console.log("Success:", data);
  showCart();
}

async function aumentarCantidad(product) {
  let response = await fetch("http://localhost:8081/carrito/2");
  let carrito = await response.json();
  console.log("Success:", carrito);

  const index = carrito.productos.findIndex(
    (item) => item.producto.id === product.id
  );
  if (index !== -1) {
    carrito.productos[index].cantidad++;
  }

  response = await fetch("http://localhost:8081/carrito", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(carrito),
  });

  let data = await response.json();
  console.log("Success:", data);
  showCart();
}

async function deleteProduct(product) {
  let response = await fetch("http://localhost:8081/carrito/2");
  let carrito = await response.json();
  console.log("Success:", carrito);

  const index = carrito.productos.findIndex(
    (item) => item.producto.id === product.id
  );
  if (index !== -1) {
    carrito.productos.splice(index, 1);
  }

  response = await fetch("http://localhost:8081/carrito", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(carrito),
  });

  let data = await response.json();
  console.log("Success:", data);
  showCart();
}

// Función para realizar el pedido
async function placeOrder(carrito) {
  const clienteId = carrito.cliente.id;
  const clienteEmail = carrito.cliente.email;
  const fechaVenta = new Date().toISOString();
  const totalVenta = calcularTotal(carrito);

  const venta = {
    ventaProductos: carrito.productos.map(producto => ({
      producto: producto.producto,
      proveedor: producto.producto.proveedor.nombre,
      cantidad: producto.cantidad,
      subtotal: producto.producto.precio * producto.cantidad,
    })),
    cliente: clienteId,
    email: clienteEmail,
    totalVenta,
    fechaVenta,
    estado: "recibido",
  };

  const response = await fetch("http://localhost:8080/venta", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(venta),
  });
  console.log("🚀 ~ placeOrder ~ venta:", venta)

  if (response.ok) {
    alert("¡Gracias por tu compra!");
    // limpia el carrito y actualiza la interfaz
    carrito.productos = [];
    showCart();
    closeCart();
  } else {
    console.error("Error realizando la venta:", response.statusText);
    alert("Ocurrió un error al realizar la compra. Por favor, intenta de nuevo.");
  }
}

async function showCart() {
  var cartModal = document.getElementById("cart-modal");
  var cartList = document.getElementById("cart-list");
  var cartSummary = document.getElementById("cart-summary");

  const cart = await fetchCart();

  const subtotal = calcularTotal(cart);

  cartList.innerHTML = "";
  cartSummary.innerHTML = `
  <div class="flex justify-between">
    <p class="text-lg font-bold">Total</p>
    <div class="">
      <p class="mb-1 text-lg font-bold">Q. ${subtotal}</p>
    </div>
  </div>
  <button
    class="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600"
    id="place-order"
    onClick='placeOrder(${JSON.stringify(cart)})'
  >
    Completar compra
  </button>`;

  cart.productos.forEach((product) => {
    cartList.innerHTML += `
        <div class="justify-between mb-6 rounded-lg bg-white p-6 shadow-md sm:flex sm:justify-start">
          <div class="sm:ml-4 sm:flex sm:w-full sm:justify-between">
            <div class="mt-5 sm:mt-0">
              <h2 class="text-lg font-bold text-gray-900">${
                product.producto.nombre
              }</h2>
              <p class="mt-1 text-xs text-gray-700">${
                product.producto.categoria
              }</p>
            </div>
            <div class="mt-4 flex justify-between sm:space-y-6 sm:mt-0 sm:block sm:space-x-6">
              <div class="flex items-center border-gray-100">
                <span class="cursor-pointer rounded-l bg-gray-100 py-1 px-3.5 duration-100 hover:bg-blue-500 hover:text-blue-50"
                onClick='disminuirCantidad(${JSON.stringify(
                  product.producto
                )})' id="disminuir-btn" >-</span>
                <input class="h-8 w-8 border bg-white text-center text-xs outline-none" type="number" value="${
                  product.cantidad
                }" min="1" disabled />
                <span class="cursor-pointer rounded-r bg-gray-100 py-1 px-3 duration-100 hover:bg-blue-500 hover:text-blue-50" id="aumentar-btn"
                onClick='aumentarCantidad(${JSON.stringify(
                  product.producto
                )})' >+</span>
              </div>
              <div class="flex items-center space-x-4">
                <p class="text-sm">Q. ${product.producto.precio}</p>
                <a id="eliminar-btn"
                onClick='deleteProduct(${JSON.stringify(product.producto)})'
                ><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="h-5 w-5 cursor-pointer duration-150 hover:text-red-500">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"></path>
                </svg></a>
              </div>
            </div>
          </div>
        </div>
      `;
  });

  cartModal.style.display = "block";
}

function calcularTotal(cart) {
  let total = 0;
  cart.productos.forEach((product) => {
    total += product.producto.precio * product.cantidad;
  });
  return total.toFixed(2);
}

async function fetchCart() {
  try {
    const response = await fetch("http://localhost:8081/carrito/2");
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching cart:", error);
    return [];
  }
}

function closeCart() {
  var cartModal = document.getElementById("cart-modal");

  cartModal.style.display = "none";
}

function getProductDetails() {}

window.addToCart = addToCart;
window.disminuirCantidad = disminuirCantidad;
window.aumentarCantidad = aumentarCantidad;
window.deleteProduct = deleteProduct;
window.placeOrder = placeOrder;

export default {
  addToCart,
  disminuirCantidad,
  placeOrder,
  showCart,
  closeCart,
  getProductDetails,
};
