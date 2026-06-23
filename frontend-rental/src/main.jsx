import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'

// BrowserRouter 讓 React 可以使用前端路由(網頁路徑切換)
import { BrowserRouter } from 'react-router-dom'
import './styles.css'


import App from './App.jsx'



createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </StrictMode>,
)
