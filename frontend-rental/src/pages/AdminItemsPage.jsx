import { useEffect, useState } from 'react';

import { apiFetch } from '../api/client';

// 空白表單
// 新增完成或取消編輯時會回到這個狀態
const emptyForm = {
  name: '',
  type: '教室',
  location: '',
  pricePerHour: 100,
  status: 'AVAILABLE',
  description: '',
  imageUrl: '',
};

export default function AdminItemsPage() {
  // items 儲存目前所有租用項目
  const [items, setItems] = useState([]);

  // form 儲存上方表單資料
  const [form, setForm] = useState(emptyForm);

  // editingId 有值代表目前是修改模式
  // editingId 是 null 代表目前是新增模式
  const [editingId, setEditingId] = useState(null);

  // message 顯示成功或錯誤訊息
  const [message, setMessage] = useState('');

  // 載入項目列表
  const load = async () => {
    // 管理者這裡也可以使用 GET /items 查詢全部項目
    const result = await apiFetch('/items');

    setItems(result.data);
  };

  // 頁面第一次載入時查詢項目
  useEffect(() => {
    load().catch((err) => setMessage(err.message));
  }, []);

  // 更新表單欄位
  const change = (field, value) => {
    setForm((prev) => ({
      ...prev,
      [field]: value,
    }));
  };

  // 表單送出
  const submit = async (e) => {
    e.preventDefault();
    setMessage('');

    try {
      if (editingId) {
        // editingId 有值，代表是修改
        await apiFetch(`/admin/items/${editingId}`, {
          method: 'PUT',
          body: JSON.stringify(form),
        });

        setMessage('修改成功');
      } else {
        // editingId 沒有值，代表是新增
        await apiFetch('/admin/items', {
          method: 'POST',
          body: JSON.stringify(form),
        });

        setMessage('新增成功');
      }

      // 清空表單
      setForm(emptyForm);

      // 離開編輯模式
      setEditingId(null);

      // 重新載入列表
      load();
    } catch (err) {
      setMessage(err.message);
    }
  };

  // 按下編輯
  const edit = (item) => {
    // 設定目前正在編輯哪一筆資料
    setEditingId(item.id);

    // 把該筆資料帶入表單
    setForm({
      name: item.name,
      type: item.type,
      location: item.location,
      pricePerHour: item.pricePerHour,
      status: item.status,
      description: item.description || '',
      imageUrl: item.imageUrl || '',
    });
  };

  // 刪除項目
  const remove = async (id) => {
    // 刪除前先確認
    if (!confirm('確定刪除？')) {
      return;
    }

    setMessage('');

    try {
      await apiFetch(`/admin/items/${id}`, {
        method: 'DELETE',
      });

      setMessage('刪除成功');

      // 刪除成功後重新載入列表
      load();
    } catch (err) {
      setMessage(err.message);
    }
  };

  // 取消編輯
  const reset = () => {
    setForm(emptyForm);
    setEditingId(null);
  };

  return (
    <section>
      <h1>項目管理</h1>

      {message && <div className="alert">{message}</div>}

      <form onSubmit={submit} className="panel form">
        <h2>{editingId ? '修改項目' : '新增項目'}</h2>

        <label>名稱</label>
        <input
          value={form.name}
          onChange={(e) => change('name', e.target.value)}
        />

        <label>類型</label>
        <select
          value={form.type}
          onChange={(e) => change('type', e.target.value)}
        >
          <option value="教室">教室</option>
          <option value="設備">設備</option>
          <option value="場地">場地</option>
        </select>

        <label>地點</label>
        <input
          value={form.location}
          onChange={(e) => change('location', e.target.value)}
        />

        <label>每小時價格</label>
        <input
          type="number"
          value={form.pricePerHour}
          onChange={(e) =>
            change('pricePerHour', Number(e.target.value))
          }
        />

        <label>狀態</label>
        <select
          value={form.status}
          onChange={(e) => change('status', e.target.value)}
        >
          <option value="AVAILABLE">可租用</option>
          <option value="UNAVAILABLE">不可租用</option>
        </select>

        <label>說明</label>
        <textarea
          value={form.description}
          onChange={(e) => change('description', e.target.value)}
        />

        <label>圖片網址</label>
        <input
          value={form.imageUrl}
          onChange={(e) => change('imageUrl', e.target.value)}
        />

        <div>
          <button type="submit">
            {editingId ? '儲存修改' : '新增'}
          </button>

          {editingId && (
            <button
              type="button"
              className="secondary"
              onClick={reset}
            >
              取消編輯
            </button>
          )}
        </div>
      </form>

      <h2>目前項目</h2>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>名稱</th>
            <th>類型</th>
            <th>地點</th>
            <th>價格</th>
            <th>狀態</th>
            <th>操作</th>
          </tr>
        </thead>

        <tbody>
          {items.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.name}</td>
              <td>{item.type}</td>
              <td>{item.location}</td>
              <td>{item.pricePerHour}</td>
              <td>{item.status}</td>
              <td>
                <button onClick={() => edit(item)}>
                  編輯
                </button>

                <button
                  className="danger"
                  onClick={() => remove(item.id)}
                >
                  刪除
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
}