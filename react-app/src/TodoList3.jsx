// TodoList練習3
import { useState } from 'react';
import './App.css'

function App(){
    // const todos = ['吃早餐','做體操','寫程式','玩橋牌']
    const [todos,setTodos] = useState([])
    const [todo,setTodo] =useState('')

    //變更todo資料
    const handleTodoChange = (e) =>{
        setTodo(e.target.value);
    }
    //加入todo到todos
    const handleTodoAdd =(e)=>{
        setTodos([...todos,todo]);
        setTodo('');
    }

    return(
        <>
             <h1>My TodoList 3</h1>
            <div>
                <input type='text' value={todo} onChange={handleTodoChange}/>
                <button onClick={handleTodoAdd}>加入</button>
            </div>
            <ul>
                {
                    todos.map((todo,index) =>(
                        <li key={index}>{todo}</li>
                    ))
                }
            </ul>
        </>
    )
}
export default App;