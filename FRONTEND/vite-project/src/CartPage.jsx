import React, { useEffect, useState } from "react";

const CartPage = () => {
  const [cart, setCart] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/userCart")
      .then((response) => response.json())
      .then((data) => setCart(data))
      .catch((error) => console.error("Error fetching cart:", error));
  }, []);

  const handleRemoveItem = (productName) => {
    fetch("http://localhost:8080/deleteFromCart", {
      method: "DELETE",
      headers: { "Content-Type": "text/plain" },
      body: productName,
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data.message);
        setCart(cart.filter((item) => item.name !== productName)); // Remove from UI
      })
      .catch((error) => console.error("Error removing item:", error));
  };

  return (
    <div>
      <h2>Your Shopping Cart</h2>
      {cart.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Remove</th>
              <th>Image</th>
              <th>Product</th>
              <th>Price</th>
              <th>Quantity</th>
              <th>Subtotal</th>
            </tr>
          </thead>
          <tbody>
          {cart.map((item, index) => (
                <tr key={`${item.name}-${item.quantity}-${index}`}> {/* Use a combination of name, quantity, and index */}
                    <td><button onClick={() => handleRemoveItem(item.name)}>❌</button></td>
                    <td><img src={`http://localhost:8080/${item.image}`} alt={item.name} width="50" /></td>
                    <td>{item.name}</td>
                    <td>RM {item.price}</td>
                    <td>{item.quantity}</td>
                    <td>RM {parseFloat(item.price) * parseInt(item.quantity)}</td>
                </tr>
                ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default CartPage;
