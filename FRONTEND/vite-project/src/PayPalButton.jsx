import React, { useState, useEffect } from "react";
import { PayPalButtons, PayPalScriptProvider } from "@paypal/react-paypal-js";

const PayPalButton = () => {
    const [totalPrice, setTotalPrice] = useState(0);
    const [transactionStatus, setTransactionStatus] = useState("");

    useEffect(() => {
        fetch("http://localhost:8080/totalPrice")
            .then((response) => response.json())
            .then((data) => {
                console.log("Fetched total price:", data.totalPrice);
                setTotalPrice(data.totalPrice);
            })
            .catch((err) => {
                console.error("Error fetching total price:", err);
            });
    }, []);

    const renderStatusBox = () => {
        if (transactionStatus === "success") {
            return (
                <div style={{ ...styles.statusBox, ...styles.successBox }}>
                    <span style={styles.icon}>✔</span>
                    <span style={styles.caption}>Transaction Successful!</span>
                </div>
            );
        } else if (transactionStatus === "cancelled") {
            return (
                <div style={{ ...styles.statusBox, ...styles.errorBox }}>
                    <span style={styles.icon}>✖</span>
                    <span style={styles.caption}>Transaction Cancelled!</span>
                </div>
            );
        }
        return null;
    };

    return (
        <div style={styles.container}>
            {renderStatusBox()}

            <PayPalScriptProvider
                options={{
                    "client-id": "AatMVNu4Mn-wDXgZosvIwySg_HyCCgyZQwtRGG5LyrVzOkS0OjCIhBeDWdDvKcsnyYTB71K7Jb4B5Z0p",
                    currency: "MYR",
                }}
            >
                <PayPalButtons
                    createOrder={(data, actions) => {
                        // Reset the transaction status before creating a new order
                        setTransactionStatus("");

                        return actions.order.create({
                            purchase_units: [
                                {
                                    amount: {
                                        value: totalPrice.toFixed(2),
                                    },
                                },
                            ],
                        });
                    }}
                    onApprove={(data, actions) => {
                        return actions.order.capture().then((details) => {
                            setTransactionStatus("success");

                        });
                    }}
                    onCancel={() => {
                        setTransactionStatus("cancelled");
                    }}
                    onError={(err) => {
                        console.error("PayPal Checkout error:", err);
                        setTransactionStatus("cancelled");
                        alert(`PayPal Error: ${err.message || "Unknown error"}`);
                    }}
                />
            </PayPalScriptProvider>
        </div>
    );
};

export default PayPalButton;

// Styles for the status box
const styles = {
    successBox: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        padding: "10px",
        backgroundColor: "#d4edda", // Light green
        color: "#155724",          // Dark green
        borderRadius: "8px",
        border: "1px solid #c3e6cb",
        margin: "20px auto",       // Center horizontally with auto
        width: "fit-content",
    },
    errorBox: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        padding: "10px",
        backgroundColor: "#f8d7da", // Light red
        color: "#721c24",          // Dark red
        borderRadius: "8px",
        border: "1px solid #f5c6cb",
        margin: "20px auto",       // Center horizontally with auto
        width: "fit-content",
    },


    icon: {
        fontSize: "20px",
        marginRight: "10px",
    },
    caption: {
        fontSize: "16px",
        fontWeight: "bold",
    },
};
