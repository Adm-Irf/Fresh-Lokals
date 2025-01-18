import React from "react";
import "./styles/LandingPage.css"; // Make sure to create this CSS file

const LandingPage = () => {
  return (
    <div className="landing-page">
      {/* Hero Section */}
      <header className="hero">
        <h1>FreshLokals</h1>
        <p>Discover and shop fresh, local goods with ease!</p>
        <button className="cta-button">Explore Now</button>
      </header>

      {/* Featured Categories */}
      <section className="categories">
        <h2>Shop by Category</h2>
        <div className="category-list">
          <div className="category-card">Fruits</div>
          <div className="category-card">Vegetables</div>
          <div className="category-card">Handmade Crafts</div>
        </div>
      </section>

      {/* Best Sellers */}
      <section className="best-sellers">
        <h2>Best Sellers</h2>
        <div className="product-list">
          <div className="product-card">Product 1</div>
          <div className="product-card">Product 2</div>
          <div className="product-card">Product 3</div>
        </div>
      </section>

      {/* Testimonials */}
      <section className="testimonials">
        <h2>What Our Customers Say</h2>
        <p>"FreshLokals changed the way I shop for local goods!" - Jane D.</p>
      </section>

      {/* Footer */}
      <footer className="footer">
        <p>© 2025 FreshLokals. All Rights Reserved.</p>
      </footer>
    </div>
  );
};

export default LandingPage;
