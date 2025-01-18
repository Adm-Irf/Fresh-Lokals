import React, { useEffect, useState } from "react";
import ProductCard from "./ProductCard";
import ProductModal from "./ProductModal"; // ✅ Import the modal component
import "../styles/ProductList.css";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const productsPerPage = 16; // ✅ Max 16 products per page
  const [selectedProduct, setSelectedProduct] = useState(null);

  // Fetch products from backend
  useEffect(() => {
    fetch("http://localhost:8080/products")
      .then((response) => response.json())
      .then((data) => setProducts(data))
      .catch((error) => console.error("Error fetching products:", error));
  }, []);

  // Pagination Logic
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
                  onClick={() => {
                      console.log("Clicked product:", product); // ✅ Debugging click
                      setSelectedProduct(product);
                  }} 
              />
              ))}
          </div>

          {/* ✅ Move pagination to the bottom */}
          <div className="pagination">
              <button 
                  onClick={() => setCurrentPage(currentPage - 1)}
                  disabled={currentPage === 1}
                  className="page-button"
              >
                  Previous
              </button>
              
              <span> Page {currentPage} of {totalPages} </span>

              <button 
                  onClick={() => setCurrentPage(currentPage + 1)}
                  disabled={currentPage === totalPages}
                  className="page-button"
              >
                  Next
              </button>
          </div>

          {/* Debugging log for modal visibility */}
          {console.log("Current selectedProduct:", selectedProduct)}

          {/* Modal should appear if selectedProduct is NOT null */}
          {selectedProduct && (
              <ProductModal product={selectedProduct} onClose={() => {
                console.log("Closing Modal");
                setSelectedProduct(null);
              }} />
          )}
      </section>
  );

};

export default ProductList;
