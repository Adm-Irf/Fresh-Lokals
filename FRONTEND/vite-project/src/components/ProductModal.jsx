import React, { useState } from "react";
import "../styles/ProductModal.css"; // Ensure the CSS file exists

const ProductModal = ({ product, onClose }) => {
  const [quantity, setQuantity] = useState(1);

  const handleAddToCart = async () => {
    const user = localStorage.getItem("user"); // ✅ Get logged-in user

    if (!user) {
        alert("Please sign in first! Redirecting to login...");
        window.location.href = "/login"; // Redirect to login page
        return;
    }

    const requestBody = `${product.name},${quantity}`;

    try {
        const response = await fetch("http://localhost:8080/addToCart", {
            method: "POST",
            headers: {
                "Content-Type": "text/plain",
            },
            body: requestBody,
        });

        if (response.ok) {
            alert("Product added to cart successfully!");
        } else {
            alert("Failed to add product to cart. Please try again.");
        }
    } catch (error) {
        console.error("Error adding product to cart:", error);
        alert("Server error. Please try again later.");
    }
};


  return (
      <div className="modal-overlay">
            <div className="modal-content">
              <button className="close-button" onClick={onClose}>×</button>
              {product.image && <img src={`http://localhost:8080/${product.image}`} alt={product.name} className="product-image" />}
              <h2>{product.name}</h2>
              <p>{product.category}</p>
              <h3 className="product-price">RM {product.price}</h3>
              <p>{product.description}</p>

              <div className="quantity-section">
                <label>Quantity:</label>
                <input 
                  type="number" 
                  className="quantity-input"
                  value={quantity} 
                  min="1" 
                  onChange={(e) => setQuantity(e.target.value)} 
                />
              </div>

              <button className="add-to-cart-button" onClick={handleAddToCart}>
                Add to Cart
              </button>
            </div>
          </div>
  );
};

export default ProductModal;