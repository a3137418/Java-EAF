// TodoList練習5
import { useState } from 'react';
import './App.css'

function App(){
    const [todos,setTodos] = useState([
        {id:1,text:'吃早餐',completed:true},
        {id:2,text:'做體操',completed:false},
        {id:3,text:'寫程式',completed:false},
    ])
    const [todo,setTodo] =useState('')

    //變更todo資料
    const handleTodoChange = (e) =>{
        setTodo(e.target.value);
    }
    //加入todo到todos
    const handleTodoAdd =(e)=>{
        // const newId = todo.length > 0 ? todo.length +1 :1;
        //在todos 中找到最大值後+1
        const newId = todo.length > 0 ? Math.max(...todos.map((t) => t.id)) +1 :1;

        const newTodo = {id:newId , text:todo , completed:false};
        
        setTodos([...todos,newTodo]);
        setTodo('');
    }

    const toggleCompletion = (id) =>{
        // 透過setTodos 來變更 todos 的 completed 資料
        //  {id:1,text:'吃早餐',completed:true}
        setTodos(
            todos.map((todo) => todo.id === id ? {...todo, completed : !todo.completed} : todo) 
        );
    }

    return(
        <>
             <h1>My TodoList 5</h1>
            <div>
                <input type='text' value={todo} onChange={handleTodoChange}/>
                <button onClick={handleTodoAdd}>加入</button>
            </div>
            <ul>
                {
                    todos.map((todo) =>(
                        <li key={todo.id} style={{textDecoration : todo.completed ? 'line-through' : 'none'}}>
                            {todo.id}-{todo.text}
                            <input type="checkbox" checked={todo.completed} onChange={() => toggleCompletion(todo.id)} />
                        </li>
                    ))
                }
            </ul>
        </>
    )
}
export default App;