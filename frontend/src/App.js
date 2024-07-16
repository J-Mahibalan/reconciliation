import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
 
import AuthService from "./services/auth.service";
import Login from "./components/Login";
import Register from "./components/Register";
import Home from "./components/home";
import Profile from "./components/Profile";
import BoardAdmin from "./components/BoardAdmin";
import BoardStaff from "./components/BoardStaff";
import AdminPanel from "./components/AdminPanel";
import EventBus from "./common/EventBus";
import FetchCDR from "./components/fetchCDR";
import Calculatecost from "./components/Calculatecost";
import ReconcileCDR from "./components/reconcilecdr";
import ReconcileBill from "./components/reconcilebill";
import FlagDiscrepancyForm from "./components/ResolveForm";
import UpdateDiscrepancy from "./components/UpdateDiscrepancy";
import Update from "./components/update";
import Subscriberlist from "./components/Subscriberlist";
import Reconcilediscrepancies from "./components/Reconcilediscrepancies";
import Discrepancy from "./components/Discrepancy";



 
const App = () => {
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [showStaffBoard, setShowStaffBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);
 
  useEffect(() => {
    const user = AuthService.getCurrentUser();
 
    if (user) {
      setCurrentUser(user);
      setShowAdminBoard(user.role.includes("ROLE_ADMIN"));
      setShowStaffBoard(user.role.includes("ROLE_TELECOM_STAFF"));
    }
 
    EventBus.on("logout", () => {
      logOut();
    });
 
    return () => {
      EventBus.remove("logout");
    };
  }, []);
 
  const logOut = () => {
    AuthService.logout();
    setShowAdminBoard(false);
    setShowStaffBoard(false);
    setCurrentUser(undefined);
  };
 
  return (
    <div>
      <nav className="navbar navbar-expand">
        <Link to={"/"} className="navbar-brand">
          <strong>RECON</strong>
        </Link>
        <div className="navbar-nav">
          <li className="nav-item">
            <Link className="nav-link" to={"/home"}>
              <strong>Home</strong>
            </Link>
          </li>
 
          {showAdminBoard && (
            <li className="nav-item">
              <Link  className="nav-link" to={"/admin"}>
              <strong>Admin Board</strong>
              </Link>
            </li>
          )}
          {showStaffBoard && (
            <li className="nav-item">
              <Link to={"/staff"} className="nav-link">
              <strong>Staff Board</strong>
              </Link>
            </li>
          )}
          
        </div>
 
        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/profile"} className="nav-link">
                <strong>{currentUser.username}</strong>
              </Link>
            </li>
            <li className="nav-item">
              <a href="/login" className="nav-link" onClick={logOut}>
              <strong>LogOut</strong>
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
              <strong>Login</strong>
              </Link>
            </li>
 
            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
              <strong>Sign Up</strong>
              </Link>
            </li>
          </div>
        )}
      </nav>
 
      <div className="container mt-3">
        <Routes>
          <Route path="/" element={<Home/>} />
          <Route path="/home" element={<Home/>} />
          <Route path="/login" element={<Login/>} />
          <Route path="/register" element={<Register/>} />
          <Route path="/profile" element={<Profile/>} />
          <Route path="/admin" element={<BoardAdmin/>} />
          <Route path="/staff" element={<BoardStaff/>} />
          <Route path = "/update-discrepency/:discrepancyId" element={<UpdateDiscrepancy/>}/>
          <Route path="/FetchCDR" element={<FetchCDR/>}/>
          <Route path="/Calculatecost" element={<Calculatecost/>}/>
          <Route path="/ReconcileBill" element={<ReconcileBill/>}/>
          <Route path="/ReconcileCDR" element={<ReconcileCDR/>}/>
          <Route path="/ResolveForm" element={<FlagDiscrepancyForm/>}/>
          <Route path="/AdminPanel" element={<AdminPanel/>}/>
          <Route path="/Subscriberlist" element={<Subscriberlist/>}/>
          <Route path="/Reconcilediscrepancies" element={<Reconcilediscrepancies/>}/>
          <Route path="/UpdateDiscrepancy" element={<Update/>}/>
          <Route path="/Discrepancy" element={<Discrepancy/>}/>
        </Routes>
      </div>
 
    </div>
  );
};

export default App;