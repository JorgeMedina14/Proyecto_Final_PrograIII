import Cart from "./cart.js";
import Products from "./products.js";
const { addToCart, placeOrder, showCart, closeCart } = Cart;
const { handleProducts } = Products;

document.addEventListener("DOMContentLoaded", () => {
  handleProducts();

  const addToCartButtons = document.querySelectorAll("#add-to-cart");
  addToCartButtons.forEach((button) => {
    button.addEventListener("click", addToCart);
  });

  document.getElementById("cart-button").addEventListener("click", showCart);
  document.getElementById("close-cart").addEventListener("click", closeCart);
});

document.addEventListener("keydown", (event) => {
  if (event.key === "Escape") {
    document.getElementById("cart-modal").style.display = "none";
  }
});