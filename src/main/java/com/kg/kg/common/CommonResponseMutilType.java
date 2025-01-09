package com.kg.kg.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseMutilType<T1, T2> {
    private int code;
    private String msg;
    private T1 data1;
    private T2 data2;

    private CommonResponseMutilType() {
    }

    private CommonResponseMutilType(int status) {
        this.code = status;
    }

    private CommonResponseMutilType(int status, String msg) {
        this.code = status;
        this.msg = msg;
    }

    private CommonResponseMutilType(int status, String msg, T1 data1, T2 data2) {
        this.code = status;
        this.msg = msg;
        this.data1 = data1;
        this.data2 = data2;
    }

    private CommonResponseMutilType(int status, T1 data, T2 data2) {
        this.code = status;
        this.data1 = data;
        this.data2 = data2;
    }

    public static <T1, T2> CommonResponseMutilType<T1, T2> createForSuccess() {
        return new CommonResponseMutilType<T1, T2>(ResponseCode.SUCCESS.getCode());
    }

    public static <T1, T2> CommonResponseMutilType<T1, T2> createForSuccess(T1 data1, T2 data2) {
        return new CommonResponseMutilType<T1, T2>(ResponseCode.SUCCESS.getCode(), data1, data2);
    }

    public static <T1, T2> CommonResponseMutilType<T1, T2> createForSuccessMessage(String msg) {
        return new CommonResponseMutilType<T1, T2>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T1, T2> CommonResponseMutilType<T1, T2> createForSuccess(String msg, T1 data1, T2 data2) {
        return new CommonResponseMutilType<T1, T2>(ResponseCode.SUCCESS.getCode(), msg, data1, data2);
    }

    public static <T1, T2> CommonResponseMutilType<T1, T2> createForError() {
        return new CommonResponseMutilType<T1, T2>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDescription());
    }

    public static <T1, T2> CommonResponseMutilType<T1, T2> createForError(String msg) {
        return new CommonResponseMutilType<T1, T2>(ResponseCode.ERROR.getCode(), msg);
    }

    public static <T1, T2> CommonResponseMutilType<T1, T2> createForError(int code, String msg) {
        return new CommonResponseMutilType<T1, T2>(code, msg);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }
}
