import React from "react";
import { Link } from "react-router-dom";
import "./styles/LandingPage.css";
import durianImg from "./assets/images/durian.jpg";
import riceImg from "./assets/images/rice.jpeg";
import seafoodImg from "./assets/images/seafood.jpg";
import farmerImg from "./assets/images/farmers.jpg";
import aboutImg from "./assets/images/padi.webp";
import freshImg from "./assets/images/fresh.jpg";
const LandingPage = () => {
  return (
    <div className="container mx-auto px-4">
      {/* Hero Section */}
      <header className="hero">
        <h1>FreshLokals</h1>
        <p>Discover and shop fresh, local goods with ease!</p>
      </header>

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

      <div className="container">
      {/* Featurette: Meet Our Farmers & Fishermen */}
      <section className="featurette">
        <div className="featurette-content">
          <h2 className="featurette-heading">Meet Our Farmers & Fishermen</h2>
          <p>
            From the rice fields of Kedah to the durian orchards of Raub, our farmers & fishermen work tirelessly to bring fresh, quality food to Malaysian homes.
          </p>
          <Link to="/farmer" className="cta-button">Meet Our Farmers</Link>
        </div>
        <img src={farmerImg} alt="Local Farmers" className="featurette-image" />
      </section>

      {/* Featurette: About FreshLokals (Image on Left) */}
      <section className="featurette">
        <img src={aboutImg} alt="About FreshLokals" className="featurette-image" />
        <div className="featurette-content">
          <h2 className="featurette-heading">About FreshLokals</h2>
          <p>
            FreshLokals is on a mission to support local farmers, small businesses, and communities by making fresh, high-quality Malaysian produce accessible to everyone.
          </p>
          <Link to="/about" className="cta-button">Our Story</Link>
        </div>
      </section>

      {/* Featurette: Join FreshLokals & Enjoy Freshness! */}
      <section className="featurette"> 
        <img src={freshImg} alt="Fresh" className="featurette-image" />
        <div className="featurette-content">
          <h2 className="featurette-heading">Join FreshLokals & Enjoy Freshness!</h2>
          <p>
            Sign up today and enjoy <strong>kampung-style</strong> freshness with modern convenience.
          </p>
          <Link to="/signup" className="cta-button">Sign Up Now</Link>
        </div>
      </section>
    </div>
    </div>
  );
};

export default LandingPage;
