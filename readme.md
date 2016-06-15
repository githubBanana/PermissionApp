android 6.0（api23或+）运行时权限申请测试

1.targetSdkVersion < 23 :
    在清单文件注册 安装时权限会全部获取到  但运行于android6.0系统上可能不适配
2.targetSdkVersion >=23 :
    在清单文件注册 + 在运行时申请权限