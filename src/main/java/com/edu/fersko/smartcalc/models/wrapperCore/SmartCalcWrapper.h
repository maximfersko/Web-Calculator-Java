#ifndef SMART_CALC_WRAPPER_H_
#define SMART_CALC_WRAPPER_H_

#include <jni.h>
#include "smartCalc.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_com_example_SmartCalcWrapper_clean(JNIEnv *env, jobject obj);
JNIEXPORT jdouble JNICALL Java_com_example_SmartCalcWrapper_RPN(JNIEnv *env, jobject obj, jstring jstr, jdouble jx);


#ifdef __cplusplus
}
#endif

#endif // SMART_CALC_WRAPPER_H_