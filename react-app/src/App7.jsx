import './App.css'

//陣列(複合元素) 以及 map 與 filter 的使用

function App(){
    //物件陣列
    const items =[
        {id:101,name:'Apple',price:20},
        {id:102,name:'Banana',price:30},
        {id:103,name:'Orange',price:40},
    ]

    //過濾 price > 25
    const filterItems = items.filter((item) => item.price > 25);


    return(
        <>
            <table border="1">
                <thead cellPadding={1} cellspacing={0} border={1}>
                    <tr style={{background:'lightblue'}}>
                        <th>Index</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                {
                    items.map((item,index) =>{
                        return(
                            <tr key={item.id}>
                                <td>{index}</td>
                                <td>{item.id}</td>
                                <td>{item.name}</td>
                                <td>{item.price}</td>
                            </tr>
                        )
                    })
                        
                }
                </tbody>
            </table>
        </>
    )
}

export default App;