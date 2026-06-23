import './App.css'
/*
商品資料如下:
{ id: 1, name: '蘋果', price: 40, category: '水果' },
{ id: 2, name: '洗髮精', price: 120, category: '日用品' },
{ id: 3, name: '香蕉', price: 55, category: '水果' },
{ id: 4, name: '牙膏', price: 45, category: '日用品' }
請利用 react 將上述商品資料透過 jsx + <table> 標籤呈現
加分題:計算價格總和(利用reduce)
*/

function App(){
    const items = [
        { id: 1, name: '蘋果', price: 40, category: '水果' , qty:2},
        { id: 2, name: '洗髮精', price: 120, category: '日用品' , qty:1},
        { id: 3, name: '香蕉', price: 55, category: '水果' , qty:3},
        { id: 4, name: '牙膏', price: 45, category: '日用品' , qty:1}
    ]

    const sumItems = items.reduce((sum,items) => sum + items.price * items.qty,0)



    return(
        <>
            <h1>商品列表</h1>
            <table>
                <thead cellPadding={1} cellspacing={0} border={1}>
                    <tr style={{background:'lightblue'}}>
                        <th>編號</th>
                        <th>名稱</th>
                        <th>價格</th>
                        <th>數量</th>
                        <th>分類</th>
                        <th>小計</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        items.map((item) =>{
                            return(
                                <tr key={item.id}>
                                <td>{item.id}</td>
                                <td>{item.name}</td>
                                <td>{item.price}</td>
                                <td>{item.qty}</td>
                                <td>{item.category}</td>
                                <td>{item.price * item.qty}</td>
                            </tr>
                            )
                        })
                    }
                </tbody>
            </table>
            <p>價格總和: {sumItems}</p>
        </>
    )
}

export default App;
