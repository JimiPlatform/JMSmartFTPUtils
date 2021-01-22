//
//  JMFTPFileInfo.h
//  JMSmartFTPUtils
//
//  Created by Jason on 2020/7/28.
//  Copyright © 2020 Jimi. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface JMFTPFileInfo : NSObject

/// 完整路径
@property(nonatomic) NSString *filePath;
/// 文件目录
@property(nonatomic) NSString *fileDir;
/// 文件名称
@property(nonatomic) NSString *fileName;
/// 修改时间
@property(nonatomic) NSString *changeTime;
/// 是否为文件夹
@property(nonatomic, assign) BOOL isDir;
/// 文件大小
@property(nonatomic, assign) NSInteger fileSize;

@end

NS_ASSUME_NONNULL_END
