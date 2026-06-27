import { useState } from 'react'
import Navbar from './components/Navbar'
import LoginView from './views/LoginView'
import AdminView from './views/AdminView'
import TeacherView from './views/TeacherView'
import StudentView from './views/StudentView'

function App() {
  const [token, setToken] = useState(localStorage.getItem('token'))
  const [currentUser, setCurrentUser] = useState(
    JSON.parse(localStorage.getItem('user') || 'null')
  )

  // 登入成功後由 LoginView 呼叫，傳入 LoginResponseDTO
  const handleLogin = (loginData) => {
    localStorage.setItem('token', loginData.token)
    localStorage.setItem('user', JSON.stringify(loginData.user))
    setToken(loginData.token)
    setCurrentUser(loginData.user)
  }

  const handleLogout = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    setToken(null)
    setCurrentUser(null)
  }

  // 根據 Role 決定渲染哪個視窗
  const renderView = () => {
    switch (currentUser?.role) {
      case 'ROLE_ADMIN':    return <AdminView currentUser={currentUser} />
      case 'ROLE_TEACHER':  return <TeacherView currentUser={currentUser} />
      case 'ROLE_STUDENT':  return <StudentView currentUser={currentUser} />
      default:              return <p style={{ padding: '20px' }}>未知角色</p>
    }
  }

  if (!token || !currentUser) {
    return <LoginView onLogin={handleLogin} />
  }

  return (
    <>
      <Navbar currentUser={currentUser} onLogout={handleLogout} />
      {renderView()}
    </>
  )
}

export default App
