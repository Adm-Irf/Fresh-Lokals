import React from "react";
import ProductModule from "./ProductModal";
import "./styles/LandingPage.css";

const LandingPage = () => {
  return (
    <div className="container mx-auto px-4">
      {/* Hero Section */}
      {/* Hero Section */}
      <header className="hero">
        <h1>FreshLokals</h1>
        <p>Discover and shop fresh, local goods with ease!</p>
      </header>

      {/* Product List Section */}
      <ProductModule />

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

      {/* Best Sellers */}
      <section className="best-sellers text-center my-10">
        <h2 className="text-2xl font-bold mb-4">Best Sellers</h2>
        <div className="grid grid-cols-3 gap-6 max-w-lg mx-auto">
          <div className="product-card bg-white p-4 rounded-lg shadow-md text-center">Product 1</div>
          <div className="product-card bg-white p-4 rounded-lg shadow-md text-center">Product 2</div>
          <div className="product-card bg-white p-4 rounded-lg shadow-md text-center">Product 3</div>
        </div>
      </section>

      {/* Testimonials */}
      <section className="testimonials text-center my-10">
        <h2 className="text-2xl font-bold mb-4">What Our Customers Say</h2>
        <p className="text-gray-600 italic">"FreshLokals changed the way I shop for local goods!" - Jane D.</p>
      </section>

      {/* Footer */}
      <footer className="footer text-center bg-gray-900 text-white py-4 mt-10">
        <p>© 2025 FreshLokals. All Rights Reserved.</p>
      </footer>
    </div>
  );
};

export default LandingPage;