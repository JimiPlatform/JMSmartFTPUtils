//
//  JMFTPUserInfo.h
//  JMSmartFTPUtils
//
//  Created by Jason on 2020/7/30.
//  Copyright © 2020 Jimi. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface JMFTPUserInfo : NSObject

/// 域名或ip地址
@property(nonatomic, copy) NSString *domain;
/// 端口
@property(nonatomic, assign) int port;
/// 账户名
@property(nonatomic, copy) NSString *account;
/// 账户密码
@property(nonatomic, copy) NSString *password;

- (instancetype)initWithDomain:(nonnull NSString *)domain port:(int)port account:(nonnull NSString *)account password:(nonnull NSString *)password;

@end

NS_ASSUME_NONNULL_END
