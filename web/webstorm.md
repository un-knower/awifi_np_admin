WebStorm配置File and Code Templates

1) File > settings

2) Editor > File and Code Templates

3) Files选项

* JavaScript File
```
/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: ${YEAR}-${MONTH}-${DAY} ${HOUR}:${MINUTE}
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: ${NAME}
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */
```

* SCSS File
```
/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: ${YEAR}-${MONTH}-${DAY} ${HOUR}:${MINUTE}
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: ${NAME}
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */
```

* Vue File
```
/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: ${YEAR}-${MONTH}-${DAY} ${HOUR}:${MINUTE}
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: ${NAME}
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */
```

4) Live Templates

4.1) 点击右侧+号 > Template Group > 填写Vue

4.2) 选中 Vue 点击右侧+号 > Live Template

* Abbreviation=> -m
* Description=> 注释
* Template text=>
```
/**
 * 功能: xx
 * 修改记录: xx
 * @author 朱学煌
 * @date $DATE$
 */
$END$
```

4.3) Chanage => 全选

4.4) Edit variables =>
DATE / Default vaule / 填写 `date("yyyy-MM-dd HH:mm")`

5) Editor > Code Style

所有文件 Tabs and Indents / Tab size=4 Index=4
不勾选 Use tab character

6) 完成配置
