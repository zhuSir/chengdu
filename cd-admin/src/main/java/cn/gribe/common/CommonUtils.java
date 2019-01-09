package cn.gribe.common;

import cn.gribe.common.validator.Assert;
import cn.gribe.common.validator.ValidatorImgUtils;
import cn.gribe.modules.oss.cloud.CloudStorageConfig;
import cn.gribe.modules.oss.cloud.OSSFactory;
import org.springframework.web.multipart.MultipartFile;

public class CommonUtils {

    /**
     * 验证图片
     * @param file
     * @return
     */
    public static void validateImg(MultipartFile[] file){
        CloudStorageConfig config = OSSFactory.getSysConfig();
        boolean isTrue = ValidatorImgUtils.validateImg(config.getAliyunAccessKeyId(),config.getAliyunAccessKeySecret(),file);
        if(isTrue){
            Assert.state(true,"图片检测涉黄请重新上传");
        }
    }

    /**
     * 验证图片
     * @param content
     * @return
     */
    public static void validateTxt(String content){
        CloudStorageConfig config = OSSFactory.getSysConfig();
        boolean isTrue = ValidatorImgUtils.validateTxt(config.getAliyunAccessKeyId(),config.getAliyunAccessKeySecret(),content);
        if(isTrue){
            Assert.state(true,"图片涉及内容违规请重新上传");
        }
    }


}
