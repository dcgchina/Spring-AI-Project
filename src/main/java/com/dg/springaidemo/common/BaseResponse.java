package com.dg.springaidemo.common;

import com.dg.springaidemo.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


@Data
@Schema(description = "通用 API 响应封装")
public class BaseResponse<T> implements Serializable {

    @Schema(description = "状态码，如 200 表示成功", example = "200")
    private int code;

    @Schema(description = "响应消息，如 '操作成功'", example = "操作成功")
    private T data;
    @Schema(description = "响应数据")
    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}

