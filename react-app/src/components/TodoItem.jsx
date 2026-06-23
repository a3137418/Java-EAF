function TodoItem({todo,toggleCompletion,handleTodoDelete}){
    return(
         <li key={todo.id} style={{textDecoration : todo.completed ? 'line-through' : 'none'}}>
            {todo.id}-{todo.text}
            <input type="checkbox" checked={todo.completed} onChange={() => toggleCompletion(todo.id)} />
            {!todo.completed &&
                (<button onClick={() => handleTodoDelete(todo.id)}>X</button>)
            }
        </li>
    )
}

export default TodoItem;