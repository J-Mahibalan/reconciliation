import React, { useState, useEffect } from 'react';
import CDRservice from "../services/cdrservice";
import 'bootstrap/dist/css/bootstrap.min.css';

const GenerateBill = () => {
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
    <table className="table mt-4">
      <thead>
        <tr>
          <th>Bill_id</th>
          <th>Billing_period</th>
          <th>Due_date</th>
          <th>Total_amount</th>
          <th>Subscriber_Id</th>
        </tr>
      </thead>
      <tbody>
        {billingMessage.map((bill, index) => (
          <tr key={index}>
            <td>{bill.billId}</td>
            <td>{bill.billingPeriod}</td>
            <td>{bill.dueDate.substring(0,10)}</td>
            <td>{bill.totalAmount.toString().substring(0,5)}</td>
            <td>{bill.subscriber.subscriberId}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default GenerateBill;
