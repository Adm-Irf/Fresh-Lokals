import { useEffect } from "react";

const PayPalButton = ({ totalAmount }) => {
    useEffect(() => {
        // ✅ Ensure PayPal script is only loaded once
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
                                        currency_code: "MYR", // ✅ Change currency to MYR
                                        value: totalAmount.toFixed(2), // ✅ Use the correct cart total
                                    },
                                },
                            ],
                        });
                    },
                    onApprove: async (data, actions) => {
                        const order = await actions.order.capture();
                        alert(`Payment Successful! Transaction ID: ${order.id}`);

                        // ✅ Redirect to Order Confirmation Page
                        window.location.href = "/order-success";
                    },
                    onError: (err) => {
                        console.error("PayPal Checkout Error:", err);
                        alert("Payment failed. Please try again.");
                    },
                }).render("#paypal-button-container");
            };

            document.body.appendChild(script);
        }
    }, [totalAmount]);

    return <div id="paypal-button-container"></div>;
};

export default PayPalButton;
