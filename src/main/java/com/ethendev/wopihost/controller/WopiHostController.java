package com.ethendev.wopihost.controller;

import com.ethendev.wopihost.entity.FileInfo;
import com.ethendev.wopihost.entity.WopiStatus;
import com.ethendev.wopihost.service.WopiHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;

/**
 * WOPI HOST mian controller
 *
 * @author ethendev
 * @date 2017/4/15
 */
@RestController
@RequestMapping(value = "/wopi")
public class WopiHostController {

    private WopiHostService wopiHostService;

    @Autowired
    public WopiHostController(WopiHostService wopiHostService) {
        this.wopiHostService = wopiHostService;
    }

    /**
     * search a file from the host, return a file’s binary contents
     */
    @GetMapping("/files/{name}/contents")
    public void getFile(@PathVariable String name, HttpServletRequest request, HttpServletResponse response) {
        if(checkAccessToken(request)) {
            wopiHostService.getFile(decode(name), response);
        } else {
            response.setStatus(WopiStatus.UNAUTHORIZED.value());
        }
    }

    /**
     * The postFile operation updates a file’s binary contents.
     */
    @PostMapping("/files/{name}/contents")
    public void postFile(@PathVariable(name = "name") String name, @RequestBody byte[] content,
                         HttpServletRequest request, HttpServletResponse response) {
        if(checkAccessToken(request)) {
            wopiHostService.postFile(decode(name), content, request);
        } else {
            response.setStatus(WopiStatus.UNAUTHORIZED.value());
        }
    }

    /**
     * returns information about a file, a user’s permissions on that file,
     * and general information about the capabilities that the WOPI host has on the file.
     */
    @GetMapping("/files/{name}")
    public ResponseEntity<FileInfo> checkFileInfo(@PathVariable(name = "name") String name, HttpServletRequest request) throws Exception {
        if(checkAccessToken(request)) {
            return wopiHostService.getFileInfo(decode(name));
        } else {
            return ResponseEntity.status(WopiStatus.UNAUTHORIZED.value()).build();
        }
    }

    /**
     * Handling lock related operations
     */
    @PostMapping("/files/{name}")
    public ResponseEntity handleLock(@PathVariable(name = "name") String name, HttpServletRequest request) {
        if(checkAccessToken(request)) {
            return wopiHostService.handleLock(decode(name), request);
        } else {
            return ResponseEntity.status(WopiStatus.UNAUTHORIZED.value()).build();
        }
    }

    //文件名base64解码
    private String decode(String filename) {
        if(filename.indexOf(".") != -1) {
            return filename;
        } else {
            try {
                String s = new String(Base64.getDecoder().decode(filename));
                s = URLDecoder.decode(s, "UTF-8");
                return s;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    //校验access_token
    private boolean checkAccessToken(HttpServletRequest request) {
        String accessToken = request.getParameter("access_token");

        if(accessToken != null && accessToken.equals("123")) {
            return true;
        } else {
            return false;
        }
    }

}