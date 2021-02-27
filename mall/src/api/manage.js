import http from './public'
const baseUrl = '/account'
const commodityBaseUrl = '/com'
// 返回所有用户账号
export const getUsers = (params) => {
  return http.fetchGet(`${baseUrl}/manage/users`, params)
}
// 删除账号
export const deleteUser = (params, account) => {
  return http.fetchDelete(`${baseUrl}/manage/users/`+account, params)
}

// 返回所有商品
export const getCommodities = (params) => {
  return http.fetchGet(`${commodityBaseUrl}/commodity/all`, params)
}

// 新增商品
export const addCommodity = (params) => {
  return http.fetchPost(`${commodityBaseUrl}/commodity/add`, params)
}

// 删除商品
export const deleteFile = (params) => {
  return http.fetchDelete(`${commodityBaseUrl}/file/delete`, params)
}
