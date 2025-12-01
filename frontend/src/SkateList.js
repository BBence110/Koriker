import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const SkateList = () => {
    const [skates, setSkates] = useState([]);
    const [myRentals, setMyRentals] = useState([]);
    const navigate = useNavigate();
    
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const role = localStorage.getItem('role');

    useEffect(() => {
        if (!token) {
            navigate('/'); // Ha nincs token, kidobjuk
            return;
        }
        fetchData();
    }, [token, navigate]);

    const fetchData = async () => {
        const config = { headers: { Authorization: `Bearer ${token}` } };
        try {
            const resSkates = await axios.get('http://localhost:8080/api/skates', config);
            setSkates(resSkates.data);
            
            const resRentals = await axios.get('http://localhost:8080/api/rentals/my', config);
            setMyRentals(resRentals.data);
        } catch (error) {
            console.error("Hiba az adatok lekérésekor", error);
        }
    };

    const handleRent = async (skateId) => {
        try {
            await axios.post(`http://localhost:8080/api/rentals/rent/${skateId}`, {}, {
                headers: { Authorization: `Bearer ${token}` }
            });
            alert("Sikeres kölcsönzés!");
            fetchData();
        } catch (error) {
            alert("Hiba: Már foglalt vagy lejárt a munkamenet.");
        }
    };

    const handleReturn = async (rentalId) => {
        try {
            await axios.post(`http://localhost:8080/api/rentals/return/${rentalId}`, {}, {
                headers: { Authorization: `Bearer ${token}` }
            });
            alert("Visszavéve!");
            fetchData();
        } catch (error) {
            alert("Hiba történt");
        }
    };

    const logout = () => {
        localStorage.clear();
        navigate('/');
    };

    return (
        <div className="container">
            <header className="header">
                <div>
                    <h1>⛸️ Jégkorcsolya Kölcsönző</h1>
                    <p>Üdvözöllek, <strong>{username}</strong>! ({role === 'ROLE_ADMIN' ? 'Adminisztrátor' : 'Felhasználó'})</p>
                </div>
                <button onClick={logout} className="logout-btn">Kijelentkezés</button>
            </header>

            <h3>Elérhető Korcsolyák</h3>
            <div className="grid">
                {skates.map(skate => (
                    <div key={skate.id} className={`card ${skate.available ? 'available' : 'rented'}`}>
                        <div className="icon">{skate.type === 'Hoki' ? '🏒' : '⛸️'}</div>
                        <h4>{skate.type}</h4>
                        <p>Méret: <strong>{skate.size}</strong></p>
                        <p>Szín: {skate.color}</p>
                        {skate.available ? (
                            <button onClick={() => handleRent(skate.id)} className="rent-btn">Kölcsönzés</button>
                        ) : (
                            <span className="status-badge">Foglalt</span>
                        )}
                    </div>
                ))}
            </div>

            <hr />

            <h3>Saját Kölcsönzéseim</h3>
            {myRentals.length === 0 ? <p>Nincs aktív kölcsönzésed.</p> : (
                <ul className="rental-list">
                    {myRentals.map(rental => (
                        <li key={rental.id} className={rental.active ? 'active-rental' : 'closed-rental'}>
                            <span>
                                <strong>{rental.skate.type}</strong> (Méret: {rental.skate.size}) 
                                - {rental.active ? " 🟢 Aktív" : " ⚫ Lezárva"}
                            </span>
                            {rental.active && (
                                <button onClick={() => handleReturn(rental.id)} className="return-btn">Visszavétel</button>
                            )}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default SkateList;