package org.hiveadmin.hive.action;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 
 * @ClassName: BaseAction
 * @Description: Action的Base类，获取基本的Response,Request,Response
 * @author wizardholy	wizardholy@163.com
 * @date 2013-7-22 下午6:09:52
 *
 */
@SuppressWarnings("serial")
public class BaseAction extends ActionSupport {
       protected Log logger = LogFactory.getLog(BaseAction.class);
 
       /**
        * 在action中用于return到index
        */
       public static final java.lang.String INDEX = "index";
       /**
        * <p>在action中用于return到login</p>
        * <p>一般指的是登录页面</p>
        */
       public static final java.lang.String LOGIN = "login";
       public static final java.lang.String MYACCOUNT = "myaccout";
       /**
        * <p>在action中用于return到message</p>
        * <p>一般指的是信息提示页面</p>
        */
       public static final java.lang.String MESSAGE = "message";
       /**
        * <p>提示信息,一般用于提示用户的错误操作或成功操作</p>
        * <p>example：message="你的操作成功完成";return MESSAGE;</p>
        */
       protected String message;

       public  String error;

       /**
        * <p>功能描述：获得request对象</p>
        * @return
        * @author:wizardholy
        * @update:[日期YYYY-MM-DD][更改人姓名][变更描述]
        */
       public HttpServletRequest getRequest() {
               return ServletActionContext.getRequest();
       }
      
       /**
        * <p>功能描述：获得response对象</p>
        * @return
        * @author:wizardholy
        * @update:[日期YYYY-MM-DD][更改人姓名][变更描述]
        */
       public HttpServletResponse getResponse() {
               return ServletActionContext.getResponse();
       }
       /**
        *
        * <p>功能描述：获得session对象</p>
        * @return
        * @author:wizardholy
        * @update:[日期YYYY-MM-DD][更改人姓名][变更描述]
        */
       public HttpSession getSession() {
               return getRequest().getSession();
       }
       /**
        * 获得context上下文
        * @return
        * @author:wizardholy
        * @update:[日期YYYY-MM-DD][更改人姓名][变更描述]
        */
       public ServletContext getServletContext() {
               return ServletActionContext.getServletContext();
       }
      
      
       /**
        * <p>功能描述：根据cookie名称获得cookie值</p>
        * @param cookiename
        * @return
        * @author:wizardholy
        * @update:[日期YYYY-MM-DD][更改人姓名][变更描述]
        */
       public String getCookie(String cookiename) {
               String cookieValue = "";
               Cookie cookies[] = getRequest().getCookies();
               Cookie sCookie = null;
               if (cookies != null && cookies.length > 0) {
                       for (int i = 0; i < cookies.length; i++) {
                               sCookie = cookies[i];
                               sCookie.setPath("/");
                               if (sCookie.getName().equals(cookiename)) {
                                       cookieValue = sCookie.getValue();
                                       break;
                               }
                       }
               }
               return cookieValue;
              
       }
     
       public Log getLogger() {
      
               return logger;
       }
       public void setLogger(Log logger) {
               this.logger = logger;
       }
       public String getError() {
               return error;
       }
       public void setError(String error) {
               this.error = error;
       }
       public String getMessage() {
               return message;
       }
       public void setMessage(String message) {
               this.message = message;
       }
      
      

      
}

