package com.test.okhttp;

/**
 * Created by acer1 on 2016/6/30.
 */
import java.io.Serializable;
import java.util.List;

public class Root implements Serializable{
    private String msg;

    private List<Result> result ;

    private String retCode;

    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setResult(List<Result> result){
        this.result = result;
    }
    public List<Result> getResult(){
        return this.result;
    }
    public void setRetCode(String retCode){
        this.retCode = retCode;
    }
    public String getRetCode(){
        return this.retCode;
    }
}