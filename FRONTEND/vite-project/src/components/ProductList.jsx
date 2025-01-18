import React, { useEffect, useState } from "react";
import ProductCard from "./ProductCard";
import ProductModule from "./ProductModal";  // ✅ Make sure it's correct
import "../styles/ProductList.css"; // Ensure this file exists

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState(null);

  // Fetch products from backend
  useEffect(() => {
    fetch("http://localhost:8080/products") // Ensure this API exists in your backend
      .then((response) => response.json())
      .then((data) => setProducts(data))
      .catch((error) => console.error("Error fetching products:", error));
  }, []);

  return (
    <section className="product-list-container">
      <h2 className="product-list-title">Available Products</h2>
      <div className="product-grid">
        {products.map((product) => (
          <ProductCard key={product.name} product={product} />
        ))}
      </div>
    </section>
  );
};

export default ProductList;
