import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

//使双向绑定生效
import { FormsModule } from '@angular/forms';
//发送http请求
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,//引进
    HttpClientModule//引进
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
