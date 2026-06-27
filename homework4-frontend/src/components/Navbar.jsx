function Navbar({ currentUser, onLogout }) {
  if (!currentUser) return null

  const roleLabel = {
    'ROLE_ADMIN': '管理員',
    'ROLE_TEACHER': '講師',
    'ROLE_STUDENT': '學生'
  }[currentUser.role] || currentUser.role

  return (
    <header>
      <span>Learning Platform 管理系統</span>
      <div className="user-info">
        <span>{currentUser.username}（{roleLabel}）</span>
        <button className="btn-logout" onClick={onLogout}>登出</button>
      </div>
    </header>
  )
}

export default Navbar
