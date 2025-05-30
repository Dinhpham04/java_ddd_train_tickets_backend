package com.xxxx.ddd.controller.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * VO tương tác giữa frontend và backend
 */
@Data
public class ResultMessage<T> implements Serializable {
    // generic T để làm việc với mọi kiểu dữ liệu chưa biết trước
    private static final long serialVersionUID = 1L;
    // static có nghĩa là thuộc tính thuộc về lớp, không thuộc về instance
    /**
     * Cờ thành công
     */
    private boolean success;

    /**
     * Thông báo
     */
    private String message;

    /**
     * Mã trả về
     */
    private Integer code;

    /**
     * Dấu thời gian
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * Đối tượng kết quả
     */
    private T result;
}

/**
 * Tạo một lớp generic để chứa kết quả trả về từ API
 * Có thể serialization/deserialization để truyền dữ liệu
 */
