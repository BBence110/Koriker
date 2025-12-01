import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const SkateList = () => {
    const [skates, setSkates] = useState([]);
    const [myRentals, setMyRentals] = useState([]);
    
    const [newSkate, setNewSkate] = useState({ type: 'Hoki', size: '38', color: '' });
    
    const navigate = useNavigate();
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const role = localStorage.getItem('role');

    useEffect(() => {
        if (!token) {
            navigate('/');
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
            console.error(error);
        }
    };
    
    const getSkateIcon = (type) => {
        if (type.includes('Hoki')) return '🏒';
        if (type.includes('Műkorcsolya')) return '❄️';
        if (type.includes('Gyerek')) return '⛄';
        return '🎁'; // Egyéb
    };

    const handleAddSkate = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8080/api/skates/add', newSkate, {
                headers: { Authorization: `Bearer ${token}` }
            });
            alert("Korcsolya hozzáadva!");
            setNewSkate({ type: 'Hoki', size: '38', color: '' }); 
            fetchData(); 
        } catch (error) {
            alert("Hiba a hozzáadáskor!");
        }
    };

    const handleDeleteSkate = async (id) => {
        if(!window.confirm("Biztosan törlöd?")) return;
        try {
            await axios.delete(`http://localhost:8080/api/skates/delete/${id}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            fetchData();
        } catch (error) {
            alert("Hiba a törléskor!");
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
                    <h1>⛸️ Jégkorcsolya Kölcsönző ⛸️</h1>
                    <p>Üdvözöllek, <strong>{username}</strong> a korcsolya foglaló oldalamon! ({role === 'ROLE_ADMIN' ? 'Adminisztrátor' : 'Felhasználó'})</p>
                </div>
                <button onClick={logout} className="logout-btn">Kijelentkezés</button>
            </header>
            
            {role === 'ROLE_ADMIN' && (
                <div className="card" style={{marginBottom: '30px', border: '2px solid #007bff'}}>
                    <h3>➕ Új Korcsolya Felvétele ➕</h3>
                    <form onSubmit={handleAddSkate} style={{display:'flex', gap:'10px', justifyContent:'center', flexWrap:'wrap'}}>
                        <select 
                            value={newSkate.type} 
                            onChange={(e) => setNewSkate({...newSkate, type: e.target.value})}
                            style={{padding:'10px'}}
                        >
                            <option value="Hoki">🏒 Hoki 🏒</option>
                            <option value="Műkorcsolya">❄️ Műkorcsolya ❄️</option>
                            <option value="Gyerek">👦 Gyerek 👧</option>
                        </select>

                        <select 
                            value={newSkate.size} 
                            onChange={(e) => setNewSkate({...newSkate, size: e.target.value})}
                            style={{padding:'10px'}}
                        >
                            {[...Array(16)].map((_, i) => (
                                <option key={i} value={30 + i}>{30 + i}</option>
                            ))}
                        </select>

                        <input 
                            type="text" 
                            placeholder="Szín (pl. Fekete)" 
                            value={newSkate.color}
                            onChange={(e) => setNewSkate({...newSkate, color: e.target.value})}
                            required
                        />
                        <button type="submit" className="rent-btn" style={{width:'auto'}}>Hozzáadás</button>
                    </form>
                </div>
            )}

            <h3>Elérhető Korcsolyák</h3>
            <div className="grid">
                {skates.map(skate => (
                    <div key={skate.id} className={`card ${skate.available ? 'available' : 'rented'}`}>
                        <div className="icon">{getSkateIcon(skate.type)}</div>
                        <h4>{skate.type}</h4>
                        <p>Méret: <strong>{skate.size}</strong></p>
                        <p>Szín: {skate.color}</p>
                        
                        {skate.available ? (
                            <button onClick={() => handleRent(skate.id)} className="rent-btn">Kölcsönzés</button>
                        ) : (
                            <span className="status-badge">Foglalt</span>
                        )}
                        {role === 'ROLE_ADMIN' && (
                            <button 
                                onClick={() => handleDeleteSkate(skate.id)} 
                                style={{marginTop:'10px', backgroundColor:'#dc3545', width:'100%', color:'white'}}
                            >
                                Törlés
                            </button>
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
                                - {rental.active ? " ✅ Aktív" : "❌ Lezárva"}
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