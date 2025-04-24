<template>
	<view class="homeLayout bg">
		<view class="header">
			<view class="icon"><uni-icons type="bars" size="25" @click="showDrawerLeft()"></uni-icons></view>
			<view class="recommendation">推荐</view>
		</view>
		<view class="banner bg">
			<view class="row" v-for="(item,index) in rangomData" :key="index" @click="toDetail(index)">
				<view class="category">
					<image :src="item.imgUrl" mode="aspectFill"></image>
					<view class="text">
						<view class="name"><text>{{item.categoryName}}</text></view>
						<view class="info">
							<view class="left"><text>帖子 {{item.count}}k</text></view>
							<view class="right"><text>热度 {{item.hot}}k</text></view>
						</view>
					</view>
				</view>
				<view class="title"><text style="font-weight: bold;">{{item.agricultureInfomationTitle}}</text></view>
				<view class="content"><text style="font-size: 0.9em;">{{item.detail}}</text></view>
			</view>
		</view>
	</view>
<uni-drawer ref="drawerShowLeft" mode="left" :width="320" class="drawerLeft">
	<view class="banner">
		<view class="user">
			<image src="../../static/logo.png" mode="aspectFill"></image>
			<view class="name">AlanCheng</view>
		</view>
		<view class="history">
			<view class="historyInfo" v-for="(item,index) in userHistoryData.slice(0,8)" :key="index" @click="getCategorylist()">
				<view class="detail">
					<image :src="item.imgUrl" mode="aspectFill"></image>
					<text style="font-size: 20rpx;">{{item.categoryName}}</text>
				</view>
			</view>
		</view>
		<view class="historylist">
			<view class="list" v-for="(item,index) in userHistoryData" :key="index" @click="toDetail2(index)">
				<image :src="item.imgUrl" mode="aspectFill"></image>
				<text class="title">{{item.agricultureInfomationTitle}}</text>
			</view>
		</view>
	</view>
</uni-drawer>

<view>
	<uni-fab
			horizontal="right"
			vertical="bottom"
			@fabClick="fabClick"
	></uni-fab>
</view>
<view class="popupNew">
	<uni-popup ref="popup" type="center" border-radius="10px 10px 10px 10px" background-color="#FFFFFF">
		<view class="banner">
			<view class="content">
				<view class="title">
					<text>输入标题：</text>
					<input type="text" placeholder="请输入标题" v-model="infoAddData.title" />
				</view>
				<view class="category">
					<text>选择类别：</text>
					<picker :value="index" :range="categoryName" @change="infoAddCategory">
						<view class="uni-input">{{categoryName[index]}}</view>
					</picker>
				</view>
				<view class="info">
					<text>输入内容：</text>
					<textarea cols="300" rows="100" placeholder="请输入信息内容" maxlength="-1" v-model="infoAddData.detail"></textarea>
				</view>
			</view>
		</view>
		<view class="button">
			<button @click="infoAddButton()">提交</button>
		</view>
	</uni-popup>
</view>
</template>

<script setup> 
import {onLoad,onPullDownRefresh} from "@dcloudio/uni-app"
import { ref } from 'vue';
const categoryName = ref(['各地动态','惠农政策','农机','农品安全','农业科技','农资','市场动态','现代果业','乡村振兴','养殖','种植'])
const index = ref(0)



//抽屉
const drawerShowLeft = ref(null)
const drawerShowRight = ref(null)
const showDrawerLeft = () =>{
	getuserHistoryData();
	drawerShowLeft.value.open()
}
const showDrawerRight = () =>{
	drawerShowRight.value.open()
}
const closeDrawerRight =  (index) =>{
	getCategorylist(index+1)
	drawerShowRight.value.close()
}

//悬浮球   弹窗
const popup = ref(null)
const fabClick = () =>{
	popup.value.open()
}
//授权登录和数据信息获取
//主页展示数据
import {getRandInfo,infoAdd,userLoginApi,getHistoryDataApi,getCategoryListApi,addHistoryDataApi} from '../../api/api.js'
const rangomData = ref([])
//用户数据
const userData = ref({
	'userId':'',
	'openId':'',
	'token':''
})

onLoad((e)=>{
    uni.showModal({
        title:"请登录",
        showCancel:false,
        confirmText:"授权登录",
        success() {
            wx.login({
                success(res){
					//首次登录随机获取数据
					userlogin(res.code);
                    getRandInfoData();
                }
            })
        }
    })
})
const userlogin = async(code)=>{
	let res =  await userLoginApi({'code':code});
	userData.value.userId = res.data.id;
	userData.value.openId = res.data.openid;
	userData.value.token = res.data.token;
	console.log(userData.value);
}


//获取随机数据
const getRandInfoData = async() =>{
	let res = await getRandInfo();
	console.log('获取数据中');
	rangomData.value = res.data;
	console.log(rangomData.value);
}
//新建数据
const infoAddData = ref({'title':'','categoryId':1,'detail':''})
//修改农业信息类型name 页面展示
const infoAddCategory = (e)=>{
	index.value =e.detail.value;
	infoAddData.value.categoryId = Number(index.value)+1;
}

const infoAddButton = ()=>{
	infoAdd(infoAddData.value);
	uni.showToast({
		title:"添加成功",
		icon:"none"
	})
	popup.value.close();
}

//根据用户id获取用户浏览历史
const userHistoryData = ref([])
const getuserHistoryData = async() =>{
	let res = await getHistoryDataApi(userData.value.userId);
	userHistoryData.value = res.data;
	console.log(userHistoryData.value);
	
}

