import React, { useState, useEffect } from 'react';
import CDRservice from "../services/cdrservice";
import 'bootstrap/dist/css/bootstrap.min.css';

const ReconcileBill = () => {
  const [billr, setreconcilebill] = useState([]);

  useEffect(() => {
    reconcilebill();
  }, []);

  const reconcilebill = () => {
    CDRservice.getAllreconcilebill()
      .then(response => {
        console.log(response)
        setreconcilebill(response);
      })
      .catch(error => {
        console.error('Error fetching reconciled Bill', error);
      });
  };

  return (
    <table className="custom-table">
      <thead>
        <tr>
          <th>Discrepancy ID</th>
          <th>Description</th>
          <th>Status</th>
          <th>Subscriber ID</th>
        </tr>
      </thead>
      <tbody>
        {billr.map((billr, index) => (
          <tr key={index}>
            <td>{billr.discrepancyId}</td>
            <td>{billr.description}</td>
            <td>{billr.resolved ? 'Complete' : 'Incomplete'}</td>
            <td>{billr.subscriber.subscriberId}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default ReconcileBill;