//package com.my.security.securityconfig;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
///**
// * 资源服务中 如果获取用户信息 但是只能获取一个用户名 所以需要处理 ,这个制作数据转换展示  没有用到securit 的校验
// * @author liuwei
// *//  因为启动了zuul 在zuul 进行了安全相关的配置 所以这里直接注释掉
// */
//@Component
//public class MyUserDetailsService implements UserDetailsService {
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		//通过用户名获取用户的信息
//		MyUser myUser = new MyUser();
//		myUser.setId(100l);
//		myUser.setUsername(username);
//		return myUser;
//	}
//
//}
