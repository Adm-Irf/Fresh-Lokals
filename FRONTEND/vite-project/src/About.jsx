import React from "react";
import "./styles/About.css";
import localFarmersImg from "./assets/images/farmers.jpg"; // Ensure path is correct

const About = () => {
    return (
        <div className="about-page">
            {/* Hero Section with Background Image */}
            <section 
                className="about-hero" 
                style={{ backgroundImage: `url(${localFarmersImg})` }}
            >
                <div className="hero-overlay">
                    <h1>Connecting Malaysia’s Freshest Produce to Your Home</h1>
                    <p>Supporting local farmers, reducing food miles, and bringing Malaysians closer to fresh, high-quality groceries.</p>
                </div>
            </section>

            {/* Impact Section */}
            <section className="impact">
                <h2>🌱 Making a Difference in Malaysia</h2>
                <div className="impact-grid">
                    <div className="impact-item">
                        <h3>👨‍🌾 150+ Local Farmers</h3>
                        <p>We collaborate with small-scale farmers across Malaysia.</p>
                    </div>
                    <div className="impact-item">
                        <h3>🗺️ Covering 13 States</h3>
                        <p>From Sabah to Penang, we deliver fresh groceries nationwide.</p>
                    </div>
                    <div className="impact-item">
                        <h3>♻️ Eco-Friendly & Sustainable</h3>
                        <p>We promote local sourcing, reducing carbon footprint.</p>
                    </div>
                </div>
            </section>

            {/* Mission Section */}
            <section className="mission">
                <h2>🎯 Our Mission</h2>
                <p>We connect local farmers and suppliers with Malaysian consumers, ensuring fresh, healthy, and affordable groceries while supporting the local economy.</p>
            </section>

            {/* What We Offer Section */}
            <section className="offerings">
                <h2>🛒 What We Offer</h2>
                <ul>
                    <li>🥬 Fresh produce & local ingredients</li>
                    <li>🚚 Convenient online grocery delivery</li>
                    <li>🤝 Supporting small Malaysian businesses</li>
                    <li>🌍 Environmentally sustainable sourcing</li>
                </ul>
            </section>

            {/* Contact Section */}
            <section className="contact">
                <h2>📞 Get in Touch</h2>
                <p>📍 Visit us at Universiti Sains Malaysia</p>
                <p>📧 Email: <a href="mailto:contact@freshlokals.com">contact@freshlokals.com</a></p>
                <p>📞 Phone: +604-2621957</p>

                {/* Embedded Google Map */}
                <iframe
                    src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15889.570857299219!2d100.28907639754955!3d5.356895913948655!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x304ac1a836ae7e53%3A0x835ac54fe8f4d95a!2sUniversiti%20Sains%20Malaysia!5e0!3m2!1sen!2smy!4v1737186409438!5m2!1sen!2smy"
                    width="600"
                    height="450"
                    style={{ border: "0", borderRadius: "10px", boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.2)" }}
                    allowFullScreen=""
                    loading="lazy"
                    referrerPolicy="no-referrer-when-downgrade"
                ></iframe>
            </section>
        </div>
    );
};

export default About;
