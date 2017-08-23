/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-14 15:17
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: route-hash.js
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

import querystring from 'querystring';
import axios from 'axios';
import $route from './route';
import $notice from './notice';

/**
 * body元素动态添加script标签
 * @param type text|src body元素最后添加script子元素
 * @param content 子元素内容
 */
function createScript(type = 'text', content) {
    let doc = document;
    let newScript = doc.createElement('script');
    if (type === 'text') {
        newScript.textContent = content;
    } else if (type === 'src') {
        newScript.setAttribute('src', content);
    }
    doc.body.appendChild(newScript);
}

/**
 * 判断body中是否包含data-route="hash"属性和当前DOM-oldJs元素
 * @returns {hasHash: boolean, oldJs: *}
 */
function getDataHash() {
    let doc = document;
    let bodys = doc.body;
    let bodyScripts = bodys.getElementsByTagName('script');
    let oldJs = null;
    let hasHash = false;

    for (let i = 0, len = bodyScripts.length; i < len; i++) {
        let dataRoute = bodyScripts[i].getAttribute('data-route');
        // data-route="hash" 仅有一个
        if (dataRoute && dataRoute === 'hash') {
            oldJs = bodyScripts[i];
            hasHash = true;
            break;
        }
    }

    // 判断 data-route="hash" 属性
    if (!hasHash) {
        return {
            hasHash: false,
            oldJs: oldJs
        };
    }

    return {
        hasHash: true,
        oldJs: oldJs
    };
}

/**
 * 替换body中 <script src="xx" data-route="hash"></script> 标签元素
 * @param src 新的src地址
 */
function replaceScript(src, oldJs) {
    let doc = document;
    let bodys = doc.body;
    let newJs = doc.createElement('script');
    newJs.setAttribute('data-route', 'hash');
    newJs.src = `${src}?_t=${new Date().getTime()}`;
    // document.body.appendChild(s);
    bodys.replaceChild(newJs, oldJs);
}

/**
 * 获取业务模板中所有<script>xx</script>内容
 * @param htm 模板html代码
 * @returns {string} js脚本内容
 */
function getScriptText(htm) {
    let scriptText = '';
    let regScriptText = /<script>(.*?)<\/script>/gmi;
    let scriptTextList = htm.match(regScriptText);
    if (scriptTextList) {
        if (scriptTextList.length > 1) {
            $notice({code: 'S001', msg: '模板页面仅含有一个script标签'});
            return scriptText;
        }
        scriptText = scriptTextList.join('')
            .replace(/<(\/)?script>/g, '');
    }
    if (scriptText) {
        createScript('text', scriptText);
    }
    return scriptText;
}

/**
 * 获取业务模板中所有<script src="xx"></script>内容
 * @param htm 模板html代码
 * @returns {Array} src链接地址
 */
function getScriptSrc(htm) {
    let srcList = [];
    let regScriptSrc = /<script src="(.*?)"><\/script>/gmi;
    let scriptSrcList = htm.match(regScriptSrc);
    if (scriptSrcList) {
        if (scriptSrcList.length > 1) {
            $notice({code: 'S001', msg: '模板页面仅含有一个script标签'});
            return srcList;
        }
        scriptSrcList.forEach(function (item) {
            let srcArr = item.match(/src="(.*?)"/i);
            let src = (srcArr && srcArr[1]) ? srcArr[1] : '';
            srcList.push(src);
        });
    }
    return srcList;
}

/**
 * history hashchange事件处理方法
 * @param e hashchange事件对象
 */
function routerHashChanged(e) {
    let routeQuery = $route.query();
    let targetId = routeQuery.target || 'right';

    let {oldURL: oldUrl, newURL: newUrl} = e;
    let _hash = location.hash.substr(1);
    // 当hash为空或为/时不请求
    if (_hash == '' || _hash == '/') {
        return;
    }
    let hashList = _hash.split('?');
    let hashPath = hashList[0];
    let hashQuery = querystring.parse(hashList[1]);

    // if (oldUrl !== newUrl && e.type === 'hashchange') {
    if (e.type === 'hashchange') {
        // 异步请求模板内容，并替换至相对应的DOM id=“right”中
        axios.get(hashPath).then(function (resp) {

            // code不为0直接输出
            if (resp.data && resp.data.code && resp.data.code !== '0') {
                $notice(resp.data);
                return;
            }

            // 获取data-route属性和oldJs对象
            let {hasHash, oldJs} = getDataHash();

            // 判断 data-route="hash" 属性
            if (!hasHash) {
                $notice({code: 'S001', msg: '未找到 script data-route="hash" 属性'});
                return;
            }

            // 复制模板内容删除其他的script标签
            let domHtml = resp.data.substr();
            domHtml = domHtml.replace(/<script( src=".*?")?>(.*?)?<\/script>/gmi, '');

            // 替换id=right DOM内容
            try {
                document.getElementById(targetId).innerHTML = domHtml;
            } catch (e) {
                $notice({code: 'S001', msg: '未找到 ' + targetId + ' DOM元素'});
                return;
            }

            // 动态添加script标签
            // getScriptText(resp.data);
            let srcList = getScriptSrc(resp.data);

            // 替换 data-route="hash" 元素
            replaceScript(srcList[0], oldJs);

        }).catch(function (err) {

        });
    }
}

export default {
    routerHashChanged
};