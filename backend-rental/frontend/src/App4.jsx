import './App.css'

//變數的應用
 function App(){
    const text = "Hello React";
    return(
        <>
            <h1>{text}</h1>
            <h1>{text} {text}</h1>
            <h1>{text.toUpperCase()}</h1>
            <h1 style={{backgroundColor:'red'}}>{text}</h1>
            <form>
                <label style={{fontSize:50}}>
                    Text:
                </label>
                <input style={{fontSize:50}}
                        type='text'
                        value={text} />
            </form>
        </>
    )
 }

 export default App