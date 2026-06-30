/**
 * 建立共用導覽列，讓使用者可以點選切換頁面
 * 
 */
// Link 是 React Router 提供的連結元件
// 使用 Link 切換頁面時，不會重新整理整個網頁
import { Link, useNavigate } from "react-router-dom";

// 匯入 useAuth 取得登入狀態
import { useAuth } from "../state/AuthContext";

function Navbar(){
    // 從 AuthContext 取得登入者的資料與相關方法
    const {user , isLogin , isAdmin , logout} = useAuth();

    // navigate 用來登出後導回首頁
    const navigate = useNavigate();

    // 登出功能
    const handleLogout = () => {
        // 清除登入狀態
        logout();

        // 登出後回首頁
        navigate('/');
    }

    return(
        <header className="navbar">
            {/* 系統名稱，點擊後回首頁 */}
            <Link to="/" className="brand">
                租用系統
            
            </Link>

            {/* 導覽連結 */}
            <nav>
                {/* 所有人都可以看到租用項目 */}
                <Link to="/">租用項目</Link>

                {/* 登入後才能看到我的預約 */}
                {isLogin && < Link to="/my-reservations">我的預約</Link>}

                {/* 管理者才能看到項目管理 */}
                {isAdmin && <Link to="/admin/items">項目管理</Link>}

                {/* 管理者才能看到預約審核 */}
                {isAdmin && <Link to="/admin/reservations">預約審核</Link>}

            </nav>

            <div className="user-area">
                {
                    isLogin ? (
                        <>
                            {/* 顯示登入者名稱與角色 */}
                            <span>
                                { user.fullName } ({user.role})
                            </span>
                            <botton onClick={handleLogout}>
                                登出
                            </botton>
                        </>
                    ) : (
                         <Link to="/login">登入</Link>
                    )
                }


            </div>


        </header>
    );
}

export default Navbar;