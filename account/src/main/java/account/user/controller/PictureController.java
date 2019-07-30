package account.user.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import account.user.pojo.User;
import account.user.service.UserService;

@Controller
public class PictureController {
	public static  String profilesPath = "E:/image/";
	
	@Autowired
    HttpSession session;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/now")
	@ResponseBody
	public String getNow() {
		return userService.getNow(((User)(session.getAttribute("user"))).getId());
	}
	@PostMapping("/set")
	@ResponseBody
	public String setPicture(@RequestParam(required = true) MultipartFile profile) {
        if (!profile.isEmpty()) {
        	//session获取用户id
        	//设置一个随机的图片名字 建议uuid
        	String fileName = new Date().getTime()+".jpg";//设置头像名字
        	
        	
       
            // 磁盘保存
            BufferedOutputStream out = null;
            try {
                File folder = new File(profilesPath+fileName);
                
                out = new BufferedOutputStream(new FileOutputStream(folder));
                // 写入新文件
                out.write(profile.getBytes());
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
                return "设置头像失败";
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
         	//存入数据库 用户表字段 headPicture  默认1.jpg  更新
            String userId = ((User)(session.getAttribute("user"))).getId();
            userService.upPic(userId,fileName);
            return fileName;
        } else {
            return "设置头像失败";
        }
	}
	
	
	@GetMapping(value="/get/{url}",produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPicture(@PathVariable String url) throws Exception{
		File file = new File(profilesPath+url);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
	}
}
