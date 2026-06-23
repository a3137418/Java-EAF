function BookForm({handleSubmit,form,handleChange,editing,setEditing,setForm}){
    return(
        <form onSubmit={handleSubmit}>
            書名:<input type="text" name="name" value={form.name} onChange={handleChange} required/><p />
            價格:<input type="number" name="price" value={form.price} onChange={handleChange} required/><p />
            數量:<input type="number" name="amount" value={form.amount} onChange={handleChange} required/><p />
            出刊:<input type="checkbox" name="pub" checked={form.pub} onChange={handleChange}/><p />
            <button type="submit" >{editing ? '修改': '新增'}書籍</button>
            {
                editing && (
                <button type="button" onClick={()=>{
                    setEditing(false);//取消編輯模式
                    setForm({id:null , name:'' , price:'', amount:'' , pub:false});//清空表單
                }}>重置</button>
                )
            }
        </form>
    )
}

export default BookForm;