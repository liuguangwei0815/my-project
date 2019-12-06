package com.my.security.vaidata.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.my.security.properites.SecurityProperties;

@RestController
public class ImageCodeController {

	private SessionStrategy stategy = new HttpSessionSessionStrategy();

	@Autowired
	private SecurityProperties securityProperties;

	@GetMapping("/image/code")
	public void code(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletRequestBindingException, NumberFormatException {
		// 生产Image
		ImageCode code = createImageCode(request, response);
		// 将code放到session
		stategy.setAttribute(new ServletWebRequest(request), ImageCodeContant.SESSIONKEY, code);
		// 将图片推送的接口上
		ImageIO.write(code.getImage(), "JPEG", response.getOutputStream());

	}

	private ImageCode createImageCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, NumberFormatException {

		int width = ServletRequestUtils.getIntParameter(request, "width",
				securityProperties.getCode().getImage().getWidth());
		int height = ServletRequestUtils.getIntParameter(request, "height",
				securityProperties.getCode().getImage().getHeight());
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		// 设置图片背景色
		g.setColor(getRandColor(200, 500));
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		String sRand = "";
		for (int i = 0; i < securityProperties.getCode().getImage().getLength(); i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}
		g.dispose();
		return new ImageCode(sRand, image, securityProperties.getCode().getImage().getExprireTime());
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
