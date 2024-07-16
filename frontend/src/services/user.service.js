import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/";


const getPublicContent = () => {
  return axios.get( "http://localhost:8080/api/test/"+ "all");
};
const getAdminBoard = () => {
  return axios.get(API_URL + "admin", { headers: authHeader() });
};
const getStaffBoard = () => {
  return axios.get(API_URL + "test/telecomstaff", { headers: authHeader() });
};

const UserService = {
  getPublicContent,
  getAdminBoard,
  getStaffBoard
};

export default UserService;
