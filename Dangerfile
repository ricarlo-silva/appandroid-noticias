
Dir["**/reports/detekt/detekt-checkstyle.xml"].each do |file_name|
  kotlin_detekt.severity = "warning"
  # kotlin_detekt.gradle_task = "detektCheck"
  kotlin_detekt.skip_gradle_task = true
  kotlin_detekt.report_file = file_name
  kotlin_detekt.detekt(inline_mode: true)
end


Dir["**/reports/ktlint/ktlint-checkstyle.xml"].each do |file_name|
  # checkstyle_format.gradle_task = "ktlint"
  # checkstyle_format.skip_gradle_task = true
  checkstyle_format.base_path = file_name
  checkstyle_format.report(file_name, inline_mode = true)
end


Dir["**/reports/*.xml"].each do |file_name|
  # android_lint.gradle_task = "lintFreeDebug"
  android_lint.skip_gradle_task = true
  android_lint.severity = "Error"
  android_lint.filtering = true
  android_lint.report_file = file_name
  android_lint.lint(inline_mode: true)
end


Dir["**/test-results/**/*.xml"].each do |file_name|
  junit.parse file_name
  junit.show_skipped_tests = true
  junit.report
end


# jacoco.minimum_project_coverage_percentage = 5
# jacoco.minimum_package_coverage_map = {
#   'com/noticiasnow/' => 5,
#   'br/com/ricarlo/network/' => 50
# }
# jacoco.minimum_class_coverage_map = {
#   'com/noticias_now/login/LoginViewModel' => 15
# }
# jacoco.minimum_class_coverage_percentage = 75
Dir["**/reports/jacoco/**/*.xml"].each do |file_name|
  unless file_name.start_with?("ui")
    jacoco.report(file_name, fail_no_coverage_data_found: false)
  end
end
# jacoco.report(Dir.glob("**/reports/jacoco/**/*.xml"), fail_no_coverage_data_found: false)
