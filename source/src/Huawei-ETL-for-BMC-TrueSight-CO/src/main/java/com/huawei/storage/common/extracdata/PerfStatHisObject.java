package com.huawei.storage.common.extracdata;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


/**
 * 移植v1的代码
 * 
 * @author  l90005176
 * @date     2012-9-4
 * @version  V100R001C00
 * @see      [相关类/方法]
 */
public class PerfStatHisObject implements Serializable , Comparable
{
    private static final long serialVersionUID = 1L;

    private Integer objectType;
    
    private String resId;
    
    private List<Integer> dataTypes;
    
    private long startTime;
    
    private int period;
    
    private List<int[]> data = new LinkedList<int[]>();
    
    public Integer getObjectType()
    {
        return objectType;
    }
    
    public void setObjectType(Integer objectType)
    {
        this.objectType = objectType;
    }
    
    public String getResId()
    {
        return resId;
    }
    
    public void setResId(String resId)
    {
        this.resId = resId;
    }
    
    public List<Integer> getDataTypes()
    {
        return dataTypes;
    }
    
    public void setDataTypes(List<Integer> dataTypes)
    {
        this.dataTypes = dataTypes;
    }
    
    public List<int[]> getData()
    {
        return data;
    }
    
    public void setData(List<int[]> data)
    {
        this.data = data;
    }
    
    public int getPeriod()
    {
        return period;
    }
    
    public void setPeriod(int period)
    {
        this.period = period;
    }
    
    public long getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }
    private void writeObject(ObjectOutputStream stream) throws IOException 
    {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
    }

    public int compareTo(Object o) {
        return ((PerfStatHisObject)o).getStartTime() - startTime >0? 1 : -1;
    }

    @Override
    public String toString() {
        return "PerfStatHisObject{" +
                "objectType=" + objectType +
                ", resId='" + resId + '\'' +
                ", dataTypes=" + dataTypes +
                ", startTime=" + startTime +
                ", period=" + period +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PerfStatHisObject){
            return resId.equals(((PerfStatHisObject)obj).getResId());
        }
        return  false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 20*Integer.parseInt(resId);
    }
}
