import React, { useState } from 'react';
import CDRservice from "../services/cdrservice";
import 'bootstrap/dist/css/bootstrap.min.css';

const Subscriberlist = () => {
  const [subscriberId, setSubscriberId] = useState('');
  const [bills, setBills] = useState([]);
  const [error, setError] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await CDRservice.getSubscriberById(subscriberId);
      console.log(response);

      if (response && response.length > 0) {
        setBills(response);
        setError('');
      } else {
        setError('No bills found for this subscriber.');
      }
    } catch (error) {
      setError('Failed to fetch bills. Please try again.');
      console.error('Error fetching bills:', error);
    }
  };

  return (
   <div className="app-container">
  <h1 className="app-title">Subscriber Bills</h1>
  
  <form onSubmit={handleSubmit} className="subscriber-form">
    <label htmlFor="subscriberId" className="form-label">Subscriber ID:</label>
    
    <input
      type="number"
      id="subscriberId"
      value={subscriberId}
      onChange={(e) => setSubscriberId(e.target.value)}
      required
      className="form-input"
    />
    
    <button type="submit" className="submit-button">Show Bills</button>
  </form>

      {error && <p className="error">{error}</p>}

      <div id="billsContainer">
        {bills.length > 0 ? (
          <table className="custom-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Billing Period</th>
                <th>Due Date</th>
                <th>Amount</th>
              </tr>
            </thead>
            <tbody>
              {bills.map((bill, index) => (
                <tr key={index}>
                  <td>{bill.name}</td>
                  <td>{bill.email}</td>
                  <td>{bill.billingPeriod}</td>
                  <td>{bill.dueDate}</td>
                  <td>{bill.billingAmount}</td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <p>{error ? error : "No bills found for this subscriber."}</p>
        )}
      </div>
    </div>
  );
};

export default Subscriberlist;
