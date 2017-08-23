package com.awifi.np.admin.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.exception.BaseException;
import com.awifi.np.admin.service.IAdminUserService;
import com.awifi.np.admin.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月13日 上午10:09:09
* 创建作者：王冬冬
* 文件名称：LoginController.java
* 版本：  v1.0
* 功能：登录接口
* 修改记录：
*/

@Controller
@RequestMapping("/public")
public class LoginController extends BaseController {
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired  
	private IAdminUserService userService;

	/**
	 * 登录接口
	 * @param request
	 * @param response
	 * @param loginAccount
	 * @param loginPwd
	 * @param vertifyCode
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月13日 下午5:12:43
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject login(HttpServletRequest request, HttpServletResponse response, String loginAccount,
			String loginPwd, String vertifyCode) {
        if(StringUtils.isEmpty(loginAccount)){
        	return returnError("E1001003","用户名为空");
        }
        if(StringUtils.isEmpty(loginPwd)){
        	return returnError("E1001003","密码为空");
        }
        if(StringUtils.isEmpty(vertifyCode)){
        	return returnError("E1001003","验证码为空");
        }
        if(getVertifyCode(request)==null){
        	return returnError("E1001003","请重新刷新验证码");
        }
		if(!getVertifyCode(request).equalsIgnoreCase(vertifyCode)){
			return returnError("E1001002");
		}
		NPAdminUser user=null;
		
		try {
//			if(userService.countLoingErrorTimes(loginAccount)>=3){
//				return returnError("E1001005");//账号锁定
//			}
			user = userService.login(loginAccount,MD5.md5(loginPwd));
		} catch (BaseException e) {
			logger.debug("error:{}",e.getMessage());
//			userService.addErrorTime(loginAccount);//发生异常登录错误次数加1
			return returnError("E1001004",e.getMessage());
		}
		request.getSession().setAttribute(Constants.ADMIN_SESSION_KEY, user);
		return returnSuccess(user);
	}
	/**
	 * 从session里拿验证码
	 * @param request
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月16日 上午11:17:51
	 */
	private String getVertifyCode(HttpServletRequest request) {
		if(request.getSession()==null){
			return null;
		}
		return (String) request.getSession().getAttribute("vertifyCode");
	}

	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @author 王冬冬  
	 * @date 2017年1月13日 下午5:13:01
	 */
	@RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
	public void getVerifImg(HttpServletRequest request, HttpServletResponse response) {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 在内存中创建图象
		int width = Constants.VERFITYCODE_WIDTH;
		int height = Constants.VERFITYCODE_HIGHT;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		Integer defaultFc=160;
		Integer defaultBc=200;
		// 设定背景色
//		g.setColor(getRandColor(200, 250));
		g.setColor(getRandColor(defaultFc, defaultBc));
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font(Constants.VERFITYCODE_FONT, Font.PLAIN, Constants.FONT_SIZE));
		// 画边框
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
//		Integer lineCount=155;
		g.setColor(getRandColor(defaultFc-50, defaultBc-50));
		Integer offset=12;//偏移量
//		for (int i = 0; i < lineCount; i++) {
//			int x = random.nextInt(width);
//			int y = random.nextInt(height);
//			int xl = random.nextInt(offset);
//			int yl = random.nextInt(offset);
//			g.drawLine(x, y, x + xl, y + yl);
//		}
		// 取随机产生的认证码(4位数字或字母)
		StringBuilder sRand =new StringBuilder();
		for (int i = 0; i < Constants.CODE_COUNT; i++) {
			String rand = String.valueOf(Constants.VERFITYCODE_SEED.charAt(random.nextInt(Constants.VERFITYCODE_SEED.length())%Constants.VERFITYCODE_SEED.length()));
			sRand.append(rand);
			// 将认证码显示到图象中
//			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110))); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			Integer randomColor=110;
			g.setColor(new Color(20+random.nextInt(randomColor),20+random.nextInt(randomColor), 20+random.nextInt(randomColor))); // 调用函数出来的颜色相近，可能是因为种子太接近，所以只能直接生成
			Integer startX=13*i+6;
			Integer startY=16;
			g.drawString(rand,startX, startY);
		}

		// 将认证码存入SESSION
		request.getSession().setAttribute("vertifyCode", sRand.toString());
		// 图象生效
		g.dispose();
		// 输出图象到页面
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给定范围获得随机颜色
	 * @param fc 前景色
	 * @param bc 背景色
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月13日 下午5:13:24
	 */
	public Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
		Random random = new Random();
		fc = (fc > 255) ? 255 : fc;
		bc = (bc > 255) ? 255 : bc;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
