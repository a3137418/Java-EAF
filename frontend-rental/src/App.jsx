/*
路徑: /                   模組:<ItemsPage>             租用項目
路徑: /login              模組:<LoginPage>             登入項目
路徑: /my-reservations    模組:<MyReservationPage>     我的預約
路徑: /admin/items        模組:<AdminItemsPage>        租用項目管理
路徑: /admin/reservations 模組:<AdminReservationsPage> 預約審核

安裝 React Router
指令 npm install react-router-dom

*/

import { Route , Routes } from "react-router-dom"
import ItemsPage from "./pages/ItemsPage"
import LoginPage from "./pages/LoginPage"
import MyReservationsPage from "./pages/MyReservationsPage"
import AdminItemsPage from "./pages/AdminItemsPage"
import AdminReservationsPage from "./pages/AdminReservationsPage"
// 匯入導覽列
import Navbar from "./components/Navbar"

function App(){
  return(
    <>
    {/* Navbar 放在 Router 的外面代表每一頁都可以看的到 */}
    <Navbar></Navbar>
      <main className="container">
        {/* Routes 代表路由區域 */}
        <Routes>
          {/* 首頁 */}
          <Route path="/" element={<ItemsPage />}/>
          {/* 登入頁 */}
          <Route path="/login" element={<LoginPage />}/>
          {/* 我的預約頁 */}
          <Route path="/my-reservations" element={<MyReservationsPage />}/>
          {/* 管理者-項目管理頁 */}
          <Route path="/admin/items" element={<AdminItemsPage />}/>
          {/* 管理者-預約審核頁 */}
          <Route path="/admin/reservations" element={<AdminReservationsPage />}/>

        </Routes>
      </main>
    </>
    
  )
}

export default App