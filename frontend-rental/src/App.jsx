/*
路徑: /                   模組:<ItemsPage>             租用項目
路徑: /login              模組:<LoginPage>             登入項目
路徑: /my-reservations    模組:<MyReservationPage>     我的預約
路徑: /admin/items        模組:<AdminItemsPage>        租用項目管理
路徑: /admin/reservations 模組:<AdminReservationsPage> 預約審核

安裝 React Router
指令 npm install react-router-dom

*/
function App(){
  return(
    <div>
      {/* 頁面主標題 */}
      <h1>租用預約系統</h1>
      {/* 頁面說明文字 */}
      <p>歡迎使用 React 租用預約系統</p>
    </div>

    
  )
}

export default App