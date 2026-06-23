function BookList({books,handleEdit,handleDelete}){
    return(
        <table border={1} cellPadding={4}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>書名</th>
                        <th>價格</th>
                        <th>數量</th>
                        <th>出刊</th>
                        <th>操作表</th>
                    </tr>
                </thead>
            
                <tbody>
                    {
                        books.map((book)=>(
                            <tr key={book.id}>
                                <td>{book.id}</td>
                                <td>{book.name}</td>
                                <td>{book.price}</td>
                                <td>{book.amount}</td>
                                <td>{book.pub ? '是' : '否'}</td>
                                <td>
                                    <button onClick={()=> handleEdit(book)}>修改</button>
                                    <button onClick={()=> handleDelete(book)}>刪除</button>
                                </td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
    )
}
export default BookList;