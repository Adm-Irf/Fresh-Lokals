import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./styles/PurchasePage.css";

const PurchasesPage = () => {
    const [purchases, setPurchases] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const user = localStorage.getItem("user");

        if (!user) {
            alert("Please sign in first to view your purchases.");
            navigate("/login");
            return;
        }

        fetch("http://localhost:8080/purchases")
            .then(response => response.json())
            .then(data => {
                if (Array.isArray(data)) {
                    setPurchases(data);
                } else {
                    setPurchases([]);
                }
            })
            .catch(error => {
                console.error("Error fetching purchases:", error);
                setPurchases([]);
            });
    }, [navigate]);

    return (
        <div className="purchases-container">
            <h2>Your Purchase History</h2>
            {purchases.length === 0 ? (
                <p>No purchases yet.</p>
            ) : (
                <table className="purchases-table">
                    <thead>
                        <tr>
                            <th>Transaction ID</th>
                            <th>Category</th>
                            <th>Product</th>
                            <th>Price (RM)</th>
                        </tr>
                    </thead>
                    <tbody>
                        {purchases.map((purchase, index) => (
                            <tr key={index}>
                                <td>{purchase[0]}</td> {/* Transaction ID */}
                                <td>{purchase[1]}</td> {/* Category */}
                                <td>{purchase[2]}</td> {/* Product Name */}
                                <td>RM {purchase[3]}</td> {/* Price */}
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default PurchasesPage;