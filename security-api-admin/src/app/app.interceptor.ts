import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpResponse
} from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

/** Pass untouched request through to the next request handler. */
@Injectable()
export class RefreshInterceptor implements HttpInterceptor {

  constructor(private http: HttpClient) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      tap(
        () => {},
        error => {
          console.log(error);
          if (error.status === 500 && error.error.message === 'refresh fail') {
            // this.logout();
            window.location.href="http://auth.security.com:7024/oauth/authorize"
            +"?response_type=code"
            +"&client_id=adminServer";
            +"&redirect_uri=http://admin.security.com:7027/oauth/callback"
            +"&state=ABC"; //这个是盐 标识一个状态，比如在某一步跳转到了登录认证，回来的时候会原样回来，那么 就可以通过这个字符串 恢复之前跳转的页面
          }
        }));
  }

  // logout() {
  //   this.http.post('logout', {}).subscribe(() => {
  //     window.location.href = 'http://auth.imooc.com:9090/logout?redirect_uri=http://admin.imooc.com:8080';
  //   });
  // }
}
