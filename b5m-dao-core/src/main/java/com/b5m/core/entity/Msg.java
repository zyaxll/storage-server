package com.b5m.core.entity;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http请求返回的标准消息，不适用于dubbo接口
 * @author lucky.liu
 * @email liuwb2010@gmail.com
 * @version Nov 26, 2015 8:48:39 PM
 */
public class Msg implements Serializable {

    private static final long serialVersionUID = -2898504227762981528L;

    private static final int DEFAULT_MAP_SIZE = 1;

    public static final String EMPTY = "";

    private static final String OK_CODE = MsgCode.OK.value();
    private static final String SERVER_ERROR_CODE = MsgCode.SERVER_INTERNAL_ERROR.value();

    /**
     * 统计数量接口返回数据的key值
     */
    public static final String DATA_COUNT_KEY = "count";

    /**
     * 是否存在接口返回数据的key值
     */
    public static final String DATA_EXISTS_KEY = "exists";

    /**
     * 新增数据对应的主键ID对应key值
     */
    public static final String DATA_CREATE_ID_KEY = "createId";

    /**
     * 返回服务端所有新增数据的key值
     */
    public static final String DATA_CREATE_COLLECTION_KEY = "newPos";

    /**
     * 返回服务端新增数据的key值
     */
    public static final String DATA_NEW_DATA_KEY = "newData";

    /**
     * 返回服务端前一版本数据的key值
     */
    public static final String DATA_OLD_DATA_KEY = "oldData";


    // 返回状态码
    private String code;

    /**
     * 请求状态描述
     */
    private String msg = EMPTY;

    // 返回信息
    private Map<String, Object> data;

    public Msg() {
    }

    public Msg(boolean ok){
        super();
        if(ok){
            this.code = OK_CODE;
        }else{
            this.code = SERVER_ERROR_CODE;
        }
    }

    public Msg(boolean ok, String msg) {
        this(ok);
        this.msg = msg;
    }

    public Msg(boolean ok, String msg, Map<String, Object> data) {
        this(ok, msg);
        this.data = data;
    }

    public Msg(String code) {
        super();
        this.code = code;
    }

    public Msg(MsgCode code) {
        super();
        this.code = code.value();
    }

    public Msg(String code, String msg) {
        this(code);
        this.msg = msg;
    }

    public Msg(MsgCode code, String msg) {
        this(code);
        this.msg = msg;
    }

    public Msg(String code, String msg, Map<String, Object> data) {
        this(code, msg);
        this.data = data;
    }

    public Msg(MsgCode code, String msg, Map<String, Object> data) {
        this(code, msg);
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = (msg == null ? EMPTY : msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public boolean assertOk(){
        return OK_CODE.equals(code);
    }

    @SuppressWarnings("unchecked")
    public <T> T data(String key) {
        if(data == null || data.isEmpty()){
            return null;
        }
        return (T)data.get(key);
    }

    public static Msg msg(boolean ok){
        return new Msg(ok);
    }

    public static Msg msg(boolean ok, String msg){
        return new Msg(ok, msg);
    }

    public static Msg msg(MsgCode code){
        return new Msg(code);
    }

    public static Msg msg(String code, String msg){
        return new Msg(code, msg);
    }

    public static Msg msg(MsgCode code, String msg){
        return new Msg(code, msg);
    }

    public static Msg ok(){
        return msg(true);
    }

    public static Msg ok(String msg){
        return msg(true, msg);
    }

    public static Msg ok(String msg, Map<String, Object> data){
        return msg(true, msg).data(data);
    }

    public static Msg failed(){
        return msg(false);
    }

    public static Msg failed(MsgCode code){
        return msg(code);
    }

    public static Msg failed(String code, String msg){
        return msg(code, msg);
    }

    public static Msg failed(MsgCode code, String msg){
        return msg(code, msg);
    }

    public static Msg failed(String msg){
        return msg(false, msg);
    }

    public static Msg failed(String msg, Map<String, Object> data){
        return msg(false, msg).data(data);
    }

    public static Msg failed(Errors error){
        Msg msg = Msg.failed(MsgCode.CLIENT_PARAMETER_ERROR);
        if (error.hasErrors()) {
            List<ObjectError> errors = error.getAllErrors();
            List<String> el = new ArrayList<>(errors.size());
            for (ObjectError e : errors) {
                el.add(e.getDefaultMessage());
            }
            msg.info("error", el);
        }
        return msg;
    }

    public static boolean isOk(Msg msg){
        return null != msg && msg.assertOk();
    }

    public static boolean isNotOk(Msg msg){
        return !isOk(msg);
    }

    public static boolean isOkAndNullOk(Msg msg){
        return null == msg || msg.assertOk();
    }

    public static boolean isOk(String code){
        return OK_CODE.equals(code);
    }

    public static boolean isOk(MsgCode code){
        return code != null && OK_CODE.equals(code.value());
    }

    public Msg code(String code){
        this.code = code;
        return this;
    }

    public Msg code(MsgCode code){
        this.code = code.value();
        return this;
    }

    public Msg msg(String msg){
        setMsg(msg);
        return this;
    }

    public Msg data(Map<String, Object> data){
        this.data = data;
        return this;
    }

    public Msg createIdInfo(Object createId){
        return info(DATA_CREATE_ID_KEY, createId);
    }

    public Msg count(int count){
        return info(DATA_COUNT_KEY, count);
    }

    public Msg exists(boolean exists){
        return info(DATA_EXISTS_KEY, exists);
    }

    public Msg info(String key, Object value){
        if(data == null){
            data = new HashMap<>(DEFAULT_MAP_SIZE);
        }
        data.put(key, value);
        return this;
    }

    public Msg updateInfo(Object oldData, Object newData){
        if(data == null){
            data = new HashMap<>(DEFAULT_MAP_SIZE);
        }
        data.put(DATA_OLD_DATA_KEY, oldData);
        data.put(DATA_NEW_DATA_KEY, newData);
        return this;
    }

    public Msg updateNewInfo(Object newData){
        return info(DATA_NEW_DATA_KEY, newData);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Msg [code=").append(code)
                .append(", msg=").append(msg)
                .append(", data=").append(data)
                .append("]");
        return sb.toString();
    }

}
