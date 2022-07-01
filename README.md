# bmcplugin

#获取

插件包：https://github.com/gh-ca/bmcplugin/releases/tag/v20.02.01
用户手册： https://github.com/gh-ca/bmcplugin/releases/tag/User_Guide

# 编译

项目涉及到bmc TrueSight Capacity Optimization官方提供的开发工具中未开源的三方jar：

 1. eal-common-core-20.02.00.jar
 2. eal-common-util-20.02.00.jar
 3. eal-scheduler-api-10.5.3.jar
 4. eal-scheduler-core-10.5.3.jar
 5. etl-eda-java-20.02.00-20200202.173825-157.jar

因此需要将上述三方包放到项目目录：
    
    source/src/Huawei-ETL-for-BMC-TrueSight-CO/build/lib中(若不存在/build/lib请创建目录)。

# jar获取方式
  
  
    # 前置条件：已安装bmc TrueSight Capacity Optimization环境（版本：20.02）

    1.获取官方提供的开发工具：Integration Studio ，获取方式可参考BMC官方文档

      1）https://docs.bmc.com/docs/capacityoptimization/btco2002/installing-and-configuring-integration-studio-914179514.html
      
      2）进入页面：Developing --> Integration Studio --> Installing and configuring Integration Studio 

    2.安装完成Integration Studio后，去安装目录下：
    
          program\.metadata\.plugins\com.bmc.cpit.eds\framework\capetllib即可找到上述jar包。

    3.将jar包放入项目目录：
    

          source/src/Huawei-ETL-for-BMC-TrueSight-CO/build/lib中(若不存在/build/lib请创建目录)，编译即可。

    
    注：bmc TrueSight Capacity Optimization版本不同，获取到的Integration Studio工具版本不同，对应的jar版本不同。
  

