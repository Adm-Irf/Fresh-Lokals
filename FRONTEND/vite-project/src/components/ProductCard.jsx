import React from "react";
import "../styles/ProductCard.css"; // Ensure you have a CSS file for styling

const ProductCard = ({ product, onClick }) => {
  console.log("Product Data:", product); // ✅ Debugging

  const imageUrl = product.image.startsWith("http") 
      ? product.image // ✅ Already a full URL
      : `http://localhost:8080/${product.image}`; // ✅ Add base URL if needed

  return (
    <div className="product-card" onClick={() => {
      console.log("Clicked product:", product); // ✅ Debugging click
      if (typeof onClick === "function") { // ✅ Ensure it's a function before calling
        onClick();
      } else {
        console.error("onClick is not a function:", onClick);
      }
    }}>
      {product.image ? (
        <img src={imageUrl} alt={product.name} className="product-image" />
      ) : (
        <p>Image not available</p>
      )}
      <p className="product-category">{product.category}</p>
      <h3 className="product-name">{product.name}</h3>
      <p className="product-price">RM {product.price}</p>
    </div>
  );
};

export default ProductCard;
