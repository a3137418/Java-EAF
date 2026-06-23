import "../App.css";

import { useState , useEffect} from "react";
import BookForm from "./BookForm";
import BookList from "./BookList";

const API_URL = 'http://localhost:8080/book'; //後台API


/**
 * 回家作業-請拆分
 * book/
 ├─ App.jsx (主畫面)
 ├─ BookForm.jsx (表單)
 └─ BookList.jsx (列表)
 */





function App(){

    const [books , setBooks] = useState([]);//書籍列表資料
    // const [form, setForm] = useState({id:null , name:'demo' , price:'10', amount:'20' , pub:true}); //表單內容
    const [form, setForm] = useState({id:null , name:'' , price:'', amount:'' , pub:false}); //表單內容
    const [editing, setEditing] = useState(false);//是否為編輯(修改)模式
    //讀取書籍資料
    const fetchBooks = async() => {
        try{
            const res = await fetch(API_URL);
            const result = await res.json();
            console.log(result);
            //設定書籍
            setBooks(result.data || []);
        }catch(e){
            console.error('讀取錯誤:', e)
        }
    }

    //React 將UI畫面渲染後要執行的工作 
    //[] 代表在整個畫面只會執行一次
    useEffect(() => {
        fetchBooks();
    } , [])
    //當表單欄位被改變，把最新的資料存回到 form 資料中
    const handleChange = (e) => {
        const {name,value,type,checked} = e.target;
        console.log(name,value,type,checked)
        setForm((prev) => ({
            ...prev,
            [name] : name === 'pub' ? checked : value
        }));
    }

    //新增
    const handleSubmit = async(e) =>{
        e.preventDefault(); //停止預設行為
        try {
            const method = editing ? 'PUT' : 'POST';
            const url = editing ? `${API_URL}/${form.id}` : API_URL;
            const res = await fetch(url , {
                method,
                headers : {'Content-type' : 'application/json'},
                body : JSON.stringify(form)
            });
            const result = await res.json();
            console.log(result);
            if(res.ok){
                await fetchBooks();//重新查詢所有書籍
                //清空表單
                setForm({id:null , name:'' , price:'', amount:'' , pub:false});
            }else{
                alert(result.message || '操作失敗');
            }
        } catch (e) {
            console.error('錯誤',e);
        }

        //恢復到新增狀態
        setEditing(false);
    }

    //修改功能
    const handleEdit = (book) => {
        setForm(book); //將book資料填入到表單中
        setEditing(true);
    }
    //刪除功能
    const handleDelete = async(book) =>{
        const conFirmed = window.confirm(`確定要刪除<<${book.name}>>嗎?`)
        if(!conFirmed) return;//按取消結束
        try {
            const method =  'DELETE';
            const url = `${API_URL}/${book.id}`;
            const res = await fetch(url , {method});
            const result = await res.json();
            console.log(result);
            if(res.ok){
                await fetchBooks();//重新查詢所有書籍
            }else{
                alert(result.message || '刪除失敗');
            }

        } catch (e) {
            console.error('刪除錯誤',e);
        }
    }


    return(
        <>
            <h2>📚書籍管理系統(使用 fetch)</h2>
            < BookForm handleSubmit={handleSubmit} form={form} handleChange={handleChange} editing={editing} setEditing={setEditing} setForm={setForm}/>
            <h2>📗書籍列表</h2>
            < BookList books={books} handleEdit={handleEdit} handleDelete={handleDelete}/>

        </>
    )
}

export default App;