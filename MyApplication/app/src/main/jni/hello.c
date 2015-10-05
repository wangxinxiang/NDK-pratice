#include<jni.h>
#include <string.h>
#include <android/log.h>
#include <unistd.h>

#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

/**
 * 显示对话框
 */
void showDialog(JNIEnv *env, jobject obj, char *msg) {
    jclass jclazz = (*env)->FindClass(env, "com/example/administrator/myapplication/MainActivity");
    if (jclazz == 0) {
        LOGI("没有找到类 \n");
    } else {
        jmethodID jmethod = (*env)->GetMethodID(env, jclazz, "showDialog", "(Ljava/lang/String;)V");
        if (jmethod == 0) {
            LOGI("没有找到方法 \n");
        } else {
            (*env)->CallVoidMethod(env, obj, jmethod, (*env)->NewStringUTF(env, msg));
        }
    }
}

/**
 * 关闭对话框
 */
void dimissDialog(JNIEnv *env, jobject obj) {
    jclass jclazz = (*env)->FindClass(env, "com/example/administrator/myapplication/MainActivity");
    if (jclazz == 0) {
        LOGI("没有找到类 \n");
    } else {
        jmethodID jmethod = (*env)->GetMethodID(env, jclazz, "dismissDialog", "()V");
        if (jmethod == 0) {
            LOGI("没有找到方法 \n");
        } else {
            (*env)->CallVoidMethod(env, obj, jmethod);
        }
    }
}


JNIEXPORT jstring JNICALL Java_com_example_administrator_myapplication_MainActivity_getStringFromNative
        (JNIEnv *env, jobject obj) {
    char *str = "Hello From JNI!";
    showDialog(env,obj, "C里调用dialog");
    sleep(2);
    dimissDialog(env, obj);
    return (*env)->NewStringUTF(env, str);
}

