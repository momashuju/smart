package com.example.smartApplication.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.smartApplication.po.HomeFormPO;
import com.example.smartApplication.utils.JsonReader;
import com.example.smartApplication.vo.Response;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import com.example.smartApplication.utils.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


@Service
public class HomeService {

    public Response init(){
        String root_path=System.getProperty("user.dir");
        String target_path=root_path.concat("/src/main/config/smart_forecast.json");
        System.out.println(target_path);
        Object s= JsonReader.ReadFile(target_path);
        return Response.buildSuccess(s);
    }

    public Response run(HomeFormPO homeFormPO){

        String name=homeFormPO.getName();
        List<String> params=homeFormPO.getParams();
        String target=homeFormPO.getTarget();
        System.out.println(params);
        String root_path=System.getProperty("user.dir").concat("/src/main");
        String target_path=root_path.concat(String.format(target,params.get(0)));
        String temp_path=target_path.concat(".txt");
        System.out.println("save res in".concat(target_path));

        String temp_command= String.format("touch %s", temp_path);//创建用于判断已经run的txt文件

        String script_path=root_path.concat("/script");
        String run_command=String.format("cd %s && sh %s ",script_path,name);
        for(int i=0;i<params.size();i++){
            run_command.concat(params.get(i)).concat(" ");
        }
        System.out.println(run_command);
        return Response.buildSuccess();
    }

    public Response get_result(HomeFormPO homeFormPO){
        String name=homeFormPO.getName();
        String target=homeFormPO.getTarget();
        List<String> params=homeFormPO.getParams();

        String root_path=System.getProperty("user.dir").concat("/src/main");
        String target_path=root_path.concat(String.format(target,params.get(0)));
        String temp_path=target_path.concat(".txt");
        System.out.println("find res in".concat(target_path));

        File target_file=new File(target_path);
        File temp_file=new File(temp_path);
        if(target_file.exists()){
            try{
                //System.out.println("?");
                BASE64Encoder base64Encoder=new BASE64Encoder();
                String str=encodeBase64File(target_path);
                String res="data:image/png;base64,".concat(str);
                return Response.buildSuccess(res);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else if(temp_file.exists()){
            return Response.buildSuccess("正在计算结果，请等待片刻后重试");
        }else{
            return Response.buildFailure("请先点击确认");
        }

        return Response.buildSuccess();
    }

    /**
     * <p>将文件转成base64 字符串</p>
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }

}
