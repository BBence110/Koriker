import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const SkateList = () => { 
    const [skates, setSkates] = useState([]);
    const [myRentals, setMyRentals] = useState([]);
    const [allUsers, setAllUsers] = useState([]); // Adminnak: felhasználók
    
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

            //admin felületen felhasznalok lekerese
            if (role === 'ROLE_ADMIN') {
                const resUsers = await axios.get('http://localhost:8080/api/users', config);
                setAllUsers(resUsers.data);
            }

        } catch (error) {
            console.error("Hiba az adatok lekérésekor", error);
        }
    };
    
    const getSkateIcon = (type) => {
        if (!type) return '🎁';
        if (type.includes('Hoki')) return '🏒';
        if (type.includes('Műkorcsolya')) return '❄️';
        if (type.includes('Gyerek')) return '⛄';
        if (type.includes('Gyors')) return '🦌';
        return '🎁';
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
            alert("Visszaviszem!");
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
                    <p>Üdvözöllek, <strong>{username}</strong>! ({role === 'ROLE_ADMIN' ? 'Adminisztrátor' : 'Felhasználó'})</p>
                </div>
                <button onClick={logout} className="logout-btn">Kijelentkezés</button>
            </header>
            
            {role === 'ROLE_ADMIN' && (
                <div className="card" style={{marginBottom: '30px', border: '2px solid #16a085'}}>
                    <h3>➕ Új Korcsolya Felvétele ➕</h3>
                    <form onSubmit={handleAddSkate} style={{display:'flex', gap:'10px', justifyContent:'center', flexWrap:'wrap'}}>
                        <select 
                            value={newSkate.type} 
                            onChange={(e) => setNewSkate({...newSkate, type: e.target.value})}
                            style={{padding:'10px', borderRadius:'20px', border:'1px solid #ccc'}}
                        >
                            <option value="Hoki">🏒 Hoki 🏒</option>
                            <option value="Műkorcsolya">❄️ Műkorcsolya ❄️</option>
                            <option value="Gyerek">⛄ Gyerek ⛄</option>
                        </select>

                        <select 
                            value={newSkate.size} 
                            onChange={(e) => setNewSkate({...newSkate, size: e.target.value})}
                            style={{padding:'10px', borderRadius:'20px', border:'1px solid #ccc'}}
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
                                style={{marginTop:'10px', backgroundColor:'#c0392b', width:'100%', color:'white'}}
                            >
                                Törlés
                            </button>
                        )}
                    </div>
                ))}
            </div>

            <hr />

            <h3>Saját Kölcsönzéseim: </h3>
            {myRentals.length === 0 ? <p style={{textAlign:'center', color:'#555'}}>Nincs aktív kölcsönzésed.</p> : (
                <ul className="rental-list">
                    {myRentals.map(rental => (
                        <li key={rental.id} className={rental.active ? 'active-rental' : 'closed-rental'}>
                            <span>
                                {getSkateIcon(rental.skate.type)} <strong>{rental.skate.type}</strong> (Méret: {rental.skate.size}) 
                                - {rental.active ? " ✅ Aktív" : " ❌ Inaktív"}
                            </span>
                            {rental.active && (
                                <button onClick={() => handleReturn(rental.id)} className="return-btn">Visszavétel</button>
                            )}
                        </li>
                    ))}
                </ul>
            )}

            {role === 'ROLE_ADMIN' && (
                <div style={{marginTop: '40px'}}>
                    <hr />
                    <h3>Regisztrált Felhasználók: </h3>
                    <div className="card" style={{overflowX: 'auto'}}>
                        <table style={{width: '100%', borderCollapse: 'collapse'}}>
                            <thead>
                                <tr style={{backgroundColor: '#f8f9fa', borderBottom: '2px solid #ddd'}}>
                                    <th style={{padding: '10px', textAlign:'left'}}>ID</th>
                                    <th style={{padding: '10px', textAlign:'left'}}>Felhasználónév</th>
                                    <th style={{padding: '10px', textAlign:'left'}}>Email</th>
                                    <th style={{padding: '10px', textAlign:'left'}}>Telefonszám</th>
                                    <th style={{padding: '10px', textAlign:'left'}}>Szerepkör</th>
                                </tr>
                            </thead>
                            <tbody>
                                {allUsers.map(user => (
                                    <tr key={user.id} style={{borderBottom: '1px solid #eee'}}>
                                        <td style={{padding: '10px'}}>{user.id}</td>
                                        <td style={{padding: '10px'}}><strong>{user.username}</strong></td>
                                        <td style={{padding: '10px'}}>{user.email}</td>
                                        <td style={{padding: '10px'}}>{user.phoneNumber || '-'}</td>
                                        <td style={{padding: '10px'}}>
                                            <span style={{
                                                padding: '5px 10px', 
                                                borderRadius: '15px', 
                                                backgroundColor: user.role === 'ROLE_ADMIN' ? '#d1c4e9' : '#c8e6c9',
                                                color: user.role === 'ROLE_ADMIN' ? '#512da8' : '#2e7d32',
                                                fontWeight: 'bold',
                                                fontSize: '0.8em'
                                            }}>
                                                {user.role === 'ROLE_ADMIN' ? 'ADMIN' : 'USER'}
                                            </span>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            )}
        </div>
    );
};

export default SkateList;