package com.dg.springaidemo.advisor.service;

import com.dg.springaidemo.advisor.pojo.CheckPrompt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CheckPromptService {
    @Select("SELECT * PERMISSIONS")
    List<CheckPrompt> findSelectAll();
}
