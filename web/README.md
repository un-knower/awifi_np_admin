# 新运营平台 管理系统后台前端

> Vue.js Webpack project

## 安装环境

``` bash
# 安装 node_modules
npm install

# 本地开发环境 serve with hot reload at localhost:2027
npm run dev

# 生产环境打包 build for production with minification
npm run build
```
## 目录说明

--src/  `前端所有源代码`

--static/  `预处理后代码`

--webpack.config.js  `webpack配置文件`

--pageage.json  `npm包管理文件`

--index.html  `管理系统首页`

--login.html  `管理系统登录页`


## 命名说明

* 文件 `连接字符-` 如 button-group.vue
* 方法名 `驼峰法（首字母小写）` 如 clickHandler
* 类名  `驼峰法（首字母大写）` 如 HttpApi

* vue 文件
推荐只写 `<template>` 和 `<script>` 标签内容，`<style>` 移至外部文件统一管理


## 生产部署

```
cd web
npm run build
```
在 web 目录下自动生成 static/np-admin/css img js 3个目录

把np-admin目录上传至服务器
