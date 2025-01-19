import React, { useEffect, useState } from "react";
import ProductCard from "./ProductCard";
import ProductModal from "./ProductModal";
import "../styles/ProductList.css";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const productsPerPage = 16;
  const [selectedProduct, setSelectedProduct] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/products")
        .then((response) => {
            if (!response.ok) {
                if (response.status === 401) {
                    console.warn("User not logged in. Showing products anyway.");
                    return []; // ✅ Return empty array instead of undefined
                }
                throw new Error("Failed to fetch products");
            }
            return response.json();
        })
        .then((data) => {
            if (!Array.isArray(data)) {
                console.error("Invalid products data received:", data);
                setProducts([]); // ✅ Prevents crashes
            } else {
                setProducts(data);
            }
        })
        .catch((error) => {
            console.error("Error fetching products:", error);
            setProducts([]); // ✅ Fallback to prevent crashes
        });
}, []);



  const indexOfLastProduct = currentPage * productsPerPage;
  const indexOfFirstProduct = indexOfLastProduct - productsPerPage;
  const currentProducts = products.slice(indexOfFirstProduct, indexOfLastProduct);
  const totalPages = Math.ceil(products.length / productsPerPage);

  return (
    <section className="product-list-container">
      <div className="product-grid">
        {currentProducts.map((product, index) => (
          <ProductCard 
            key={index} 
            product={product} 
            onClick={() => setSelectedProduct(product)} 
          />
        ))}
      </div>

      <div className="pagination">
        <button onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 1}>
          Previous
        </button>
        <span> Page {currentPage} of {totalPages} </span>
        <button onClick={() => setCurrentPage(currentPage + 1)} disabled={currentPage === totalPages}>
          Next
        </button>
      </div>

      {selectedProduct && (
        <ProductModal product={selectedProduct} onClose={() => setSelectedProduct(null)} />
      )}
    </section>
  );
};

export default ProductList;
