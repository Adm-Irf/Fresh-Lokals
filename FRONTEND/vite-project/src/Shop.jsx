import React from "react";
import ProductList from "./components/ProductList";
import "./styles/Shop.css";

const Shop = () => {
    return (
        <div className="shop-container">
            <h1 className="shop-title">Shop Fresh Local Products</h1> {/* ✅ Centered Title */}
            <ProductList />
        </div>
    );
};

export default Shop;
