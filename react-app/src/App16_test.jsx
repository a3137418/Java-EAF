import { useState } from "react";
import './App.css'

function App(){
    const [height,setHeight] = useState(170)
    const [weight,setWeight] = useState(75)
    const [bmi,setBmi] = useState('')
    const [result,setResult] = useState('')
    const [history,setHistory] = useState([])

    const handleBMI = () =>{
        const h = Number(height)/100
        const w = Number(weight)
        //計算
        const bmivalue = w/(h*h)
        setBmi(bmivalue.toFixed(2))

        //判斷
        let msg = (bmivalue <=18)?"過輕":(bmivalue>23)?"過重":"正常"
        setResult(msg)

        //加入歷史紀錄
        const historylist = `身高:${height}cm，體重:${w}kg，BMI:${bmivalue.toFixed(2)}，${msg}`
        setHistory([...history,historylist])
    }


    return(
        <>
            <h1>BMI計算機</h1>
            身高:<input type="number" value={height} onChange={(e)=>setHeight(e.target.value)} placeholder="身高cm"/><p/>    
            體重:<input type="number" value={weight} onChange={(e)=>setWeight(e.target.value)} placeholder="體重kg"/><p/>
            <button onClick={handleBMI}>計算</button>

            <h2>BMI:{bmi}({result})</h2>
            <h3>歷史紀錄</h3  >
            <ul>
                {
                    history.map((historylist,index)=>{
                        return <li key={index}>{historylist}</li>
                    })
                }
            </ul>
        </>
    )
}

export default App