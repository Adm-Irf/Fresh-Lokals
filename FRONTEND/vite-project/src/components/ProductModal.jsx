import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/ProductModal.css";

const ProductModal = ({ product, onClose }) => {
  if (!product) return null; // If product is null, don't render

  const navigate = useNavigate();
  const [quantity, setQuantity] = useState(1);
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
        const storedUser = localStorage.getItem("currentUser");
        if (storedUser) {
            setCurrentUser(storedUser);
        }
    }, []);

    useEffect(() => {
      const storedUser = localStorage.getItem("currentUser");
      if (storedUser) {
          setCurrentUser(storedUser);
      }
  }, []);

  const handleAddToCart = () => {
      if (!currentUser) {
          alert("Please sign in first! Redirecting to login...");
          navigate("/login"); // ✅ Redirect to login if not signed in
          return;
      }

      // Proceed with adding to cart
      console.log(`Adding ${quantity} of ${product.name} to cart for user: ${currentUser}`);
      
      // Send request to backend
      fetch("http://localhost:8080/addToCart", {
          method: "POST",
          headers: { "Content-Type": "text/plain" },
          body: `${product.name},${quantity},${currentUser}`,
      })
      .then(response => response.json())
      .then(data => {
          alert(data.message);
          onClose(); // ✅ Close modal after adding
      })
      .catch(error => console.error("Error adding to cart:", error));
  };

  console.log("Rendering ProductModal with product:", product);

   // ✅ Ensure correct image URL formatting
   const imageUrl = product.image.startsWith("http")
   ? product.image
   : `http://localhost:8080/${product.image}`;

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <button className="close-button" onClick={onClose}>×</button>
        
        {/* ✅ Debugging log for image URL */}
        {console.log("Product Image URL:", imageUrl)}

        {product.image ? (
          <img src={imageUrl} alt={product.name} className="modal-image" />
        ) : (
          <p>Image not available</p>
        )}

        {/* ✅ Product Info */}
        <div className="modal-info">
          <h2>{product.name}</h2>
          <p className="product-category">{product.category}</p>
          <p className="product-price">RM {product.price}</p>
          <p className="product-description">{product.description}</p>

          {/* ✅ Select Quantity */}
          <div className="quantity-section">
            <label>Quantity:</label>
            <input type="number" min="1" defaultValue="1" className="quantity-input" />
          </div>

          {/* ✅ Add to Cart Button */}
          <button className="add-to-cart-button">Add to Cart</button>
        </div>
      </div>
    </div>
  );
};

export default ProductModal;
