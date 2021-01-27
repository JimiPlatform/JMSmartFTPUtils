//
//  JMFTPClient.h
//  JMSmartFTPUtils
//
//  Created by Jason on 2020/7/27.
//  Copyright © 2020 Jimi. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "JMFTPFileInfo.h"
#import "JMFTPUserInfo.h"

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSInteger, JMFTPClientState) {
    JMFTPClientStateUnkonwError = -1 ,  //未知错误
    JMFTPClientStateSuccess = 0,        //成功
    JMFTPClientStateMalformat = 3,      //网址的格式不正确
    JMFTPClientStateCoulantConnect = 7, //无法连接到主机
    JMFTPClientStateWeirdServerReply = 8,       //远程服务器不可用
    JMFTPClientStateRemoteAccessDenied = 9,     //被拒绝访问的资源的URL
    JMFTPClientStateWeirdPassReply = 11,        //FTP密码错误
    JMFTPClientStateAccessTimeout = 12,         //连接超时
    JMFTPClientStateQuoteError = 21,            //表示不成功的完成命令
    JMFTPClientStateUploadFailed = 25,          //上传失败
    JMFTPClientStateReadError = 26              //读取本地文件错误
};

@protocol JMFTPClientDelegate;

@interface JMFTPClient : NSObject

@property(nonatomic,strong) JMFTPUserInfo *userInfo;
@property(nonatomic,weak) id<JMFTPClientDelegate> delegate;
@property(nonatomic,copy) NSString *tag;

- (instancetype)initWithUserInfo:(nonnull JMFTPUserInfo *)userInfo;

/// 下载文件
/// @param remotePath 需下载的远程文件的路径
/// @param localDir 本地文件夹
/// @param localFileName 本地文件
- (void)downloadFileWithRemotePath:(nonnull NSString *)remotePath localDir:(nonnull NSString *)localDir localFileName:(nonnull NSString *)localFileName;

/// 上传文件
/// @param remotePath 需到远程的文件路径
/// @param loaclPath 本地文件路径
- (void)uploadFileWithRemotePath:(nonnull NSString *)remotePath loaclPath:(nonnull NSString *)loaclPath;

/// 创建目录
/// @param dirPath 远程文件夹路径
/// @param completion 结果回调
- (void)createDirWithPath:(nonnull NSString *)dirPath completion:(void(^)(BOOL isSuccess))completion;

/// 判断是否存在文件/文件夹
/// @param filePath 文件路径
/// @param completion 结果回调
- (void)existFileWithPath:(nonnull NSString *)filePath completion:(void(^)(BOOL isSuccess))completion;

/// 移动或重命名文件
/// @param fromPath 原文件地址
/// @param toPath 移动后地址
/// @param completion 结果回调
- (void)moveFileFromPath:(nonnull NSString *)fromPath toPath:(nonnull NSString *)toPath completion:(void(^)(BOOL isSuccess))completion;

/// 删除文件
/// @param filePath 文件路径
/// @param completion 结果回调
- (void)deleteFileWithPath:(nonnull NSString *)filePath completion:(void(^)(BOOL isSuccess))completion;

/// 查询指定目录下所有文件和目录（异步）
/// @param dirPath 远程文件夹目录
/// @param completion 回调
- (void)findFliesWithDirPath:(nonnull NSString *)dirPath completion:(nullable void(^)(NSArray<JMFTPFileInfo *> *fileInfoList))completion;

/// 查询指定目录下所有文件和目录（同步）
/// @param dirPath 文远程文件夹目录
/// @param completion 回调
- (void)findFliesSyncWithDirPath:(nonnull NSString *)dirPath completion:(nullable void(^)( NSArray<JMFTPFileInfo *> *fileInfoList))completion;

/// 暂停
- (void)pause;

/// 回复上传或下载
- (void)resume;

/// 取消上传或下载
- (void)cancel;

@end

@protocol JMFTPClientDelegate <NSObject>

/// 上传下载进度回调
/// @param progress 进度
- (void)didFtpClient:(JMFTPClient *)client progress:(NSProgress *)progress;

/// 上传下载状态回调
/// @param state 0为成功，不为零则是错误回调
- (void)didFtpClient:(JMFTPClient *)client state:(JMFTPClientState)state;

@end

NS_ASSUME_NONNULL_END
