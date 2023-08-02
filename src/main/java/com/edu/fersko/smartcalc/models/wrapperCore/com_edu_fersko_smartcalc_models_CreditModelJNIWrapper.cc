#include "com_edu_fersko_smartcalc_models_CreditModelJNIWrapper.h"
#include "core/creditCalculator/creditModel.h"

static model::creditModel credit;

JNIEXPORT void JNICALL Java_com_edu_fersko_smartcalc_models_CreditModelJNIWrapper_annuity
(JNIEnv* env, jobject /* this */, jdouble summa, jdouble period, jdouble rate) {
    credit.annuity(summa, period, rate);
}

JNIEXPORT void JNICALL Java_com_edu_fersko_smartcalc_models_CreditModelJNIWrapper_deffirentated
(JNIEnv* env, jobject /* this */, jdouble summa, jdouble rate, jdouble period) {
    credit.deffirentated(summa, rate, period);
}

JNIEXPORT jobject JNICALL Java_com_edu_fersko_smartcalc_models_CreditModelJNIWrapper_getResult(JNIEnv* env, jobject /* this */) {
    model::creditData data = credit.getResult();

    jclass creditDataClass = env->FindClass("com/edu/fersko/smartcalc/models/CreditData");
    jmethodID constructor = env->GetMethodID(creditDataClass, "<init>", "(DDDDDD)V");
    jobject result = env->NewObject(creditDataClass, constructor,
                                    data.totalPayment, data.monthlyPayment, data.overPayment,
                                    data.minMonthlyPayment, data.maxMonthlyPayment, data.payments);

    return result;
}

