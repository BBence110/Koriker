import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './App.css';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', {
                username,
                password
            });

            // Adatok mentése a böngészőbe
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('role', response.data.role);
            localStorage.setItem('username', response.data.username);

            navigate('/skates');
        } catch (err) {
            setError('Hibás a felhasználónév vagy jelszó!');
        }
    };

    return (
        <div className="container center-screen">
            <div className="card login-card">
                <h2>⛸️KoriKer⛸️ -Korcsolya Kölcsönző</h2>
                <form onSubmit={handleLogin} className="form-box">
                    <input
                        type="text"
                        placeholder="Felhasználónév (pl. admin)"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                    <input
                        type="password"
                        placeholder="Jelszó (pl. admin)"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <button type="submit">Belépés</button>
                    {error && <p className="error">{error}</p>}
                </form>
            </div>
        </div>
    );
};

export default Login;