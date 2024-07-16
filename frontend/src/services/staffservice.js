import axios from "axios";
import authHeader from "./auth-header";
const BASE_URL = "http://localhost:8080/api/telecomstaff";
const getAllreconcilecdr = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/reconcilecdr`, {
      headers: {
        'Content-Type': 'application/json',
        ...authHeader(),
        'Access-Control-Allow-Origin': '*'
      }
    });
    console.log(response.data);
    return response.data;
  } catch (err) {
    throw err;
  }
};
const getalldiscrepancy = async () => {
    try {
      const response = await axios.get(`${BASE_URL}/getalldiscrepancy`, {
        headers: {
          'Content-Type': 'application/json',
          ...authHeader(),
          'Access-Control-Allow-Origin': '*'
        }
      });
      return response.data;
    } catch (err) {
      throw err;
    }
  };

  const updateDiscrepancy = async(data) => {
    try {
        const response = await axios.post(`${BASE_URL}/flagdiscrepancybyid`, data,
          {headers: {
              'Content-Type': 'application/json',
              ...authHeader(),
              'Access-Control-Allow-Origin': '*'
            }}
        );
        return response.data;
      } catch (err) {
        throw err;
      }
};

const Staffservice = {
    getAllreconcilecdr,
    getalldiscrepancy,
    updateDiscrepancy  
};
export default Staffservice;