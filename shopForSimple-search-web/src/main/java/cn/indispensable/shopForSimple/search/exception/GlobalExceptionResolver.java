/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.indispensable.shopForSimple.search.exception;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**

 * @author cicicc
 * @since 1.0.0
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    //创建logger对象
    Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    /**
     * 全局异常处理
     * search包下的全局异常处理类
     * 处理逻辑：
     * 捕获整个系统中发生的异常。
     * 1、异常写入日志文件
     * 2、及时通知开发人员。发邮
     * 件、短信。
     * 3、展示一个错误页面，例如：
     * 您的网络故障，请重试。
     * @return 视图对象
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //写日志文件

        logger.error("系统发生异常", ex);
        //发邮件、发短信
        //Jmail：可以查找相关的资料
        //需要在购买短信。调用第三方接口即可。
        //展示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "系统发生异常，请稍后重试");
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
