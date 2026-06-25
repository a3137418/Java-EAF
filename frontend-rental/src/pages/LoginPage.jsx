// useState 用來儲存表單欄位資料(狀態)
import { useState } from "react";


function LoginPage(){
    // 帳號
    const [username , setUsername] = useState('admin');

    // 密碼
    const [password , setPassword] = useState('admin123');

    // 送出表單
    const handleSubmit = (e) => {
        // 停止表單預設行為
        e.preventDefault();

        // 印 log
        console.log('帳號:' , username);
        console.log('密碼:' , password);

        alert(`帳號: ${username} , 密碼: ${password}`);
    }


    return(
        <section className="panel narrow">
            <h1>登入</h1>
            <p className="hint">
                管理者: admin / admin123 ; 會員: user /user123 
            </p>

            <form onSubmit={handleSubmit} className="form">
                <label>帳號</label>
                <input value={username}
                        onChange={(e) => setUsername(e.target.value)} />

                <label>密碼</label>
                <input value={password}
                        type="password"
                        onChange={(e) => setPassword(e.target.value)} />

                <button type="submit">登入</button>
            </form>
        </section>
    )
}

export default LoginPage;