import { Component } from '@angular/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = '我的而第一个angular2';
  //声明变量控制页面显示
  authenticated = false;
  credentials = { username: "xixi", password: "123456" };
  order = {};
  //注入http
  constructor(private http: HttpClient) {

    this.http.get("me").subscribe(data=>{
      if(data){
        this.authenticated = true;
      }
       //如果未认证直接跳转到认证服务器进行认证 
      // /oauth/authorize?response_type=code&client_id=orderApp&redirect_uri=http://example.com
      if(!this.authenticated){
        window.location.href="http://security.auth.com:7024/oauth/authorize"
        +"?response_type=code"
        +"&client_id=adminServer";
        +"&redirect_uri=http://security.admin.com:7027/oauth/callback"
        +"&state=ABC"; //这个是盐 标识一个状态，比如在某一步跳转到了登录认证，回来的时候会原样回来，那么 就可以通过这个字符串 恢复之前跳转的页面
      }
    });
  }
  //方法
  login() {
    this.http.post("login", this.credentials).subscribe(() => {
      this.authenticated = true;
    }, () => {
      alert("login fail");
    });
  }

   //方法
  logout() {
    this.http.post("logout", {}).subscribe(() => {
      this.authenticated = false;
    }, () => {
      alert("login fail");
    });
  }

  //作为前端服务 将前端请求携带token 访问网关服务器 然后网关然后在转发到资源服务器上
  getOrderInfo() {
    this.http.get("api/order/orders/1").subscribe(data => {
      this.order = data;
    }, () => {
      alert("get order  fail");
    });
  }


}
