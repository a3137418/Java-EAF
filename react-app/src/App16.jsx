/**
使用 React + useState 完成一個「可判斷體態 + 驗證輸入」的 BMI 工具

畫面示意
身高(cm)：[ 170  ]
體重(kg)：[ 60   ]

[ 計算 ]

BMI：20.76（正常）

歷史資料紀錄:
身高：170cm 體重：60kg BMI：22.76（正常）
身高：170cm 體重：60kg BMI：22.76（正常）
身高：170cm 體重：60kg BMI：22.76（正常）
身高：170cm 體重：60kg BMI：22.76（正常）

BMI驗證: 過輕 18 < 正常 <= 23 過重
 */
import { useState } from "react";
import './App.css'

function App() {

    const [height, setHeight] = useState(170)
    const [weight, setWeight] = useState(60)
    const [bmi, setBmi] = useState('')
    const [result, setResult] = useState('')
    const [history, setHistory] = useState([])

    const handleCalculate = () => {
        const h = Number(height);
        const w = Number(weight);

        // 計算 bmi
        const bmiValue = w / ((h/100) * (h/100));
        setBmi(bmiValue.toFixed(2));

        // 判斷
        let msg = (bmiValue <= 18) ? '過輕' : (bmiValue > 23) ? '過重' : '正常';
        setResult(msg);
        
        // 加入到歷史紀錄
        const record = `身高：${h}cm 體重：${w}kg BMI：${bmiValue.toFixed(2)}（${msg}）`;
        setHistory([record, ...history]);
    }

    return (
        <>
            <h1>BMI 計算機</h1>
            身高：<input type="number" value={height} onChange={(e) => setHeight(e.target.value)} placeholder="身高(cm)" /><p />
            體重：<input type="number" value={weight} onChange={(e) => setWeight(e.target.value)} placeholder="體重(kg)" /><p />
            <button onClick={handleCalculate}>計算</button>
            <h2>
                BMI:{bmi} ({result})
            </h2>
            <h2>歷史資料紀錄:</h2>
            <ul>
                {
                    history.map((record, index) => (
                        <li key={index}>{record}</li>
                    ))
                }
            </ul>
        </>
    )
}

export default App