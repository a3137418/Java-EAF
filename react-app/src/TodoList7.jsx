/* TodoList 拆分模組練習
將 TodoList6.jsx 拆分如下:
src/
├── components/
│   ├── TodoInput.jsx
│   ├── TodoItem.jsx
│   └── TodoList.jsx
├── TodoList7.jsx
├── App.css
*/
import './App.css'
import { useState } from 'react';
import TodoInput from './components/TodoInput';
import TodoList from './components/TodoList';


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

    const handleTodoDelete = (id) =>{
        //利用 todos.filter 來過濾不需要的資料
        setTodos(todos.filter((todo) => todo.id !== id))
    }

    return(
        <>
            <h1>My TodoList 7</h1>
            <TodoInput todo={todo} handleTodoChange={handleTodoChange} handleTodoAdd={handleTodoAdd}/>
            <TodoList todos={todos} toggleCompletion={toggleCompletion} handleTodoDelete={handleTodoDelete}/>
        </>
    )
}
export default App;