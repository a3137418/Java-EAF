import { useState, useEffect } from 'react'
import api from '../services/api'

function TeacherView() {
  const [courses, setCourses] = useState([])
  const [teachers, setTeachers] = useState([])
  const [title, setTitle] = useState('')
  const [price, setPrice] = useState('')
  const [teacherId, setTeacherId] = useState('')
  const [students, setStudents] = useState([])
  const [showStudents, setShowStudents] = useState(false)
  const [selectedTeacherName, setSelectedTeacherName] = useState('')
  const [toast, setToast] = useState(null)

  const showToast = (msg, ok = true) => {
    setToast({ msg, ok, key: Date.now() })
    setTimeout(() => setToast(null), 2800)
  }

  const loadCourses = async () => {
    const res = await api.get('/courses')
    setCourses(res.data)
  }

  const loadTeachers = async () => {
    const res = await api.get('/teachers')
    setTeachers(res.data)
  }

  useEffect(() => { loadCourses(); loadTeachers() }, [])

  const createCourse = async (e) => {
    e.preventDefault()
    if (!title || !price || !teacherId) { showToast('請填寫所有欄位並選擇講師', false); return }
    try {
      await api.post(`/courses?teacherId=${teacherId}`, { title, price: parseFloat(price) })
      showToast('課程建立成功')
      setTitle(''); setPrice('')
      loadCourses()
    } catch (err) {
      showToast(err.response?.data?.message || '建立失敗', false)
    }
  }

  const viewStudents = async (teacher) => {
    const res = await api.get(`/teachers/${teacher.id}/students`)
    setStudents(res.data)
    setSelectedTeacherName(teacher.name)
    setShowStudents(true)
  }

  return (
    <div className="container">
      <div className="card">
        <h2>新增課程</h2>
        <div className="form-row">
          <div className="form-group">
            <label>課程名稱</label>
            <input value={title} onChange={e => setTitle(e.target.value)} placeholder="e.g. JavaOCP" />
          </div>
          <div className="form-group" style={{ maxWidth: '130px' }}>
            <label>售價 (NT$)</label>
            <input type="number" value={price} onChange={e => setPrice(e.target.value)} placeholder="3600" />
          </div>
          <div className="form-group">
            <label>授課講師</label>
            <select value={teacherId} onChange={e => setTeacherId(e.target.value)}>
              <option value="">-- 選擇講師 --</option>
              {teachers.map(t => (
                <option key={t.id} value={t.id}>{t.name}（{t.expertise}）</option>
              ))}
            </select>
          </div>
          <button className="btn btn-primary" onClick={createCourse}>新增課程</button>
        </div>
      </div>

      <div className="card">
        <h2>課程清單</h2>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>課程名稱</th>
              <th>售價</th>
              <th>授課講師</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            {courses.length === 0
              ? <tr><td colSpan="5" style={{ color: '#aaa' }}>目前無課程資料</td></tr>
              : courses.map(c => (
                <tr key={c.id}>
                  <td>{c.id}</td>
                  <td>{c.title}</td>
                  <td>NT$ {c.price}</td>
                  <td>{c.teacher?.name || '—'}</td>
                  <td>
                    {c.teacher && (
                      <button
                        className="btn btn-primary"
                        style={{ fontSize: '12px', padding: '4px 10px' }}
                        onClick={() => viewStudents(c.teacher)}
                      >
                        查看選修學生
                      </button>
                    )}
                  </td>
                </tr>
              ))
            }
          </tbody>
        </table>
      </div>

      {showStudents && (
        <div className="card">
          <h2>{selectedTeacherName} 的選修學生</h2>
          {students.length === 0
            ? <p style={{ color: '#aaa' }}>尚無學生選修</p>
            : (
              <table>
                <thead>
                  <tr>
                    <th>帳號</th>
                    <th>Email</th>
                    <th>角色</th>
                  </tr>
                </thead>
                <tbody>
                  {students.map(s => (
                    <tr key={s.id}>
                      <td>{s.username}</td>
                      <td>{s.email}</td>
                      <td><span className="tag">{s.role}</span></td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )
          }
        </div>
      )}

      {toast && (
        <div key={toast.key} className={`toast ${toast.ok ? '' : 'error'}`}>{toast.msg}</div>
      )}
    </div>
  )
}

export default TeacherView
