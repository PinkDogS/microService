package com.ailen.core.service;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 数据库操作配置
 *
 * @author jimin
 * @since 2023-02-10 09:41:32
 */
@Data
@Accessors(chain = true)
@ToString
public class CurdConfig {
    String idDbField = "id";
    
    String idField = "id";
    
    String parentIdDbField = "parent_id";
    
    String parentIdField = "parentId";
    
    String createdByDbField = "created_by";
    
    String createdByField = "createdBy";
    
    String createdTimeDbField = "created_time";
    
    String createdTimeField = "createdTime";
    
    String updatedByDbField = "updated_by";
    
    String updatedByField = "updatedBy";
    
    String updatedTimeDbField = "updated_time";
    
    String updatedTimeField = "updatedTime";
    
    String hasChildrenDbField = "has_children";
    
    String hasChildrenField = "hasChildren";
    
    String isDeletedDbField = "is_deleted";
    
    String isDeletedField = "isDeleted";
    
    boolean deleteLogical = true;
    
    public static CurdConfig create() {
        return new CurdConfig();
    }
}
