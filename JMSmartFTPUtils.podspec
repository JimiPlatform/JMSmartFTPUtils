Pod::Spec.new do |s|
  s.name = "JMSmartFTPUtils"
  s.version = "1.0.5"
  s.summary = "Provide FTP for Jimi iOS Platform."
  s.license = {"type"=>"Apache License 2.0", "file"=>"LICENSE"}
  s.authors = {"Eafy"=>"lizhijian_21@163.com"}
  s.homepage = "https://github.com/JimiPlatform/JMSmartFTPUtils"
  s.description = "Jimi meida Player SDK for iOS at CocoaPods."
  s.libraries = ["c++"]
  s.requires_arc = true
  s.source = { :git => "https://github.com/JimiPlatform/JMSmartFTPUtils.git", :tag => "v#{s.version}" }
  s.xcconfig = { 'VALID_ARCHS' => 'armv7 armv7s arm64 arm64e x86_64' }
  s.static_framework = true

  s.ios.deployment_target    = '9.0'
  s.ios.vendored_framework   = 'JMSmartFTPUtils.framework'
  s.vendored_libraries = 'JMSmartFTPUtils.framework/**/*.a'

end

#发布命令
#pod trunk push JMSmartFTPUtils.podspec --verbose --allow-warnings --use-libraries
