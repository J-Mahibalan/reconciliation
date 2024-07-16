import React, { useState, useEffect } from "react";
import "./home.css";
import UserService from "../services/user.service";
import { Link } from 'react-router-dom';

const Home = () => {
  const [content, setContent] = useState("");

  useEffect(() => {
    UserService.getPublicContent().then(
      (response) => {
        setContent(response.data);
      },
      (error) => {
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();
        setContent(_content);
      }
    );
  }, []);

  return (
    <div className="home-container">
      <header className="jumbotron">
        <h1>Welcome to Billing Reconciliation</h1>
      </header>

      <main>
        <section id="home" className="hero">
          <div className="container1">
            <div className="hero-content">
              {/* <h1 className="hero-title">Streamline Your Telecom Inventory</h1> */}
              <p className="hero-description">
              Reconciling data usage records with billing records is a critical process for ensuring accuracy and transparency in billing operations. This procedure involves comparing recorded data usage with the billed amounts to identify any discrepancies. By systematically aligning these records, organizations can detect undercharges, overcharges, and potential fraud. Accurate reconciliation helps in maintaining customer trust and satisfaction by ensuring that bills reflect actual usage. Additionally, it aids in financial accuracy and regulatory compliance. Overall, the reconciliation process is essential for operational integrity and financial health.
              </p>
              <Link to="/Login" className="primary-button">Get Started</Link>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
};

export default Home;
