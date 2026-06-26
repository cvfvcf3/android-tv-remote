-keep class com.example.tvremote.** { *; }
-keep class com.example.tvremote.service.** { *; }
-keep class com.example.tvremote.handler.** { *; }
-keep class org.json.** { *; }

-dontwarn java.lang.invoke.*
-dontwarn java.lang.reflect.*
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-dontwarn sun.misc.**

-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

-verbose
