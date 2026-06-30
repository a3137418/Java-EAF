// useEffect 頁面載入時執行某些動作
// useState 是 React 的狀態管理
import { useEffect, useState } from "react";

// 匯入自訂 API 工具
import { apiFetch } from "../api/client";

function ItemsPage() {
    // items 改成空陣列, 之後由後端 API 載入
    const [items, setItems] = useState([]);

    // 顯示錯誤或成功的訊息
    const [message, setMessage] = useState('');

    // keyword 儲存關鍵字-查詢條件
    const [keyword, setKeyword] = useState('');

    // type 儲存類型-查詢條件
    const [type, setType] = useState('');

    // 載入租用項目資料
    const loadItems = async() => {
        // URLSearchParams 來組 query string
        const params = new URLSearchParams();
        if(keyword) {
            params.append('keyword', keyword);
        }
        if(type) {
            params.append('type', type);
        }
        const queryString = params.toString();

        const path = queryString ? `/items?${queryString}` : '/items';

        // 呼叫後端 API
        const result = await apiFetch(path);

        // 將後端回傳的 data 放進 items
        setItems(result.data);
    };

    // 載入畫面第一次要執行的程式
    useEffect(() => {
        loadItems().catch((err) => setMessage(err.message));
    }, []);

    return (
        <section>
            <h1>租用項目</h1>
            
            {/* 查詢工具列 */}
            <div className="toolbar">
                <input value={keyword} 
                       placeholder="關鍵字, 例如 教室"
                       onChange={(e) => setKeyword(e.target.value)} />

                <select value={type} onChange={(e) => setType(e.target.value)}>
                    <option value="">全部類型</option>
                    <option value="教室">教室</option>
                    <option value="設備">設備</option>
                    <option value="場地">場地</option>
                </select>

                <button onClick={loadItems}>查詢</button>
                
            </div>

            {message && <div className="alert">{message}</div>}

            {/* 利用 grid 來排版多張卡片(租用項目) */}
            <div className="grid">
                {/* 利用 map 來走訪 filteredItems 裡的每一筆資料並轉成畫面UI標籤 */}
                {
                    items.map((item) => (
                        <article className="card" key={item.id}>
                            {/* 如果後端有 imageURL 就顯示圖片 */}
                            {
                                item.imageUrl && (
                                    <img src={item.imageUrl} alt={item.name} className="item-image" />
                                )
                            }
                            <h2>{item.name}</h2>
                            <p>
                                {item.type} | {item.location}
                            </p>
                            <p>
                               {item.description} 
                            </p>
                            <p className="price">
                                NT$ {item.pricePerHour} / 小時
                            </p>
                            <span className="badge">
                                {item.status}
                            </span>
                        </article>
                    ))
                }

            </div>

        </section>

    )

}

export default ItemsPage;