//根据类型id获取相同数据类型
const infoCategoryId = {'各地动态':1,'惠农政策':2,'农机':3,'农品安全':4,'农业科技':5,'农资':6,'市场动态':7,'现代果业':8,'乡村振兴':9,'养殖':10,'种植':11}
const infoCategoryData = ref([])
const getCategorylist = async(categoryId)=>{
	let res = await getCategoryListApi(categoryId);
	rangomData.value = res.data;
}

//下拉刷新
onPullDownRefresh(()=>{
	uni.showLoading({
		title:'数据获取中',
		mask:true,
	})
	getRandInfoData();
	uni.hideLoading();
	uni.stopPullDownRefresh();
})

//主页跳转到详情页  并添加浏览历史
const toDetail = (index) =>{
	let history = rangomData.value[index];
	history['id'] = userData.value.userId;
	addHistoryDataApi(history);
	console.log(index);
	//放入缓存中
	uni.setStorageSync('infoData',rangomData.value)
	uni.navigateTo({
		url:'/pages/detail/detail?index='+index+'&id='+userData.value.userId,
		success() {
			console.log('成功跳转');
		}
	})
}
//浏览历史跳转到详情页 并添加浏览历史
const toDetail2 = (index) =>{
	console.log(index);
	let history = rangomData.value[index];
	history['id'] = userData.value.userId;
	addHistoryDataApi(history);
	//放入缓存中
	uni.setStorageSync('infoData',userHistoryData.value)
	uni.navigateTo({
		url:'/pages/detail/detail?index='+index+'&id='+userData.value.userId,
		success() {
			console.log('成功跳转'+index);
			
		}
	})
}

//点击分类图片获取同类信息



// //添加浏览信息历史
// const addHistory = async(index) =>{
// 	let res = await addHistoryDataApi(rangomData.value[index]);
// }
</script>


<style lang="scss" scoped>
.popupNew{
	.banner{
		padding: 20rpx;
		
		
		.content{
			
			.title{
				display: flex;
				margin: 15rpx 15rpx;
				text{
					padding: 15rpx;
				}
				input{
					box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.1);
					border-radius: 15rpx;
					padding: 15rpx 15rpx;
					font-size: 25rpx;
				}
			}
			.category{
				display: flex;
				margin: 15rpx 15rpx;
				.uni-input{
					box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.1);
					border-radius: 15rpx;
					padding: 15rpx 15rpx;
					font-size: 25rpx;
				}
				text{
					padding: 15rpx;
				}
			}
			.info{
				margin: 15rpx 15rpx;
				textarea{
					box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.1);
					border-radius: 15rpx;
					padding: 15rpx 15rpx;
				}
				text{
					padding: 15rpx;
				}
			}
		}
	}
}	
	
.drawerLeft{
	.banner{
		padding:150rpx 20rpx;
		
		.user{
			display: flex;
			align-items: center;
			image{
				width: 75rpx;
				height: 75rpx;
				border-radius: 50%;
			}
			.name{
				margin-left: 20rpx;
			}
		}
		.history{
			padding: 20rpx;
			margin-top: 25rpx;
			box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.1);
			border-radius: 20rpx;
			display: grid;
			grid-template-rows: 100rpx 100rpx;
			grid-template-columns: 25% 25% 25% 25%;
			align-items: center;
			.historyInfo{
				.detail{
					display: flex;
					flex-direction: column;
					align-items: center;
					image{
						width: 60rpx;
						height: 60rpx;
						border-radius: 25rpx;
					}
				}
			}
			
		}
		.historylist{
			padding: 20rpx ;
			margin-top: 25rpx;
			box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.1);
			border-radius: 20rpx;
			.list{
				display: flex;
				align-items: center;
				justify-content: space-between;
				padding-top: 20rpx;
				image{
					width: 60rpx;
					height: 60rpx;
					border-radius: 20rpx;
				}
				.title{
					padding-right: 30rpx;
					margin-left: 15rpx;
					width: 16em;
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}
			}
		}
	}
}	
	
	
// .drawerRight{
// 	.banner{
// 		// height: 1000px;
// 		// background-color: #EAEEFA;
// 		padding: 0 15rpx;
// 		.row{
// 			font-size: 45rpx;
// 			padding: 15rpx 15rpx;
// 			border-radius: 15rpx;
// 			box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.1);
// 		}
// 		.row:hover{
// 			background-color: #FAE5F7;
// 		}
// 	}
// }
.homeLayout{
	
	.header{
		display: flex;
		justify-content: center;
		padding-top: 100rpx;
		margin-bottom: 20rpx;
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		z-index: 10;
		// height: 100px;
		background:
		    linear-gradient(to bottom,transparent ,#F2F2F4 600rpx),
		    linear-gradient(to right ,#FBE4F8 20%,#DDF3FE );
		    min-height: 5vh;
		.icon{
			position: absolute;
			left: 20rpx;
		}
		.recommendation{
			font-size: 40rpx;
			font-weight: bold;
			font-style: italic;
			text-shadow: #bbb 1px 5px 10px;
			margin-left: 50rpx;
			
		}
	}
	.banner{
		// padding: 0 20rpx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		padding-top: 200rpx;
		.row{
			
			background-color: white;
			width: 680rpx;
			padding: 20rpx 20rpx;
			border-radius: 15rpx;
			margin-top: 10rpx;
			
			.category{
				display: flex;
				align-items: center;
				image{
					width: 75rpx;
					height: 75rpx;
					border-radius: 10rpx;
					margin-right: 20rpx;
				}
				.text{
					.name{
						font-size: 35rpx;
					}
					.info{
						display: flex;
						color: #bbb;
						.left{
							text{
								font-size: 25rpx;
							}
							
						}
						.right{
							text{
								font-size: 25rpx;
								margin-left: 20rpx;
							}
						}
					}
				}
			}
			.content{
				width: 22em;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 3;
				overflow: hidden;
				text-overflow: ellipsis;
				
			}
		}
	}
}
</style>
