#include <jni.h>
#include "core/smartCalculator/smartCalc.h"
#include "com_edu_fersko_smartcalc_models_RPN.h"

// Define smartCalcInstance as a global variable
static model::SmartCalc smartCalcInstance;

JNIEXPORT jdouble JNICALL Java_com_edu_fersko_smartcalc_models_RPN_getResult(JNIEnv *env, jobject obj, jstring jstr, jdouble jx) {
    const char* str = env->GetStringUTFChars(jstr, nullptr);
    std::string cppStr(str);
    env->ReleaseStringUTFChars(jstr, str);

    double result = smartCalcInstance.RPN(cppStr, static_cast<model::SmartCalc::data>(jx));
    return static_cast<jdouble>(result);
}

JNIEXPORT void JNICALL Java_com_edu_fersko_smartcalc_models_RPN_clean(JNIEnv *env, jobject obj) {
    smartCalcInstance.clean();
}

JNIEXPORT jobjectArray JNICALL Java_com_edu_fersko_smartcalc_models_RPN_graphBuilder
(JNIEnv *env, jobject obj, jdoubleArray jData, jstring jstr) {
    jsize length = env->GetArrayLength(jData);
    jdouble* data = env->GetDoubleArrayElements(jData, nullptr);
    std::vector<double> Data(data, data + length);
    env->ReleaseDoubleArrayElements(jData, data, JNI_ABORT);

    const char* str = env->GetStringUTFChars(jstr, nullptr);
    std::string cppStr(str);
    env->ReleaseStringUTFChars(jstr, str);

    std::pair<std::vector<double>, std::vector<double>> graphPoints = smartCalcInstance.graphBuilder(Data, cppStr);

    int pointsSize = graphPoints.first.size();
    jdoubleArray jX = env->NewDoubleArray(pointsSize);
    jdoubleArray jY = env->NewDoubleArray(pointsSize);
    env->SetDoubleArrayRegion(jX, 0, pointsSize, graphPoints.first.data());
    env->SetDoubleArrayRegion(jY, 0, pointsSize, graphPoints.second.data());

    jobjectArray resultArray = env->NewObjectArray(2, env->GetObjectClass(jX), nullptr);
    env->SetObjectArrayElement(resultArray, 0, jX);
    env->SetObjectArrayElement(resultArray, 1, jY);

    return resultArray;
}

