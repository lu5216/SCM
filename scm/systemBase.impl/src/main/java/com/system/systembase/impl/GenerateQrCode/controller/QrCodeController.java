package com.system.systembase.impl.GenerateQrCode.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.system.common.vo.Result;
import com.system.systembase.api.GenerateQrCode.vo.GenerateCodeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.UUID;

import static com.system.common.utils.QRCodeGeneratorUtil.getBarCode;
import static com.system.common.utils.QRCodeGeneratorUtil.insertWords;

/**
 * @author lutong
 */

@Slf4j
@RestController
@Api(tags = "接口-生成二维码/条形码")
@RequestMapping("/generateCode")
@Controller
public class QrCodeController {

    @ApiOperation(value = "生成二维码图片")
    @GetMapping(value = "/generateQrCode")
    public Result<GenerateCodeVo> generateQrCode(@RequestParam(value = "content") String content,
                                                 @RequestParam(value = "path", required = false) String path,
                                                 @RequestParam(value = "width", required = false) Integer width,
                                                 @RequestParam(value = "height", required = false) Integer height) {
        try {
            if (width == null) width = 300;
            if (height == null) height = 300;
            // 校验
            GenerateCodeVo vo = this.verifyPathAndName(path, "qrCode");
            // 生成二维码图片
            QrCodeUtil.generate(content, width, height, FileUtil.file(vo.getPicturePath()));

            return Result.success("二维码生成成功！", vo);
        } catch (Exception e) {
            log.error("二维码生成失败，失败原因：" + e);
            return Result.failed("二维码生成失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "生成条形码图片")
    @GetMapping(value = "/generateBarCode")
    public Result<GenerateCodeVo> generateBarCode(@RequestParam(value = "content") String content,
                                                  @RequestParam(value = "createDate") String createDate,
                                                  @RequestParam(value = "username") String username,
                                                  @RequestParam(value = "path", required = false) String path) {
        // 校验
        GenerateCodeVo vo = this.verifyPathAndName(path, "barCode");
        try {
            // 处理中文乱码问题(未处理)
            // content = new String(content.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            BufferedImage image = insertWords(getBarCode(content), content, createDate, username);
            if (image != null) {
                ImageIO.write(image, "jpg", FileUtil.file(vo.getPicturePath()));
            }
            return Result.success("条形码生成成功！", vo);
        } catch (Exception e) {
            log.error("条形码生成失败，失败原因：" + e);
            return Result.failed("条形码生成失败，失败原因：" + e);
        }
    }



    public GenerateCodeVo verifyPathAndName(String path, String type) {
        String uuid = UUID.randomUUID().toString();
        if (path == null || path.equals("")) path = "../../src/main/resources/" + type + "/" + uuid + ".jpg";
        // 判断重复文件
        while (FileUtil.file(path).exists()) {
            String[] splits = path.split("/");
            StringBuilder newPath = new StringBuilder();
            for (int i = 0; i < splits.length-1; i++) {
                newPath.append(splits[i]).append('/');
            }
            uuid = UUID.randomUUID().toString();
            path = newPath + uuid + ".jpg";
        }
        // 返回
        GenerateCodeVo vo = new GenerateCodeVo();
        vo.setPicturePath(path);
        vo.setPictureName(uuid + ".jpg");
        return vo;
    }
}
