import React, { useState, useEffect } from 'react';
import CDRservice from "../services/cdrservice";

const Reconcilediscrepancies = () => {
  const [billingMessage, setBillingMessage] = useState([]);
  const [reconcile,setReconcile] = useState([]);
  useEffect(() => {
    generateMonthlyBillingRecords();
  }, []);

  const generateMonthlyBillingRecords = () => {

    CDRservice.getreconcilediscrepancies()
    .then(response => {
        console.log(response);
        setReconcile(response);
      })
      .catch(error => {
        console.error('Error reconsiling monthly billing records:', error);
      });
    CDRservice.getreconcilediscrepancies()
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
          <th>Bill ID</th>
          <th>Billing Period</th>
          <th>Due Date</th>
          <th>Total Amount</th>
          <th>Subscriber ID</th>
        </tr>
      </thead>
      <tbody>
        {billingMessage.map((bill, index) => (
          <tr key={index}>
            <td>{bill.billId}</td>
            <td>{bill.billingPeriod}</td>
            <td>{bill.dueDate}</td>
            <td>{bill.totalAmount.toString().substring(0, 5)}</td>
            <td>{bill.subscriber.subscriberId}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default Reconcilediscrepancies;