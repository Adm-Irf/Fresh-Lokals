import React from "react";
import "../styles/Footer.css";
import paypalLogo from "../assets/images/paypal.png";
import facebookIcon from "../assets/images/facebook.png";
import instagramIcon from "../assets/images/instagram.png";
import xIcon from "../assets/images/x-logo.png"; // ✅ X (formerly Twitter)

const Footer = () => {
    return (
        <footer className="footer">
            <div className="footer-container">
                {/* Footer Sections */}
                <div className="footer-sections">
                    <div className="footer-section">
                        <h3>About FreshLokals</h3>
                        <p>
                            FreshLokals connects local farmers and artisans with consumers. 
                            Our mission is to promote sustainability and support local businesses.
                        </p>
                    </div>

                    <div className="footer-section">
                        <h3>Quick Links</h3>
                        <ul>
                            <li><a href="/">Home</a></li>
                            <li><a href="/shop">Shop</a></li>
                            <li><a href="/farmer">Meet Our Farmers</a></li>
                            <li><a href="/about">About Us</a></li>
                        </ul>
                    </div>

                    <div className="footer-section">
                        <h3>Contact Us</h3>
                        <p>📍 Universiti Sains Malaysia, Penang, Malaysia</p>
                        <p>📧 <a href="mailto:contact@freshlokals.com">contact@freshlokals.com</a></p>
                        <p>📞 +60 12-3456789</p>
                    </div>
                </div>

                {/* PayPal & Social Media */}
                <div className="footer-bottom">
                    <div className="payment-info">
                        <p>💳 We accept payments via PayPal</p>
                        <img src={paypalLogo} alt="PayPal Logo" />
                    </div>

                    <div className="social-media">
                        <a href="https://facebook.com" target="_blank" rel="noopener noreferrer">
                            <img src={facebookIcon} alt="Facebook" />
                        </a>
                        <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">
                            <img src={instagramIcon} alt="Instagram" />
                        </a>
                        <a href="https://x.com" target="_blank" rel="noopener noreferrer">  
                            <img src={xIcon} alt="X (formerly Twitter)" />
                        </a>
                    </div>
                </div>

                {/* Copyright */}
                <p className="footer-copyright">© 2025 FreshLokals. All Rights Reserved.</p>
            </div>
        </footer>
    );
};

export default Footer;
