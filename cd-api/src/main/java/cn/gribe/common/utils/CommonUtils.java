package cn.gribe.common.utils;

import cn.gribe.common.utils.oss.CloudStorageConfig;
import cn.gribe.common.utils.oss.OSSFactory;
import cn.gribe.common.validator.Assert;
import cn.gribe.common.validator.ValidatorImgUtils;
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
            Assert.state(true,"图片涉及内容违规请重新上传");
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
            Assert.state(true,"涉及内容违规请重新填写");
        }
    }

}
