<template>
	<view class="Layout">
		<view class="icon">
			<uni-icons type="back" size="22"  @click="backToIndex()"></uni-icons>
		</view>
		<view class="title">
			<text>{{infoData.agricultureInfomationTitle}}</text>
		</view>
		<view class="categoryname">
			{{infoData.categoryName}}
		</view>
		<view class="detail" style="display: flex;flex-direction: column;align-items: center;justify-content: center;">
				<image :src="infoData.imgUrl" mode="aspectFill" style="border-radius: 15rpx;"></image>
			<rich-text >
				{{infoData.detail}}
			</rich-text>
		</view>
	</view>
	<view class="score">
		<view class="text">评分：</view>
		<uni-rate v-model="scoreValue" @change="onChange"/>
	</view>
</template>

<script setup>
//获取缓存数据
import {onLoad} from "@dcloudio/uni-app"
import { ref } from 'vue';
const infoData = ref({})
const userId = ref()
onLoad((e)=>{
	let res = uni.getStorageSync('infoData');
	infoData.value = res[Number(e.index)];
	userId.value = e.id;
})
	
	
//跳转回index
const backToIndex = () =>{
	uni.navigateBack();
}
import {addUserScoreDataApi} from '../../api/api.js'
//评分
const scoreValue = ref(2)
const onChange = () =>{
	console.log(scoreValue.value);
	let scoreData = infoData.value;
	scoreData['id'] = userId.value;
	scoreData['score'] = scoreValue.value;
	addUserScoreDataApi(scoreData);
}

</script>

<style lang="scss" scoped>
.Layout{
	padding: 105rpx 20rpx;
	font-family: Arial, sans-serif;  
	line-height: 1.6;  
	color: #333;  
	margin: 0;  
	background-color: #f4f4f4;
	.title{
		padding-top: 80rpx;
		font-size: 1.5em;  
		
	}
	.categoryname{
		color: #8B9BA8;
		font-size: 0.875em;
		margin: 20rpx auto;
	}
	.detail{
		line-height: 1.6;
		color: #282C35;
	}
}
.score{
	padding: 20rpx 20rpx;
	width: 100%;
	color: #000;
	height: 100rpx;
	display: flex;
	background-color: #f4f4f4;
	position: fixed;
	bottom: 0;
	.text{
		padding-top: 10rpx;
	}
	uni-rate{
		padding-top: 10rpx;
	}
	
}
</style>
