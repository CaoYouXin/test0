/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileorganizationi;

/**
 *
 * @author caolisheng
 */
public interface IFileOrganization {
    
    /**
     * 查询方式本应该是多种多样的，而且查询结果也可能是一个记录的集合
     * @param key
     * @return 
     */
    public IRecord search(IKey key);
    
    public boolean insert(IRecord record);
    
    public boolean modify(IRecord record);
    
    public boolean delete(IKey key);
    
}
