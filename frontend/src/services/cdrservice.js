import axios from "axios";
import authHeader from "./auth-header";
const BASE_URL = "http://localhost:8080/api/admin";
const getAllCDR = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/fetchdatausagedetails`, {
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
const getDiscrepancy = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getalldiscrepancy`, {
      headers: {
        'Content-Type': 'application/json',
        ...authHeader() ,
       'Access-Control-Allow-Origin': '*'
       }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};
const getgenerateMonthlyBillingRecords = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/calculatecost`, {
      headers: {
        'Content-Type': 'application/json',
        ...authHeader() ,
       'Access-Control-Allow-Origin': '*'
       }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};
const getreconcilediscrepancies = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/resolvediscrepancies`, {
      headers: {
        'Content-Type': 'application/json',
        ...authHeader() ,
       'Access-Control-Allow-Origin': '*'
       }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};
const getAllreconcilebill = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/reconcile`, {
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

const getSubscriberById = async (Id) => { // Updated function to use Axios
  try {
    const response = await axios.post(`${BASE_URL}/billdetailsbysubscriber`,{id: `${Id}`}, 
      {
      headers: {
        'Content-Type': 'application/json',
        ...authHeader(),
        'Access-Control-Allow-Origin': '*'
      }
      
    });
    console.log(response.data);
    return response.data; // Return response data
  } catch (err) {
    throw err;
  }
};

const CDRservice = {
    getAllCDR,
    getgenerateMonthlyBillingRecords,
    getAllreconcilebill,
    getreconcilediscrepancies,
    getSubscriberById,
    getDiscrepancy,
};
export default CDRservice;
