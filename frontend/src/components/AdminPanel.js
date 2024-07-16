import React, { useState } from 'react';
import axios from 'axios';
import authHeader from "../services/auth-header";
import "./adminpanel.css";

const AdminPanel = () => {
    const BASE_URL = "http://localhost:8080"
    const [createUsername, setCreateUsername] = useState('');
    const [createPassword, setCreatePassword] = useState('');
    const [createEmail, setCreateEmail] = useState('');
    const [updateId, setUpdateId] = useState('');
    const [updateUsername, setUpdateUsername] = useState('');
    const [updatePassword, setUpdatePassword] = useState('');
    const [updateEmail, setUpdateEmail] = useState('');
    const [telecomStaff, setTelecomStaff] = useState(null);
    const [message, setMessage] = useState('');

    const createTelecomStaff = async () => {
        try {
            const response = await axios.post(BASE_URL+'/api/admin/addtelecomstaff', {
                username: createUsername,
                password: createPassword,
                email: createEmail
            },{headers: {
                'Content-Type': 'application/json',
                ...authHeader(),
                'Access-Control-Allow-Origin': '*'
            }});
            setTelecomStaff(response.data);
            console.log(response.data);
            setMessage('Telecom staff created successfully.');
        } catch (error) {
            setMessage('Error: ' + error.message);
        }
    };

    const getTelecomStaff = async (id) => {
        try {
            const response = await axios.get(BASE_URL+`/api/admin/gettelecomstaff/${id}`,
            {headers: {
                'Content-Type': 'application/json',
                ...authHeader(),
                'Access-Control-Allow-Origin': '*'
            }});
            setTelecomStaff(response.data);
            console.log(response.data);
            setMessage('');
        } catch (error) {
            setMessage('Error: ' + error.message);
        }
    };

    const updateTelecomStaff = async () => {
        try {
            const response = await axios.post(BASE_URL+`/api/admin/updatetelecomstaff`,{
                id: updateId,
                username:updateUsername,
                password:updatePassword,
                email:updateEmail
            },
            {headers: {
                'Content-Type': 'application/json',
                ...authHeader(),
                'Access-Control-Allow-Origin': '*'
            }});
            setTelecomStaff(response.data);
            console.log(response.data);
           
            setMessage('Telecom staff updated successfully.');
        } catch (error) {
            setMessage('Error: ' + error.message);
        }
 
    };

    return (
        <div>
            <h2>Admin Panel</h2>
            <div className='create'>
                <h3 className='h-1'>Create Telecom Staff</h3>
                <label>Username: </label>
                <input className='input' type="text" value={createUsername} onChange={(e) => setCreateUsername(e.target.value)} />
                <br />
                <label>Password: </label>
                <input className='input' type="password" value={createPassword} onChange={(e) => setCreatePassword(e.target.value)} />
                <br />
                <label>Email: </label>
                <input className='input' type="email" value={createEmail} onChange={(e) => setCreateEmail(e.target.value)} />
                <br /><br />
                <button className='btn-1' onClick={createTelecomStaff}>Create</button>
            </div><br/>
            <div className='get'>
                <h3 className='h-2'>Get Telecom Staff</h3>
                <label>Staff ID: </label>
                <input className='input' type="number" onChange={(e) => getTelecomStaff(e.target.value)} />
                <br />
                <br />
                <br />
                {telecomStaff && (
                <div>
                    <p>Username: {telecomStaff.username}</p>
                    <p>Password: {telecomStaff.password}</p>
                    <p>Email: {telecomStaff.email}</p>
                </div>
                )}
            </div><br/>
            <div className='update'>
                <h3>Update Telecom Staff</h3>
                <label>ID: </label>
                <input className='input' type="number" value={updateId} onChange={(e) => setUpdateId(e.target.value)} />
                <br />
                <label>Username: </label>
                <input className='input' type="text" value={updateUsername} onChange={(e) => setUpdateUsername(e.target.value)} />
                <br />
                <label>Password: </label>
                <input className='input' type="password" value={updatePassword} onChange={(e) => setUpdatePassword(e.target.value)} />
                <br />
                <label>Email: </label>
                <input className='input' type="email" value={updateEmail} onChange={(e) => setUpdateEmail(e.target.value)} />
                <br /><br/>
                <button className='btn-2' onClick={updateTelecomStaff}>Update</button><br/>
                <p></p>{message && <p>{message}</p>}
            </div>
        </div>
    );
};

export default AdminPanel;
