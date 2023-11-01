#include "com_edu_fersko_smartcalc_models_CreditModelJNIWrapper.h"
#include "core/creditCalculator/creditModel.h"

static model::creditModel credit;

JNIEXPORT void JNICALL Java_com_edu_fersko_smartcalc_models_CreditModelJNIWrapper_annuity
(JNIEnv* env, jobject /* this */, jdouble summa, jdouble period, jdouble rate) {
    try {
        credit.annuity(summa, period, rate);
    } catch (const std::exception& e) {
        jclass calculationExceptionClass = env->FindClass("com/edu/fersko/smartcalc/exceptions/CalculationException");
        if (calculationExceptionClass != nullptr) {
            jmethodID ctor = env->GetMethodID(calculationExceptionClass, "<init>", "(Ljava/lang/String;)V");
            if (ctor != nullptr) {
                jstring message = env->NewStringUTF(e.what());
                jobject exception = env->NewObject(calculationExceptionClass, ctor, message);
                env->Throw((jthrowable)exception);
            }
        }
    }
}

JNIEXPORT void JNICALL Java_com_edu_fersko_smartcalc_models_CreditModelJNIWrapper_deffirentated
(JNIEnv* env, jobject /* this */, jdouble summa, jdouble rate, jdouble period) {
    try {
        credit.deffirentated(summa, rate, period);
    } catch (const std::exception& e) {
        jclass calculationExceptionClass = env->FindClass("com/edu/fersko/smartcalc/exceptions/CalculationException");
        if (calculationExceptionClass != nullptr) {
            jmethodID ctor = env->GetMethodID(calculationExceptionClass, "<init>", "(Ljava/lang/String;)V");
            if (ctor != nullptr) {
                jstring message = env->NewStringUTF(e.what());
                jobject exception = env->NewObject(calculationExceptionClass, ctor, message);
                env->Throw((jthrowable)exception);
            }
        }
    }
}

JNIEXPORT jobject JNICALL Java_com_edu_fersko_smartcalc_models_CreditModelJNIWrapper_getResult(JNIEnv* env, jobject /* this */) {
    try {
        model::creditData data = credit.getResult();

        jclass creditDataClass = env->FindClass("com/edu/fersko/smartcalc/models/CreditData");
        jmethodID constructor = env->GetMethodID(creditDataClass, "<init>", "(DDDDDD)V");
        jobject result = env->NewObject(creditDataClass, constructor,
                                        data.totalPayment, data.monthlyPayment, data.overPayment,
                                        data.minMonthlyPayment, data.maxMonthlyPayment, data.payments);

        return result;
    } catch (const std::exception& e) {
        jclass calculationExceptionClass = env->FindClass("com/edu/fersko/smartcalc/exceptions/CalculationException");
        if (calculationExceptionClass != nullptr) {
            jmethodID ctor = env->GetMethodID(calculationExceptionClass, "<init>", "(Ljava/lang/String;)V");
            if (ctor != nullptr) {
                jstring message = env->NewStringUTF(e.what());
                jobject exception = env->NewObject(calculationExceptionClass, ctor, message);
                env->Throw((jthrowable)exception);
            }
        }
        return nullptr;
    }
}

