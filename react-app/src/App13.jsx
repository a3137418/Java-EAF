import './App.css'
//變數狀態與渲染
//組件
function App(){
    let count = 0;
    //方法
    function handleClick(){
        count++;//更新 count 變數狀態 => 相當於setCount
        console.log(count);
        //渲染
        document.getElementById('count').textContent = count;
    }

    return(
        <>
            <div id='count'>0</div>
            <button onClick={handleClick}>按我一下+1</button>
        </>
    )
}

export default App;