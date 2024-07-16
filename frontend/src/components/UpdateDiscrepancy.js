import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import staffservice from '../services/staffservice';
import "./UpdateDiscrepancy.css";
 
function UpdateDiscrepancy() {
  const navigate = useNavigate();
 
  const [discrepancyData, setDiscrepancyData] = useState({
    id:'',
    checkDiscrepancy:''
  });
 
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setDiscrepancyData((prevDiscrepancyData) => ({
      ...prevDiscrepancyData,
      [name]: value
    }));
  };
 
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await staffservice.updateDiscrepancy(discrepancyData);
      navigate("/staff");
    } catch (error) {
      console.error('Error updating discrepancy : ', error);
      alert(error.message || 'An error occurred while updating discrepancy.');
    }
  };
 
  return (
    <div className="auth-container">
      <h2 className='h2'><strong>UPDATE DISCREPANCY</strong></h2>
      <form onSubmit={handleSubmit}>
      <div className="form-group">
          <label>Discrepancy ID :</label>
          <input className='input' type="number" name="id" value={discrepancyData.id || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Resolved :</label>
          <input className='input' type="text" name="checkDiscrepancy" value={discrepancyData.checkDiscrepancy || ''} onChange={handleInputChange} />
        </div>
       
        <button className='update-btn' type="submit">UPDATE</button>
      </form>
    </div>
  );
}
 
export default UpdateDiscrepancy;