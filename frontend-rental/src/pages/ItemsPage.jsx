// useEffect 頁面載入時執行某些動作
// useState 是 React 的狀態管理
import { useEffect, useState } from "react";

// 匯入自訂 API 工具
import { apiFetch } from "../api/client";

// 取得登入狀態
import { useAuth } from '../state/AuthContext';

function ItemsPage() {
  // isLogin 用來判斷是否已登入
  const { isLogin } = useAuth();

  const [items, setItems] = useState([]);
  const [keyword, setKeyword] = useState('');
  const [type, setType] = useState('');
  const [message, setMessage] = useState('');

  // reservationForms 用來記錄每一個 item 的預約表單
  // 格式大概是：
  // {
  //   1: { startTime: '...', endTime: '...', note: '...' },
  //   2: { startTime: '...', endTime: '...', note: '...' }
  // }
  const [reservationForms, setReservationForms] = useState({});

  // 載入租用項目
  const loadItems = async () => {
    const params = new URLSearchParams();

    if (keyword) {
      params.append('keyword', keyword);
    }

    if (type) {
      params.append('type', type);
    }

    const queryString = params.toString();
    const path = queryString ? `/items?${queryString}` : '/items';

    const result = await apiFetch(path);
    setItems(result.data);
  };

  // 頁面第一次載入時讀取項目
  useEffect(() => {
    loadItems().catch((err) => setMessage(err.message));
  }, []);

  // 更新某一個 item 的某一個表單欄位
  const updateForm = (itemId, field, value) => {
    setReservationForms((prev) => ({
      ...prev,

      // 用 itemId 區分不同卡片的表單
      [itemId]: {
        ...(prev[itemId] || {}),

        // 動態更新 startTime、endTime 或 note
        [field]: value,
      },
    }));
  };

  // 送出預約
  const reserve = async (itemId) => {
    setMessage('');

    // 取得目前 item 對應的表單資料
    const form = reservationForms[itemId] || {};

    try {
      await apiFetch('/reservations', {
        method: 'POST',
        body: JSON.stringify({
          itemId,
          startTime: form.startTime,
          endTime: form.endTime,
          note: form.note || '',
        }),
      });

      setMessage('預約建立成功，等待管理者審核');

      // 清空該項目的表單
      setReservationForms((prev) => ({
        ...prev,
        [itemId]: {},
      }));
    } catch (err) {
      setMessage(err.message);
    }
  };

  return (
    <section>
      <h1>租用項目</h1>

      <div className="toolbar">
        <input
          placeholder="關鍵字，例如 教室"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
        />

        <select
          value={type}
          onChange={(e) => setType(e.target.value)}
        >
          <option value="">全部類型</option>
          <option value="教室">教室</option>
          <option value="設備">設備</option>
          <option value="場地">場地</option>
        </select>

        <button onClick={loadItems}>查詢</button>
      </div>

      {message && <div className="alert">{message}</div>}

      <div className="grid">
        {items.map((item) => {
          // 取得這個 item 對應的表單
          const form = reservationForms[item.id] || {};

          return (
            <article className="card" key={item.id}>

                {/* 顯示租用項目圖片 */}
                {item.imageUrl && (
                <img
                src={item.imageUrl}
                alt={item.name}
                className="item-image"
                />
                )}
              <h2>{item.name}</h2>

              <p>
                {item.type}｜{item.location}
              </p>

              <p>{item.description}</p>

              <p className="price">
                NT$ {item.pricePerHour} / 小時
              </p>

              <span className="badge">
                {item.status}
              </span>

              {/* 登入後才顯示預約表單 */}
              {isLogin ? (
                <div className="reserve-box">
                  <label>開始時間</label>
                  <input
                    type="datetime-local"
                    value={form.startTime || ''}
                    onChange={(e) =>
                      updateForm(item.id, 'startTime', e.target.value)
                    }
                  />

                  <label>結束時間</label>
                  <input
                    type="datetime-local"
                    value={form.endTime || ''}
                    onChange={(e) =>
                      updateForm(item.id, 'endTime', e.target.value)
                    }
                  />

                  <label>備註</label>
                  <input
                    value={form.note || ''}
                    onChange={(e) =>
                      updateForm(item.id, 'note', e.target.value)
                    }
                    placeholder="例如需要投影機"
                  />

                  <button onClick={() => reserve(item.id)}>
                    送出預約
                  </button>
                </div>
              ) : (
                <p className="hint">登入後可送出預約</p>
              )}
            </article>
          );
        })}
      </div>
    </section>
  );
}

export default ItemsPage;