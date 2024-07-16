// import React, { useState } from 'react';
// import axios from 'axios';
// import authHeader from "../services/auth-header";
// import 'bootstrap/dist/css/bootstrap.min.css';

// const FlagDiscrepancyForm = () => {
//     const BASE_URL = "http://localhost:8080/api/telecomstaff"
//   const [id, setId] = useState('');
//   const [resolved, setResolved] = useState('');
//   const [message, setMessage] = useState('');

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     try {
//       const response = await axios.post(`${BASE_URL}/flagdiscrepancybyid`, id ,
//         {headers: {
//             'Content-Type': 'application/json',
//             ...authHeader(),
//             'Access-Control-Allow-Origin': '*'
//           }}
//       );
//       console.log(response.data);
//       setMessage(response.data);
//     } catch (error) {
//       setMessage('Error: ' + error.message);
//     }
//   };

//   return (
//     <div>
//       <form onSubmit={handleSubmit}>
//         <label>
//           Discrepancy ID:
//           <input
//             type="number"
//             value={id}
//             onChange={(e) => setId(e.target.value)}
//           />
//         </label>
//         <label>
//   Resolved:
//   <input
//     type="text"

//     onChange={(e) => setResolved(e.target.value)}
//   />
// </label>


//         <button type="submit">Flag Discrepancy</button>
//       </form>
//       {message && <p>{message}</p>}
//     </div>
//   );
// };

// export default FlagDiscrepancyForm;
