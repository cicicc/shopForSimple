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
package cn.indispensable.shopForSimple.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成HTML伪静态页面的控制层代码
 * @author cicicc
 * @since 1.0.0
 */
@Controller
public class HtmlGenerateController {
    //注入freemarkerConfigurer对象
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 生成静态页面(页面的测试代码)
     * @return
     */
    @RequestMapping("/genhtml")
    @ResponseBody
    public String generateHtml()throws Exception{
        // 1、从 spring 容器中获得 FreeMarkerConfigurer 对象。(对象已经注入 无需再次进行配置)
        // 2、从 FreeMarkerConfigurer 对象中获得 Configuration 对象。
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        // 3、使用 Configuration 对象获得 Template 对象。
        Template template = configuration.getTemplate("hello.ftl");
        // 4、创建数据集
        Map dataModel = new HashMap<>();
        dataModel.put("hello", "1000");
        // 5、创建输出文件的 Writer 对象。
        Writer out = new FileWriter(new File("F:\\code\\shopForSimple\\shopForSimple-item-web\\src\\main\\webapp\\WEB-INF\\ftl\\hello.html"));
        // 6、调用模板对象的 process 方法，生成文件。
        template.process(dataModel, out);
        // 7、关闭流。
        out.close();
        return "OK";
    }
}
