  // import React, { useState, useEffect } from "react";
  import { Link } from "react-router-dom";
  import "./admin.css";

  const BoardAdmin = () => {

    return (
      <div className="container">
        <header className="admin-header">
          <h3>Admin Board</h3>
        </header>
        <div className="admin-buttons">
        <div className="button-row">
          <Link className="admin-button" to="/FetchCDR">Fetch Data Usage</Link>
          <Link className="admin-button" to="/Calculatecost">Calculate Cost</Link>
          <Link className="admin-button" to="/ReconcileBill">Reconcile Bill</Link>
        </div>
        <div className="button-row">
          <Link className="admin-button" to="/Reconcilediscrepancies">Resolve Discrepancies</Link>
          <Link className="admin-button" to="/Subscriberlist">Subscriber List</Link>
          <Link className="admin-button" to="/Discrepancy">Discrepancies</Link>
        </div>
      </div>
      </div>
    );
  };

  export default BoardAdmin;
