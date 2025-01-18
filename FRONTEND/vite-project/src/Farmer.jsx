import React from "react";
import "./styles/Farmer.css";

const farmers = [
  {
    name: "Kampung Fresh Farm",
    image: "https://picsum.photos/seed/farm1/300/200", // Placeholder
    description: "An organic farm in Perlis known for its pesticide-free fresh vegetables and herbs.",
  },
  {
    name: "Sabah Dairy Farm",
    image: "https://picsum.photos/seed/farm2/300/200", // Placeholder
    description: "A dairy farm in Kundasang, Sabah, supplying fresh milk from naturally raised cows.",
  },
  {
    name: "Langkawi Rice Fields",
    image: "https://picsum.photos/seed/farm3/300/200", // Placeholder
    description: "A top producer of fragrant local rice grown in the traditional paddy fields of Langkawi.",
  },
  {
    name: "Cameron Tropical Fruits",
    image: "https://picsum.photos/seed/farm4/300/200", // Placeholder
    description: "Supplying fresh strawberries, passion fruit, and bananas from Cameron Highlands.",
  },
  {
    name: "Johor Stingless Bee Honey",
    image: "https://picsum.photos/seed/farm5/300/200", // Placeholder
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
