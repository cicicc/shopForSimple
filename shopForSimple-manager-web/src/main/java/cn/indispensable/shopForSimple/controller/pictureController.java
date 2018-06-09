package cn.indispensable.shopForSimple.controller;

import cn.indispensable.shopForSimple.common.utils.FastDFSClient;
import cn.indispensable.shopForSimple.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@Controller
public class pictureController {
    //获取配置文件中的图片服务器的地址
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;
    /**
     * 图片上传功能实现 对图片进行相应处理 并返回对应的信息
     * @param uploadFile 上传的文件文件名
     * @return 本次上传的结果信息所封装的map
     */
    @RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf8")
    @ResponseBody
    public String picUpload(MultipartFile uploadFile){
        Map<Object, Object> map = new HashMap<>();
        try {
            //1、取文件的扩展名
            //首先获取文件的原始名称
            String filename = uploadFile.getOriginalFilename();
            String fileSuffix = filename.substring(filename.lastIndexOf('.') + 1);
            //2、创建一个 FastDFS 的客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            //3、执行上传处理
            String path = fastDFSClient.uploadFile(uploadFile.getBytes(), fileSuffix);
            //4、拼接返回的 url 和 ip 地址，拼装成完整的 url
            String url = IMAGE_SERVER_URL + path;

            //程序未曾报错的话error返回零
            map.put("error", 0);
            map.put("url", url);
        } catch (Exception e) {
            e.printStackTrace();

            map.put("error", 1);
            map.put("message", "图片上传失败");
        }
        //5、返回 json数据
        return JsonUtils.objectToJson(map);
    }
}
