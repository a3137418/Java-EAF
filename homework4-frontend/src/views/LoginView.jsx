import { useState } from 'react'
import api from '../services/api'

function LoginView({ onLogin }) {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!username || !password) { setError('請輸入帳號與密碼'); return }
    setError('')
    try {
      const response = await api.post('/auth/login', { username, password })
      onLogin(response.data.data)
    } catch (err) {
      setError(err.response?.data?.message || '帳號或密碼錯誤')
    }
  }

  return (
    <div className="login-overlay">
      <div className="login-box">
        <h2>Learning Platform 登入</h2>
        <div className="form-group">
          <label>帳號</label>
          <input
            value={username}
            onChange={e => setUsername(e.target.value)}
            placeholder="username"
          />
        </div>
        <div className="form-group">
          <label>密碼</label>
          <input
            type="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            placeholder="password"
            onKeyDown={e => e.key === 'Enter' && handleSubmit(e)}
          />
        </div>
        <button className="btn btn-primary login-btn" onClick={handleSubmit}>
          登入
        </button>
        <div className="login-error">{error}</div>
      </div>
    </div>
  )
}

export default LoginView
