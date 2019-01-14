package cn.gribe.common.validator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.green.extension.uploader.ClientUploader;
import com.aliyuncs.green.model.v20180509.ImageSyncScanRequest;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class ValidatorImgUtils {

    public static final Logger logger = LoggerFactory.getLogger(ValidatorImgUtils.class);

    public static boolean validateImg(String accessKeyId, String accessKeySecret, MultipartFile[] files){
        if(files != null){
            return false;
        }
        IClientProfile profile = DefaultProfile
                .getProfile("cn-shanghai", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Green", "green.cn-shanghai.aliyuncs.com");
        } catch (ClientException e) {
            e.printStackTrace();
            logger.error("初始化图片检查错误.",e.getMessage());
        }
        IAcsClient client = new DefaultAcsClient(profile);
        ImageSyncScanRequest imageSyncScanRequest = new ImageSyncScanRequest();
        // 指定api返回格式
        imageSyncScanRequest.setAcceptFormat(FormatType.JSON);
        // 指定请求方法
        imageSyncScanRequest.setMethod(MethodType.POST);
        imageSyncScanRequest.setEncoding("utf-8");
        //支持http和https
        imageSyncScanRequest.setProtocol(ProtocolType.HTTP);

        JSONObject httpBody = new JSONObject();
        /**
         * 设置要检测的场景, 计费是按照该处传递的场景进行
         * 一次请求中可以同时检测多张图片，每张图片可以同时检测多个风险场景，计费按照场景计算
         * 例如：检测2张图片，场景传递porn,terrorism，计费会按照2张图片鉴黄，2张图片暴恐检测计算
         * porn: porn表示色情场景检测
         */
        String[] type = {"porn","terrorism"};
        httpBody.put("scenes", type);

        /**
         * 如果您要检测的文件存于本地服务器上，可以通过下述代码片生成url，
         * 再将返回的url作为图片地址传递到服务端进行检测
         */
        ClientUploader uploader = ClientUploader.getImageClientUploader(client);
        byte[] imageBytes = null;
        String url = null;
        JSONArray tasks = new JSONArray();
        try{
//            //这里读取本地文件作为二进制数据，当做输入做为示例, 实际使用中请直接替换成您的图片二进制数据
//            imageBytes = FileUtils.readFileToByteArray(new File("/Users/01fb4ab6420b5f34623e13b82b51ef87.jpg"));
            for(MultipartFile file : files){
                imageBytes = file.getBytes();
                //上传到服务端
                url = uploader.uploadBytes(imageBytes);
                JSONObject task = new JSONObject();
                task.put("dataId", UUID.randomUUID().toString());
                //设置图片链接为上传后的url
                task.put("url", url);
                task.put("time", new Date());
                tasks.add(task);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //多张图片
        httpBody.put("tasks", tasks);
        imageSyncScanRequest.setHttpContent(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(httpBody.toJSONString()),
                "UTF-8", FormatType.JSON);

        /**
         * 请设置超时时间, 服务端全链路处理超时时间为10秒，请做相应设置
         * 如果您设置的ReadTimeout 小于服务端处理的时间，程序中会获得一个read timeout 异常
         */
        imageSyncScanRequest.setConnectTimeout(3000);
        imageSyncScanRequest.setReadTimeout(10000);
        HttpResponse httpResponse = null;
        try {
            httpResponse = client.doAction(imageSyncScanRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //服务端接收到请求，并完成处理返回的结果
        if (httpResponse != null && httpResponse.isSuccess()) {
            JSONObject scrResponse = JSON.parseObject(org.apache.commons.codec.binary.StringUtils.newStringUtf8(httpResponse.getHttpContent()));
            System.out.println(JSON.toJSONString(scrResponse, true));
            logger.info(JSON.toJSONString(scrResponse, true));
            int requestCode = scrResponse.getIntValue("code");
            //每一张图片的检测结果
            JSONArray taskResults = scrResponse.getJSONArray("data");
            if (200 == requestCode) {
                for (Object taskResult : taskResults) {
                    //单张图片的处理结果
                    int taskCode = ((JSONObject) taskResult).getIntValue("code");
                    //图片要检测的场景的处理结果, 如果是多个场景，则会有每个场景的结果
                    JSONArray sceneResults = ((JSONObject) taskResult).getJSONArray("results");
                    if (200 == taskCode) {
                        for (Object sceneResult : sceneResults) {
                            String scene = ((JSONObject) sceneResult).getString("scene");
                            String suggestion = ((JSONObject) sceneResult).getString("suggestion");
                            int rate = ((JSONObject) sceneResult).getIntValue("rate");
                            //根据scene和suggetion做相关处理
                            //do something
                            logger.info("scene = [" + scene + "]");
                            logger.info("suggestion = [" + suggestion + "]");
                            String label = ((JSONObject) sceneResult).getString("label");
                            if(!"normal".equals(label)){
                                return true;
                            }
                        }
                    } else {
                        //单张图片处理失败, 原因是具体的情况详细分析
                        logger.error("task process fail. task response:" + JSON.toJSONString(taskResult));
                    }
                }
            } else {
                /**
                 * 表明请求整体处理失败，原因视具体的情况详细分析
                 */
                logger.error("the whole image scan request failed. response:" + JSON.toJSONString(scrResponse));
            }
        }
        return false;
    }

    /**
     * 内容检测
     * @param accessKeyId
     * @param accessKeySecret
     * @param content
     * @return
     * @throws Exception
     */
    public static boolean validateTxt(String accessKeyId, String accessKeySecret, String content){
        if(StringUtils.isEmpty(content)){
            return false;
        }
        //请替换成您自己的accessKeyId、accessKeySecret
        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Green", "green.cn-shanghai.aliyuncs.com");
        } catch (ClientException e) {
            e.printStackTrace();
            logger.error("==>>>>:内容检测错误",e.getMessage());
        }
        IAcsClient client = new DefaultAcsClient(profile);
        TextScanRequest textScanRequest = new TextScanRequest();
        textScanRequest.setAcceptFormat(FormatType.JSON); // 指定api返回格式
        textScanRequest.setHttpContentType(FormatType.JSON);
        textScanRequest.setMethod(com.aliyuncs.http.MethodType.POST); // 指定请求方法
        textScanRequest.setEncoding("UTF-8");
        textScanRequest.setRegionId("cn-shanghai");
        List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
        Map<String, Object> task1 = new LinkedHashMap<String, Object>();
        task1.put("dataId", UUID.randomUUID().toString());
        task1.put("content", content);
        tasks.add(task1);
        JSONObject data = new JSONObject();
        /**
         * 文本垃圾检测：antispam
         * 关键词检测：keyword
         **/
        data.put("scenes", Arrays.asList("antispam"));
        data.put("tasks", tasks);
        System.out.println(JSON.toJSONString(data, true));
        try {
            textScanRequest.setHttpContent(data.toJSONString().getBytes("UTF-8"), "UTF-8", FormatType.JSON);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("==>>>>:内容检测错误",e.getMessage());
        }
        // 请务必设置超时时间
        textScanRequest.setConnectTimeout(3000);
        textScanRequest.setReadTimeout(6000);
        try {
            HttpResponse httpResponse = client.doAction(textScanRequest);
            if(httpResponse.isSuccess()){
                JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getHttpContent(), "UTF-8"));
                logger.info("==>>>>:内容检测:",JSON.toJSONString(scrResponse, true));
                if (200 == scrResponse.getInteger("code")) {
                    JSONArray taskResults = scrResponse.getJSONArray("data");
                    for (Object taskResult : taskResults) {
                        if(200 == ((JSONObject)taskResult).getInteger("code")){
                            JSONArray sceneResults = ((JSONObject)taskResult).getJSONArray("results");
                            for (Object sceneResult : sceneResults) {
                                String label = ((JSONObject)sceneResult).getString("label");
                                if(!"normal".equals(label)){
                                    return true;
                                }
                            }
                        }else{
                            logger.error("task process fail:" + ((JSONObject)taskResult).getInteger("code"));
                        }
                    }
                } else {
                    logger.error("detect not success. code:" + scrResponse.getInteger("code"));
                }
            }else{
                logger.error("response not success. status:" + httpResponse.getStatus());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.put("a","a");
        JSONArray arr = new JSONArray();
        arr.add(obj);
        System.out.println(Arrays.asList(obj));
        System.out.println(arr);
    }

}
