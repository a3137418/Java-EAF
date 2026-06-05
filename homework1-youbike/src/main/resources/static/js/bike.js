// ========================
// 功能:
// 1.查詢全部
// 2.查詢單筆
// 3.顯示訊息
// 4.顯示表格
// ========================

// API 基本路徑
const API_BASE_URL = "/bike";

// 抓取畫面元素(id)-查詢相關
const messageBox = document.getElementById("messageBox");
const searchId = document.getElementById("searchId");
const findOneBtn = document.getElementById("findOneBtn");
const findAllBtn = document.getElementById("findAllBtn");
const singleResult = document.getElementById("singleResult");
const bikeTableBody = document.getElementById("bikeTableBody");

// 抓取畫面元素(id)-新增表單相關
const bikeForm = document.getElementById("bikeForm");
const bikeId = document.getElementById("bikeId");
const bikeName = document.getElementById("bikeName");
const bikePrice = document.getElementById("bikePrice");
const bikeType = document.getElementById("bikeType");
const bikeIsAvailable = document.getElementById("bikeIsAvailable");
const addBtn = document.getElementById("addBtn");
const updateBtn = document.getElementById("updateBtn");
const resetBtn = document.getElementById("resetBtn");

// 紀錄目前表單模式
// create = 新增模式
// update = 修改模式
let formMode = "create";


// 綁定查詢按鈕事件
findOneBtn.addEventListener("click", findBikeById);
findAllBtn.addEventListener("click", findAllBikes);
addBtn.addEventListener("click", addBike);
updateBtn.addEventListener("click", updateBike);
resetBtn.addEventListener("click", ()=>{
	bikeForm.reset();
	updateFormMode("create");
});

//頁面載入初始化
window.addEventListener("DOMContentLoaded", () => {
	findAllBikes();
	updateFormMode("create");
});

//顯示訊息
function showMessage(message, type="info"){
	messageBox.textContent = message;
	messageBox.className = `message-box ${type}`;
}

//統一控制模式的方法
function updateFormMode(mode) {
	formMode = mode;
	
	if(mode === "create") {
		addBtn.disabled = false;
		updateBtn.disabled = true;
	} else {
		addBtn.disabled = true;
		updateBtn.disabled = false;
	}
}


//統一處理 fetch 回應
async function handleResponse(response){
	const result = await response.json();
	if(!response.ok){
		throw new Error(result.message || "發生錯誤");
	}
	return result;
}

//將資料帶回表單
function fillForm(bike){
	bikeId.value = bike.id ?? "";
	bikeName.value = bike.stationName ?? "";
	bikePrice.value = bike.rentPrice ?? "";
	bikeType.value = bike.bikeType ?? "";
	bikeIsAvailable.checked = bike.isAvailable ?? false;
	
	updateFormMode('update');
}

// 表單新增的資料
function getBikekFormDataForCreate() {
	return 	{
		stationName: bikeName.value.trim(),
		rentPrice: bikePrice.value ? Number(bikePrice.value) : null,
		bikeType: bikeType.value ? bikeType.value.trim() : null,
		isAvailable: bikeIsAvailable.checked
	};
}

// 表單修改的資料
function getBikeFormDataForUpdate() {
	return 	{
		id: bikeId.value ? Number(bikeId.value) : null,
		stationName: bikeName.value.trim(),
		rentPrice: bikePrice.value ? Number(bikePrice.value) : null,
		bikeType: bikeType.value ? bikeType.value.trim() : null,
		isAvailable: bikeIsAvailable.checked
	};
}


// 新增資料
async function addBike() {
	try {
		
		const bike = getBikekFormDataForCreate();
		
		if(!bike.stationName || bike.rentPrice === null || bike.bikeType === null) {
			showMessage("請輸入完整站名, 車型,出租價格,", "error");
			return;
		}
		
		const response = await fetch(API_BASE_URL, {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(bike)
		});
		
		const result = await handleResponse(response);
		
		console.log(result);
		showMessage(result.message || "新增成功", "success");
		
		bikeForm.reset(); // 清空表單
		findAllBikes(); // 重新查詢所有車輛
		
	} catch(error) {
		showMessage(error.message, "error");
	}
}

