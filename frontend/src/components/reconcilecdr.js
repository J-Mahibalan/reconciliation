import React, { useState, useEffect } from 'react';
import Staffservice from '../services/staffservice';
import 'bootstrap/dist/css/bootstrap.min.css';

const ReconcileCDR = () => {
  const [cbill, setreconcilecdr] = useState([]);

  useEffect(() => {
    reconcilecdr();
  }, []);

  const reconcilecdr = () => {
    Staffservice.getAllreconcilecdr()
      .then(response => {
        console.log(response)
        setreconcilecdr(response);
      })
      .catch(error => {
        console.error('Error fetching reconciled CDR', error);
      });
  };

  return (
    <table className="table mt-4">
      <thead>
        <tr>
          <th>cdr_id</th>
          <th>call_cost</th>
          <th>call_type</th>
          <th>callee_id</th>
          <th>duration</th>
          <th>start_time</th>
          <th>subscriber_id</th>
        </tr>
      </thead>
      <tbody>
        {cbill.map((cdrbill) => (
          <tr key={cdrbill.cdrId}>
            <td>{cdrbill.cdrId}</td>
            <td>{cdrbill.callCost}</td>
            <td>{cdrbill.callType}</td>
            <td>{cdrbill.calleeId}</td>
            <td>{cdrbill.duration}</td>
            <td>{cdrbill.startTime}</td>
            <td>{cdrbill.subscriber.subscriberId}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default ReconcileCDR;