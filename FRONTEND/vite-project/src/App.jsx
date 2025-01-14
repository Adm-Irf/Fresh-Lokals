import { useState, useEffect } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Header from './Header';

function App() {
  const [count, setCount] = useState(0);
  const [backendMessage, setBackendMessage] = useState('');

  // Fetch data from the Java backend
  useEffect(() => {
    // Fetch data from the Java backend
    fetch('http://localhost:8080/compute') // Adjust the URL if your backend uses a different port
      .then((response) => response.json())
      .then((data) => {
        setBackendMessage(data.message); // Save the backend message
      })
      .catch((error) => {
        console.error('Error connecting to backend:', error);
        setBackendMessage('Error connecting to backend.');
      });
  }, []); // Empty dependency array ensures this runs only once on component mount

  return (
    <>
      <Header/>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <h2>Message from Backend:</h2>
      <p>{backendMessage}</p>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}

export default App
