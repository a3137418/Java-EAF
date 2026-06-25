/**
 * 建立共用導覽列，讓使用者可以點選切換頁面
 * 
 */
// Link 是 React Router 提供的連結元件
// 使用 Link 切換頁面時，不會重新整理整個網頁
import { Link } from "react-router-dom";

function Navbar(){
    return(
        <header className="navbar">
            {/* 系統名稱，點擊後回首頁 */}
            <Link to="/" className="brand">
                租用系統
            
            </Link>

            {/* 導覽連結 */}
            <nav>
                <Link to="/">租用項目</Link>
                <Link to="/my-reservations">我的預約</Link>
                <Link to="/admin/items">項目管理</Link>
                <Link to="/admin/reservations">預約審核</Link>
                <Link to="/login">登入</Link>

            </nav>
        </header>
    );
}

export default Navbar;