// 修改資料
async function updateBike() {
	try {
		
		const bike = getBikeFormDataForUpdate();
		
		if(!bike.id) {
			showMessage("修改時必須要有 ID", "error");
			return;
		}
		
		if(!bike.stationName || bike.rentPrice === null || bike.bikeType === null) {
			showMessage("請輸入完整的站名,車型,價格", "error");
			return;
		}
		
		const response = await fetch(`${API_BASE_URL}/${bike.id}`, {
			method: "PUT",
			headers: {
				"Content-type": "application/json"
			},
			body: JSON.stringify(bike)
		});
		
		const result = await handleResponse(response);
		
		showMessage(result.message || "修改成功", "success");
		singleResult.textContent = JSON.stringify(result.data);
		findAllBikes();
		
	} catch(error) {
		showMessage(error.message, "error");
	}
}



//查詢全部
async function findAllBikes(){
	try{
		const response = await fetch(API_BASE_URL);
		const result = await handleResponse(response);
		console.log(result);//debug用
		renderBikeTable(result.data);// 資料渲染到表格中
		showMessage(result.message || "查詢全部成功", "success");
	}catch(error){
		renderBikeTable([]);//資料渲染空白資料
		showMessage(error.message, "error");
	}
}

// 查詢單筆
async function findBikeById() {
	try {
		const id = searchId.value;
		
		if(!id) {
			showMessage("請輸入要查詢的車輛 ID", "error");
			return;
		}
		
		const response = await fetch(`${API_BASE_URL}/${id}`);
		const result = await handleResponse(response);
		
		console.log(result); // debug 用, 顯示 json 物件
		console.log(JSON.stringify(result)); // debug 用, 顯示 json 字串
		
		singleResult.textContent = JSON.stringify(result); 
		showMessage(result.message || "單筆查詢成功", "success");
		fillForm(result.data);//將查到後的資料帶回表單
	} catch(error) {
		singleResult.textContent = "查詢失敗";
		showMessage(error.message, "error");
	}
}

//由表格載入資料到表單

async function loadBikeById(id){
	try{
		const response = await fetch(`${API_BASE_URL}/${id}`);
		const result = await handleResponse(response);
		
		singleResult.textContent = JSON.stringify(result); 
		showMessage(`已載入車輛 ID :${id}`,"success");
		fillForm(result.data);//將查到後的資料帶回表單
		
	}catch(error){
		showMessage(error.message, "error");
	}
}

//刪除車輛
async function deleteBikeById(id){
	
	const isConfirmed = confirm(`你確定要刪除車輛 ID : ${id} 嗎?`);
	
	if(!isConfirmed){
		showMessage(`已取消刪除車輛 ID :${id}`,"info");
		return;
	}
	
	
	try{
		const response = await fetch(`${API_BASE_URL}/${id}`,{
			method : "DELETE"
		});
		
		const result = await handleResponse(response);
		
		showMessage(result.message || "刪除成功", "success");
		findAllBikes();
		
		
				
	}catch(error){
		singleResult.textContent = `刪除車輛 ID : ${id} 失敗`;
		showMessage(error.message, "error");
	}
}


// 渲染表格
function renderBikeTable(bikes) {
	if(!bikes || bikes.length === 0) {
		bikeTableBody.innerHTML = `
			<tr>
				<td colspan="5" class="empty-now">目前沒有資料</td>
			</tr>
		`;
		return;
	}
	
	let html = "";
	bikes.forEach(bike => {
		html += `
			<tr>
				<td onclick="loadBikeById(${bike.id})" style="cursor : pointer" title="按我一下即可修改資料">${bike.id}</td>
				<td>${escapeHtml(bike.stationName)}</td>
				<td>${bike.bikeType}</td>
				<td>${bike.rentPrice}</td>
				<td>${bike.isAvailable ? "已出租" : "未出租"}</td>
				<td>
					<button onclick="loadBikeById(${bike.id})">修改</button>
					<button onclick="deleteBikeById(${bike.id})" class="danger">刪除</button>
				</td>
			</tr>
		`;
	});
	
	bikeTableBody.innerHTML = html; 
}

// 避免文字直接塞進 html
function escapeHtml(text) {
	if(text === null || text === undefined) {
		return "";
	}
	
	return String(text)
			.replaceAll("&", "&amp;")
			.replaceAll("<", "&lt;")
			.replaceAll(">", "&gt;")
			.replaceAll('"', "&quot;")
			.replaceAll("'", "&#39;");
}