// 使用 useState (Hook)
// 建立一個"會記住值的變數" + "改變他的方法"
import { useState } from "react"
import './App.css'

// 組件
function App() {
    const [count, setCount] = useState(0);

    // 方法
    function handleClick() {
        const newCount = count + 1; // 同步
        setCount(newCount); // 非同步(setCount在 react 設計上的時候是非同步模式)
        console.log(newCount);
    }

    return (
        <>
            <div>{count}</div>
            <button onClick={handleClick}>按我一下 + 1</button>
        </>
    )
}

export default App