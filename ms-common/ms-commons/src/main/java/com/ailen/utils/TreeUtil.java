package com.ailen.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.anshare.base.common.exception.AppException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 树构建器
 *
 * @author jimin
 * @since 2023-02-08 15:53:55
 */
public class TreeUtil {
    
    /**
     * 递归遍历，按 JSONObject 形式处理对象
     * @author jimin
     */
    public static void recursiveHandle(List<JSONObject> list, TreeConfig config, Consumer<JSONObject> fn) {
        list.forEach(o -> {
            fn.accept(o);
            if (o.containsKey(config.getChildrenKey())
                    && o.get(config.getChildrenKey()) instanceof List
                    && CollectionUtil.isNotEmpty((List<JSONObject>) o.get(config.getChildrenKey()))) {
                recursiveHandle((List<JSONObject>) o.get(config.getChildrenKey()), config, fn);
            }
        });
    }
    
    public static List<JSONObject> toTree(List list, Object root) {
        return toTree(list, JSONObject.parseObject(JsonUtil.toJsonWithNull(root)), new TreeConfig());
    }
    
    public static List<JSONObject> toTree(List list, JSONObject root, TreeConfig config) {
        List<JSONObject> tree = new ArrayList<>();
        Set<Serializable> handledKey = new HashSet<>();
        
        List<JSONObject> jsonObjectList = (ArrayList)list.stream()
                .map(i -> JSONObject.parseObject(JsonUtil.toJsonWithNull(i)))
                .collect(Collectors.toList());
        Set<Serializable> keySet = jsonObjectList
                .stream()
                .map(i -> (Serializable)i.get(config.getIdKey()))
                .collect(Collectors.toSet());
        
        if (root == null) {
            // 查询所有
            jsonObjectList.forEach(i -> {
                Object vPid = i.get(config.getParentIdKey());
                // 查找第1级，条件：vPid对应记录不存在
                if (!keySet.contains(vPid)) {
                    handledKey.add((Serializable)i.get(config.getIdKey()));
                    tree.add(i);
                }
            });
            // 查找下一级
            tree.forEach(p -> fetchChildren(jsonObjectList, handledKey, p, config));
        } else {
            // 指定父级
            handledKey.add((Serializable)root.get(config.getIdKey()));
            tree.add(root);
            fetchChildren(jsonObjectList, handledKey, root, config);
        }
        
        return tree;
    }
    
    /**
     * ID 属性校验，保留未用
     * @author jimin
     */
    private static void checkIdKey(JSONObject o, TreeConfig config) {
        if (!o.containsKey(config.getIdKey())) {
            throw new AppException("缺少属性[" + config.getIdKey() + "]");
        }
    }
    
    /**
     * PID 属性校验，保留未用
     * @author jimin
     */
    private static void checkParentIdKey(JSONObject o, TreeConfig config) {
        if (!o.containsKey(config.getParentIdKey())) {
            throw new AppException("缺少属性[" + config.getParentIdKey() + "]");
        }
    }
    
    private static void fetchChildren(List<JSONObject> list, Set<Serializable> handledKey, JSONObject parent, TreeConfig config) {
        List<JSONObject> children = new ArrayList<>();
        list.forEach(i -> {
            if (!handledKey.contains((Serializable)i.get(config.getIdKey())) && i.get(config.getParentIdKey()).equals(parent.get(config.getIdKey()))) {
                handledKey.add((Serializable)i.get(config.getIdKey()));
                children.add(i);
                fetchChildren(list, handledKey, i, config);
            }
        });
        parent.put(config.getChildrenKey(), children);
    }
    
    public static void main(String[] args) {
        List<Node> list = new ArrayList<>();
        list.add(new Node("0", null, "root"));
        list.add(new Node("1", "0", "parent1"));
        list.add(new Node("2", "0", "parent2"));
        list.add(new Node("3", "1", "son1"));
        list.add(new Node("4", "2", "son2"));
        list.add(new Node("5", null, "root2"));
    
        List<JSONObject> reList = TreeUtil.toTree(list, null);
    
        System.out.println(JsonUtil.toJsonWithNull(reList));
    
        recursiveHandle(reList, new TreeConfig(), o -> {
            System.out.println(o.get("name"));
        });
    }
}

@Data
@AllArgsConstructor
class Node {
    String id;
    String parentId;
    String name;
    
}