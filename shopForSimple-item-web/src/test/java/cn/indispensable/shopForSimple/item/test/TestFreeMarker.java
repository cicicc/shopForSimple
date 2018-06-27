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
package cn.indispensable.shopForSimple.item.test;

import cn.indispensable.shopForSimple.item.pojo.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试freemarker的使用
 * @author cicicc
 * @since 1.0.0
 */
public class TestFreeMarker {
    /**
     *生成最简单的HTML文件
     */
    @Test
    public void generateHelloFile() throws IOException, TemplateException {
        //第一步：创建一个 Configuration 对象，直接 new 一个对象。构造方法的参数就是 freemarker 对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        //第二步：设置模板文件所在的路径。
        configuration.setDirectoryForTemplateLoading(new File("F:\\code\\shopForSimple\\shopForSimple-item-web\\src\\main\\webapp\\WEB-INF\\freemarkerTemplate"));
        //第三步：设置模板文件使用的字符集。一般就是 utf-8.
        configuration.setURLEscapingCharset("utf-8");
        //第四步：加载一个模板，创建一个模板对象。
        Template template = configuration.getTemplate("hello.ftl");
        //第五步：创建一个模板使用的数据集，可以是 pojo 也可以是 map。一般是 Map。
        Map<Object, Object> dataModel = new HashMap<>();
        dataModel.put("hello", "welcome");
        //第六步：创建一个 Writer 对象，一般创建一 FileWriter 对象，指定生成的文件名。
        FileWriter fileWriter = new FileWriter("F:\\code\\shopForSimple\\shopForSimple-item-web\\src\\main\\webapp\\hello.html");
        //第七步：调用模板对象的 process 方法输出文件。
        template.process(dataModel, fileWriter);
        //第八步：关闭流。
        fileWriter.close();
        //模板：
        //${hello}
    }
    /**
     *生成student POJO类相关的HTML文件
     */
    @Test
    public void generateStudentFile() throws IOException, TemplateException {
        //第一步：创建一个 Configuration 对象，直接 new 一个对象。构造方法的参数就是 freemarker 对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        //第二步：设置模板文件所在的路径。
        configuration.setDirectoryForTemplateLoading(new File("F:\\code\\shopForSimple\\shopForSimple-item-web\\src\\main\\webapp\\WEB-INF\\freemarkerTemplate"));
        //第三步：设置模板文件使用的字符集。一般就是 utf-8.
        configuration.setURLEscapingCharset("utf-8");
        //第四步：加载一个模板，创建一个模板对象。
        Template template = configuration.getTemplate("student.ftl");
        //第五步：创建一个模板使用的数据集，可以是 pojo 也可以是 map。一般是 Map。
        Map<String, Student> dataModel = new HashMap<>();
        Student student = new Student();
        student.setId("001");
        student.setAge(25);
        student.setName("张三");
        dataModel.put("student", student);
        //第六步：创建一个 Writer 对象，一般创建一 FileWriter 对象，指定生成的文件名。
        FileWriter fileWriter = new FileWriter("F:\\code\\shopForSimple\\shopForSimple-item-web\\src\\main\\webapp\\WEB-INF\\freemarkerTemplate\\student.html");
        //第七步：调用模板对象的 process 方法输出文件。
        template.process(dataModel,fileWriter);
        //第八步：关闭流。
        fileWriter.close();
    }

    /**
     *生成studentList相关的HTML文件
     */
    @Test
    public void generateStudentListFile() throws IOException, TemplateException {
        //第一步：创建一个 Configuration 对象，直接 new 一个对象。构造方法的参数就是 freemarker 对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        //第二步：设置模板文件所在的路径。
        configuration.setDirectoryForTemplateLoading(new File("F:\\code\\shopForSimple\\shopForSimple-item-web\\src\\main\\webapp\\WEB-INF\\freemarkerTemplate"));
        //第三步：设置模板文件使用的字符集。一般就是 utf-8.
        configuration.setURLEscapingCharset("utf-8");
        //第四步：加载一个模板，创建一个模板对象。
        Template template = configuration.getTemplate("studentList.ftl");
        //第五步：创建一个模板使用的数据集，可以是 pojo 也可以是 map。一般是 Map。
        Map<String, List<Student>> dataModel = new HashMap<>();
        List<Student> students = new ArrayList<>();
        students.add(new Student("001", "汪纯", 19));
        students.add(new Student("002", "汪纯1", 19));
        students.add(new Student("003", "汪纯2", 19));
        students.add(new Student("004", "陈纯", 20));
        //第六步：创建一个 Writer 对象，一般创建一 FileWriter 对象，指定
        dataModel.put("studentList", students);
        // 生成的文件名。
        FileWriter fileWriter = new FileWriter("F:\\code\\shopForSimple\\shopForSimple-item-web\\src\\main\\webapp\\WEB-INF\\freemarkerTemplate\\studentList.html");
        //第七步：调用模板对象的 process 方法输出文件。
        template.process(dataModel,fileWriter);
        //第八步：关闭流。
        fileWriter.close();
    }
}
