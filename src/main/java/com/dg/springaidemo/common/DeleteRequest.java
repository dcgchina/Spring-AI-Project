package com.dg.springaidemo.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteRequest implements Serializable {

    /**
     * id(待删除id)
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
