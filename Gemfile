source "https://rubygems.org"

gem "fastlane"
gem 'danger', '~> 8.2'
gem "danger-kotlin_detekt"
gem "danger-checkstyle_format"
gem 'danger-android_lint'
gem 'danger-junit'
gem 'danger-jacoco',

plugins_path = File.join(File.dirname(__FILE__), 'fastlane', 'Pluginfile')
eval_gemfile(plugins_path) if File.exist?(plugins_path)
