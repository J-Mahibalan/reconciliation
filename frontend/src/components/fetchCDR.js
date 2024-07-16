import React, { useState, useEffect } from 'react';
import CDRservice from "../services/cdrservice";
import 'bootstrap/dist/css/bootstrap.min.css';

function FetchCDR  ()  {
  const [CDR, setCDR] = useState([]);

  useEffect(() => {
    fetchCDR();
  }, []);

  const fetchCDR = () => {
    CDRservice.getAllCDR()
      .then(response => {
        console.log(response)
        setCDR(response);
      })
      .catch(error => {
        console.error('Error fetching CDR:', error);
      });
  };

  return (
    <table className="custom-table">
      <thead>
        <tr>
          <th>Data ID</th>
          <th>Subscriber ID</th>
          <th>Start Time</th>
          <th>End Time</th>
          <th>Data Consumed</th>
        </tr>
      </thead>
      <tbody>
        {CDR.map((cdr, index) => (
          <tr key={index}>
            <td>{cdr.dataId}</td>
            <td>{cdr.subscriber.subscriberId}</td>
            <td>{cdr.startTime.toString().substring(0, 10) + " " + cdr.startTime.toString().substring(11)}</td>
            <td>{cdr.endTime.toString().substring(0, 10) + " " + cdr.endTime.toString().substring(11)}</td>
            <td>{cdr.dataConsumed}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default FetchCDR;