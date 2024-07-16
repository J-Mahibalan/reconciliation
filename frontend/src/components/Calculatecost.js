import React, { useState, useEffect } from 'react';
import CDRservice from "../services/cdrservice";
import 'bootstrap/dist/css/bootstrap.min.css';

const Calculatecost = () => {
  const [billingMessage, setBillingMessage] = useState([]);

  useEffect(() => {
    generateMonthlyBillingRecords();
  }, []);

  const generateMonthlyBillingRecords = () => {
    CDRservice.getgenerateMonthlyBillingRecords()
      .then(response => {
        console.log(response);
        setBillingMessage(response);
      })
      .catch(error => {
        console.error('Error generating monthly billing records:', error);
      });
  };

  return (
    <table className="custom-table">
      <thead>
        <tr>
          <th>Cost ID</th>
          <th>Subscriber ID</th>
          <th>Data Consumed</th>
          <th>Data Cost</th>
          <th>Start Time</th>
          <th>End Time</th>
        </tr>
      </thead>
      <tbody>
        {billingMessage.map((bill, index) => (
          <tr key={index}>
            <td>{bill.costId}</td>
            <td>{bill.subscriber.subscriberId}</td>
            <td>{bill.data}</td>
            <td>{bill.dataCost.toString().substring(0, 5)}</td>
            <td>{bill.startTime.toString().substring(0, 10) + " " + bill.startTime.toString().substring(11)}</td>
            <td>{bill.endTime.toString().substring(0, 10) + " " + bill.endTime.toString().substring(11)}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default Calculatecost;
