#include <jni.h>
#include "core/smartCalculator/smartCalc.h"
#include "com_edu_fersko_smartcalc_models_RPN.h"

// Define smartCalcInstance as a global variable
static model::SmartCalc smartCalcInstance;

JNIEXPORT jdouble JNICALL Java_com_edu_fersko_smartcalc_models_RPN_getResult(JNIEnv *env, jobject obj, jstring jstr, jdouble jx) {
    const char* str = env->GetStringUTFChars(jstr, nullptr);
    std::string cppStr(str);
    env->ReleaseStringUTFChars(jstr, str);

    // Use the global smartCalcInstance here
    double result = smartCalcInstance.RPN(cppStr, static_cast<model::SmartCalc::data>(jx));
    return static_cast<jdouble>(result);
}

JNIEXPORT void JNICALL Java_com_edu_fersko_smartcalc_models_RPN_clean(JNIEnv *env, jobject obj) {
    // Use the global smartCalcInstance here
    smartCalcInstance.clean();
}

JNIEXPORT jobjectArray JNICALL Java_com_edu_fersko_smartcalc_models_RPN_graphBuilder
(JNIEnv *env, jobject obj, jdoubleArray jData, jstring jstr) {
    // Convert jdoubleArray to std::vector<double>
    jsize length = env->GetArrayLength(jData);
    jdouble* data = env->GetDoubleArrayElements(jData, nullptr);
    std::vector<double> Data(data, data + length);
    env->ReleaseDoubleArrayElements(jData, data, JNI_ABORT);

    // Convert jstring to std::string
    const char* str = env->GetStringUTFChars(jstr, nullptr);
    std::string cppStr(str);
    env->ReleaseStringUTFChars(jstr, str);

    // Use the global smartCalcInstance to calculate the graph points
    std::pair<std::vector<double>, std::vector<double>> graphPoints = smartCalcInstance.graphBuilder(Data, cppStr);

    // Convert the results back to jdoubleArray
    int pointsSize = graphPoints.first.size();
    jdoubleArray jX = env->NewDoubleArray(pointsSize);
    jdoubleArray jY = env->NewDoubleArray(pointsSize);
    env->SetDoubleArrayRegion(jX, 0, pointsSize, graphPoints.first.data());
    env->SetDoubleArrayRegion(jY, 0, pointsSize, graphPoints.second.data());

    // Create a jobjectArray to hold both jdoubleArrays
    jobjectArray resultArray = env->NewObjectArray(2, env->GetObjectClass(jX), nullptr);
    env->SetObjectArrayElement(resultArray, 0, jX);
    env->SetObjectArrayElement(resultArray, 1, jY);

    return resultArray;
}

