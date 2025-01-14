import { useState, useEffect } from 'react';
import reactLogo from './assets/react.svg';
import viteLogo from '/vite.svg';
import './App.css';
import PayPalButton from './PayPalButton';  // Correct PayPalButton import

function App() {
    const [count, setCount] = useState(0);
    const [backendMessage, setBackendMessage] = useState('');

    // Fetch data from the Java backend
    useEffect(() => {
        fetch('http://localhost:8080/compute') // Adjust if necessary
            .then((response) => response.json())
            .then((data) => {
                setBackendMessage(data.message);
            })
            .catch((error) => {
                console.error('Error connecting to backend:', error);
                setBackendMessage('Error connecting to backend.');
            });
    }, []);

    return (
        <>
            <div>
                <a href="https://vite.dev" target="_blank">
                    <img src={viteLogo} className="logo" alt="Vite logo" />
                </a>
                <a href="https://react.dev" target="_blank">
                    <img src={reactLogo} className="logo react" alt="React logo" />
                </a>
            </div>
            <h1>Welcome to Fresh Lokals</h1>
            <h2>Message from Backend:</h2>
            <p>{backendMessage}</p>

            <div className="card">
                <button onClick={() => setCount(count + 1)}>
                    count is {count}
                </button>
                <p>
                    Edit <code>src/App.jsx</code> and save to test HMR
                </p>
            </div>
            <PayPalButton />  {/* Correct PayPalButton render */}
        </>
    );
}

export default App;
