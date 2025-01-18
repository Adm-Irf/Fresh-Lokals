import React from "react";
import ReactDOM from "react-dom";
import "./styles/About.css";

const About = () => {
    return (
        <div className="about-page">
            <h1>About FreshLokals</h1>
            <p>Discover fresh, locally sourced goods with ease!</p>

            <section className="mission">
                <h2>Our Mission</h2>
                <p>We connect local farmers and artisans with customers, promoting sustainability and supporting local communities.</p>
            </section>

            <section className="offerings">
                <h2>What We Offer</h2>
                <ul>
                    <li>🌱 Fresh produce & local products</li>
                    <li>🛒 Convenient online shopping</li>
                    <li>🤝 Supporting small businesses</li>
                    <li>📦 Fast & reliable delivery</li>
                </ul>
            </section>

            <section className="contact">
                <h2>Get in Touch</h2>
                <p>📍 Visit us at [Your Business Address]</p>
                <p>📧 Email: <a href="mailto:contact@freshlokals.com">contact@freshlokals.com</a></p>
                <p>📞 Phone: +60 [Your Contact Number]</p>

                {/* ✅ Fixed iframe attributes */}
                <iframe
                    src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15889.570857299219!2d100.28907639754955!3d5.356895913948655!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x304ac1a836ae7e53%3A0x835ac54fe8f4d95a!2sUniversiti%20Sains%20Malaysia!5e0!3m2!1sen!2smy!4v1737186409438!5m2!1sen!2smy"
                    width="600"
                    height="450"
                    style={{ border: "0" }}  // ✅ FIXED `style` syntax
                    allowFullScreen=""  // ✅ FIXED `allowfullscreen`
                    loading="lazy"
                    referrerPolicy="no-referrer-when-downgrade"  // ✅ FIXED `referrerpolicy`
                ></iframe>
            </section>

        </div>
    );
};

export default About;