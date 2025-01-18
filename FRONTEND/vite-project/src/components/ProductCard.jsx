import React from "react";
import "../styles/ProductCard.css";

const ProductCard = ({ product, onClick }) => {
  const imageUrl = product.image.startsWith("http")
    ? product.image
    : `http://localhost:8080/${product.image}`;

  return (
    <div className="product-card" onClick={onClick}>
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
