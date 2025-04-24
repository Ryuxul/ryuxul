import { request } from "../utils/request";

export function userLoginApi(data={}){
	return request({url:'/user/login',data,method:'post'})
}

export function getRandInfo(){
	return request({url:'/info/random'})
}

export function getCategoryListApi(categoryId){
	return request({'url':'/info/category/list?id='+categoryId})
}

export function infoAdd(data={}){
	return request({url:'/info/add',data,method:'post'})
}

export function getHistoryDataApi(userId){
	return request({'url':'/user/history/list?id='+userId})
}

export function addHistoryDataApi(data={}){
	return request({'url':'/user/history/add',data,method:'post'})
}

export function addUserScoreDataApi(data={}){
	return request({'url':'/user/score',data,method:'post'})
}