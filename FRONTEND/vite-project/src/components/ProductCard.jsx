import React from "react";
import "../styles/ProductCard.css"; // Ensure you have a CSS file for styling

const ProductCard = ({ product }) => {
  return (
    <div className="product-card">
      <img src={product.image} alt={product.name} className="product-image" />
      <div className="product-details">
        <p className="product-category">{product.category}</p>
        <h3 className="product-name">{product.name}</h3>
        <p className="product-price">RM {product.price}</p>
      </div>
    </div>
  );
};

export default ProductCard;
