import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import './App.css';

const Register = () => {
    // Űrlap adatok
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        phoneNumber: '', 
        password: '',
        confirmPassword: ''
    });
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleRegister = async (e) => {
        e.preventDefault();
        if (formData.password !== formData.confirmPassword) {
            setError("A két jelszó nem egyezik!");
            return;
        }

        try {
            await axios.post('http://localhost:8080/api/auth/register', {
                username: formData.username,
                email: formData.email,
                phoneNumber: formData.phoneNumber,
                password: formData.password,
                role: 'ROLE_USER'
            });

            alert("Sikeres regisztráció! Most már bejelentkezhetsz.");
            navigate('/'); 
        } catch (err) {
            setError("Hiba a regisztráció során!");
        }
    };

    return (
        <div className="container center-screen">
            <div className="card login-card">
                <h2>Regisztráció</h2>
                <form onSubmit={handleRegister} className="form-box">
                    <input
                        type="text" name="username" placeholder="Felhasználónév"
                        value={formData.username} onChange={handleChange} required
                    />
                    <input
                        type="email" name="email" placeholder="Email cím"
                        value={formData.email} onChange={handleChange} required
                    />
                    <input
                        type="text" name="phoneNumber" placeholder="Telefonszám (Opcionális)"
                        value={formData.phoneNumber} onChange={handleChange}
                    />
                    <input
                        type="password" name="password" placeholder="Jelszó"
                        value={formData.password} onChange={handleChange} required
                    />
                    <input
                        type="password" name="confirmPassword" placeholder="Jelszó megerősítése"
                        value={formData.confirmPassword} onChange={handleChange} required
                    />
                    
                    <button type="submit">Regisztráció</button>
                    {error && <p className="error">{error}</p>}
                </form>
                <p style={{marginTop: '20px'}}>
                    Van már fiókod? <Link to="/">Jelentkezz be!</Link>
                </p>
            </div>
        </div>
    );
};

export default Register;