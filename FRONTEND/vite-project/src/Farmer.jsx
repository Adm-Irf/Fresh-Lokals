import React from "react";
import "./styles/Farmer.css";
import Sabahimg from "./assets/images/sabah.webp"
import Perlisimg from "./assets/images/perlis.jpg"
import Riceimg from "./assets/images/padi.webp"
import Cameronimg from "./assets/images/cameron.jpg"
import Johorimg from "./assets/images/johor.webp"

const farmers = [
  {
    name: "Kampung Fresh Farm",
    image: Perlisimg,
    description: "An organic farm in Perlis known for its pesticide-free fresh vegetables and herbs.",
  },
  {
    name: "Sabah Dairy Farm",
    image: Sabahimg, 
    description: "A dairy farm in Kundasang, Sabah, supplying fresh milk from naturally raised cows.",
  },
  {
    name: "Langkawi Rice Fields",
    image: Riceimg,
    description: "A top producer of fragrant local rice grown in the traditional paddy fields of Langkawi.",
  },
  {
    name: "Cameron Tropical Fruits",
    image: Cameronimg, 
    description: "Supplying fresh strawberries, passion fruit, and bananas from Cameron Highlands.",
  },
  {
    name: "Johor Stingless Bee Honey",
    image: Johorimg,
    description: "Pure stingless bee honey sustainably harvested in Johor, packed with health benefits.",
  },
];

const Farmer = () => {
  return (
    <div className="container">
      <header className="farmer-hero">
        <h1>Our Local Suppliers</h1>
        <p>Meet the local farmers and suppliers bringing fresh produce straight to your table.</p>
      </header>

      <section className="farmer-list">
        {farmers.map((farmer, index) => (
          <div key={index} className="farmer-card">
            <img src={farmer.image} alt={farmer.name} className="farmer-image" />
            <div className="farmer-info">
              <h2>{farmer.name}</h2>
              <p>{farmer.description}</p>
            </div>
          </div>
        ))}
      </section>
    </div>
  );
};

export default Farmer;
