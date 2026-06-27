import { useState, useEffect } from 'react'
import api from '../services/api'

function StudentView({ currentUser }) {
  const [courses, setCourses] = useState([])
  const [toast, setToast] = useState(null)

  useEffect(() => {
    api.get('/courses').then(res => setCourses(res.data))
  }, [])

  const showToast = (msg, ok = true) => {
    setToast({ msg, ok, key: Date.now() })
    setTimeout(() => setToast(null), 2800)
  }

  const enroll = async (courseId) => {
    try {
      await api.post(`/students/${currentUser.id}/enroll/${courseId}`)
      showToast('選課成功！')
    } catch (err) {
      const msg = err.response?.data?.error || err.response?.data?.message || '選課失敗'
      if (msg.includes('未繳費')) {
        alert('未繳費，請聯繫管理員繳費後再選課。')
      }
      showToast(msg, false)
    }
  }

  return (
    <div className="container">
      <div className="card">
        <h2>
          學生主控台 &nbsp;
          {currentUser.paid
            ? <span className="badge-paid">✅ 已繳費</span>
            : <span className="badge-unpaid">❌ 未繳費（無法選課）</span>
          }
        </h2>
      </div>

      <div className="card">
        <h2>可選課程</h2>
        <table>
          <thead>
            <tr>
              <th>課程名稱</th>
              <th>售價</th>
              <th>講師</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            {courses.length === 0
              ? <tr><td colSpan="4" style={{ color: '#aaa' }}>目前無課程資料</td></tr>
              : courses.map(c => (
                <tr key={c.id}>
                  <td>{c.title}</td>
                  <td>NT$ {c.price}</td>
                  <td>{c.teacher?.name || '—'}</td>
                  <td>
                    <button
                      className="btn btn-success"
                      style={{ fontSize: '12px', padding: '4px 10px' }}
                      onClick={() => enroll(c.id)}
                    >
                      選課
                    </button>
                  </td>
                </tr>
              ))
            }
          </tbody>
        </table>
      </div>

      {toast && (
        <div key={toast.key} className={`toast ${toast.ok ? '' : 'error'}`}>{toast.msg}</div>
      )}
    </div>
  )
}

export default StudentView
