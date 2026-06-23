import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import ItemsPage from './pages/ItemsPage.jsx'
import LoginPage from './pages/LoginPage.jsx'
import MyReservationPage from './pages/MyReservationPage.jsx'
import AdminItemsPage from './pages/AdminItemsPage.jsx'
import AdminReservationsPage from './pages/AdminReservationsPage.jsx'


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <LoginPage />
  </StrictMode>,
)
