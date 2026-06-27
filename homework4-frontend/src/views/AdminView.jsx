import { useState, useEffect } from 'react'
import api from '../services/api'

function AdminView() {
  const [users, setUsers] = useState([])
  const [toast, setToast] = useState(null)

  const loadUsers = async () => {
    const res = await api.get('/users')
    setUsers(res.data)
  }

  useEffect(() => { loadUsers() }, [])

  const showToast = (msg, ok = true) => {
    setToast({ msg, ok })
    setTimeout(() => setToast(null), 2800)
  }

  const toggleStatus = async (id) => {
    try {
      await api.put(`/users/${id}/status`)
      showToast('帳號狀態已更新')
      loadUsers()
    } catch {
      showToast('操作失敗', false)
    }
  }

  const approvePayment = async (id) => {
    try {
      await api.post(`/users/${id}/approve`)
      showToast('金流審核完成，已設定為已繳費')
      loadUsers()
    } catch {
      showToast('操作失敗', false)
    }
  }

  return (
    <div className="container">
      <div className="card">
        <h2>使用者管理</h2>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>帳號</th>
              <th>Email</th>
              <th>角色</th>
              <th>繳費狀態</th>
              <th>帳號狀態</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            {users.length === 0
              ? <tr><td colSpan="7" style={{ color: '#aaa' }}>載入中...</td></tr>
              : users.map(user => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.username}</td>
                  <td>{user.email}</td>
                  <td><span className="tag">{user.role}</span></td>
                  <td>
                    {user.paid
                      ? <span className="badge-paid">✅ 已繳費</span>
                      : <span className="badge-unpaid">❌ 未繳費</span>
                    }
                  </td>
                  <td>
                    {user.enabled
                      ? <span className="badge-enabled">啟用</span>
                      : <span className="badge-disabled">停權</span>
                    }
                  </td>
                  <td>
                    <button
                      className={`btn ${user.enabled ? 'btn-warning' : 'btn-success'}`}
                      style={{ fontSize: '12px', padding: '4px 10px' }}
                      onClick={() => toggleStatus(user.id)}
                    >
                      {user.enabled ? '停權' : '啟用'}
                    </button>
                    {!user.paid && (
                      <button
                        className="btn btn-success"
                        style={{ fontSize: '12px', padding: '4px 10px', marginLeft: '6px' }}
                        onClick={() => approvePayment(user.id)}
                      >
                        審核繳費
                      </button>
                    )}
                  </td>
                </tr>
              ))
            }
          </tbody>
        </table>
      </div>
      {toast && (
        <div key={Date.now()} className={`toast ${toast.ok ? '' : 'error'}`}>{toast.msg}</div>
      )}
    </div>
  )
}

export default AdminView
