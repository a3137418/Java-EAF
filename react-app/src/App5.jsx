import './App.css'

//事件處理

function App(){
    const handleClick2 = function(e){
            alert('OK2' + e.type);
            console.log(e);
        }
    const handleClick3 = (e) => {
            alert('OK3' + e.type);
            console.log(e);
        }
    return(
        <>
            <button onClick={function() {alert('OK1');}}>我是按鈕一</button>
            <button onClick={handleClick2}>我是按鈕二</button>
            <button onClick={handleClick3}>我是按鈕三</button>
        </>
    )
}

export default App;