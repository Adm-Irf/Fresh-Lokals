import { useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react";
import "./styles/CartPage.css";

const CartPage = () => {
    const [cart, setCart] = useState([]);
    const navigate = useNavigate();
    const hasRedirected = useRef(false);

    useEffect(() => {
        const user = localStorage.getItem("user");

        if (!user && !hasRedirected.current) {
            hasRedirected.current = true;
            alert("Please sign in first to view your cart!");
            navigate("/login");
            return;
        }

        if (user) {
            fetch("http://localhost:8080/userCart")
                .then((response) => {
                    if (!response.ok) {
                        if (response.status === 401) {
                            alert("Session expired. Please log in again.");
                            localStorage.removeItem("user");
                            navigate("/login");
                            return;
                        }
                        throw new Error("Failed to fetch cart");
                    }
                    return response.json();
                })
                .then((data) => setCart(data))
                .catch((error) => {
                    console.error("Error fetching cart:", error);
                    setCart([]);
                });
        }
    }, [navigate]);

    const handleRemoveItem = (itemName) => {
        const updatedCart = cart.filter(item => item.name !== itemName);
        setCart(updatedCart);
        alert(`${itemName} removed from cart!`);
    };

    const totalPrice = cart.reduce((sum, item) => sum + parseFloat(item.price) * parseInt(item.quantity), 0);

    return (
        <div className="cart-container">
            <h2>Your Shopping Cart</h2>
            {cart.length === 0 ? (
                <p>Your cart is empty.</p>
            ) : (
                <>
                    <table className="cart-table">
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
                                <tr key={`${item.name}-${item.quantity}-${index}`}>
                                    <td>
                                        <button onClick={() => handleRemoveItem(item.name)}>❌</button>
                                    </td>
                                    <td>
                                        <img
                                            src={`http://localhost:8080/${item.image}`}
                                            alt={item.name}
                                            className="cart-image"
                                            onError={(e) => e.target.src = "fallback-image.png"}
                                        />
                                    </td>
                                    <td>{item.name}</td>
                                    <td>RM {parseFloat(item.price).toFixed(2)}</td>
                                    <td>{item.quantity}</td>
                                    <td>RM {(parseFloat(item.price) * parseInt(item.quantity)).toFixed(2)}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <h3 style={{ textAlign: "right", marginTop: "20px" }}>Total: RM {totalPrice.toFixed(2)}</h3>
                </>
            )}
        </div>
    );
};

export default CartPage;
