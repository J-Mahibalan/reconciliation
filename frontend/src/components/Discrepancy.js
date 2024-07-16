import React, { useState, useEffect } from 'react';
import CDRservice from "../services/cdrservice";
import 'bootstrap/dist/css/bootstrap.min.css';
 
function Discrepancy  ()  {
  const [CDR, setDiscrepancy] = useState([]);
 
  useEffect(() => {
    discrepancy();
  }, []);
 
  const discrepancy = () => {
    CDRservice.getDiscrepancy()
      .then(response => {
        console.log(response)
        setDiscrepancy(response);
      })
      .catch(error => {
        console.error('Error fetching Discrepancy:', error);
      });
  };
 
  return (
    <>
    <h1 style={{
  color: '#0C4160',
  fontFamily: 'Arial, sans-serif',
  fontSize: '28px',
  fontWeight: 'bold',
  textAlign: 'center',
  marginTop: '30px',
  marginBottom: '20px',
  paddingBottom: '10px',
  borderBottom: '2px solid #0C4160',
  textTransform: 'uppercase',
  letterSpacing: '1px'
}}>
  Discrepancy Table
</h1>
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
        {CDR.map((cdr, index) => (
          <tr key={index}>
            <td>{cdr.discrepancyId}</td>
            <td>{cdr.description}</td>
            <td>{cdr.resolved ? 'Complete' : 'Incomplete'}</td>
            <td>{cdr.subscriber.subscriberId}</td>
          </tr>
        ))}
      </tbody>
    </table>
    </>
  );
};
 
export default Discrepancy;