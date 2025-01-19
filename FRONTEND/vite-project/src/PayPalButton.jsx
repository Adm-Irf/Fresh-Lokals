import { useEffect } from "react";

const PayPalButton = ({ totalAmount, cartItems }) => {
    useEffect(() => {
        if (!window.paypal) {
            const script = document.createElement("script");
            script.src = "https://www.paypal.com/sdk/js?client-id=AatMVNu4Mn-wDXgZosvIwySg_HyCCgyZQwtRGG5LyrVzOkS0OjCIhBeDWdDvKcsnyYTB71K7Jb4B5Z0p&currency=MYR";
            script.async = true;
            script.onload = () => {
                window.paypal.Buttons({
                    createOrder: (data, actions) => {
                        return actions.order.create({
                            purchase_units: [
                                {
                                    amount: {
                                        currency_code: "MYR",
                                        value: totalAmount.toFixed(2),
                                    },
                                },
                            ],
                        });
                    },
                    onApprove: async (data, actions) => {
                        const order = await actions.order.capture();
                        alert(`Payment Successful! Transaction ID: ${order.id}`);
                    
                        try {
                            const response = await fetch("http://localhost:8080/purchase", {
                                method: "POST",
                                headers: { "Content-Type": "application/json" },
                                mode: "cors", // ✅ Force CORS
                                body: JSON.stringify({ transactionId: order.id, cartItems }),
                            });
                    
                            if (response.ok) {
                                alert("Your purchase has been recorded!");
                                window.location.href = "/order-success";
                            } else {
                                const errorText = await response.text();
                                console.error("Purchase failed:", errorText);
                                alert("Failed to save purchase. Please contact support.");
                            }
                        } catch (error) {
                            console.error("Error saving purchase:", error);
                            alert("Server error. Please try again later.");
                        }
                    },
                    onError: (err) => {
                        console.error("PayPal Checkout Error:", err);
                        alert("Payment failed. Please try again.");
                    },
                }).render("#paypal-button-container");
            };

            document.body.appendChild(script);
        }
    }, [totalAmount, cartItems]);

    return <div id="paypal-button-container"></div>;
};

export default PayPalButton;