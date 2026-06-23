// useState 與事件處理，展開運算子(...)
import { useState } from "react";
import './App.css'

function App(){
    const [text , setText] = useState('')//輸入框的資料
    const [messages, setMessages] = useState([])//保存並累計留言資料

    const handleInputChange = (e) => {
        //e.target.value 是 input 欄位的內容
        const newText = e.target.value;
        setText(newText);
        console.log(newText);
    }

    const handleAddMessage = (e) => {
        // setMessages(messages.concat(text));
        setMessages([...messages,text]);
    }

    const handleKeyDown = (e) => {
        // 偵測到使用者按下 Enter 鍵
        if(e.key === 'Enter'){
            handleAddMessage();
        }
    }

    return(
        <>
            <h1>留言板</h1>
            <input  type="text" value={text} 
                    onChange={handleInputChange}
                    onKeyDown={handleKeyDown}/>
            <button onClick={handleAddMessage}>Send</button>

            <p/>
            <ul>
                {
                    messages.map((message,index)=>(
                        <li key={index}>{message}</li>
                    ))
                }
            </ul>
        </>
    )
}

export default App;