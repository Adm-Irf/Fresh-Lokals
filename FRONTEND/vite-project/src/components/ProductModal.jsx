import React from "react";

const ProductCard = ({ product, onClick }) => {
  return (
    <div className="product-card" onClick={onClick}>
      <img src={product.image} alt={product.name} className="product-image" />
      <h3>{product.name}</h3>
      <p>{product.category}</p>
      <p>RM {product.price}</p>
    </div>
  );
};

export default ProductCard;
