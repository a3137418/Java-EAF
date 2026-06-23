import './App.css'
import App2 from './App2.jsx'


//建立組件的兩種方法
// 1.function
// 2.()=> 函數式


//子組件
function Hello(){
    return(
        <h1>Hello</h1>
    )
}


const World = () =>{
    return(
        <h1>World</h1>
    )
}

//父組件
function App(){
    return(
        <>
            <Hello/>
            <World/>
            <App2/>
        </>

    )
}

export default App;