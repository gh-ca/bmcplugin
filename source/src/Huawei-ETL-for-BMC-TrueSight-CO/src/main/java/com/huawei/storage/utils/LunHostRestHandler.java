package com.huawei.storage.utils;

import com.huawei.storage.domain.PreQuery;
import com.huawei.storage.domain.StorageObject;
import com.huawei.storage.exception.ETLException;
import com.huawei.storage.oceanstor.rest.operation.DeviceManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LunHostRestHandler {
     private final Logger logger = Logger.getLogger(LunHostRestHandler.class);


    public List<StorageObject> getMappedLunHost(String countCommand, String queryCommand, PreQuery preQuery, DeviceManager deviceManager) throws ETLException {
        List<StorageObject> hostLUNs = new ArrayList<StorageObject>();
        List<StorageObject> mappingViews = getAllMappingView(deviceManager);
        for (StorageObject mappingView : mappingViews) {
            List<StorageObject> mappedLuns = getAllLunByMappingviewID(mappingView.getId(), deviceManager);
            List<StorageObject> hosts = getAllHostByMappingviewID(mappingView.getId(), deviceManager);
            createHostLunObjects(hostLUNs, mappedLuns, hosts);
        }
        return hostLUNs;
    }

    private void createHostLunObjects(List<StorageObject> hostLUNs, List<StorageObject> mappedLuns, List<StorageObject> hosts) {
        //one lun mapped to multi host
        for (StorageObject lun : mappedLuns) {
            for (StorageObject host : hosts) {
                StorageObject hostLun = new StorageObject();
                hostLun.setId(lun.getId());
                hostLun.setRestData(lun.getRestData());
                StorageObject linkedHost = new StorageObject();
                linkedHost.setId(host.getId());
                linkedHost.setRestData(host.getRestData());
                linkedHost.setName(host.getRestData().get("NAME"));
                hostLun.setLinkedObject(linkedHost);
                hostLUNs.add(hostLun);
            }
        }
    }


    private List<StorageObject> getAllMappingView(DeviceManager deviceManager) throws ETLException {
        return RestPageUtils.pageGetAll(deviceManager, "get-mappingview-count", "batch-find-mappingview", new HashMap<String, String>());
    }


    private List<StorageObject> getAllLunByMappingviewID(String id, DeviceManager deviceManager) throws ETLException {
        Map<String, String> operatrionData = new HashMap<String, String>();
        operatrionData.put("ASSOCIATEOBJTYPE", "245");
        operatrionData.put("ASSOCIATEOBJID", id);
        return RestPageUtils.pageGetAll(deviceManager, "get-associate-lun-count", "get-associate-lun",operatrionData);
    }

    private List<StorageObject> getAllHostByMappingviewID(String id, DeviceManager deviceManager) throws ETLException {
        List<StorageObject> hostGroups = getAllHostGroupByMappingViewID(id, deviceManager);
        List<StorageObject> hosts = new ArrayList<StorageObject>();
        for (StorageObject hostGroup : hostGroups) {
            hosts.addAll(getAllHostByHostgroupID(hostGroup.getId(), deviceManager));
        }
        return hosts;
    }

    private List<StorageObject> getAllHostGroupByMappingViewID(String id, DeviceManager deviceManager) throws ETLException {
        Map<String, String> operatrionData = new HashMap<String, String>();
        operatrionData.put("ASSOCIATEOBJTYPE", "245");
        operatrionData.put("ASSOCIATEOBJID", id);
        return RestPageUtils.pageGetAll(deviceManager, "get-associate-hostgroup-count", "get-associate-hostgroup",operatrionData);
    }


    private List<StorageObject> getAllHostByHostgroupID(String id, DeviceManager deviceManager) throws ETLException {
        Map<String, String> operatrionData = new HashMap<String, String>();
        operatrionData.put("ASSOCIATEOBJTYPE", "14");
        operatrionData.put("ASSOCIATEOBJID", id);
        return RestPageUtils.pageGetAll(deviceManager, "get-associate-host-count", "get-associate-host",operatrionData);
    }



}
