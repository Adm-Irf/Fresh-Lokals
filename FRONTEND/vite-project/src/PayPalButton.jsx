import { useEffect } from 'react';

const PayPalButton = () => {
    useEffect(() => {
        // Make sure to load the PayPal script only once
        if (!window.paypal) {
            const script = document.createElement('script');
            script.src = "https://www.paypal.com/sdk/js?client-id=AatMVNu4Mn-wDXgZosvIwySg_HyCCgyZQwtRGG5LyrVzOkS0OjCIhBeDWdDvKcsnyYTB71K7Jb4B5Z0p&components=buttons";
            script.addEventListener('load', () => {
                window.paypal.Buttons({
                    createOrder: (data, actions) => {
                        return actions.order.create({
                            purchase_units: [{
                                amount: {
                                    value: "10.00"
                                }
                            }]
                        });
                    },
                    onApprove: (data, actions) => {
                        return actions.order.capture().then((details) => {
                            alert("Transaction completed by " + details.payer.name.given_name);
                        });
                    }
                }).render('#paypal-button-container');
            });
            document.body.appendChild(script);
        }
    }, []);

    return <div id="paypal-button-container"></div>;
};

export default PayPalButton;
