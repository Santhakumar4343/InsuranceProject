
import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom'; // Import the Router component
import App from './App'; // Your main App component

ReactDOM.render(
  <BrowserRouter> {/* Wrap your app with the Router */}
    <App />
  </BrowserRouter>,
  document.getElementById('root')
);

