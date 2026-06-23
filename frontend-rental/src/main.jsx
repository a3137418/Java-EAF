import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import ItemsPage from './pages/ItemsPage.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <ItemsPage />
  </StrictMode>,
)
