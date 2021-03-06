package com.huawei.storage.domain;

public class Tier {
    private int raidLV;
    private int diskNum;
    private long capacity;

    public Tier(String raidLV, String diskNum, String tierCapacity) {
        this.raidLV = Integer.parseInt(raidLV);
        this.diskNum = Integer.parseInt(diskNum);
        this.capacity = Long.parseLong(tierCapacity);
    }

    public int getRaidLV() {
        return raidLV;
    }

    public void setRaidLV(int raidLV) {
        this.raidLV = raidLV;
    }

    public int getDiskNum() {
        return diskNum;
    }

    public void setDiskNum(int diskNum) {
        this.diskNum = diskNum;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }
}
