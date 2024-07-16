import React, { useState, useEffect, useRef } from "react";
import { useNavigate,Link } from "react-router-dom";
import staffservice from "../services/staffservice";
const Update = () => {
    const [newDiscrepency, setNewDiscrepency] = useState({
      description: '',
      resolved : '',
      cdrId : ''
    });
  
    const [discrepancies,setDiscrepancies] = useState([]);
    const [loading, setLoading] = useState(false);
  
    const form = useRef();
    const navigate = useNavigate();
   
    useEffect(() => {
      fetchDiscrepancies();
    }, []);
   
    const fetchDiscrepancies = () => {
      staffservice.getalldiscrepancy()
        .then(response => {
          setDiscrepancies(response);
          console.log(response);
        })
        .catch(error => {
          console.error('Error fetching discripencies:', error);
        });
    };
return (
<table className="table mt-4">
        <thead>
          <tr>
          <th>Discrepancy_Id</th>
          <th>Description</th>
          <th>Resolved</th>
          <th>CDR_Id</th>
          <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {discrepancies.map(discrepancy => (
            <tr key={discrepancy.discrepancyId}>
              <td>{discrepancy.discrepancyId}</td>
              <td>{discrepancy.description}</td>
              <td>{discrepancy.resolved.toString()}</td>
              <td>{discrepancy.cdr.cdrId}</td>
              <td>
              <button className="btn btn-default"><Link to={`/update-discrepency/${discrepancy.discrepancyId}`}>Update</Link></button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
)
}
export default Update;