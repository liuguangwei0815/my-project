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
  credentials = {username:"xixi",password:"123456"};
  //注入http
  constructor(private http : HttpClient){
  }
  //方法
  login(){
    this.http.post("login",this.credentials).subscribe(()=>{
      this.authenticated = true;
    },()=>{
      alert("login fail");      
    });
  }
}
