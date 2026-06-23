/*
商品資料如下:
{ id: 1, name: '蘋果', price: 40, category: '水果', qty:2 },
{ id: 2, name: '洗髮精', price: 120, category: '日用品', qty:4 },
{ id: 3, name: '香蕉', price: 55, category: '水果', qty:6 },
{ id: 4, name: '牙膏', price: 45, category: '日用品', qty:8 }
請利用 react 將上述商品資料透過 jsx + <table> 標籤呈現
加分題:計算價格總和(利用 reduce)
*/
import './App.css'

function App() {

    const products = [
        { id: 1, name: '蘋果', price: 40, category: '水果', qty:2 },
        { id: 2, name: '洗髮精', price: 120, category: '日用品', qty:4 },
        { id: 3, name: '香蕉', price: 55, category: '水果', qty:6 },
        { id: 4, name: '牙膏', price: 45, category: '日用品', qty:8 }
    ];

    // 計算價格的總和
    const totalPrice = products.reduce((sum, product) => sum + product.price * product.qty, 0);

    return (
        <>
            <h1>商品列表</h1>
            <table border={1} cellSpacing={0} cellPadding={6}>
                <thead>
                    <tr style={{backgroundColor: 'lightgrey'}}>
                        <th>ID</th>
                        <th>Category</th>
                        <th>Name</th><th>Price</th><th>Qty</th><th>Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        products.map((product) => {
                            const subtotal = product.price * product.qty;
                            return(
                                <tr key={product.id} 
                                    onMouseOver={(e) => e.currentTarget.style.backgroundColor = "lightblue"}
                                    onMouseOut={(e) => e.currentTarget.style.backgroundColor = ""}>
                                    <td>{product.id}</td>
                                    <td>{product.category}</td>
                                    <td>{product.name}</td>
                                    <td align='right'>{product.price}</td>
                                    <td align='right'>{product.qty}</td>
                                    <td align='right'>{subtotal}</td>
                                </tr>
                            )
                        })
                    }
                </tbody>
                <tfoot>
                    <tr>
                        <td colSpan={5} align='right'>總計</td>
                        <td align='right'>{totalPrice}</td>
                    </tr>
                </tfoot>
            </table>
        </>
    )
}

export default App