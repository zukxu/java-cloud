package com.zukxu.tree;

import cn.hutool.core.util.ObjectUtil;
import com.zukxu.tree.annotations.TreeId;
import com.zukxu.tree.annotations.TreePid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 重载树工具类
 *
 * @author zukxu
 * @since 2022/1/2 19:05:36
 */
public class ConvertTree<T> {

    /**
     * 形成森林数据结构
     *
     * @param dataList
     * @param idName
     * @param pidName
     *
     * @return
     */
    public List<TreeNode<T>> getForest(List<T> dataList, String idName, String pidName) {
        List<TreeNode<T>> forest = new ArrayList<>();
        while(!dataList.isEmpty()) {
            TreeNode<T> tree = getTree(dataList, idName, pidName);
            forest.add(tree);
        }
        return forest;
    }

    /**
     * 形成森林(使用注解)
     *
     * @param dataList
     */
    public List<TreeNode<T>> getForest(List<T> dataList) {
        //通过注解获取idName和pidName
        String idName = null;
        String pidName = null;
        if(!dataList.isEmpty()) {
            //得到class
            Class<?> cls = dataList.get(0).getClass();
            //得到所有属性
            Field[] fields = cls.getDeclaredFields();
            for(Field field : fields) {
                TreeId treeId = field.getAnnotation(TreeId.class);
                if(treeId != null) {
                    idName = field.getName();
                }
                TreePid treeFid = field.getAnnotation(TreePid.class);
                if(treeFid != null) {
                    pidName = field.getName();
                }
            }
        }

        return getForest(dataList, idName, pidName);
    }

    /**
     * 生成树结构
     *
     * @param dataList
     * @param idName
     * @param pidName
     *
     * @return
     */
    public TreeNode<T> getTree(List<T> dataList, String idName, String pidName) {
        //获取树根
        TreeNode<T> rootNode = getRootNode(dataList, idName, pidName);
        //    遍历树节点
        List<TreeNode<T>> childrenNodeList = rootNode.getChildrenNode();
        forChildren(dataList, idName, pidName, childrenNodeList);
        //    返回树
        return rootNode;
    }

    /**
     * 递归遍历子节点
     *
     * @param dataList
     * @param idName
     * @param pidName
     * @param childrenNodeList
     */
    private void forChildren(List<T> dataList, String idName, String pidName, List<TreeNode<T>> childrenNodeList) {
        //遍历集合
        List<TreeNode<T>> needForList = new ArrayList<>();
        for(TreeNode<T> tTreeNode : childrenNodeList) {
            List<TreeNode<T>> treeNodes = tTreeNode.childrenNode(dataList, idName, pidName);
            needForList.addAll(treeNodes);
        }
        if(!needForList.isEmpty()) {
            forChildren(dataList, idName, pidName, needForList);
        }
    }

    /**
     * 获取根节点
     *
     * @param dataList
     * @param idName
     * @param pidName
     *
     * @return
     */
    public TreeNode<T> getRootNode(List<T> dataList, String idName, String pidName) {
        if(dataList.isEmpty()) {
            return null;
        }
        T node = dataList.get(0);
        T rootNode = getRootNode(dataList, idName, pidName, node);
        TreeNode<T> rootTreeNode = new TreeNode<>();
        dataList.remove(rootNode);
        rootTreeNode.setRootNode(rootNode);
        rootTreeNode.childrenNode(dataList, idName, pidName);
        return rootTreeNode;
    }

    /**
     * 递归遍历根节点
     *
     * @param dataList
     * @param idName
     * @param pidName
     * @param node
     *
     * @return
     */
    private T getRootNode(List<T> dataList, String idName, String pidName, T node) {
        T fNode = null;
        String fieldValue = getFieldValue(node, pidName);
        for(T data : dataList) {
            if(getFieldValue(data, idName).equals(fieldValue)) {
                fNode = data;
                break;
            }
        }
        if(ObjectUtil.isEmpty(fNode)) {
            return node;
        } else {
            return getRootNode(dataList, idName, pidName, fNode);
        }

    }

    /**
     * 根据反射获取字段值
     *
     * @param obj
     * @param fieldName
     *
     * @return
     */
    public String getFieldValue(T obj, String fieldName) {
        Class<?> cls = obj.getClass();
        //获取所有属性
        Field[] fields = cls.getFields();
        for(Field field : fields) {
            try {
                //打开私有访问，允许访问私有变量
                field.setAccessible(true);
                //获取属性
                if(field.getName().equals(fieldName)) {
                    Object res = field.get(obj);
                    if(ObjectUtil.isEmpty(res)) {
                        return null;
                    }
                    return res.toString();
                }
            } catch(IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("获取属性值错误");
    }

}
