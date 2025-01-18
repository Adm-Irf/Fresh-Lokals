import React from "react";
import "./styles/LandingPage.css";
import durianImg from "./assets/images/durian.jpg";
import riceImg from "./assets/images/rice.jpeg";
import seafoodImg from "./assets/images/seafood.jpg";

// Hardcoded recently added items for now
const recentlyAddedItems = [
  { id: 1, name: "Organic Apples" },
  { id: 2, name: "Fresh Carrots" },
  { id: 3, name: "Handmade Clay Pot" },
  { id: 4, name: "Local Honey" },
];

const LandingPage = () => {
  return (
    <div className="container mx-auto px-4">
      {/* Hero Section */}
      <header className="hero">
        <h1>FreshLokals</h1>
        <p>Discover and shop fresh, local goods with ease!</p>
      </header>

      {/* Featured Categories */}
      <section className="categories text-center my-10">
        <h2 className="text-2xl font-bold mb-4">Shop by Category</h2>
        <div className="grid grid-cols-3 gap-6 max-w-lg mx-auto">
          <div className="category-card bg-green-100 p-4 rounded-lg shadow-md text-center font-semibold">
            Fruits
          </div>
          <div className="category-card bg-yellow-100 p-4 rounded-lg shadow-md text-center font-semibold">
            Vegetables
          </div>
          <div className="category-card bg-red-100 p-4 rounded-lg shadow-md text-center font-semibold">
            Handmade Crafts
          </div>
        </div>
      </section>

      {/* Recently Added Items */}
      <section className="recently-added text-center my-10">
        <h2 className="text-2xl font-bold mb-4">Recently Added Items</h2>
        <div className="grid">
          {recentlyAddedItems.slice(0, 4).map((item) => (
            <div key={item.id} className="product-card bg-white p-4 rounded-lg shadow-md text-center">
              {item.name}
            </div>
          ))}
        </div>
      </section>

      {/* Local Grocery Promotions Section */}
      <section className="promotions text-center my-10">
        <h2 className="text-2xl font-bold mb-6">Exclusive Deals on Fresh Local Groceries</h2>
        <div className="promotions-grid">
          <div className="promotion-card" style={{ backgroundImage: `url(${durianImg})` }}>
            <div className="overlay">
              <h3>Musang King Durian</h3>
              <p>Premium Grade - Up to 20% Off</p>
            </div>
          </div>
          <div className="promotion-card" style={{ backgroundImage: `url(${riceImg})` }}>
            <div className="overlay">
              <h3>Local Fragrant Rice</h3>
              <p>Freshly Milled - Special Discount Available</p>
            </div>
          </div>
          <div className="promotion-card" style={{ backgroundImage: `url(${seafoodImg})` }}>
            <div className="overlay">
              <h3>Fresh Seafood</h3>
              <p>Wild-Caught & Sustainably Sourced</p>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default LandingPage;