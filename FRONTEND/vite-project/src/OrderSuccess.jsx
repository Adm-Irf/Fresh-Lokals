import React from "react";
import { Link } from "react-router-dom";
import "./styles/OrderSuccess.css";

const OrderSuccess = () => {
    return (
        <div className="order-success-container">
            <div className="order-success-box">
                <h2>🎉 Order Placed Successfully!</h2>
                <p>Thank you for shopping with <strong>FreshLokals</strong>.</p>
                <p>Your payment was successful, and we are preparing your order.</p>
                <Link to="/" className="return-home-btn">
                    Return to Home
                </Link>
            </div>
        </div>
    );
};

export default OrderSuccess;
