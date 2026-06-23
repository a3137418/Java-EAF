// React 組件之間參數傳遞
import './App.css'

// 子組件
function CircleArea1({r, pi}) {
    const area = r * r * pi;
    return (<div>{area.toFixed(2)}</div>)
}

// 子組件: 函數式
const CircleArea2 = ({r, pi}) => {
    const area = r * r * pi;
    return (<div>{area.toFixed(2)}</div>)
}

const Fruit1 = ({fruitName, fruitPrice}) => {
    return (<div>水果名稱:{fruitName} 水果價格:{fruitPrice}</div>)
}

const Fruit2 = (props) => {
    return (<div>水果名稱:{props.fruitName} 水果價格:{props.fruitPrice}</div>)
}

const Fruit3 = (props) => {
    props.printLog();
    return (<div>水果名稱:{props.fruitName} 水果價格:{props.fruitPrice}</div>)
}

function App() {
    return (
        <>
            <CircleArea1 r="10" pi="3.1415926" />
            <CircleArea2 r="10" pi="3.1415926" />
            <Fruit1 fruitName="Apple" fruitPrice="50" />
            <Fruit2 fruitName="Banana" fruitPrice="30" />
            <Fruit3 fruitName="Orange" fruitPrice="40" printLog={() => console.log('柳丁')} />
        </>
    )
}

export default App