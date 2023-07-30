#include "SmartCalcWrapper.h"
#include "smartCalc.h"

static model::SmartCalc smartCalcInstance;

JNIEXPORT jdouble JNICALL Java_com_example_SmartCalcWrapper_RPN(JNIEnv *env, jobject obj, jstring jstr, jdouble jx) {
    const char* str = env->GetStringUTFChars(jstr, nullptr);
    std::string cppStr(str);
    env->ReleaseStringUTFChars(jstr, str);

    double result = smartCalcInstance.RPN(cppStr, static_cast<model::SmartCalc::data>(jx));
    return static_cast<jdouble>(result);
}

JNIEXPORT void JNICALL Java_com_example_SmartCalcWrapper_clean(JNIEnv *env, jobject obj) {
    smartCalcInstance.clean();
}
