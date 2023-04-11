package com.ailen.mapstruct;

import java.util.List;

/**
 *
 * Mapper文件基类
 * 更多的用法需自行实现
 * @param <D> 目标对象，一般为DTO对象
 * @param <P> 源对象，一般为需要转换的对象
 */
public interface EntityConverter<D, P> {

    /**
     * 将源对象转换为DTO对象
     * @param p
     * @return D
     */
    D po2dto(P p);

    /**
     * 将源对象集合转换为DTO对象集合
     * @param ps
     * @return List<D>
     */
    List<D> po2dto(List<P> ps);


    /**
     * 将目标对象转换为源对象
     * @param d
     * @return E
     */
    P dto2po(D d);

    /**
     * 将目标对象集合转换为源对象集合
     * @param ds
     * @return List<E>
     */
    List<P> dto2po(List<D> ds);

}
